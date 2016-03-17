package group8.matchtracker.activities;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import group8.matchtracker.R;
import group8.matchtracker.adapters.PagerAdapter;

public class TabbedActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private int tid;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.tabbed_activity);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Log.d(TAG, String.valueOf(extras.getInt("SELECTED_PLAYER_ID")));
            tid = extras.getInt("TOURNAMENT_ID");
            Log.d("Tournament", String.valueOf(tid));
        }

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Match Feed"));
        tabLayout.addTab(tabLayout.newTab().setText("Tournament Feed"));
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Fragment stuff
        TabMatchFeedFragment tabMatchFeedFragment = new TabMatchFeedFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.tabbed_activity_data_fragment, tabMatchFeedFragment);
        fragmentTransaction.commit();

        Log.d(TAG, "onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_feed_activity, menu);

        Log.d(TAG, "onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.directions:
                Intent i = new Intent(this,MapActivity.class);
                i.putExtra("TOURNAMENT_ID", tid);
                startActivity(i);
                break;
            case R.id.action_settings:
                break;
        }

        Log.d(TAG, "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO - Check for updates to the possible Tournaments

        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }
}
