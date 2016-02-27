package group8.matchtracker.activities;


import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;

import group8.matchtracker.R;

public class LoginFragment extends ListFragment implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        ListView lv = (ListView)v.findViewById(android.R.id.list);

        String[] names = new String[] {"Name1", "Name2", "Name3", "Name4"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);


        return v;
    }

    @Override
    public void onClick(View v){

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){

    }

}
