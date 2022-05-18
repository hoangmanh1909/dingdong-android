package com.ems.dingdong.fcm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.dialog.DialogTextThanhConhg;
import com.ems.dingdong.functions.login.LoginActivity;
import com.ems.dingdong.utiles.Log;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_text_thanhcong);
        //bundle must contain all info sent in "data" field of the notification

        Intent intent = getIntent();
        String value1 = intent.getStringExtra("message");
        Log.d("Asd123123",value1);

        TextView textView = findViewById(R.id.tv_noidung);
        LinearLayout ll_dong = findViewById(R.id.ll_dong);
        textView.setText(value1);
        ll_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
