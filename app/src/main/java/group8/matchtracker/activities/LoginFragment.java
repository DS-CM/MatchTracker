package group8.matchtracker.activities;


import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;

import java.util.ArrayList;

import group8.matchtracker.R;

public class LoginFragment extends ListFragment {

    ArrayList<String> nameList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.login_fragment, container, false);

        nameList.add("Name1");
        nameList.add("Name2");
        nameList.add("Name3");
        nameList.add("Name4");
        nameList.add("Name5");

        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, nameList));

        return v;
    }

    @Override
    public void onListItemClick(ListView lv, View v, int position, long id){
        super.onListItemClick(lv, v, position, id);

        String value = lv.getItemAtPosition(position).toString();
        Log.println(Log.INFO, "TEST: ", value);

        Context context = v.getContext();
        Intent i = new Intent(context, TabbedActivity.class);
        context.startActivity(i);
    }

}
