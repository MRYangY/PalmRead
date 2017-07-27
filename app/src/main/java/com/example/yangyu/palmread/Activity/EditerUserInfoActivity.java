package com.example.yangyu.palmread.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.Fragment.EditerUserProfireDialog;
import com.example.yangyu.palmread.R;
import com.example.yangyu.palmread.Util.CommonUtils;
import com.example.yangyu.palmread.Util.SharePreferenceUtils;
import com.example.yangyu.palmread.Util.ToastUtils;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yangyu on 17/3/18.
 */

public class EditerUserInfoActivity extends AppCompatActivity implements EditerUserProfireDialog.DialogFragmentDataImp {

    private View settingTop;
    private ImageView back;
    private TextView editor;
    private LinearLayout name;
    private LinearLayout introduce;
    private TextView infoName;
    private TextView infoIntroduce;
    private LinearLayout photo;
    private PopupWindow popupWindow;
    private CircleImageView editorPhoto;

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editer_user_info);
        settingTop = findViewById(R.id.setting_top);
        back = (ImageView) settingTop.findViewById(R.id.back);
        editor = (TextView) settingTop.findViewById(R.id.editor);
        name = (LinearLayout) findViewById(R.id.name);
        infoName = (TextView) name.findViewById(R.id.info_name);
        introduce = (LinearLayout) findViewById(R.id.jieshao);
        infoIntroduce = (TextView) introduce.findViewById(R.id.info_introduce);
        photo = (LinearLayout) findViewById(R.id.photo);
        editorPhoto = (CircleImageView) photo.findViewById(R.id.editer_photo);
        initData();
    }

    private void initData() {
        editor.setText("编辑资料");
//        Bitmap avatar = CommonUtils.restoreBitmap("user_avatar");
        String sinfoPic = SharePreferenceUtils.getStringData(this, ProjectContent.USER_INFO_PIC);
        if (sinfoPic != null) {
            Glide.with(this).load(sinfoPic).into(editorPhoto);
        } else {
            editorPhoto.setBackgroundResource(R.drawable.user_photo);
        }
        String sinfoName = SharePreferenceUtils.getStringData(EditerUserInfoActivity.this, ProjectContent.USER_INFO_NAME);
        String sinfoIntroduce = SharePreferenceUtils.getStringData(EditerUserInfoActivity.this, ProjectContent.USER_INFO_INTRODUCE);
        if (sinfoName != null) {
            infoName.setText(sinfoName);
        } else {
            infoName.setText("--");
        }
        if (sinfoIntroduce != null) {
            infoIntroduce.setText(sinfoIntroduce);
        } else {
            infoIntroduce.setText("--");
        }
        back.setOnClickListener(mBackListener);
        name.setOnClickListener(mNameListener);
        introduce.setOnClickListener(mIntroduceListener);
        photo.setOnClickListener(mAvatorListener);
    }

    private View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener mNameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showEditDialog("name");
        }
    };

    private View.OnClickListener mIntroduceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showEditDialog("introduce");
        }
    };

    private View.OnClickListener mAvatorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPop();
        }
    };

    public void showEditDialog(String type) {
        android.support.v4.app.FragmentTransaction mFragTransaction = getSupportFragmentManager().beginTransaction();
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag("dialogFragment");
        if (fragment != null) {
            //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
            mFragTransaction.remove(fragment);
        }
        EditerUserProfireDialog dialogFragment = EditerUserProfireDialog.newInstance(type);
        dialogFragment.show(mFragTransaction, "dialogFragment");//显示一个Fragment并且给该Fragment添加一个Tag，可通过findFragmentByTag找到该Fragment
    }


    @Override
    public void setInfoName(String message) {
        infoName.setText(message);
        SharePreferenceUtils.setStringData(EditerUserInfoActivity.this, message, ProjectContent.USER_INFO_NAME);
    }

    @Override
    public void setInfoIntroduce(String message) {
        infoIntroduce.setText(message);
        SharePreferenceUtils.setStringData(EditerUserInfoActivity.this, message, ProjectContent.USER_INFO_INTRODUCE);
    }

    public void showPop() {
        popupWindow = new PopupWindow(EditerUserInfoActivity.this);
        View view = LayoutInflater.from(EditerUserInfoActivity.this).inflate(R.layout.layout_pop, null);
        TextView popCamera = (TextView) view.findViewById(R.id.from_camera);
        TextView popPhoto = (TextView) view.findViewById(R.id.from_photo);
        popCamera.setOnClickListener(popCameraListener);
        popPhoto.setOnClickListener(popPhotoListener);
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View rootview = LayoutInflater.from(EditerUserInfoActivity.this).inflate(R.layout.activity_editer_user_info, null);
        popupWindow.setAnimationStyle(R.style.popAnimStyle);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    private View.OnClickListener popCameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(EditerUserInfoActivity.this
                    , Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditerUserInfoActivity.this
                        , new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                goCamara();
            }
        }
    };

    private View.OnClickListener popPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent openAlbumIntent = new Intent(
                    Intent.ACTION_GET_CONTENT);
            openAlbumIntent.setType("image/*");
            startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
            popupWindow.dismiss();
        }
    };

    private void goCamara() {
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        tempUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), "image.jpg"));
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
        popupWindow.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goCamara();
                } else {
                    ToastUtils.TipToast(EditerUserInfoActivity.this, "你拒绝了相机权限");
                }
                break;
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
//            photo = Utils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
            editorPhoto.setImageBitmap(photo);
            CommonUtils.saveMyBitmap(photo, "user_avatar");
        }
    }
}
