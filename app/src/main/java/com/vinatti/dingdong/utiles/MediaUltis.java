package com.vinatti.dingdong.utiles;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialcamera.MaterialCamera;
import com.vinatti.dingdong.R;

import java.io.File;

public class MediaUltis {
    public static void captureImage(Fragment fragment) {
        File saveDir = null;
        if (ContextCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            saveDir = new File(Environment.getExternalStorageDirectory(), "MaterialCamera");
            saveDir.mkdirs();
        }
        MaterialCamera materialCamera = new MaterialCamera(fragment)
                .saveDir(saveDir)
                .showPortraitWarning(true)
                .allowRetry(true)
                .defaultToFrontFacing(false);

        materialCamera.stillShot(); // launches the Camera in stillshot mode
        materialCamera.labelConfirm(R.string.cam_use_capture);
        materialCamera.labelRetry(R.string.retry);
        materialCamera.start(Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /**
     * @param fragment ->return requestCode : #Constants.CAMERA_CAPTURE_VIDEO_REQUEST_CODE
     */
    public static void recordVideo(Fragment fragment) {
        File saveDir = null;
        if (ContextCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            saveDir = new File(Environment.getExternalStorageDirectory(), "MaterialCamera");
            saveDir.mkdirs();
        }
        MaterialCamera materialCamera = new MaterialCamera(fragment)
                .saveDir(saveDir)
                .showPortraitWarning(true)
                .allowRetry(true)
                .defaultToFrontFacing(true);
        materialCamera.start(Constants.CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    /**
     * take image and video
     *
     * @param context -> Activity or Fragment
     *                return requestCode : #Constants.CHOOSE_IMAGE_OR_VIDEO_REQUEST_CODE
     */
    public static void choseImageAndVideo(Fragment context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/* video/*");
            context.startActivityForResult(intent, Constants.CHOOSE_IMAGE_OR_VIDEO_REQUEST_CODE);
        } else {
            String manufacturer = Build.MANUFACTURER;
            if (Constants.MANUFACTURE_XIAOMI.equals(manufacturer)) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*  video/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
                context.startActivityForResult(Intent.createChooser(intent, "Select File"), Constants.CHOOSE_IMAGE_OR_VIDEO_REQUEST_CODE);
            } else {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
                context.startActivityForResult(intent, Constants.CHOOSE_IMAGE_OR_VIDEO_REQUEST_CODE);

            }
        }
    }

    /**
     * take file
     *
     * @param fragment -> Fragment
     *                 return requestCode : #Constants.CHOOSE_FILE_REQUEST_CODE
     */
    public static void chooseFile(Fragment fragment) {
        Intent action = new Intent(Intent.ACTION_GET_CONTENT);
        action = action.setType("*/*").addCategory(Intent.CATEGORY_OPENABLE);
        action.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        fragment.startActivityForResult(Intent.createChooser(action, "Upload file from..."), Constants.CHOOSE_FILE_REQUEST_CODE);
    }

    /**
     * @param fragment return requestCode : #Constants.CHOOSE_IMAGE_REQUEST_CODE
     */
    public static void loadImageFromGallery(Fragment fragment) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        fragment.startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                Constants.CHOOSE_IMAGE_REQUEST_CODE);
    }
}
