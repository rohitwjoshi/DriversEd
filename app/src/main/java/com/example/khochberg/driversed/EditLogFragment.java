package com.example.khochberg.driversed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by khochberg on 4/3/17.
 */

public class EditLogFragment extends NewLessonFragment {

    private View rootView;
    private String date;
    private float hours;
    private String timeOfDay;
    private String lessonType;
    private String weather;
    private int id;

    public EditLogFragment() {
        // Required empty public constructor
    }

    public EditLogFragment(Lesson lesson) {
        this.date = lesson.getDate();
        this.hours = Float.parseFloat(lesson.getHours());
        this.timeOfDay = lesson.getTimeOfDay();
        this.lessonType = lesson.getLessonType();
        this.weather = lesson.getWeather();
        this.id = lesson.getID();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        //String strtext=getArguments().getString("message");
        // Populate the view with data
        populateData();
        saveButton.setText(R.string.update);
        startButton.setVisibility(View.GONE);
        stopButton.setText(R.string.delete);

        datePicker.setVisibility(View.VISIBLE);

        stopButton.setEnabled(true);
        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);

        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                delete();
            }
        });

        hoursText.setEnabled(true);
        hoursText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String orig = v.getText().toString();
                int hrs;
                try {
                    hrs = Integer.parseInt(hoursText.getText().toString());
                    hoursText.setText(String.format("%.2f", hrs));
                    return true;
                } catch (NumberFormatException bad) {
                    Toast.makeText(getActivity().getApplicationContext(), "ERROR: invalid hours", Toast.LENGTH_LONG).show();
                    hoursText.setText(orig);
                }
                return false;
            }
        });

        return rootView;
    }

    public void populateData() {
        dateText.setText(this.date);
        hoursText.setText(Float.toString(this.hours));
        if (this.timeOfDay.equals("day")) {
            dayButton.setChecked(true);
        } else if (this.timeOfDay.equals("night")){
            nightButton.setChecked(true);
        }

        if (this.lessonType.equals("Residential")) {
            lessonSpinner.setSelection(0);
        } else if (this.lessonType.equals("Highway")) {
            lessonSpinner.setSelection(1);
        } else if (this.lessonType.equals("Commercial")){
            lessonSpinner.setSelection(2);
        }

        if (this.weather.equals("Clear")) {
            weatherSpinner.setSelection(0);
        } else if (this.weather.equals("Rainy")) {
            weatherSpinner.setSelection(1);
        } else if (this.weather.equals("Snow/Ice")){
            weatherSpinner.setSelection(2);
        }
    }

    public void delete() {
        // Confirmation to delete:
        AlertDialog deleteDialogue = new AlertDialog.Builder(getActivity())
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(getActivity());
                        if (dayButton.isChecked()) {
                            db.deleteLesson(new Lesson(id, dateText.getText().toString(), hoursText.getText().toString(), "day", lessonSpinner.getSelectedItem().toString(), weatherSpinner.getSelectedItem().toString()));
                        } else {
                            db.deleteLesson(new Lesson(id, dateText.getText().toString(), hoursText.getText().toString(), "night", lessonSpinner.getSelectedItem().toString(), weatherSpinner.getSelectedItem().toString()));
                        }
                        getActivity().getFragmentManager().popBackStack();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void cancel() {
        // Nothing should happen on cancels
        getActivity().getFragmentManager().popBackStack();
    }

    @Override
    public void save() {
        // Save updates to database

        AlertDialog updateDialogue = new AlertDialog.Builder(getActivity())
                .setTitle("Update")
                .setMessage("Are you sure you want to update this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(getActivity());
                        if (dayButton.isChecked()) {
                            db.updateLesson(new Lesson(id, dateText.getText().toString(), hoursText.getText().toString(), "day", lessonSpinner.getSelectedItem().toString(), weatherSpinner.getSelectedItem().toString()));
                        } else {
                            db.updateLesson(new Lesson(id, dateText.getText().toString(), hoursText.getText().toString(), "night", lessonSpinner.getSelectedItem().toString(), weatherSpinner.getSelectedItem().toString()));
                        }
                        Toast.makeText(getActivity().getApplicationContext(), "Successfully updated entry!.", Toast.LENGTH_SHORT).show();
                        getActivity().getFragmentManager().popBackStack();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
