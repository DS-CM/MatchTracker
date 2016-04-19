package group8.matchtracker;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by dsoll on 4/18/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this); // Used for debugging, see: http://facebook.github.io/stetho/
    }
}
