package com.beoni.openwaterswimtracking;


import android.content.Intent;

import com.beoninet.android.easymessage.EasyMessageManager;
import com.beoninet.openwaterswimtracking.shared.Constants;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Here using a WearableListener because it is managed automatically
 * by the OS, invoked also when the app is not running, so it can
 * start the app as soon as the wear sends a message to the OS.
 * In the other side, it uses the EasyMessageLibrary utility
 * to send back messages to the wear because it's much easier
 * to use.
 */
public class WearMessageListener extends WearableListenerService
{
    EasyMessageManager mEasyMessageManager;

    @Override
    public void onMessageReceived(MessageEvent msgEv)
    {
        boolean gotData = msgEv.getData()!=null && msgEv.getData().length>0;

        if(msgEv.getPath().equals(Constants.MSG_SWIM_DATA_AVAILABLE))
        {
            String message;

            if(gotData){
                message = getString(R.string.swim_data_downloaded);

                Intent intent = new Intent(this,SwimEditActivity_.class);
                intent.putExtra(Constants.EXTRA_SWIM_GPS_DATA,new String(msgEv.getData()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP); //to update the activity intent and related data
                startActivity(intent);
            }
            else
                message = getString(R.string.no_data_received);


            mEasyMessageManager.sendMessage(
                    Constants.MSG_SWIM_MESSAGE_RECEIVED,
                    message
            );
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mEasyMessageManager = new EasyMessageManager(this);
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        mEasyMessageManager.connect();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mEasyMessageManager.disconnect();
    }
}
