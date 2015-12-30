package com.insart.finder;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MyFileWalker {

    private static List<File> files = new LinkedList<>();

    public static List<File> findFilesInTheDir(File directoryOrFile, String extension) {

        if (directoryOrFile.isFile()) {
            if (directoryOrFile.getName().endsWith(extension)) {
                addToFiles(directoryOrFile);
            }
        } else {
            for (File child: directoryOrFile.listFiles()) {
                findFilesInTheDir(child, extension);
            }
        }

        return files;
    }

    private static void addToFiles(File directoryOrFile) {

        synchronized (files) {
            files.add(directoryOrFile);
            files.notifyAll();
        }
    }

}
