package group8.matchtracker.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import group8.matchtracker.activities.TabTournamentBracketFragment;
import group8.matchtracker.activities.TabMatchFeedFragment;
import group8.matchtracker.activities.TabTournamentFeedFragment;

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
                TabMatchFeedFragment left = new TabMatchFeedFragment();
                return left;
            case 1:
                TabTournamentFeedFragment middle = new TabTournamentFeedFragment();
                return middle;
            case 2:
                TabTournamentBracketFragment right = new TabTournamentBracketFragment();
                return right;
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
