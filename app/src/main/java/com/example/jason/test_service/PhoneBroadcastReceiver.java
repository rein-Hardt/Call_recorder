package com.example.jason.test_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PhoneBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            String outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Intent pit = new Intent(context,PhoneService.class);
            pit.putExtra("outgoingNumber",outgoingNumber);
            context.startService(pit);
        }else {
            context.startService(new Intent(context,PhoneService.class));
        }
    }
}
