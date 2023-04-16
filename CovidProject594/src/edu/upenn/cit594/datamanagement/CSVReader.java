package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class CSVReader {

    private HashMap<String, Integer> headerID = new HashMap<>();
    private BufferedReader br;
    private String filename;
    private CharacterReader reader;

    public CSVReader(CharacterReader reader) {
        this.reader = reader;
    }


//    public CSVReader(String filename) throws IOException {
//        this.filename = filename;
//        this.br = new BufferedReader(new FileReader(filename));
//        this.headerID = new HashMap<String, Integer>();
//    }

    public String[] readRow() throws IOException, CSVFormatException {
        // check input stream one by one;
        int c = this.reader.read();
        StringBuilder str = new StringBuilder();
        ArrayList<String> res = new ArrayList<>();
        Stack<Integer> doubleQuote = new Stack<>();
        String currentState = "Non_Escape";

        // Switch state
        while (c != -1) {
            switch (currentState) {
                case "Non_Escape":
                    switch (c) {

                        case 10: // line break LR
                            // it should be the end of the line
                            String s = str.toString();
                            res.add(s);
                            return res.toArray(new String[res.size()]);

                        case 13: // CR


                            // if there is a CR in the unescaped field, need to check if there is a LF
                            c = reader.read();
                            if (c == 10) {
                                // there is a LF
                                s = str.toString();
                                res.add(s);
                                return res.toArray(new String[res.size()]);
                            } else
                                throw new CSVFormatException();


                        case 44: // comma
                            s = str.toString();
                            res.add(s);
                            str = new StringBuilder();
                            break;

                        case 34: // Double Quote
                            if (doubleQuote.isEmpty()) {
                                doubleQuote.add(c);
                            } else {
                                doubleQuote.pop();
                            }

                            // the first double quote before anything
                            if (str.length() == 0) {
                                currentState = "Escape";
                                // add the "" to the stack
                                //doubleQuote.add(c);
                            } else {
                                throw new CSVFormatException();
                            }
                            break;

                    }
                    break;
                case "Escape":
                    switch (c) {
                        case 44: // comma
                            // in the escape state, the comma. need to add to the list
                            str.append((char) c);
                            break;

                        case 34: // Double Quote
                            // double quote need to appear in pairs
                            // at the end of the program, the stack should be empty
                            if (doubleQuote.isEmpty()) {
                                doubleQuote.add(c);
                            } else {
                                doubleQuote.pop();
                            }

                            // this will be at least the second double quote

                            // the middle double quote has to be in pairs
                            c = reader.read();
                            if (c == 34) {
                                str.append((char) c);
                                if (doubleQuote.isEmpty()) {
                                    doubleQuote.add(c);
                                } else {
                                    doubleQuote.pop();
                                }
                            }
                            // this " is the last one, and the next is ,
                            else if (c == 44) {
                                String s = str.toString();
                                res.add(s);
                                str = new StringBuilder();
                                currentState = "Non_Escape";
                            }
                            // after " this is the CR,
                            else if (c == 13) {

                                //check if the next is LF
                                c = reader.read();
                                if (c == 10) {
                                    String s = str.toString();
                                    res.add(s);
                                    return res.toArray(new String[res.size()]);
                                }
                                // if there is only 1 CR, throw an exception
                                else {
                                    throw new CSVFormatException();

                                }
                            }
                            // this " is the last one, and the next is LR or -1
                            else if (c == 10 || c == -1) {
                                String s = str.toString();
                                res.add(s);
                                return res.toArray(new String[res.size()]);
                            } else
                                throw new CSVFormatException();


                    }


            }
            // this step only add character other than "" and ,
            if (c != 44 && c != 34) {
                str.append((char) c); // cast c into the string
                //System.out.println(str);
            }


            c = reader.read(); // read the next line;
            // if the next line within while loop is -1
            if (c == -1) {
                if (!doubleQuote.isEmpty()) throw new CSVFormatException();
                if (str != null) {
                    String s = str.toString();
                    res.add(s);
                    return res.toArray(new String[res.size()]);
                }
            }


        }
        //  out of  while loop
        // for the last sentence
        if (!doubleQuote.isEmpty()) throw new CSVFormatException();

        return null;

    }

    public HashMap<String, Integer> generateHeaderID() throws IOException, CSVFormatException {
        String[] firstRow = readRow();
        for(int i = 0; i<firstRow.length;i++){
            String header = firstRow[i];
            headerID.put(header, i);

        }
        return headerID;

    }

    public int getFieldPerHeader(String header){
        int ID = this.headerID.get(header);
        return ID;

    }

    public List<String[]> readAll() throws CSVFormatException, IOException {
        List<String[]> allRows = new ArrayList<>();
        String[] row;
        while((row= readRow())!=null){
            allRows.add(row);
        }
        return allRows;

    }

    public boolean checkTimeFormat(String timestamp){

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try{
            LocalDateTime.parse(timestamp, format);
            return true;

        } catch (Exception e) {
            return false;
        }
    }





}
