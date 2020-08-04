package com.ems.dingdong.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.ems.dingdong.R;

public class CustomDiapadView extends FrameLayout implements View.OnClickListener {

    private DiapadClickListener listener;
    private CustomDiapadButton button1;
    private CustomDiapadButton button2;
    private CustomDiapadButton button3;
    private CustomDiapadButton button4;
    private CustomDiapadButton button5;
    private CustomDiapadButton button6;
    private CustomDiapadButton button7;
    private CustomDiapadButton button8;
    private CustomDiapadButton button9;
    private CustomDiapadButton button0;
    private CustomDiapadButton buttonStar;
    private CustomDiapadButton buttonHashTag;

    public CustomDiapadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View v = inflate(context, R.layout.widget_custom_diapad, this);
        initView(v);
    }

    public CustomDiapadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View v = inflate(context, R.layout.widget_custom_diapad, this);
        initView(v);
    }

    public CustomDiapadView(Context context) {
        super(context);
        View v = inflate(context, R.layout.widget_custom_diapad, this);
        initView(v);
    }

    public void setOnItemClickListener(DiapadClickListener listener) {
        this.listener = listener;
    }

    void initView(View v) {
        button0 = v.findViewById(R.id.btn_zero);
        button1 = v.findViewById(R.id.btn_one);
        button2 = v.findViewById(R.id.btn_two);
        button3 = v.findViewById(R.id.btn_three);
        button4 = v.findViewById(R.id.btn_four);
        button5 = v.findViewById(R.id.btn_five);
        button6 = v.findViewById(R.id.btn_six);
        button7 = v.findViewById(R.id.btn_seven);
        button8 = v.findViewById(R.id.btn_eight);
        button9 = v.findViewById(R.id.btn_nine);
        buttonStar = v.findViewById(R.id.btn_star);
        buttonHashTag = v.findViewById(R.id.btn_hashtag);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonStar.setOnClickListener(this);
        buttonHashTag.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_zero:
                listener.OnItemClickListener('0');
                break;
            case R.id.btn_one:
                listener.OnItemClickListener('1');
                break;
            case R.id.btn_two:
                listener.OnItemClickListener('2');
                break;
            case R.id.btn_three:
                listener.OnItemClickListener('3');
                break;
            case R.id.btn_four:
                listener.OnItemClickListener('4');
                break;
            case R.id.btn_five:
                listener.OnItemClickListener('5');
                break;
            case R.id.btn_six:
                listener.OnItemClickListener('6');
                break;
            case R.id.btn_seven:
                listener.OnItemClickListener('7');
                break;
            case R.id.btn_eight:
                listener.OnItemClickListener('8');
                break;
            case R.id.btn_nine:
                listener.OnItemClickListener('9');
                break;
            case R.id.btn_star:
                listener.OnItemClickListener('*');
                break;
            case R.id.btn_hashtag:
                listener.OnItemClickListener('#');
                break;

        }
    }

    public interface DiapadClickListener {
        void OnItemClickListener(char s);
    }
}
