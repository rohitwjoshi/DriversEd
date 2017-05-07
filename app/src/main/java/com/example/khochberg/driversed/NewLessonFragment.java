package com.example.khochberg.driversed;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.util.Log;


import java.util.Calendar;

public class NewLessonFragment extends Fragment {

    protected View rootView;
    protected RadioButton dayButton, nightButton;
    protected Spinner lessonSpinner, weatherSpinner;
    protected TextView dateText;
    protected EditText hoursText;
    protected Button startButton, stopButton, saveButton, cancelButton;
    private Calendar startTime, stopTime;
    private long elapsedTime;
    protected ImageButton datePicker;

    public NewLessonFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_new_lesson, container, false);

        dateText = (TextView) rootView.findViewById(R.id.date);
        hoursText = (EditText) rootView.findViewById(R.id.hours);

        hoursText.setText("");
        dateText.setText("");

        hoursText.setEnabled(false);

        lessonSpinner = (Spinner) rootView.findViewById(R.id.lesson_spinner);
        weatherSpinner = (Spinner) rootView.findViewById(R.id.weather_spinner);

        dayButton = (RadioButton) rootView.findViewById(R.id.day_radio_btn);
        nightButton = (RadioButton) rootView.findViewById(R.id.night_radio_btn);
        //RadioGroup radiogroup = (RadioGroup) rootView.findViewById(R.id.radio_buttons);

        datePicker = (ImageButton) rootView.findViewById(R.id.date_picker);
        datePicker.setVisibility(View.GONE);

        datePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        startButton = (Button) rootView.findViewById(R.id.start_button);
        stopButton = (Button) rootView.findViewById(R.id.stop_button);
        saveButton = (Button) rootView.findViewById(R.id.save_button);
        cancelButton = (Button) rootView.findViewById(R.id.cancel_button);

        stopButton.setEnabled(false);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                start();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stop();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancel();
            }
        });

        final ArrayAdapter<CharSequence> lessonAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.lesson_array, R.layout.spinner_item);
        lessonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<CharSequence> weatherAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.weather_array, R.layout.spinner_item);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lessonSpinner.setAdapter(lessonAdapter);
        weatherSpinner.setAdapter(weatherAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void start() {
        startTime = Calendar.getInstance();
        stopButton.setEnabled(true);
        startButton.setEnabled(false);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
        dateText.setText(String.format("%02d", startTime.get(Calendar.MONTH) + 1) + "/" +
                String.format("%02d",startTime.get(Calendar.DAY_OF_MONTH)) + "/" + startTime.get(Calendar.YEAR));
        hoursText.setText("");
    }

    public void stop() {
        stopTime = Calendar.getInstance();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);
        elapsedTime = stopTime.getTimeInMillis() - startTime.getTimeInMillis();
        double hours = (double) elapsedTime / (double) 1000 / (double) 60 / (double) 60;
        hoursText.setText(String.format("%.2f", hours));
    }

    public void save() {
        //int selectedId = rootView.radiogroup.getCheckedRadioButtonId();
        //RadioButton radioButton = (RadioButton) rootView.findViewById(selectedId);
        DatabaseHandler db = new DatabaseHandler(getActivity());
        if (dayButton.isChecked()) {
            db.addLesson(new Lesson(dateText.getText().toString(), hoursText.getText().toString(), "day", lessonSpinner.getSelectedItem().toString(), weatherSpinner.getSelectedItem().toString()));
        } else {
            db.addLesson(new Lesson(dateText.getText().toString(), hoursText.getText().toString(), "night", lessonSpinner.getSelectedItem().toString(), weatherSpinner.getSelectedItem().toString()));
        }
        Toast.makeText(getActivity().getApplicationContext(), "Save Pressed.", Toast.LENGTH_SHORT).show();
        resetView();
    }

    public void cancel() {
        Toast.makeText(getActivity().getApplicationContext(), "Cancel Pressed.", Toast.LENGTH_SHORT).show();
        resetView();
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                dateText.setText(String.format("%02d",(month + 1)) + "/" + String.format("%02d",day) + "/" + year);
            }
        };
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    public void resetView() {
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
        hoursText.setText("");
        dateText.setText("");
    }

    public void updateDate(String date) {
        dateText.setText(date);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Empty method - update actual date in New Lesson Fragment showDatePicker()
        }
    }
}

