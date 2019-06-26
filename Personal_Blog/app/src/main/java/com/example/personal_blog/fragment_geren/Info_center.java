package com.example.personal_blog.fragment_geren;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.personal_blog.MainActivity;
import com.example.personal_blog.R;

import java.io.File;
import java.util.List;

public class Info_center extends AppCompatActivity {
    private Button btn_logout,qiandao;
    private ImageButton xiugaiInfo,back;
    private ImageButton update_info;
    private TextView yonghuming,gerenjieshao;
    private ImageButton help,about;
    private Context mContext;
    private NotificationManager notificationManager;
    private Notification notification1;
    private CircleImageView circle;
    Bitmap LargeBitmap = null;
    private static final int NOTIFYID_1 = 1;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_center);
        btn_logout=(Button)findViewById(R.id.bt_main_logout);
        update_info=(ImageButton)findViewById(R.id.update_info);
        qiandao=(Button)findViewById(R.id.qiandao);
        yonghuming=(TextView) findViewById(R.id.yonghuming);
        gerenjieshao=(TextView)findViewById(R.id.gerenjieshao);
        xiugaiInfo=(ImageButton) findViewById(R.id.xiugai_Info);
        help=(ImageButton)findViewById(R.id.help);
        about=(ImageButton)findViewById(R.id.about) ;
        circle=(CircleImageView)findViewById(R.id.circle);
        back=(ImageButton)findViewById(R.id.back);
        mContext = Info_center.this;
        //获得通知消息管理类对象
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //获取大图标
        LargeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        user_database db=new user_database(Info_center.this);
        // String username=editText2.getText().toString();
        SQLiteDatabase sqLiteDatabase=db.getWritableDatabase();
        List<userInfo> data = db.querydata(sqLiteDatabase);
        int i=data.size()-1;
        userInfo user = data.get(i);
        String name=user.getUsername();
        String intro=user.getIntro();
        yonghuming.setText(name);
        gerenjieshao.setText(intro);

        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Info_center.this,update_Info.class);
                startActivity(intent);

            }
        });
        qiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "channel_001";
                String name = "name";
                qiandao.setText("已签到");
                NotificationManager notificationManager = (NotificationManager) Info_center.this.getSystemService(NOTIFICATION_SERVICE);
                Notification notification = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
                    notificationManager.createNotificationChannel(mChannel);
                    notification = new Notification.Builder(Info_center.this)
                            .setChannelId(id)
                            .setContentTitle("提醒：今日已签到")
                            .setWhen(System.currentTimeMillis())
                            .setContentText("")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .build();
                } else {
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(Info_center.this)
                            .setContentTitle("提醒：今日已签到")
                            .setWhen(System.currentTimeMillis())
                            .setContentText("")
                            .setSmallIcon(R.drawable.bing06)
                            .setOngoing(true)
                            .setChannelId(id);//无效
                    notification = notificationBuilder.build();

                    //Intent resultIntent = new Intent(MainActivity.this,test.class);

                    //PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                    //notificationBuilder.setContentIntent(pendingIntent);
                    notification = notificationBuilder.build();
                    notification.flags = Notification.FLAG_ONGOING_EVENT;
                    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                }
                notificationManager.notify(1, notification);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Info_center.this).setTitle("系统提示").setMessage("确认退出吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Info_center.this, login.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

            }
        });
        xiugaiInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Info_center.this,personal_homepage.class);
                startActivity(intent);

            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Info_center.this, com.example.personal_blog.fragment_geren.help.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Info_center.this, com.example.personal_blog.fragment_geren.about.class);
                startActivity(intent);
            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Info_center.this);
                builder.setTitle("设置头像");
                String[] items = { "选择本地照片", "拍照" };
                builder.setNegativeButton("取消", null);
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case CHOOSE_PICTURE: // 选择本地照片
                                Intent openAlbumIntent = new Intent(
                                        Intent.ACTION_GET_CONTENT);
                                openAlbumIntent.setType("image/*");
                                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                                break;
                            case TAKE_PICTURE: // 拍照
                                Intent openCameraIntent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                tempUri = Uri.fromFile(new File(Environment
                                        .getExternalStorageDirectory(), "image.jpg"));
                                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Info_center.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
     *
     * @param
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            circle.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...
            ;
        }
    }
}
