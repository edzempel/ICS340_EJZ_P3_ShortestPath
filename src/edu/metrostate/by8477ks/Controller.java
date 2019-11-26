package edu.metrostate.by8477ks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
                getSortAndWriteFile();
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
            }
            view.ta1.append("\n--------------------------------");
//            view.scrollPane.getVerticalScrollBar().setValue(view.scrollPane.getVerticalScrollBar().getMaximum());
            view.ta1.setCaretPosition(view.ta1.getDocument().getLength());
        }
    }

    /**
     * Prompts user for file, merge sorts the integer  contents in descending order
     * and writes the sorted data to a new file in the same directory
     *
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws WrongFileTypeException
     */
    private void getSortAndWriteFile() throws FileNotFoundException, UnsupportedEncodingException, WrongFileTypeException {
        //JFileChooser only allows text files
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt", "text");
        chooser.setFileFilter(filter);


        // Prompt user for file with JFileChooser
        int returnVal = chooser.showOpenDialog(view.frame.getParent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileToSort = chooser.getSelectedFile();
            if (!filter.accept(fileToSort)) {
                throw new WrongFileTypeException("Wrong file type. Choose a .txt file.");
            }
            String[] fullName = separateNameAndExtension(fileToSort);
            String fileName = fullName[0];
            String extension = fullName[1];
            String fullPath = fileToSort.getPath();


            // isolate directory from filename
            String directory = fullPath.substring(0, fullPath.length() - (fileName + extension).length());

            //update view
            view.tf1.setText("Input file: " + fileName + extension);

            // Read file into memory
            ArrayList<Integer> lineItems = readFileIntoMemory(fileToSort); // O(n)

            // Convert ArrayList to int[] O(n)
            int[] intArray = new int[lineItems.size()];
            for (int i = 0; i <lineItems.size(); i++) {
                intArray[i] = lineItems.get(i);
            }

            // Sort array in place O(n lg n)
//            Model.mergesort(intArray, 0, intArray.length);

            // Give the user feedback after successfully writing the sorted file
            view.ta1.append("\n" + writeArrayToFile(directory, fileName + "_sorted" + extension, intArray)); // O(n)

        }
    }

    /**
     * Reads file into memory as an ArrayList O(n)
     *
     * @param userFile File
     * @return ArrayList Integer
     * @throws FileNotFoundException
     */
    public static ArrayList<Integer> readFileIntoMemory(File userFile) throws FileNotFoundException, NumberFormatException {
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
     * Write array of integers to the file in the specified directory. O(n)
     *
     * @param directory String path to file
     * @param filename  String name of file
     * @param array     int[] data to be written to the file line by line
     * @throws UnsupportedEncodingException
     */
    public static String writeArrayToFile(String directory, String filename, int[] array) throws UnsupportedEncodingException, FileNotFoundException {
        PrintWriter writer = new PrintWriter(directory + filename, "UTF-8");
        for (int i = 0; i < array.length; i++) {
            writer.println(array[i]);
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
