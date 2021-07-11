package net.dulatello08.medorg.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import net.dulatello08.medorg.QRScanActivity;
import net.dulatello08.medorg.R;

@SuppressWarnings("SpellCheckingInspection")
public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(v -> {
            if (v.getId() == R.id.button) {
                Intent intent1 = new Intent(getActivity(), QRScanActivity.class);
                intent1.putExtra("check", "TWVkT3JnJOKCrENSSVRfS0VZCg==");
                startActivity(intent1);
            }
        });
        return view;
    }
}