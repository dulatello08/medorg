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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.marvinlabs.intents.MediaIntents;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class ReportsActivity extends AppCompatActivity {
    private static final String TAG = "REPORT";
    private LinearLayout parentLinearLayout;
    private Button delButton;
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);
        Button addButton = findViewById(R.id.add_field_button);
        Button sendButton = findViewById(R.id.send_button);
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
            String name = getDefaults("name", getApplicationContext());
            //final View container = parentLinearLayout.getChildAt(1);
            //EditText textName = (EditText) container.findViewById(R.id.rtext_name);
            int children = parentLinearLayout.getChildCount()-2;
            Log.d(TAG+"WTF",Integer.toString(children));
            for (int i = 1; i  <= children ; i++) {
                final View container = parentLinearLayout.getChildAt(i);
                EditText textName = (EditText) container.findViewById(R.id.rtext_name);
                Log.e(TAG+"textName", textName.getText().toString());
                //lesss go
                valName.put("textName "+ i, textName.getText().toString());
            }
            for (int i = 1; i <= children; i++) {
                final View container = parentLinearLayout.getChildAt(i);
                EditText text = (EditText) container.findViewById(R.id.rtext);
                Log.e(TAG+"text", text.getText().toString());
                //lesss go
                val.put("text "+ i, text.getText().toString());
            }
            valName.putAll(val);
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

