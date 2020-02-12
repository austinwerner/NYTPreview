package com.example.nytpreview.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.nytpreview.R;

public abstract class BaseActivity extends AppCompatActivity {

    public ProgressBar mProgressBar;

    @Override
    public void setContentView(int layoutResId) {

        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);

        getLayoutInflater().inflate(layoutResId, frameLayout, true);
        super.setContentView(constraintLayout);
    }

    // Handles hiding/showing progress bar spinner
    public void showProgressBar(boolean visible) {

        mProgressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
}