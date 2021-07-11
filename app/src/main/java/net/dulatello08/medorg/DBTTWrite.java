package net.dulatello08.medorg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DBTTWrite extends AppCompatActivity{
    private static final String TAG = "DB";
    // Write a message to the database
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    // --Commented out by Inspection (7/9/21, 2:14 PM):DatabaseReference reference = database.getReference("Time Track");
    protected String getDate() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date = new SimpleDateFormat("'Месяц 'MM,'День 'dd,'Час 'HH,'Минута 'mm, 'Cекунда 's");
        // you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

        return date.format(currentLocalTime);
    }
    protected String getDateName() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC+6:00"));
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date = new SimpleDateFormat("s");
        date.setTimeZone(TimeZone.getTimeZone("UTC+6:00"));
        return date.format(currentLocalTime);
    }
    protected String getUser(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent go = getIntent();
        String check = go.getStringExtra("check");
        boolean checked= check.equals("TWVkT3JnJOKCrENSSVRfS0VZCg==");
        setContentView(R.layout.activity_tt_write);

        Button sendButton = (Button) findViewById(R.id.sendButton);

        if (!checked) {
            TextView sendWorkTime = (TextView) findViewById(R.id.sendWorkTime);
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            TextView workTimeType = (TextView) findViewById(R.id.workTimeType);

            sendWorkTime.setText(R.string.error_u_hacker);
            spinner.setVisibility(View.GONE);
            workTimeType.setVisibility(View.GONE);
            sendButton.setVisibility(View.GONE);
        }
        sendButton.setOnClickListener(view -> {
            Long tsLong = System.currentTimeMillis()/1000;
            // Access a Cloud Firestore instance from your Activity
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            String type = spinner.getSelectedItem().toString();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, String> time = new HashMap<>();
            String name = getUser("name", getApplicationContext());
            String email = getUser("email", getApplicationContext());
            String region = getDefaults("region", getApplicationContext());
            String project = getDefaults("project", getApplicationContext());
            if (region==null || region.equals("По умолчанию")) {
                Toast.makeText(getApplicationContext(), "Ошибка У вас не настроен регион", Toast.LENGTH_LONG).show();
                return;
            }else if(project.equals("defaultValue")){
                Toast.makeText(getApplicationContext(), "Ошибка У вас не настроен проект", Toast.LENGTH_LONG).show();
                return;
            }
            time.put("Имя: ", name);
            time.put("Email: ", email);
            time.put("Время: ", getDate());
            time.put("Тип: ", type);
            time.put("Регион", region);
            time.put("Проект", project);
            String docRef = name + " " +
                    type + "" + getDateName();
            Log.e(TAG, name);
            Log.w(TAG, email);
            db.collection("Time Tracking").document(docRef)
                    .set(time)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                    .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
            Toast.makeText(getApplicationContext(), "ОТПРАВЛЕНО", Toast.LENGTH_LONG).show();
            Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(goToMain);
        });
    }
}
