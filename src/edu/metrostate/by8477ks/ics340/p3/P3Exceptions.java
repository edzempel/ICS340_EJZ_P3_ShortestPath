package edu.metrostate.by8477ks.ics340.p3;

/**
 * Shared exception classes
 */
public class P3Exceptions {

    /**
     * A generic exception to give contextual feedback to the user
     */
    static class ImproperHeaderFileException extends Exception {
        public ImproperHeaderFileException(String s) {
            super(s);
        }
    }
}
