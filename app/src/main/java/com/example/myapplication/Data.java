package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static Data instance = null;
    private static final List<IObserver> I_OBSERVERS = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();

    private Data() {}

    public static synchronized Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }

        return instance;
    }

    public interface IObserver {
        void onRecyclerDataChanged(List<Course> courses);
    }

    public void registerObserver(IObserver IObserver){
        I_OBSERVERS.add(IObserver);
    }

    public void unregisterObserver(IObserver IObserver){
        I_OBSERVERS.remove(IObserver);
    }

    public void notifyRecyclerDataChanged(){
        for(IObserver IObserver : I_OBSERVERS){
            IObserver.onRecyclerDataChanged(new ArrayList<>(courses));
        }
    }

    public List<Course> getCourses(){
        return courses;
    }
}
