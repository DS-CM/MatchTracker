package group8.matchtracker.activities;

        import android.support.v4.app.ListFragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;

        import group8.matchtracker.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TournamentListFragment extends ListFragment {

    public TournamentListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tournament_list, container, false);

        String[] tournaments = new String[] {"Tournament 1\ndetails","Tournament 2\ndetails","Tournament 3\ndetails"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, tournaments);
        setListAdapter(adapter);

        return rootView;
    }
}