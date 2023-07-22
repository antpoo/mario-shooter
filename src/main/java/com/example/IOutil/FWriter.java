package com.example.IOutil;

import java.io.*;

public class FWriter {

    // fields
    private FileWriter fw;
    private final int BASE = 31, MOD = 1000000007;
    private String file;

    // constructor
    public FWriter(String file) {
        this.file = file;
        try {
            fw = new FileWriter(file, true);
        }
        catch (IOException e) {
            System.out.println("Could not find file " + file);
        }
    }


    /**
     * Sets the FileWriter to append mode
     * @throws IOException
     */
    public void setAppend() throws IOException {
        fw = new FileWriter(file, true);
    }

    /**
     * Sets the FileWriter to overwrite mode
     * @throws IOException
     */
    public void setOverwrite() throws IOException {
        fw = new FileWriter(file, false);
    }

    /**
     * Writes to the file and adds a new line 
     * @param message String to write to file
     * @throws IOException
     */
    public void writeLn(String message) throws IOException {
        fw.write(message + '\n');
        fw.flush();
    }
    
    /**
     * Polynomial Rolling Hash for passwords
     * @param password Password to hash
     * @return the hashed password
     */
    public long polyHash(String password) {
        long hash = 0;

        // essentially BASE is the "x" of the polynomial and each character is a coefficient
        for (int i=0; i<password.length(); i++) {
            hash *= BASE;
            hash %= MOD;
            hash += password.charAt(i);
            hash %= MOD;
        }

        return hash;
    }

    /**
     * Hashes the password and writes it to the file.
     * @param username Username
     * @param password Password
     * @param hint Password hint
     * @throws IOException
     */
    public void hashAndWrite(String username, String password, String hint) throws IOException {
        writeLn(username + ", " + polyHash(password) + ", " + hint);
    }

}