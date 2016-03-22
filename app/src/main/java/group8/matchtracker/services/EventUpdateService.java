package group8.matchtracker.services;


import android.app.IntentService;
import android.content.Intent;

public class EventUpdateService extends IntentService {

    public EventUpdateService(){
        super("EventUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String dataString = intent.getDataString();

    }
}
