package com.example.myapplication.managers;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.models.AppSettings;
import com.example.myapplication.models.Assignment;
import com.example.myapplication.models.Course;
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

    public static <T> void saveToFile(Context context, String fileName, AppSettings appSettings){
        Gson gson = new Gson();
        String json = gson.toJson(appSettings);

        try(FileWriter writer = new FileWriter(new File(context.getFilesDir(), fileName))) {
            writer.write(json);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static AppSettings loadFromFile(Context context, String fileName, Type typeOfT){
        File file = new File(context.getFilesDir(), fileName);

        if(!file.exists()) {
            Log.d(TAG,"File " + fileName + "does not exist, returning empty list.");
            return new AppSettings();
        }

        try(FileReader reader = new FileReader(file)){
            AppSettings data = new Gson().fromJson(reader, typeOfT);
            return data != null ? data : new AppSettings();
        } catch (IOException e){
            e.printStackTrace();
            return new AppSettings();
        }
    }
}
