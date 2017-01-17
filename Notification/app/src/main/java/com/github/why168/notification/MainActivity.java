package com.github.why168.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {
    private int count = 0;
    private boolean isPlayMusic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void simpleNotify(View view) {
        count++;

//        PendingIntent.getBroadcast();
//        PendingIntent.getService();
//        PendingIntent.getActivities();
//        PendingIntent.getActivity();

        PendingIntent activities = PendingIntent.getActivity(getApplicationContext()
                , 1
                , new Intent(getApplicationContext(), ResultActivity.class)
                , Intent.FILL_IN_ACTION);


        RemoteInput remoteInput = new RemoteInput.Builder("")
                .setLabel("label")
                .build();

        // Build an Android N compatible Remote Input enabled action.
        NotificationCompat.Action actionReplyByRemoteInput = new NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher, "输入框", activities)
                .addRemoteInput(remoteInput)
                .build();

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.item_remote_view);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVisibility(View.VISIBLE)
                .setContentTitle("My notification")//标题
                .setContentText("Hello World!")//正文
                .setSubText("SubText")//设置在平台通知模板文本的第三行
                .setRemoteInputHistory(new CharSequence[]{"1", "2", "3"})//设置远程输入历史。
                .setContent(remoteViews)//提供一个定制RemoteViews，而不是使用标准之一。
                .setDeleteIntent(activities)//供应PendingIntent时通报用户直接从通知面板清除发送。
                .setFullScreenIntent(activities, false)//意图发动，而不是张贴通知状态栏。
                .setTicker("Tocker")//设置显示在状态栏时通报首先到达的文本。
                .setSortKey("排序键")//设置听命于同一包内的其他通知中此通知的排序键
                .setOnlyAlertOnce(false)//设置此标志，如果你只喜欢的声音，震动和股票要如果通知尚未显示播放。
                .setPriority(NotificationCompat.PRIORITY_MAX)//设置相对优先级此通知。
                .setColor(2554444)//颜色
                .setCategory(NotificationCompat.CATEGORY_PROMO)//设置的通知类别。
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.android_contact))//设置大图标所示股票和通知。
                .setNumber(3)//设置信息条数
                .setContentInfo("在右边设置大型文本的通知")
                .setWhen(System.currentTimeMillis())//设置时间
                .setShowWhen(true)//现实When
                .setContentIntent(activities)//设置Intent
                .setVibrate(new long[]{3000, 1000, 3000, 1000})//震动
                .setLights(0xff0000ff, 300, 0)//闪光灯
                .setAutoCancel(true)//点击之后自动消失
                .setOngoing(false)//用户不能手动清除
                .setProgress(100, 50, false)//进度条
                .addAction(actionReplyByRemoteInput);


        if (isPlayMusic)
            mBuilder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.actor));//设置声音播放。它将在默认流上播放。
        else
            mBuilder.setDefaults(Notification.DEFAULT_ALL);//设置将要使用的默认通知选项。

        NotificationManagerCompat.from(getApplicationContext()).notify(count, mBuilder.build());
    }
}