package com.enamakel.backseattester;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.enamakel.backseattester.fragments.JourneyFragment;
import com.enamakel.backseattester.fragments.JourneyFragment_;
import com.enamakel.backseattester.fragments.PassengerFragment;
import com.enamakel.backseattester.fragments.PassengerFragment_;
import com.enamakel.backseattester.fragments.SessionFragment;
import com.enamakel.backseattester.fragments.SessionFragment_;

import java.util.ArrayList;
import java.util.List;


//@EActivity(R.layout.activity_tabbed)
public class TabbedActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static TabbedActivity context;

    /* Instances of the different fragments */
    public static SessionFragment sessionFragment;
    public static PassengerFragment passengerFragment;
    public static JourneyFragment journeyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        context = this;

        // Initialize view
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Initialize the different fragments
        sessionFragment = new SessionFragment_();
        passengerFragment = new PassengerFragment_();
        journeyFragment = new JourneyFragment_();

        // Add the fragments into the view adapter
        adapter.addFragment(sessionFragment, "Session");
        adapter.addFragment(passengerFragment, "Passenger");
        adapter.addFragment(journeyFragment, "Journey");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> titleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }


        @Override
        public int getCount() {
            return fragmentList.size();
        }


        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            titleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }


    /**
     * Global function to log a message to the info screen and as a toast notification
     *
     * @param message Message to be logged
     */
    public static void info(final String message) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                context.sessionFragment.logMessage(message);
            }
        });
    }


    /**
     * Global function to log a message to the info screen only.
     *
     * @param message Message to be logged
     */
    public static void debug(final String message) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                context.sessionFragment.logMessage(message);
            }
        });
    }
}