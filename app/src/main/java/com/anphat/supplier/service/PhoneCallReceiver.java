package com.anphat.supplier.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.Date;

public abstract class PhoneCallReceiver extends BroadcastReceiver {
    //Mac dinh la trang thai roi
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    //La cuoc goi den
    private static boolean isIncoming;
    private static Date callStartTime;
    private static String phoneNumber;

    //Lang nghe trang thai cuoc goi di va den
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && intent != null && intent.getExtras() != null
//                    //&& TextUtils.isEmpty(intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER))) {
//                    && TextUtils.isEmpty(intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER))) {
//                return;
//            }
            //NEW_OUTGOING_CALL: cuoc goi di
            if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
                //Vua bam nut goi di, luu lai so dien thoai
                phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);//intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);//intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
                //Log.d("Phone number out", phoneNumber);
            } else {
                //Lay trang thai hien tai
                String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
                //Log.d("Phone state", stateStr);
                //Lay so dien thoai goi vao
                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);//intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                //Log.d("Phone number in", number);
                if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING) && number == null) return;
                int state = 0;
                if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    state = TelephonyManager.CALL_STATE_IDLE;
                } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    state = TelephonyManager.CALL_STATE_OFFHOOK;
                } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    state = TelephonyManager.CALL_STATE_RINGING;
                }
                onCallStateChanged(context, state, number);
            }
        } catch (Exception e) {
            Toast.makeText(context, "onReceive Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Thay doi trang thai cuoc goi
    public void onCallStateChanged(Context context, int state, String number) {
        try {
            //Khong thay doi trang thai thi return
            if (lastState == state) {
                return;
            }
            switch (state) {
                //Trang thai goi vao
                case TelephonyManager.CALL_STATE_RINGING:
                    isIncoming = true;
                    callStartTime = new Date();
                    phoneNumber = number;
                    onIncomingCallReceived(context, number, callStartTime);
                    break;
                //Chuyen doi trang thai tu do chuong sang tra loi
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                        isIncoming = false;
                        callStartTime = new Date();
                        onOutgoingCallStarted(context, phoneNumber, callStartTime);
                    } else {
                        isIncoming = true;
                        callStartTime = new Date();
                        onIncomingCallAnswered(context, phoneNumber, callStartTime);
                    }
                    break;
                //Trang thai roi
                case TelephonyManager.CALL_STATE_IDLE:
                    if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                        //Do chuong nhung khong co ai nhat may (cuoc goi nho)
                        onMissedCall(context, phoneNumber, callStartTime);
                    } else if (isIncoming) {
                        //Ket thuc cuoc goi den
                        onIncomingCallEnded(context, phoneNumber, callStartTime, new Date());
                    } else {
                        //Ket thuc cuoc goi di
                        onOutgoingCallEnded(context, phoneNumber, callStartTime, new Date());
                    }
                    break;
            }
            lastState = state;
        } catch (Exception e) {
            Toast.makeText(context, "onCallStateChanged Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    protected abstract void onIncomingCallReceived(Context ctx, String phoneNumber, Date start);

    protected abstract void onIncomingCallAnswered(Context ctx, String phoneNumber, Date start);

    protected abstract void onIncomingCallEnded(Context ctx, String phoneNumber, Date start, Date end);

    protected abstract void onOutgoingCallStarted(Context ctx, String phoneNumber, Date start);

    protected abstract void onOutgoingCallEnded(Context ctx, String phoneNumber, Date start, Date end);

    protected abstract void onMissedCall(Context ctx, String phoneNumber, Date start);
}
