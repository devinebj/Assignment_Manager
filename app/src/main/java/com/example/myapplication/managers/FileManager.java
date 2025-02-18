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

    /**
     * Saves the given AppSettings to a JSON file
     *
     * @param context       The context is used to access the file system
     * @param appSettings   The AppSettings object to be saved
     */

    public static void saveToFile(Context context, AppSettings appSettings){
        Gson gson = new Gson();
        String json = gson.toJson(appSettings);

        File file = getSettingsFile(context);
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(json);
            Log.d(TAG, "Settings successfully saved.");
        } catch (IOException e){
            Log.e(TAG, "Error saving settings: " + e.getMessage());
        }
    }

    /**
     * Loads the settings from a JSON file
     *
     * @param context The context used to access the file system
     * @return The loaded AppSettings, or a new default instance if the file does not exist or an error occurs
     */
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

    /**
     * Returns the File object for the settings file
     *
     * @param context The context used to access the file directory
     * @return The File representing the settings file
     */
    private static File getSettingsFile(Context context){
        return new File(context.getFilesDir(), FILE_NAME);
    }
}
