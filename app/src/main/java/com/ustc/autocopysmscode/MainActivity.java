package com.ustc.autocopysmscode;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_on;
    private Button btn_off;

    private SmsReceiver mReceiver;
    private ReceiverManager rm;//广播接收者管理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btn_on = (Button) findViewById(R.id.btn_on);
        btn_off = (Button) findViewById(R.id.btn_off);


        btn_off.setOnClickListener(this);
        btn_on.setOnClickListener(this);

        rm = new ReceiverManager(getApplicationContext());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_on:
                if(mReceiver!=null){
                    Toast.makeText(this, "监听已开启不需要重复操作", 0).show();
                    return;
                }
                //开启广播
                mReceiver = new SmsReceiver(this);
                rm.registerReceiver(mReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
//                registerReceiver(mReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
                Toast.makeText(this, "短信验证码监听开启", 0).show();
                break;
            case R.id.btn_off:

                if (rm.isReceiverRegistered(mReceiver)) {
                    rm.unregisterReceiver(mReceiver);
                    Toast.makeText(this, "短信验证码监听成功关闭", 0).show();
                    mReceiver = null;
                    return ;
                }
                Toast.makeText(this, "短信验证码监听已关闭", 0).show();

                break;
            default:
                break;
        }
    }
}
