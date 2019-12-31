package com.duplessis.etienne.patchmaker.service;

import org.springframework.core.io.InputStreamResource;

import java.io.*;

//*
// V1.00.00    2019-12-27     E Du Plessis Initial Version
// */

public class PatchService {

    private static final String EXTENSION = ".sql";

    //Write a file
    public  void writeToFile(String fileName, String filePath){

        try {

            fileName = fileName + EXTENSION;

            PrintWriter outputStream = new PrintWriter(fileName);
            outputStream.println("Hi There file!");
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
