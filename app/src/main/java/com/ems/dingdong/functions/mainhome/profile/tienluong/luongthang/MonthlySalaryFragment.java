package com.ems.dingdong.functions.mainhome.profile.tienluong.luongthang;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.profile.tienluong.SalaryAdapter;
import com.ems.dingdong.functions.mainhome.profile.tienluong.luongtamtinh.ProvisionalSalaryFragment;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MonthlySalaryFragment extends ViewFragment<MonthlySalaryContract.Presenter> implements MonthlySalaryContract.View {
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.edt_thang)
    TextView edtThang;
    @BindView(R.id.edt_nam)
    TextView edtNam;
    @BindView(R.id.tv_log)
    TextView tv_log;
    @BindView(R.id.tv_xemluong)
    TextView tv_xemluong;
    SharedPref sharedPref;
    String userJson;
    UserInfo userInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_salary_monthly;
    }

    public static MonthlySalaryFragment getInstance() {
        return new MonthlySalaryFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        sharedPref = new SharedPref(getActivity());
        userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
//        userInfo.setIsEms("00098199");
        tv_xemluong.setVisibility(View.GONE);
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        Calendar maxToStart = Calendar.getInstance();
        int nam = maxToStart.get(Calendar.YEAR);
        edtNam.setText(maxToStart.get(Calendar.YEAR) + "");
        edtThang.setText(maxToStart.get(Calendar.MONTH) + 1 + "");
        String url = "https://luong.vnpost.vn/Postman/DeliveryReport/report-detail-hrm-in-month/" + userInfo.getIsEms() + "?time=%22" + edtNam.getText().toString() + "-" + edtThang.getText() + "%22";
        Log.d("tasdasd12qweqwe", url);
        tv_log.setText(url);
        final WebViewClient client = new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

        };
        mWebview.setWebViewClient(client);
        mWebview.loadUrl(url);

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @OnClick({R.id.edt_thang, R.id.edt_nam, R.id.tv_lich})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edt_thang:
                pickFilterThagg(edtThang);
                break;
            case R.id.tv_lich:
                WebSettings settings = mWebview.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setJavaScriptEnabled(true);
                settings.setDomStorageEnabled(true);
                settings.setBuiltInZoomControls(true);
                settings.setUseWideViewPort(true);
                settings.setLoadWithOverviewMode(true);
                mWebview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
                Calendar maxToStart = Calendar.getInstance();
                int nam = maxToStart.get(Calendar.YEAR);

                String url = "https://luong.vnpost.vn/Postman/DeliveryReport/report-detail-hrm-in-month/" + userInfo.getIsEms() + "?time=%22" + edtNam.getText().toString() + "-" + edtThang.getText() + "%22";
                Log.d("tasdasd12qweqwe", url);
                tv_log.setText(url);
                final WebViewClient client = new WebViewClient() {

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return false;
                    }

                };
                mWebview.setWebViewClient(client);
                mWebview.loadUrl(url);
                break;
            case R.id.edt_nam:
                pickFilter(edtNam);
                break;
        }
    }

    private void pickFilter(View anchor) {
        Context wrapper = new ContextThemeWrapper(getContext(), R.style.popupMenuStyle);
        Calendar maxToStart = Calendar.getInstance();
        int nam = maxToStart.get(Calendar.YEAR);
        PopupMenu popupMenu = new PopupMenu(wrapper, anchor);
        popupMenu.getMenu().add(nam + "");
        for (int i = 1; i < 5; i++) {
            nam--;
            popupMenu.getMenu().add(nam + "");
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            edtNam.setText(item.getTitle());
            return true;
        });
        popupMenu.show();
    }

    private void pickFilterThagg(View anchor) {
        Context wrapper = new ContextThemeWrapper(getContext(), R.style.popupMenuStyle);

        PopupMenu popupMenu = new PopupMenu(wrapper, anchor);
        popupMenu.getMenu().add("01");
        popupMenu.getMenu().add("02");
        popupMenu.getMenu().add("03");
        popupMenu.getMenu().add("04");
        popupMenu.getMenu().add("05");
        popupMenu.getMenu().add("06");
        popupMenu.getMenu().add("07");
        popupMenu.getMenu().add("08");
        popupMenu.getMenu().add("09");
        popupMenu.getMenu().add("10");
        popupMenu.getMenu().add("11");
        popupMenu.getMenu().add("12");
        popupMenu.setOnMenuItemClickListener(item -> {
            edtThang.setText(item.getTitle());
            return true;
        });
        popupMenu.show();
    }
}
