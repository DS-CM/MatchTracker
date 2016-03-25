package group8.matchtracker.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import group8.matchtracker.R;

public class Login extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.player_login_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.search_menu);
        setSupportActionBar(toolbar);


        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.player_login_list_fragment, new LoginFragment());
        fragmentTransaction.commit();

        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
}
