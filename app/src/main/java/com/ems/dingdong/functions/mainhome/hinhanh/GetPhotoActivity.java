package com.ems.dingdong.functions.mainhome.hinhanh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetPhotoActivity {
//    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 50;
//    public static final int MEDIA_TYPE_IMAGE = 1;
//    private Uri _fileUri;
//
//    private void TakePictureIntent() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        _fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, _fileUri);
//        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        try {
//            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//                if (resultCode == RESULT_OK) {
//                    _imageView.setImageBitmap(DisplayRotatedPhoto(_fileUri));
//                } else if (resultCode == RESULT_CANCELED) {
//                    Intent intent = new Intent(GetPhotoActivity.this, NewNoteActivity.class);
//                    intent.putExtra("Project_ID", _projectID);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(this, "An error has occurred...", Toast.LENGTH_LONG).show();
//                }
//            }
//        } catch (Exception e) {
//            Toast.makeText(this, "An error has occurred...", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private Bitmap DisplayRotatedPhoto(Uri path) {
//        String filePath = path.toString().substring(8);
//        Bitmap oriented = null;
//        BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
//        bitmapFactoryOptions.inJustDecodeBounds = true;
//
//        int REQUIRED_SIZE = 100;
//        int width_tmp = bitmapFactoryOptions.outWidth, height_tmp = bitmapFactoryOptions.outHeight;
//        int scale = 2;
//        while (true) {
//            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
//                break;
//            width_tmp /= 2;
//            height_tmp /= 2;
//            scale++;
//        }
//        BitmapFactory.Options bitmapFactoryOptions2 = new BitmapFactory.Options();
//        bitmapFactoryOptions2.inSampleSize = scale;
//        Bitmap bmp = BitmapFactory.decodeFile(filePath, bitmapFactoryOptions2);
//
//        try {
//            ExifInterface exif = new ExifInterface(filePath);
//            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
//            Matrix m = new Matrix();
//
//            switch (orientation) {
//                case 2:
//                    m.setScale(-1, 1);
//                    break;
//                case 3:
//                    m.setRotate(180);
//                    break;
//                case 4:
//                    m.setRotate(180);
//                    m.setScale(-1, 1);
//                    break;
//                case 5:
//                    m.setRotate(90);
//                    m.setScale(-1, 1);
//                    break;
//                case 6:
//                    m.setRotate(90);
//                    break;
//                case 7:
//                    m.setRotate(-90);
//                    m.setScale(-1, 1);
//                    break;
//                case 8:
//                    m.setRotate(-90);
//                    break;
//                default:
//                    m.setScale(1, 1);
//                    break;
//            }
//
//            oriented = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
//
//        } catch (IOException e) {
//            Toast.makeText(this, "An error has occurred...", Toast.LENGTH_LONG).show();
//        } finally {
//            return oriented;
//        }
//    }
//
//    private Uri getOutputMediaFileUri(int type) {
//        return Uri.fromFile(getOutputMediaFile(type));
//    }
//
//    /**
//     * Create a File for saving an image or video
//     * @return
//     */
//    private File getOutputMediaFile(int type) {
//
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
//        if (type == MEDIA_TYPE_IMAGE) {
//
//            _mediaFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), File.separator + "IMG_" + timeStamp + ".jpg");
//        } else {
//            return null;
//        }
//        return _mediaFile;
//    }
}
