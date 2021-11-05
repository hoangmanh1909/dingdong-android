package com.ems.dingdong.functions.mainhome.scannerv1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.base.DingDongActivity;
import com.google.zxing.Result;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * The ScannerCode Fragment
 */
public class ScannerCodeV1Fragment extends ViewFragment<ScannerCodeV1Contract.Presenter> implements ScannerCodeV1Contract.View, ZBarScannerView.ResultHandler {

    @BindView(R.id.scanner_view1)
    CodeScannerView scannerView;
    private CodeScanner mCodeScanner;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    public static ScannerCodeV1Fragment getInstance() {
        return new ScannerCodeV1Fragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.qr_code;
    }


    @Override
    public void onDisplay() {
        super.onDisplay();
        checkSelfPermission();
        mCodeScanner = new CodeScanner(getViewContext(), scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        mPresenter.getDelegate().scanQrcodeResponse(result.getText());
//                        mPresenter.back();

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("OnResume", "OnResume");
        mCodeScanner.startPreview();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void initLayout() {
        super.initLayout();
        checkSelfPermission();
        if (((DingDongActivity) getActivity()).getSupportActionBar() != null)
            ((DingDongActivity) getActivity()).getSupportActionBar().hide();
        checkSelfPermission();
        mCodeScanner = new CodeScanner(getViewContext(), scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.getDelegate().scanQrcodeResponse(result.getText());
                        mPresenter.back();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }
//
//    @Override
//    public void handleResult(Result result) {
//        mPresenter.getDelegate().scanQrcodeResponse(result.getContents());
//        mPresenter.back();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                mCodeScanner.startPreview();
            }
        }
    }

    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {

    }
}
