package net.dulatello08.medorg.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import net.dulatello08.medorg.R;

public class SlideshowFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =   inflater.inflate(R.layout.fragment_slideshow,container,false);

        WebView browser = view.findViewById(R.id.webView);
        browser.loadUrl(getString(R.string.wiki_url));
        return view;
    }
}