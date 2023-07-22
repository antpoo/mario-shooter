package com.example.IOutil;

import java.io.*;

public class FReader {

    // fields
    private BufferedReader br;
    private String file;

    // constructor
    public FReader(String file) throws IOException {
        br = new BufferedReader(new FileReader(file));
        this.file = file;
    }

    /**
     * Reads a line in the input stream and moves to the next line
     * @return the next line in the file
     * @throws IOException
     */
    public String readLine() throws IOException {
        return br.readLine();
    }

    /**
     * Find a user in the database
     * @param username the username to be found 
     * @return the position of the user in the database, -1 if not found 
     * @throws IOException
     */
    public int findUser(String username) throws IOException {
        br = new BufferedReader(new FileReader(file));
        String S = "";
        // simple iteration of the file until the username is found 
        for (int i=0; (S = readLine()) != null; i++) 
            if ((S.split(", "))[0].equals(username))
                return i;

        return -1;
    }

 
    /**
     * Gets the password of a specified user.
     * @param username The username of the user whose password is being requested
     * @return The hashed password, -1 if not found 
     * @throws IOException
     */
    public long getPassword(String username) throws IOException {
        br = new BufferedReader(new FileReader(file));
        String S = "";
        // simple iteration through the file until the username is found
        while ((S = readLine()) != null) {
            String[] up = S.split(", ");
            if (up[0].equals(username)) {
                return Long.parseLong(up[1]);
            }
        }
        return -1;
    }

    /**
     * Gets the password hint of a given user.
     * @param username The username of the user whose password hint is being requested
     * @return The hint of the password, empty string if user not found 
     * @throws IOException
     */
    public String getHint(String username) throws IOException {
        br = new BufferedReader(new FileReader(file));
        String S = "";
        // simple iteration through the file until the username is found
        while ((S = readLine()) != null) {
            String[] up = S.split(", ");
            if (up[0].equals(username)) {
                return up[2];
            }
        }
        return "";
    }

    
}