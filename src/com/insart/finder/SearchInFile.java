package com.insart.finder;

import java.io.*;
import java.util.*;

/**
 * Created by shokotko on 12/22/15.
 */
public class SearchInFile implements Runnable {

    List<File> files;
    static List<String> result = new ArrayList<>();

    public SearchInFile(List<File> files) {
        this.files = files;
    }

    @Override
    public void run() {
        synchronized (files) {
            while (true) {
                if (files.size() > 0) {
                    File file = files.get(0);
                    files.remove(0);
                    Set<File> set = new HashSet<>();
                    set.add(file);

                    searchForPattern(file);

                } else {
//                    Thread.currentThread().yield();
                    try {
                        files.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    private void searchForPattern(File file) {
        System.out.println("search in thread " + Thread.currentThread().getName() + " " + Thread.currentThread() + " in file: " + file.getName());

        BufferedReader br = null;

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(file));
//
//            while ((sCurrentLine = br.readLine()) != null) {
//                if (sCurrentLine.contains("<")) {
//                    result.add(sCurrentLine);
//                }
//            }

            InputStream is = new FileInputStream(file);

            int symbol = is.read();
            StringBuilder sb = new StringBuilder();
            while (symbol != -1) {
                if (symbol == 13) {
                    if (sb.toString().contains("xml")) {
                        result.add(sb.toString());
                    }
                    sb = new StringBuilder();
                } else {
                    sb.append(Character.toChars(symbol));
                }
                symbol = is.read();
            }
            System.out.println("done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

//        try {
//            Thread.currentThread().sleep(1000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
