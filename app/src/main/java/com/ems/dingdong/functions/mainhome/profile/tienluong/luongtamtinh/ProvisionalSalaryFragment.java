package com.ems.dingdong.functions.mainhome.profile.tienluong.luongtamtinh;


import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class ProvisionalSalaryFragment extends ViewFragment<ProvisionalSalaryContract.Presenter> implements ProvisionalSalaryContract.View {

    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.ll_lich)
    LinearLayout llLich;
    @BindView(R.id.tv_xemluong)
    TextView tv_xemluong;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_salary_monthly;
    }

    public static ProvisionalSalaryFragment getInstance() {
        return new ProvisionalSalaryFragment();
    }
    SharedPref sharedPref;
    String userJson;
    UserInfo userInfo;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initLayout() {
        super.initLayout();
        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
//        userInfo.setIsEms("00098199");
        llLich.setVisibility(View.GONE);
        mWebview.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
//        Log.d("tasdasd12qweqwe", url);
        final WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

        };
        mWebview.setWebViewClient(client);

    }

    @OnClick({R.id.tv_xemluong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_xemluong:
                String url = "https://luong.vnpost.vn/Postman/DeliveryReport/report-detail-hrm/"+userInfo.getIsEms()+"/view";

                mWebview.loadUrl(url);
                tv_xemluong.setVisibility(View.GONE);
                break;

        }
    }
    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
