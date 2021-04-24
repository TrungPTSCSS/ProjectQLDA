package com.example.appnote.ui.status;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appnote.R;
//import com.example.appnote.ui.status.StatusViewModel;

public class StatusFragment extends Fragment {

    private StatusViewModel StatusViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatusViewModel =
                new ViewModelProvider(this).get(StatusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_status, container, false);
        final TextView textView = root.findViewById(R.id.text_status);
        StatusViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
