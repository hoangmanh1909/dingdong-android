package com.ems.dingdong.functions.mainhome.phathang.scanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.utiles.Log;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import butterknife.BindView;

/**
 * The ScannerCode Fragment
 */
public class ScannerCodeFragment extends ViewFragment<ScannerCodeContract.Presenter>
        implements ScannerCodeContract.View {

    @BindView(R.id.camera_view)
    SurfaceView cameraView;
    @BindView(R.id.img_back)
    View imgBack;
    @BindView(R.id.line)
    View line;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private volatile boolean isFirstReturned = false;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 103;

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
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (((DingDongActivity) getActivity()).getSupportActionBar() != null)
            ((DingDongActivity) getActivity()).getSupportActionBar().hide();
        imgBack.setOnClickListener(v -> mPresenter.back());
        checkSelfPermission();
    }

    private void initCamera() {
        barcodeDetector = new BarcodeDetector.Builder(getViewContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        cameraSource = new CameraSource
                .Builder(getViewContext(), barcodeDetector)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraView.getHolder());
                    setAnimation();
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (!isFirstReturned && barcodes.size() > 0) {
                    isFirstReturned = true;
                    new Handler(Looper.getMainLooper()).post(() -> {
                        mPresenter.getDelegate().scanQrcodeResponse(barcodes.valueAt(0).displayValue);
                        mPresenter.back();
                        cameraSource.stop();
                        barcodeDetector.release();
                        cameraSource.release();
                    });
                }
            }
        });
    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getViewContext().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            } else {
                initCamera();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (permissions[0].equals(Manifest.permission.CAMERA)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initCamera();
                try {
                    cameraSource.start(cameraView.getHolder());
                    setAnimation();
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }
        }
    }

    private void setAnimation() {
        final Animation anim = AnimationUtils.loadAnimation(getViewContext(),
                R.anim.move);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                line.startAnimation(anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        line.startAnimation(anim);
    }
}
