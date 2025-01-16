package com.example.myapplication.managers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String TAG = "FileManager";

    public static <T> void saveToFile(Context context, String fileName, List<T> data){
        Gson gson = new Gson();
        String json = gson.toJson(data);

        try(FileWriter writer = new FileWriter(new File(context.getFilesDir(), fileName))) {
            writer.write(json);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static <T> List<T> loadFromFile(Context context, String fileName, Type typeOfT){
        File file = new File(context.getFilesDir(), fileName);

        if(!file.exists()) {
            Log.d(TAG,"File " + fileName + "does not exist, returning empty list.");
            return new ArrayList<>();
        }

        try(FileReader reader = new FileReader(file)){
            List<T> data = new Gson().fromJson(reader, typeOfT);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
