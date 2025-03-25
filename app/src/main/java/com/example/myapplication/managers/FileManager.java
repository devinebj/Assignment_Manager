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
    private static final String FILE_NAME = "app_settings.json";
    private static final Gson gson = new Gson();

    public static void saveToFile(Context context, AppSettings appSettings){
        String json = gson.toJson(appSettings);
        File file = getSettingsFile(context);

        try(FileWriter writer = new FileWriter(file)) {
            writer.write(json);
            Log.d(TAG, "Settings successfully saved.");
        } catch (IOException e){
            Log.e(TAG, "Error saving settings: " + e.getMessage());
        }
    }

    public static AppSettings loadFromFile(Context context){
        File file = new File(context.getFilesDir(), FILE_NAME);

        if(!file.exists()) {
            Log.d(TAG,"File does not exist, returning default settings.");
            return new AppSettings();
        }

        try(FileReader reader = new FileReader(file)){
            AppSettings data = new Gson().fromJson(reader, AppSettings.class);
            return data != null ? data : new AppSettings();
        } catch (IOException e){
            Log.e(TAG, "Error loading settings: " + e.getMessage());
            return new AppSettings();
        }
    }

    private static File getSettingsFile(Context context){
        return new File(context.getFilesDir(), FILE_NAME);
    }
}
