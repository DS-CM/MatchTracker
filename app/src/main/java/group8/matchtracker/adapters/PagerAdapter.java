package group8.matchtracker.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import group8.matchtracker.activities.Events;
import group8.matchtracker.activities.MatchFeed;
import group8.matchtracker.activities.TabMatchFeedFragment;
import group8.matchtracker.activities.TabbedActivity;
import group8.matchtracker.activities.TournamentFeed;
import group8.matchtracker.services.EventUpdateService;


public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    Context context;

    public PagerAdapter(FragmentManager fm, Context c, int NumOfTabs){
        super(fm);
        context = c;
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                TabMatchFeedFragment tab1 = new TabMatchFeedFragment();
                return tab1;
            case 1:
                TournamentFeed tab2 = new TournamentFeed();
                return tab2;
            case 2:
                Events tab3 = new Events();
                return tab3;
            default:
                TabMatchFeedFragment defaultTab = new TabMatchFeedFragment();
                return defaultTab;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
