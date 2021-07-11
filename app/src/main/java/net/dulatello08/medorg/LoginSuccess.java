package net.dulatello08.medorg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import net.dulatello08.medorg.databinding.ActivityLoginSuccessBinding;

public class LoginSuccess extends AppCompatActivity {

    private static final String TAG = "LoginSuccess";
    private AppBarConfiguration mAppBarConfiguration;
    public String name, email;
// --Commented out by Inspection START (7/9/21, 2:14 PM):
//    public  Context getContext(){
//        Context mContext = LoginSuccess.this;
//        return mContext;
//    }
// --Commented out by Inspection STOP (7/9/21, 2:14 PM)
    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginSuccessBinding binding = ActivityLoginSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarLoginSuccess.toolbar);
        binding.appBarLoginSuccess.fab.setOnClickListener(view -> Snackbar.make(view, "Скоро", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //noinspection deprecation
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_login_success);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //data_handler
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");

        Log.e(TAG, name);
        Log.d(TAG, email);

        /*StringBuilder sb = new StringBuilder(Name);
        sb.append(LastName);*/
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.textName);
        nav_user.setText(name);

        TextView nav_email = hView.findViewById(R.id.textEmail);
        nav_email.setText(email);



        //No code no problem
        /*Uri photoUri1 = Uri.parse(photoUri);
        ImageView nav_img = (ImageView) hView.findViewById(R.id.imageView);
        nav_img.setImageURI(null);
        nav_img.setImageURI(photoUri1);*/

        setDefaults("name", name, getApplicationContext());
        setDefaults("email", email, getApplicationContext());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_success, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_login_success);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            newSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void newSettings() {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }
}