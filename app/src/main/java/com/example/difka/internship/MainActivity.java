package com.example.difka.internship;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        FragGet get = new FragGet();
        transaction.replace(R.id.frame_content, get);
        transaction.commit();
        getSupportActionBar().setTitle("Get JSON Qiscus");

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int nama = tab.getPosition();
                switch (nama){
                    case 0:
                        FragGet get1 = new FragGet();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_content, get1)
                                .addToBackStack("get1")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                        getSupportActionBar().setTitle("Get JSON Qiscus");
                        break;
                    case 1:
                        FragAbout video = new FragAbout();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_content, video)
                                .addToBackStack("video")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                        getSupportActionBar().setTitle("Video");
                        break;
                    default:
                        return;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
