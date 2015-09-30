package com.ustc.autocopysmscode;

import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/9/30.
 */
public class SmsReceiver extends BroadcastReceiver {

    private Context mContext;

    public SmsReceiver(Context context) {
        mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("DEBUG", "SMS Come on");

        Object[] objs = (Object[]) intent.getExtras().get("pdus");
        StringBuilder builder = new StringBuilder();

        for (Object obj : objs) {

            SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
            String byt = sms.getMessageBody();
            System.out.println("短信接受内容" + byt);
            builder.append(byt);

        }
        String code = builder.toString();
        System.out.println("__________________code"+code);
        Pattern pattern = Pattern.compile("(\\d{4,6})");
        Matcher matcher = pattern.matcher(code);

        if (matcher.find()) {
            code = matcher.group(0);
            Log.e("DEBUG", "code is " + code);

            //将验证码存放到剪贴板
            ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(code);

        }
    }
}
