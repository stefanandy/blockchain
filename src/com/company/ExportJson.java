package com.company;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ExportJson implements Export {

    private String jsonFile;
    private Gson gson;
    private FileOutputStream fileOutputStream;
    private OutputStreamWriter outputWriter;
    private String blockData;

    public ExportJson(String jsonFile) throws FileNotFoundException {
        this.jsonFile=jsonFile;
        gson=new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        fileOutputStream=new FileOutputStream(jsonFile);
        outputWriter=new OutputStreamWriter(fileOutputStream);
    }

    @Override
    public void writeBlock(Block block) throws IOException {
            outputWriter.append(convertToString(block));
    }

    private String convertToString(Block block){
        return gson.toJson(block);
    }

    @Override
    public void closeFile(){
        try {
            outputWriter.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
