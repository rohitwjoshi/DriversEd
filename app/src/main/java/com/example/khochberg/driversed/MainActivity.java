package com.example.khochberg.driversed;

import android.app.FragmentManager;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String[] mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;

    private static NewLessonFragment newLessonFragment = new NewLessonFragment();
    private static DrivingLogFragment drivingLogFragment = new DrivingLogFragment();
    private static StatisticsFragment statisticsFragment = new StatisticsFragment();

    private static int currentTitle = R.string.lesson;
    private static Fragment currentFragment = newLessonFragment;

    private static TextView totalHoursText;
    private static ProgressBar totalProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_closed);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getFragmentManager().beginTransaction().replace(R.id.content_frame, currentFragment).commit();
        setTitle(currentTitle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<Lesson> lessons = db.getAllLessons();
        float totalHours = 0;
        for (Lesson ln : lessons) {
            totalHours += Float.parseFloat(ln.getHours());
        }
        View headerView = navigationView.getHeaderView(0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        float totalHoursSettings = preferences.getFloat("totalHours", 10);
        totalHoursText = (TextView) headerView.findViewById(R.id.total_hours_nav);
        totalProgress = (ProgressBar) headerView.findViewById(R.id.total_progress_nav);
        totalHoursText.setText(totalHours + "/" + totalHoursSettings + " total hours trained");
        totalProgress.setProgress((int) (( totalHours/ totalHoursSettings)*100));

    }

    @Override
    public void onBackPressed() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<Lesson> lessons = db.getAllLessons();
        float totalHours = 0;
        for (Lesson ln : lessons) {
            totalHours += Float.parseFloat(ln.getHours());
        }
        View headerView = navigationView.getHeaderView(0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        float totalHoursSettings = preferences.getFloat("totalHours", 10);
        totalHoursText = (TextView) headerView.findViewById(R.id.total_hours_nav);
        totalProgress = (ProgressBar) headerView.findViewById(R.id.total_progress_nav);
        totalHoursText.setText(totalHours + "/" + totalHoursSettings + " total hours trained");
        totalProgress.setProgress((int) (( totalHours/ totalHoursSettings)*100));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.lesson_frag) {
            currentFragment = newLessonFragment;
            currentTitle = R.string.lesson;
        } else if (id == R.id.log_frag) {
            currentFragment = drivingLogFragment;
            currentTitle = R.string.log;

        } else if (id == R.id.statistics_frag) {
            currentFragment = statisticsFragment;
            currentTitle = R.string.statistics;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, currentFragment)
                .commit();
        setTitle(currentTitle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<Lesson> lessons = db.getAllLessons();
        float totalHours = 0;
        for (Lesson ln : lessons) {
            totalHours += Float.parseFloat(ln.getHours());
        }
        View headerView = navigationView.getHeaderView(0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        float totalHoursSettings = preferences.getFloat("totalHours", 10);
        totalHoursText = (TextView) headerView.findViewById(R.id.total_hours_nav);
        totalProgress = (ProgressBar) headerView.findViewById(R.id.total_progress_nav);
        totalHoursText.setText(totalHours + "/" + totalHoursSettings + " total hours trained");
        totalProgress.setProgress((int) (( totalHours/ totalHoursSettings)*100));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_new_lesson) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, newLessonFragment)
                    .commit();
            setTitle(R.string.lesson);
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshStats() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<Lesson> lessons = db.getAllLessons();
        float totalHours = 0;
        for (Lesson ln : lessons) {
            totalHours += Float.parseFloat(ln.getHours());
        }
        View headerView = navigationView.getHeaderView(0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        float totalHoursSettings = preferences.getFloat("totalHours", 10);
        totalHoursText = (TextView) headerView.findViewById(R.id.total_hours_nav);
        totalProgress = (ProgressBar) headerView.findViewById(R.id.total_progress_nav);
        totalHoursText.setText(totalHours + "/" + totalHoursSettings + " total hours trained");
        totalProgress.setProgress((int) (( totalHours/ totalHoursSettings)*100));
    }
}