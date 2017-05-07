package com.example.khochberg.driversed;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticsFragment extends Fragment {

    private View rootView;
    private ProgressBar totalProgress;
    private ProgressBar dayProgress;
    private ProgressBar nightProgress;
    private ProgressBar residentialProgress;
    private ProgressBar highwayProgress;
    private ProgressBar commercialProgress;
    private ProgressBar clearProgress;
    private ProgressBar rainProgress;
    private ProgressBar snowProgress;

    private TextView totalHoursText;
    private TextView dayHoursText;
    private TextView nightHoursText;
    private TextView residentialHoursText;
    private TextView commercialHoursText;
    private TextView highwayHoursText;
    private TextView clearHoursText;
    private TextView rainyHoursText;
    private TextView snowHoursText;


    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        totalProgress = (ProgressBar) rootView.findViewById(R.id.total_progress);
        dayProgress = (ProgressBar) rootView.findViewById(R.id.day_progress);
        nightProgress = (ProgressBar) rootView.findViewById(R.id.night_progress);
        residentialProgress = (ProgressBar) rootView.findViewById(R.id.residential_progress);
        highwayProgress = (ProgressBar) rootView.findViewById(R.id.highway_progress);
        commercialProgress = (ProgressBar) rootView.findViewById(R.id.commercial_progress);
        clearProgress = (ProgressBar) rootView.findViewById(R.id.clear_progress);
        rainProgress = (ProgressBar) rootView.findViewById(R.id.rain_progress);
        snowProgress = (ProgressBar) rootView.findViewById(R.id.snow_progress);

        totalHoursText = (TextView) rootView.findViewById(R.id.total_hours);
        dayHoursText = (TextView) rootView.findViewById(R.id.day_hours);
        nightHoursText = (TextView) rootView.findViewById(R.id.night_hours);
        residentialHoursText = (TextView) rootView.findViewById(R.id.residential_hours);
        commercialHoursText = (TextView) rootView.findViewById(R.id.commercial_hours);
        highwayHoursText = (TextView) rootView.findViewById(R.id.highway_hours);
        clearHoursText = (TextView) rootView.findViewById(R.id.clear_hours);
        rainyHoursText = (TextView) rootView.findViewById(R.id.rain_hours);
        snowHoursText = (TextView) rootView.findViewById(R.id.snow_hours);

        //String string = getArguments().getString("totalHoursSettings");


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Populate progress:
        populateHours();
    }



    public void populateHours() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        float totalHoursSettings = preferences.getFloat("totalHours", 10);
        float dayHoursSettings = preferences.getFloat("dayHours", 10);
        float nightHoursSettings = preferences.getFloat("nightHours", 10);
        float residentialHoursSettings = preferences.getFloat("residentialHours", 10);
        float commercialHoursSettings = preferences.getFloat("commercialHours", 10);
        float highwayHoursSettings = preferences.getFloat("highwayHours", 10);
        float clearHoursSettings = preferences.getFloat("clearHours", 10);
        float rainyHoursSettings = preferences.getFloat("rainyHours", 10);
        float snowHoursSettings = preferences.getFloat("snowHours", 10);


        DatabaseHandler db = new DatabaseHandler(getActivity());
        ArrayList<Lesson> lessons = db.getAllLessons();
        float totalHours = 0;
        float dayHours = 0;
        float nightHours = 0;
        float residentialHours = 0;
        float highwayHours = 0;
        float commercialHours = 0;
        float clearHours = 0;
        float rainyHours = 0;
        float snowHours = 0;
        for (Lesson ln : lessons) {
            totalHours += Float.parseFloat(ln.getHours());

            if(ln.getTimeOfDay().equals("day")) {
                dayHours += Float.parseFloat(ln.getHours());
            } else if (ln.getTimeOfDay().equals("night")){
                nightHours += Float.parseFloat(ln.getHours());
            }

            if(ln.getLessonType().equals("Residential")) {
                residentialHours += Float.parseFloat(ln.getHours());
            } else if (ln.getLessonType().equals("Commercial")){
                commercialHours += Float.parseFloat(ln.getHours());
            } else if (ln.getLessonType().equals("Highway")){
                highwayHours += Float.parseFloat(ln.getHours());
            }

            if(ln.getWeather().equals("Clear")) {
                clearHours += Float.parseFloat(ln.getHours());
            } else if (ln.getWeather().equals("Rainy")){
                rainyHours += Float.parseFloat(ln.getHours());
            } else if (ln.getWeather().equals("Snow/Ice")) {
                snowHours += Float.parseFloat(ln.getHours());
            }
        }

        totalHoursText.setText(totalHours + "/" + totalHoursSettings + " total hours trained");
        dayHoursText.setText("Day: " + dayHours + "/" + dayHoursSettings + " hours");
        nightHoursText.setText("Night: " + nightHours + "/" + nightHoursSettings +" hours");
        commercialHoursText.setText("Commercial: " + commercialHours + "/" + commercialHoursSettings +" hours");
        residentialHoursText.setText("Residential: " + residentialHours + "/" + residentialHoursSettings + " hours");
        highwayHoursText.setText("Highway: " + highwayHours + "/" + highwayHoursSettings + " hours");
        clearHoursText.setText("Clear: " + clearHours + "/" + clearHoursSettings + " hours");
        rainyHoursText.setText("Rainy: " + rainyHours + "/" + rainyHoursSettings + " hours");
        snowHoursText.setText("Snow/Ice: " + snowHours + "/" + snowHoursSettings + " hours");

        totalProgress.setProgress((int) (( totalHours/ totalHoursSettings)*100));
        dayProgress.setProgress((int) ((dayHours / dayHoursSettings)*100));
        nightProgress.setProgress((int) ((nightHours/ nightHoursSettings)*100));
        residentialProgress.setProgress((int) ((residentialHours / residentialHoursSettings)*100));
        highwayProgress.setProgress((int) ((highwayHours / highwayHoursSettings)*100));
        commercialProgress.setProgress((int) ((commercialHours / commercialHoursSettings)*100));
        clearProgress.setProgress((int) ((clearHours / clearHoursSettings)*100));
        rainProgress.setProgress((int) ((rainyHours / rainyHoursSettings)*100));
        snowProgress.setProgress((int) ((snowHours / snowHoursSettings)*100));

    }
}
