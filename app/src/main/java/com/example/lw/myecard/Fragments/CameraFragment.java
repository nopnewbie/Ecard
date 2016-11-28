package com.example.lw.myecard.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lw.myecard.Activities.PersonalCenterActivity;
import com.example.lw.myecard.R;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lw on 2016/11/26.
 */

public class CameraFragment extends Fragment {

    private static final int TAKE_PHOTO = 1;

    private ImageView mImageView;
    private Uri mImageUri;

    private Button mUploadButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        mImageView = (ImageView) view.findViewById(R.id.portrait_image_view);

        mUploadButton = (Button) view.findViewById(R.id.upload_picture_button);
       // mUploadButton.setEnabled(false);
        mUploadButton.setVisibility(View.INVISIBLE);
        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upload photo to the server.
                Toast.makeText(getActivity(), "Picture has been uploaded.", Toast.LENGTH_SHORT).show();
            }
        });

        Button button = (Button) view.findViewById(R.id.take_picture_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage = new File(Environment.getExternalStorageDirectory(), "tempImage.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mImageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        /**
         * here you should take a picture and post it to the server
         * */
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if(resultCode == Activity.RESULT_OK) {
                    Bitmap bitmap = getScaledBitmap(mImageUri.getPath(), 400, 400);
                    mImageView.setImageBitmap(bitmap);
                   // mUploadButton.setEnabled(true);
                    mUploadButton.setVisibility(View.VISIBLE);
                }
                break;

            default:
                break;
        }
    }

    private Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        if(srcWidth > destWidth || srcHeight > destHeight) {
            if(srcWidth > destWidth) {
                inSampleSize = Math.round(srcWidth / destWidth);
            } else {
                inSampleSize = Math.round(srcHeight / destHeight);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path, options);
    }

}
