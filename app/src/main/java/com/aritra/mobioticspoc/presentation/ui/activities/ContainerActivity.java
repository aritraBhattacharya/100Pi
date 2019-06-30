package com.aritra.mobioticspoc.presentation.ui.activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aritra.mobioticspoc.R;
import com.aritra.mobioticspoc.presentation.HomeInterfaces;
import com.aritra.mobioticspoc.presentation.ui.fragments.VideoListFragment;

public class ContainerActivity extends AppCompatActivity implements HomeInterfaces {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        if (savedInstanceState == null) {
            loadFirstFragment();
        }
    }

    private void loadFirstFragment() {
        Fragment fragment = new VideoListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container_view, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
