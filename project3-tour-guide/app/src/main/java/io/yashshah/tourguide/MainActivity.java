package io.yashshah.tourguide;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the ViewPager with the id viewpager in the layout
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create a new PlacePagerAdapter
        PlacePagerAdapter pagerAdapter = new PlacePagerAdapter(this, getSupportFragmentManager());

        // Set the viewpager to use the pagerAdapter
        viewPager.setAdapter(pagerAdapter);

        // Find the TabLayout with the id TabLayout in the layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // Setup the TabLayout with the viewpager
        tabLayout.setupWithViewPager(viewPager);
    }
}
