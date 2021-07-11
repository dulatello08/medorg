package net.dulatello08.medorg.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import net.dulatello08.medorg.R;
import net.dulatello08.medorg.ReportsActivity;
import net.dulatello08.medorg.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        Button btn = (Button)view.findViewById(R.id.button3);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), ReportsActivity.class);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}