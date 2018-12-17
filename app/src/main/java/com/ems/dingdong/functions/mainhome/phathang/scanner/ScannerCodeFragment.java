package com.ems.dingdong.functions.mainhome.phathang.scanner;

import android.view.View;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.base.DingDongActivity;

import butterknife.BindView;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * The ScannerCode Fragment
 */
public class ScannerCodeFragment extends ViewFragment<ScannerCodeContract.Presenter> implements ScannerCodeContract.View, ZBarScannerView.ResultHandler {

    @BindView(R.id.camera_view)
    ZBarScannerView cameraView;
    @BindView(R.id.img_back)
    View imgBack;

    public static ScannerCodeFragment getInstance() {
        return new ScannerCodeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scanner_code;
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        cameraView.setResultHandler(this); // Register ourselves as a handler for scan results.
        cameraView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (((DingDongActivity) getActivity()).getSupportActionBar() != null)
            ((DingDongActivity) getActivity()).getSupportActionBar().hide();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.back();
            }
        });
    }

    @Override
    public void handleResult(Result result) {
        mPresenter.getDelegate().scanQrcodeResponse(result.getContents());
        mPresenter.back();
    }
}
