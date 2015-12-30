package com.insart.finder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static final int THREAD_NUMBER = 5;

    public static void main (String ... strings) throws InterruptedException {

        String rootDir = ".";

        List<File> files = MyFileWalker.findFilesInTheDir(new File(rootDir), ".xml");
        System.out.println(files);

        for (int i = 0; i < THREAD_NUMBER; i++) {
            Thread searchThread = new Thread(new SearchInFile(files));
            searchThread.start();
        }

        MyFileWalker.findFilesInTheDir(new File(rootDir), ".xml");

        Thread.currentThread().sleep(2000);
        System.out.println(SearchInFile.result);

        System.out.println("main finish");


    }
}
