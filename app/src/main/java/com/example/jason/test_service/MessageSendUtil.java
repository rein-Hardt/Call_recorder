package com.example.jason.test_service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jason on 12/11/2015.
 */
public class MessageSendUtil {
    private Context context =null;
    private Intent intent = null;

    public MessageSendUtil(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }
    public void send(String receiveNumber,String phoneNumber,String type){
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent sentIntent = PendingIntent.getActivity(this.context, 0, this.intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String content = "电话号码: "+phoneNumber+"\n 类型:"+type+"\n 姓名:"+this.getName(phoneNumber)+"\n 操作时间: "+new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS").format(new Date());
        smsManager.sendTextMessage(receiveNumber,null,content,sentIntent,null);
    }
    private String getName(String phoneNumber){
        String name = null;
        String phoneSelection= ContactsContract.CommonDataKinds.Phone.NUMBER+"=?";
        String []phoneSelectionArgs = {String.valueOf(phoneNumber)};
        Cursor cursor = this.context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,phoneSelection,phoneSelectionArgs,null);
        if (cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        }else {
            name = "未知";
        }
        cursor.close();
        return name;
    }
}
