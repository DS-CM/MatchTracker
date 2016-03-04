package group8.matchtracker.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import group8.matchtracker.R;

public class Login extends FragmentActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.logindetails, new LoginFragment());
        fragmentTransaction.commit();

        Log.d(TAG, "onCreate");
    }
}
