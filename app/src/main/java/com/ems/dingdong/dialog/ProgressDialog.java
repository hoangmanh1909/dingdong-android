package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.ems.dingdong.R;



public class ProgressDialog extends Dialog {
    View progressBar;
    boolean check = false;

    public ProgressDialog(Context context) {
        super(context, R.style.AppBottomSheetDialog);
        setContentView(R.layout.dialog_progress);
//        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void show() {
//        progressBar.setVisibility(View.VISIBLE);
        super.show();
    }
}
