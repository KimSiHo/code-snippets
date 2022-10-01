package me.bigmonkey;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SampleData {

    public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter("array.txt");

        for (int i = 0; i < 150000; i++) {
            Random random = new Random();
            int nextInt = random.nextInt(1000000);
            fileWriter.write(nextInt + "\n");
        }

        fileWriter.close();
    }
}
