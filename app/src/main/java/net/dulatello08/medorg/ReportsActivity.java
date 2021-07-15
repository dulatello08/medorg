package net.dulatello08.medorg;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ReportsActivity extends AppCompatActivity {
    private LinearLayout parentLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);
    }
    public void onAddField(View v) {
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView=inflater.inflate(R.layout.rfield, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }
    private void getNameData() {
        int count = parentLinearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            final View row = parentLinearLayout.getChildAt(i);
            TextView textOut = (TextView)row.findViewById(R.id.rtext_name);
            String data = textOut.getText().toString();
        }
    }
}

