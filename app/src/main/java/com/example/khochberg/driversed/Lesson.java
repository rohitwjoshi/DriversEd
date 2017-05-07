package com.example.khochberg.driversed;

/**
 * Created by Rohit on 4/14/17.
 */

public class Lesson {

    //private variables
    int _id;
    String _date;
    String _hours;
    String _timeOfDay;
    String _lessonType;
    String _weather;

    // Empty constructor
    public Lesson(){

    }
    // constructor
    public Lesson(int id, String date, String hours, String timeOfDay, String lessonType, String weather){
        this._id = id;
        this._date = date;
        this._hours = hours;
        this._timeOfDay = timeOfDay;
        this._lessonType = lessonType;
        this._weather = weather;
    }

    // constructor
    public Lesson(String date, String hours, String timeOfDay, String lessonType, String weather){
        this._date = date;
        this._hours = hours;
        this._timeOfDay = timeOfDay;
        this._lessonType = lessonType;
        this._weather = weather;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting date
    public String getDate(){
        return this._date;
    }

    // setting date
    public void setDate(String date){
        this._date = date;
    }

    // getting hours
    public String getHours(){
        return this._hours;
    }

    public void setHours(String hours){
        this._hours = hours;
    }

    public String getTimeOfDay(){
        return this._timeOfDay;
    }
    public void setTimeOfDay(String timeOfDay){
        this._timeOfDay = timeOfDay;
    }

    public String getLessonType(){
        return this._lessonType;
    }
    public void setLessonType(String lessonType){
        this._lessonType = lessonType;
    }

    public String getWeather(){
        return this._weather;
    }
    public void setWeather(String weather){
        this._weather = weather;
    }
}
