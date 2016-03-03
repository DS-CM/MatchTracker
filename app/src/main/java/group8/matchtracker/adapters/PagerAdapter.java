package group8.matchtracker.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import group8.matchtracker.activities.Events;
import group8.matchtracker.activities.MatchFeed;
import group8.matchtracker.activities.TournamentFeed;


public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                MatchFeed tab1 = new MatchFeed();
                return tab1;
            case 1:
                TournamentFeed tab2 = new TournamentFeed();
                return tab2;
            case 2:
                Events tab3 = new Events();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
