package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

    private static Logger instance = new Logger();
    private PrintWriter writer;

    private Logger() {
    }

    public static Logger getInstance() {
        return instance;
    }

    public void setLogFile(String filename) {
        try {
            // creates a new PrintWriter that writes to the filename
            writer = new PrintWriter(new FileWriter(filename,true));
        } catch (IOException e) {
            throw new RuntimeException("Error in log file" + filename);
        }
    }

    public void log(String text) {
        if (writer != null) {
            //writes a log message to the file
            writer.println(text);
            //flushes the buffer
            writer.flush();
        } else {
            System.out.println("Writer is null.");
        }
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}