package group8.matchtracker.activities;


import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import group8.matchtracker.R;

public class LoginFragment extends ListFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.login_fragment, container, false);

        String[] names = new String[] {"Name1", "Name2", "Name3", "Name4"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);

        return v;
    }

    public void onClick(View v){

    }

}
