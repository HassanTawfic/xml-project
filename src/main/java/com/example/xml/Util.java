package com.example.xml;

import java.io.IOException;

public class Util {
    public static void stringToDom(String xmlSource)
            throws IOException {
        String finalString = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + xmlSource;
        java.io.FileWriter fw = new java.io.FileWriter("src/main/resources/users.xml");
        fw.write(finalString);
        fw.close();
    }
}
