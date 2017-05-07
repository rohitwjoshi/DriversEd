package com.example.khochberg.driversed;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrivingLogFragment extends Fragment {

    private ListView logListView;
    protected static ArrayList<DriveLog> driveLogItems;
    protected static DriveLogAdapter aa;
    private Context context;
    private View rootView;

    public DrivingLogFragment() {
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
        rootView =  inflater.inflate(R.layout.fragment_driving_log, container, false);

        logListView = (ListView) rootView.findViewById(R.id.log_list_view);

        logListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = logListView.getItemAtPosition(position);
                DatabaseHandler db = new DatabaseHandler(getActivity());
                ArrayList<Lesson> lessons = db.getAllLessons();
                Collections.reverse(lessons);
                Lesson lesson = lessons.get(position);
                //System.out.println(lesson.getID());
                EditLogFragment editLogFragment = new EditLogFragment(lesson);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, editLogFragment)
                        .addToBackStack(null)
                        .commit();
                getActivity().setTitle("Edit Log");

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        driveLogItems = populateData();

        aa = new DriveLogAdapter(getActivity(), R.layout.drive_log_item, driveLogItems);
        logListView.setAdapter(aa);

        getActivity().setTitle(R.string.log);
    }

    public ArrayList<DriveLog> populateData() {
        DatabaseHandler db = new DatabaseHandler(getActivity());
        ArrayList<Lesson> lessons = db.getAllLessons();
        ArrayList<DriveLog> driveLog = new ArrayList<DriveLog>();
        for (Lesson ln : lessons) {
            DriveLog temp = new DriveLog(ln.getID(), Float.parseFloat(ln.getHours()), ln.getDate(), ln.getTimeOfDay(), ln.getLessonType(), ln.getWeather());
            driveLog.add(temp);
            //System.out.println(ln.getLessonType());
        }
        ArrayList<DriveLog> driveLogReverse = new ArrayList<DriveLog>();
        for (int i = driveLog.size() - 1; i >= 0; i--) {
            driveLogReverse.add(driveLog.get(i));
        }
        return driveLogReverse;
    }

}
