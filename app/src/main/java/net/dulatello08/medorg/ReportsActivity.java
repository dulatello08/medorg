 package net.dulatello08.medorg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

public class ReportsActivity extends AppCompatActivity {
    private static final String TAG = "REPORT";
    private LinearLayout parentLinearLayout;

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    int addIncrement = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        String[] defValues = new String[]{"АМБ", "ПМО", "ОТСРТ", "Всего", "", "", "", "" ,"" ,"" ,"" ,""};
        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);
        Button addButton = findViewById(R.id.add_field_button);
        Button sendButton = findViewById(R.id.send_button);
        @Nonnull Map<String, String> valName = new HashMap<>();
        @Nonnull Map<String, String> val = new HashMap<>();
        addButton.setOnClickListener(v -> {
            LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView=inflater.inflate(R.layout.rfield, null);
            EditText rtext_name = (EditText) rowView.findViewById(R.id.rtext_name);
            rtext_name.setText(defValues[addIncrement]);
            addIncrement++;
            // Add the new row before the add field button.
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 2);
        });
        sendButton.setOnClickListener(v -> {
            //getNameValues
            String name = getDefaults("name", getApplicationContext());
              int children = parentLinearLayout.getChildCount()-2;
            Log.d(TAG+"WTF",Integer.toString(children));
            for (int i = 1; i  <= children ; i++) {
                final View container = parentLinearLayout.getChildAt(i);
                EditText textName = (EditText) container.findViewById(R.id.rtext_name);
                Log.e(TAG+"textName", textName.getText().toString());
                //lesss go
                valName.put("Title "+ i, textName.getText().toString());
            }
            for (int i = 1; i <= children; i++) {
                final View container = parentLinearLayout.getChildAt(i);
                EditText text = (EditText) container.findViewById(R.id.rtext);
                Log.e(TAG+"text", text.getText().toString());
                //lesss go
                val.put("Value "+ i, text.getText().toString());
            }
            String region = getDefaults("region", getApplicationContext());
            String project = getDefaults("project", getApplicationContext());
            Intent goToMain = new Intent(getApplicationContext(), SettingsActivity.class);
            goToMain.putExtra("from", "reports");
            valName.putAll(val);
            if (region==null || region.equals("По умолчанию")) {
                Toast.makeText(getApplicationContext(), "Ошибка У вас не настроен регион", Toast.LENGTH_LONG).show();
                startActivity(goToMain);
                return;
            }else if(project.equals("defaultValue")){
                Toast.makeText(getApplicationContext(), "Ошибка У вас не настроен проект", Toast.LENGTH_LONG).show();
                startActivity(goToMain);
                return;
            }
            valName.put("Project ", project);
            valName.put("Region ", region);
            FirestoreCalls.insertMap(valName, name+" "+RandomCalls.getSaltString(3), "Reports");
            Toast.makeText(getApplicationContext(), "ОТПРАВЛЕНО", Toast.LENGTH_LONG).show();
            Intent gotoMain = new Intent(this, AdsActivity.class);
            startActivity(gotoMain);
        });
    }
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }
}

