package com.example.myapplication;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String FILE_NAME = "courses.json";

    public static void saveCoursesToFile(Context context, List<Course> courses){
        Gson gson = new Gson();
        String json = gson.toJson(courses);

        try(FileWriter writer = new FileWriter(new File(context.getFilesDir(), FILE_NAME))) {
            writer.write(json);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<Course> loadCoursesFromFile(Context context){
        File file = new File(context.getFilesDir(), FILE_NAME);
        if(!file.exists()) {
            return new ArrayList<>();
        }

        try(FileReader reader = new FileReader(file)){
            Type listType = new TypeToken<List<Course>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
