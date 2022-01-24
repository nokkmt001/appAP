package com.tiha.admin.anphat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageUtils {


    public static Bitmap optimizeBitmap(int sampleSize, String filePath, Context mContext) {
        try {
            File f = new File(filePath);

            ExifInterface exif = new ExifInterface(f.getPath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            int angle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                angle = 90;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                angle = 180;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                angle = 270;
            }
            Matrix mat = new Matrix();
            mat.postRotate(angle);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = sampleSize;
//            options.inJustDecodeBounds = false;
//            options.inPurgeable = true;
//            Bitmap bitmapDecode = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            Uri uriPicture = Uri.fromFile(f);
            Bitmap bitmapDecode = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uriPicture);
            Bitmap bitmapHinhAnh = Bitmap.createBitmap(bitmapDecode, 0, 0, bitmapDecode.getWidth(), bitmapDecode.getHeight(), mat, true);
            return AppUtils.resizeBitmap(bitmapHinhAnh, AppConstants.MAX_WIDTH_IMAGE, AppConstants.MAX_HEIGHT_IMAGE);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError oom) {
            oom.printStackTrace();
            return null;
        }
    }

    public static void refreshGallery(Context mContext, String filePath) {
        // ScanFile so it will be appeared on Gallery
        MediaScannerConnection.scanFile(mContext,
                new String[]{filePath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    public static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "ANPHAT");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
//                Log.e(MainActivity.GALLERY_DIRECTORY_NAME, "Oops! Failed create "
//                        + MainActivity.GALLERY_DIRECTORY_NAME + " directory");
                //return null;
                mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "BMTIHAGAS");
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        return null;
                    }
                }
            }
        }

            // Preparing media file naming convention
            // adds timestamp
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss",
                    Locale.getDefault()).format(new Date());
            File mediaFile;

            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + "." + "PNG");


            return mediaFile;
        }

        public static Uri getOutputMediaFileUri (Context mContext, File file){
            return FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", file);
        }


    }
