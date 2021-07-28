package net.dulatello08.medorg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "Prefs";

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply(); // or editor.commit() in case you want to write data instantly
    }

    static ListPreference region;
    static EditTextPreference project;
    static String regionStr;
    static String projectStr;
    @Nonnull String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        from = getIntent().getStringExtra("from");
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            region = findPreference("user_region");
            project = findPreference("user_project");
            regionStr = region.getValue();
            projectStr = project.getText();
        }
    }
    private Intent goToMain;
    public boolean onOptionsItemSelected(MenuItem item) {
        if(from.equals("reports")) {
            goToMain = new Intent(this, ReportsActivity.class);
        }else if(from.equals("main")){
            goToMain = new Intent(this, MainActivity.class);
        }else {
            //wtf
            System.exit(255);
        }
        setDefaults("region", regionStr, getApplicationContext());
        setDefaults("project", projectStr, getApplicationContext());
        Log.d(TAG, regionStr + " " + projectStr);
        startActivity(goToMain);
        return true;
    }
}