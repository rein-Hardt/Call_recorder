package com.example.jason.test_service;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jason on 12/11/2015.
 */
//录音工具类
public class RecordAudioUtil {
    private MediaRecorder mediaRecorder=null; //媒体录制
    String recDir="CallRec";                  //保存目录
    private File recAudioSaveFileDir =null;   //文件保存目录
    private boolean sdCardExists = false;      //判断SD卡是否存在
    private boolean isRec=false;                //判断录音状态
    private String phoneNumber = null;          //记录号码
    private String callType = null;             //记录拨入或拨出类型

    public RecordAudioUtil(String callType, String phoneNumber) {
        this.callType = callType;
        this.phoneNumber = phoneNumber;
        if (this.sdCardExists = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  //判断SD卡是否存在
            this.recAudioSaveFileDir = new File(Environment.getExternalStorageDirectory().toString()+File.separator+this.recDir+File.separator);    //保存录音目录
            if (!this.recAudioSaveFileDir.exists()){
                this.recAudioSaveFileDir.mkdir();           //如果父目录不存在创建目录.
            }

        }
    }
    public File record(){
        File recAudioSaveFile = null;
        String recAudioSaveFileName = null;
        if (this.sdCardExists){
            recAudioSaveFileName = this.recAudioSaveFileDir.toString()+File.separator+"CallRec_"+new SimpleDateFormat("yyyy"+"-"+"MM"+"-"+"dd"+"-"+
            "HH"+"-"+"mm"+"-"+"ss").format(new Date())+"_"+this.callType+"_"+this.phoneNumber+".amr";
            recAudioSaveFile = new File(recAudioSaveFileName);
            this.mediaRecorder = new MediaRecorder();
            this.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
            this.mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            this.mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            this.mediaRecorder.setOutputFile(recAudioSaveFileName);
            try {
                this.mediaRecorder.prepare();
            } catch (Exception e){
                e.printStackTrace();
            }
            this.mediaRecorder.start();
            this.isRec = true;
        }
        return recAudioSaveFile;
    }
    public void stop(){
        if (this.isRec){
            this.mediaRecorder.stop();
            this.mediaRecorder.release();
        }
    }
}
