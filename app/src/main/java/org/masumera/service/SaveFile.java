package org.masumera.service;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    public void sampleFile(String responseQuery) throws IOException {

        FileWriter writer = new FileWriter("test.txt");
        writer.write(responseQuery);
        writer.close();
        
    }

}
