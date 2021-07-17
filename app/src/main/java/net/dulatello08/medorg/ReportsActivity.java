 package net.dulatello08.medorg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class ReportsActivity extends AppCompatActivity {
    private static final String TAG = "REPORT";
    private LinearLayout parentLinearLayout;
    private Button addButton;
    private Button delButton;
    private Button sendButton;
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);
        addButton = findViewById(R.id.add_field_button);
        sendButton = findViewById(R.id.send_button);
        @Nonnull Map<String, String> valName = new HashMap<>();
        @Nonnull Map<String, String> val = new HashMap<>();
        addButton.setOnClickListener(v -> {
            LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView=inflater.inflate(R.layout.rfield, null);
            // Add the new row before the add field button.
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
        });
        sendButton.setOnClickListener(v -> {
            //getNameValues
            //final View container = parentLinearLayout.getChildAt(1);
            //EditText textName = (EditText) container.findViewById(R.id.rtext_name);
            int children = parentLinearLayout.getChildCount()-2;
            Log.d(TAG+"WTF",Integer.toString(children));
            for (int i = 1; i  <= children ; i++) {
                final View container = parentLinearLayout.getChildAt(i);
                EditText textName = (EditText) container.findViewById(R.id.rtext_name);
                Log.e(TAG, textName.getText().toString());
            }
            /*for (int i = 0; i < children; i++) {
                final View container = parentLinearLayout.getChildAt(i);
                EditText textName = (EditText) findViewById(R.id.rtext_name);
                if(!(textName.getText()==null)){
                    valName.put("textName", textName.getText().toString());
                    Log.e(TAG+"textNAME", textName.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "ОШИБКА 0x217832987. Пожалуйста введите хотя бы одно поле", Toast.LENGTH_LONG).show();
                }
            }
            for (int i = 0; i < children; i++) {
                final View container = parentLinearLayout.getChildAt(i);
                EditText text = (EditText) findViewById(R.id.rtext);
                Log.wtf(TAG, text.getText().toString());
                if(!(text.getText()==null)) {
                    val.put("text", text.getText().toString());
                    Log.e(TAG+"text~", text.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "ОШИБКА 0x217832987. Пожалуйста введите хотя бы одно поле", Toast.LENGTH_LONG).show();
                }
            }
            valName.putAll(val);
            String docName = getDefaults("name", getApplicationContext());
            FirestoreCalls.insertMap(valName, docName, "Reports");
            Log.e(TAG+"Name", valName.toString());*/
            //Log.e(TAG, Integer.toString(children-2));
        });
    }
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }
}

