package bftsmart.demo.v2vapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LedgerWriter {

    String id;
    String fileName;
    BufferedWriter writer;

    public LedgerWriter(int globalID) throws IOException {
        this.id = String.valueOf(globalID);
        fileName = "ledger" + id + ".txt";
        File f1 = new File(fileName);
        if(f1.exists()) {
            f1.delete();
        }
        writer = new BufferedWriter(new FileWriter(fileName));


    }
    public void write(String output) throws IOException {
        writer.write(output.hashCode());
    }
}
