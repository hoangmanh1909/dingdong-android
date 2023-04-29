package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuaphanhuong.moredata;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.SapXepCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.AdapterVmap;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.response.ChuaPhanHuongMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogVmapChuaPhanHuowng extends Dialog {

    @BindView(R.id.rcView)
    RecyclerView rcView;
    private Context mContext;
    SapXepCallback idCallback;
    List<ChuaPhanHuongMode> list;
    private AdapterVmapChuaPhanHuong adapter;

    public DialogVmapChuaPhanHuowng(Context context, List<ChuaPhanHuongMode> list, SapXepCallback idCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.bg_yellow_primary));
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_vmap, null);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(view);
        this.idCallback = idCallback;
        ButterKnife.bind(this, view);
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();
    }

    @OnClick({R.id.img_dong, R.id.img_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_dong:
                idCallback.onResponse(0);
                dismiss();
                break;
            case R.id.img_check:
                idCallback.onResponse(1);
                dismiss();
                break;

        }
    }

    @Override
    public void show() {
        super.show();
    }

    private void initAdapter() {
        try {
            if (adapter == null) {
                adapter = new AdapterVmapChuaPhanHuong(getContext(), list);
                RecyclerUtils.setupVerticalRecyclerView(getContext(), rcView);
                rcView.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
