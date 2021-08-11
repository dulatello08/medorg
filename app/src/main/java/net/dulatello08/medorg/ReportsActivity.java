package net.dulatello08.medorg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;

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
    private static final String AD_UNIT_ID = "ca-app-pub-7509088958653785/9276819405";
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        String[] defValues = new String[]{"АМБ", "ПМО", "ОТСРТ", "Всего", "", "", ""};
        parentLinearLayout= findViewById(R.id.parent_linear_layout);
        Button addButton = findViewById(R.id.add_field_button);
        Button sendButton = findViewById(R.id.send_button);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {}
        });

        loadAd();
        @Nonnull Map<String, String> valName = new HashMap<>();
        @Nonnull Map<String, String> val = new HashMap<>();
        addButton.setOnClickListener(v -> {
            LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") final View rowView=inflater.inflate(R.layout.rfield, null);
            EditText rtext_name = rowView.findViewById(R.id.rtext_name);
            rtext_name.setText(defValues[addIncrement]);
            addIncrement++;
            // Add the new row before the add field button.
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
        });
        sendButton.setOnClickListener(v -> {
            //getNameValues
            String name = getDefaults("name", getApplicationContext());
            int children = parentLinearLayout.getChildCount()-2;
            Log.d(TAG+"WTF",Integer.toString(children));
            for (int i = 1; i  <= children ; i++) {
                final View container = parentLinearLayout.getChildAt(i);
                EditText textName = container.findViewById(R.id.rtext_name);
                //Log.e(TAG+"textName", textName.getText().toString());
                //lesss go
                //valName.put("Title "+ i, textName.getText().toString());
                Log.e(TAG, String.valueOf(children));
            }
            for (int i = 1; i <= children; i++) {
                final View container = parentLinearLayout.getChildAt(i);
                EditText text = container.findViewById(R.id.rtext);
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
            showInterstitial();
            new CountDownTimer(5000, 1000) {
                public void onFinish() {
                    // When timer is finished
                    // Execute your code here
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();

        });
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                AD_UNIT_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        ReportsActivity.this.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        Toast.makeText(ReportsActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        ReportsActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        ReportsActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        interstitialAd = null;

                        @SuppressLint("DefaultLocale") String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                        Toast.makeText(
                                ReportsActivity.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
            Snackbar.make(findViewById(android.R.id.content),"Your Text Here",Snackbar.LENGTH_SHORT).show();
        }
    }
}


