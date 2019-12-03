package edu.metrostate.by8477ks.ics340.p3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import static edu.metrostate.by8477ks.ics340.p3.Dijkstra.computePaths;
import static edu.metrostate.by8477ks.ics340.p3.Dijkstra.printShortestPath;
//import javax.swing.text.View;

public class Controller implements ActionListener {
    private View view;

    public Controller(View obj) {
        super();
        this.view = obj;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // button one action
        if (e.getSource() == view.b1) {

            // open and read file into memory as ArrayList
            try {
                view.ta1.append("\nProcessing...");
                processFiles();
            } catch (WrongFileTypeException wftex) {
                view.ta1.append("\n" + wftex.getMessage());
            } catch (NumberFormatException nfex) {
                view.ta1.append("\nFile has non-numbers,\nnumbers that are too large,\nor more than one per line.\n" + nfex.getMessage());
                System.out.println();
            } catch (FileNotFoundException fnfex) {
                view.ta1.append("\n!!! Unable to locate file.\n" + fnfex.getMessage());
                fnfex.printStackTrace();
            } catch (UnsupportedEncodingException useex) {
                view.ta1.append("\n!!! File encoding unsupported. Use UTF-8 file.\n" + useex.getMessage());
                useex.printStackTrace();
            } catch (P3Exceptions.ImproperHeaderFileException ex) {
                view.ta1.append("\n!!! Improper header.\n" + ex.getMessage());
            } catch (Exception ex) {
                view.ta1.append("\n!!! Generic Error.\n" + ex.getMessage() + "\nSee console.");
                ex.printStackTrace();
            }
            view.ta1.append("\n--------------------------------");
//            view.scrollPane.getVerticalScrollBar().setValue(view.scrollPane.getVerticalScrollBar().getMaximum());
            view.ta1.setCaretPosition(view.ta1.getDocument().getLength());
        }
    }

    /**
     * Prompts user for file, displays all shortest paths and writes them to a file in the same directory
     *
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws WrongFileTypeException
     */
    private void processFiles() throws FileNotFoundException, UnsupportedEncodingException, WrongFileTypeException, P3Exceptions.ImproperHeaderFileException {
        //JFileChooser only allows text files
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt", "text");
        chooser.setFileFilter(filter);


        // Prompt user for file with JFileChooser
        int returnVal = chooser.showOpenDialog(view.frame.getParent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File sourceFile = chooser.getSelectedFile();
            if (!filter.accept(sourceFile)) {
                throw new WrongFileTypeException("Wrong file type. Choose a .txt file.");
            }
            String[] fullName = separateNameAndExtension(sourceFile);
            String fileName = fullName[0];
            String extension = fullName[1];
            String fullPath = sourceFile.getPath();


            // isolate directory from filename
            String directory = fullPath.substring(0, fullPath.length() - (fileName + extension).length());

            //update view
            view.tf1.setText("Input file: " + fileName + extension);

            // Read header into memory
            TreeMap<String, Vertex> header = readHeaderLine(sourceFile); // O(n)
            // display header
            view.ta1.append("\nCities: " + header.keySet().toString());

            // Reader rest of file into memory as floating edges
            ArrayList<FloatingEdge> edges = readFileStartingAt(sourceFile, 1);
            // add floating edges to each vertex in adjacency list
            Vertex.addEdges(header, edges);

            // Create holding variable for file text
            ArrayList<String> fileOutput = new ArrayList<String>();

            ArrayList<VertexPair> allCombos = VertexPair.getAllPairs(header);
            for (VertexPair<Vertex> combo : allCombos
            ) {
                Vertex.resetVertices(header);

                Vertex origin = combo.getPair().get(VertexPair.VertexTypes.ORIGIN);
                Vertex destination = combo.getPair().get(VertexPair.VertexTypes.DESTINATION);

                computePaths(origin);
//                printShortestPath(combo.getPair().get(VertexPair.VertexTypes.ORIGIN), combo.getPair().get(VertexPair.VertexTypes.DESTINATION));

                view.ta1.append(String.format("\n%s: %.0f", combo.toString(), destination.getMinDistance()));

                fileOutput.add(String.format("%s-%s-%.0f", origin.getName(),
                        destination.getName(),
                        destination.getMinDistance()));
            }

            writeArrayToFile(directory, fileName + "_output" + extension, fileOutput);

        }
    }


