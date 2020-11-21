package com.andela.taccolation.app.ui.teachernotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.andela.taccolation.app.utils.Constants;

// https://developer.android.com/guide/components/broadcasts
public class NotesBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = Constants.LOG.getConstant();

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast received", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onReceive: Broadcast Received");
    }
}