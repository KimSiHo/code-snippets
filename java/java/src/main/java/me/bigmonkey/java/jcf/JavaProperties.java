package me.bigmonkey.java.jcf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Properties;

public class JavaProperties {

    public static void main(String[] args) throws MalformedURLException {
        Properties properties = new Properties();
        properties.setProperty("property1", "value1");
        properties.setProperty("property2", "value2");
        properties.setProperty("property3", "value3");

        URI uri = URI.create("data/props.properties");
        Path of = Path.of(uri);

        System.out.println();
        File file = new File(URI.create("data/props.properties"));
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fileWriter = new FileWriter("data/props.properties");
            properties.store(fileWriter, "These are properties");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail to save file");
        }

    }
}