    /**
     * Returns an TreeMap of the vertexes for each city separated by spaces from the first line of the file
     *
     * @param userFile where the first line is a hyphen delimited list of values
     * @return list of values from the first line of the file
     * @throws FileNotFoundException
     * @throws NumberFormatException
     */
    public static TreeMap<String, Vertex> readHeaderLine(File userFile) throws FileNotFoundException, NumberFormatException {
        TreeMap<String, Vertex> header = new TreeMap<String, Vertex>();


        try (Scanner myReader = new Scanner(userFile);
             Scanner scanHeader = new Scanner(myReader.nextLine());) {
            scanHeader.useDelimiter("-");

            while (scanHeader.hasNext()) {
                String city = scanHeader.next();
                header.put(city, new Vertex(city));
                // System.out.println(data); // view each element of the header
            }
        }

        return header;
    }

    /**
     * Reads file into memory as an ArrayList O(n)
     *
     * @param userFile File
     * @return list of each of the lines in the file
     * @throws FileNotFoundException
     */
    public static ArrayList<Integer> readFileIntoMemory(File userFile) throws
            FileNotFoundException, NumberFormatException {
        Scanner myReader = new Scanner(userFile);
        ArrayList<Integer> lineItems = new ArrayList<Integer>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            lineItems.add(Integer.parseInt(data));
            // System.out.println(data); // view each line of data

        }
        myReader.close();
        return lineItems;
    }

    /**
     * Reads file into memory as an ArrayList O(n) starting at specified line
     *
     * @param userFile
     * @param startingLine
     * @return
     * @throws FileNotFoundException
     * @throws NumberFormatException
     */
    public static ArrayList<FloatingEdge> readFileStartingAt(File userFile, int startingLine) throws
            FileNotFoundException, NumberFormatException {
        Scanner myReader = new Scanner(userFile);

        ArrayList<FloatingEdge> edges = new ArrayList<FloatingEdge>();

        int lineCount = 0;
        while (myReader.hasNextLine()) {
            if (lineCount >= startingLine) {
                Scanner currentLine = new Scanner(myReader.nextLine());
                currentLine.useDelimiter("-");

                int weight = Integer.parseInt(currentLine.next());
                String origin = currentLine.next();
                String destination = currentLine.next();

                edges.add(new FloatingEdge(origin, destination, weight));
                edges.add(new FloatingEdge(destination, origin, weight));

            } else {
                myReader.nextLine();
            }
            lineCount++;
        }
        myReader.close();
        return edges;
    }


    /**
     * Write array of strings to file line by line
     *
     * @param directory String path to file
     * @param filename  String name of file including extension
     * @param array     String data to be written to the file line by line
     * @return Success message
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public static String writeArrayToFile(String directory, String filename, ArrayList<String> array) throws
            UnsupportedEncodingException, FileNotFoundException {
        PrintWriter writer = new PrintWriter(directory + filename, "UTF-8");
        for (String entry : array) {
            writer.println(String.format("%s", entry));
        }
        writer.close();
        return String.format("Output file: %s", filename);
    }

    /**
     * Extract the file extension from the filename if it begins with a period.
     * Based on code from: https://www.journaldev.com/842/java-get-file-extension
     *
     * @param file input file
     * @return [0] i is the filename, [1] is the extension (empty string if none)
     */
    private static String[] separateNameAndExtension(File file) {
        String[] fileName = new String[2];
        fileName[0] = file.getName();
        if (fileName[0].lastIndexOf(".") != -1 && fileName[0].lastIndexOf(".") != 0) {
            fileName[1] = fileName[0].substring(fileName[0].lastIndexOf("."));
            fileName[0] = fileName[0].substring(0, fileName[0].lastIndexOf("."));

        } else fileName[1] = "";
        return fileName;
    }

    /**
     * A generic exception to give contextual feedback to the user
     */
    class WrongFileTypeException extends Exception {
        public WrongFileTypeException(String s) {
            super(s);
        }
    }

}
