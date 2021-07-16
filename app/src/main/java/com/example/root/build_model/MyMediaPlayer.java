package com.example.root.build_model;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import androidx.core.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.root.build_model.data.MusicFile;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyMediaPlayer extends MediaPlayer {
    //将同步内容下方到if内部，提高了执行的效率，不必每次获取对象时都进行同步，只有第一次才同步，创建了以后就没必要了。
    private static volatile MyMediaPlayer instance = null;
    boolean isPlay;
    private int mode;
    private String path;
    private MyMediaPlayer (){

    }
    public static  MyMediaPlayer getInstance(){
        if(instance==null){
            synchronized(MyMediaPlayer.class){
                if(instance==null){
                    instance=new MyMediaPlayer();
                }
            }
        }
        return instance;
    }

    public void setProcessHandler(ProcessHandler processHandler) {
        this.processHandler = processHandler;
    }

    ProcessHandler processHandler;


    public void setmMainObserver(Observer<Long> mMainObserver) {
        this.mMainObserver = mMainObserver;
    }

    private io.reactivex.Observer<Long> mMainObserver;

    public void setmPlayObserver(Observer<Long> mPlayObserver) {
        this.mPlayObserver = mPlayObserver;
    }


    private io.reactivex.Observer<Long> mPlayObserver;

    public Observable getmObservable() {
        return mObservable;
    }

    private io.reactivex.Observable mObservable=  io.reactivex.Observable.interval(1000, TimeUnit.MILLISECONDS);
    private void startCountTime(){
        mObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(mPlayObserver);
        mObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(mMainObserver);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setSongPath(String s) {
        path = s;
    }

    static class ProcessHandler extends Handler{
        WeakReference<MyMediaPlayer> myMediaPlayerWeakReference = null;

        public ProcessHandler(MyMediaPlayer myMediaPlayer) {
            myMediaPlayerWeakReference = new WeakReference<MyMediaPlayer>(myMediaPlayer);
        }

        @Override
        public void handleMessage(Message msg) {
            MyMediaPlayer mediaPlayer = null;
            if(myMediaPlayerWeakReference != null){
                mediaPlayer = myMediaPlayerWeakReference.get();
            }
            switch (msg.what){
                case Intents.MUSIC_PLAY_START:
                    if(!mediaPlayer.isPlaying()){
                        mediaPlayer.start();
                        mediaPlayer.isPlay = true;
                        if(mediaPlayer.mContext!=null){
                            Intent intent = new Intent(Intents.ACTION_PLAY);
                            mediaPlayer.mContext.sendBroadcast(intent);
                        }
                    }
                    mediaPlayer.startCountTime();
                    break;
                    case Intents.MUSIC_PLAY_PAUSE:
                        if(mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                        mediaPlayer.isPlay = false;

                        break;
                        case Intents.MUSIC_PLAY_STOP:
                            if(mediaPlayer.isPlaying()){
                                mediaPlayer.stop();
                                mediaPlayer.isPlay = false;
                            }
                            break;
                case Intents.MUSIC_PLAY_FINISH:

                    break;

            }
            super.handleMessage(msg);
        }
    }
    private NotificationManager manager;
    private RemoteViews remoteViews;
    private Context mContext;
    public void createNotificaton(Context context, MusicFile musicFile) {
        if(mContext == null)
            mContext = context;
        manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        // 设置通知栏的图片文字
        remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.customnotice);
        if(musicFile.getName().contains("-")){
            String[] tmp = musicFile.getName().split("-");
            remoteViews.setTextViewText(R.id.widget_title,tmp[1].replace(".mp3", ""));
            remoteViews.setTextViewText(R.id.widget_artist, tmp[0]);
        }else{
            remoteViews.setTextViewText(R.id.widget_title,musicFile.getName().replace(".mp3", ""));
            remoteViews.setTextViewText(R.id.widget_artist, "");

        }
        remoteViews.setImageViewResource(R.id.widget_album, R.drawable.music_bg);

        if (this.isPlay) {
            remoteViews.setImageViewResource(R.id.widget_play, R.drawable.ic_pause_black_24dp);
        }else {
            remoteViews.setImageViewResource(R.id.widget_play, R.drawable.ic_play_arrow_black_24dp);
        }

        if (musicFile.getIsFavTime()>0) {
            remoteViews.setImageViewResource(R.id.widget_fav, R.drawable.ic_favorite_black_24dp);
        }else {
            remoteViews.setImageViewResource(R.id.widget_fav, R.drawable.ic_favorite_border_black_24dp);
        }
        setNotification(context,false,musicFile.getName());
    }
    public void createNotificaton(Context context, String songName,String artistName) {
        if(mContext == null)
            mContext = context;

        manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        // 设置通知栏的图片文字
        remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.customnotice);
            remoteViews.setTextViewText(R.id.widget_title,songName );
            remoteViews.setTextViewText(R.id.widget_artist, artistName);
        remoteViews.setImageViewResource(R.id.widget_album, R.drawable.music_bg);

        if (this.isPlay) {
            remoteViews.setImageViewResource(R.id.widget_play, R.drawable.ic_pause_black_24dp);
        }else {
            remoteViews.setImageViewResource(R.id.widget_play, R.drawable.ic_play_arrow_black_24dp);
        }

//        if (musicFile.getIsFavorite()) {
//            remoteViews.setImageViewResource(R.id.widget_fav, R.drawable.ic_favorite_black_24dp);
//        }else {
            remoteViews.setImageViewResource(R.id.widget_fav, R.drawable.ic_favorite_border_black_24dp);
//        }
        setNotification(context,true,songName);
    }

    private void setNotification(Context context,boolean isOnline,String songname) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Intent intent = new Intent(context, MainActivity.class);
        // 点击跳转到主界面
        PendingIntent intent_go = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notice, intent_go);

        // 4个参数context, requestCode, intent, flags
        PendingIntent intent_close = PendingIntent.getActivity(context, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_close, intent_close);

        // 设置上一曲
        Intent prv = new Intent();
        prv.setAction(Intents.ACTION_PLAY_PREVIOUS);
        PendingIntent intent_prev = PendingIntent.getBroadcast(context, 2, prv,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_prev, intent_prev);

        // 设置播放
        if (this.isPlay) {
            Intent playorpause = new Intent();
            playorpause.setAction(Intents.ACTION_PAUSE);
            PendingIntent intent_play = PendingIntent.getBroadcast(context, 3,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_play, intent_play);
        }
        if (!this.isPlay) {
            Intent playorpause = new Intent();
            playorpause.setAction(Intents.ACTION_PLAY);
            PendingIntent intent_play = PendingIntent.getBroadcast(context, 4,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_play, intent_play);
        }

        // 下一曲
        Intent next = new Intent();
        next.setAction(Intents.ACTION_PLAY_NEXT);
        PendingIntent intent_next = PendingIntent.getBroadcast(context, 5, next,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_next, intent_next);

        // 设置收藏
        Intent fav = new Intent();
        fav.setAction(Intents.ACTION_FAVOUR);
        fav.putExtra("songname", songname);
        fav.putExtra("songpath", path);

        PendingIntent intent_fav = PendingIntent.getBroadcast(context, 6, fav,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_fav, intent_fav);
        // 设置
        Intent exit = new Intent();
        exit.setAction(Intents.ACTION_EXIT_APP);
        PendingIntent intent_exit = PendingIntent.getBroadcast(context, 7, exit,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_close, intent_exit);

        builder.setSmallIcon(R.drawable.ic_expand_more_black_24dp); // 设置顶部图标
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("123", "147", NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(mChannel);
            notify = new Notification.Builder(context)
                    .setChannelId("123")
//                    .setContentTitle("5 new messages")
//                    .setContentText("hahaha")
                    .setContent(remoteViews)
                    .setSmallIcon(R.mipmap.ic_launcher).build();
        }else {
            notify = builder.build();
            notify.contentView = remoteViews; // 设置下拉图标
            notify.bigContentView = remoteViews; // 防止显示不完全,需要添加apisupport
            notify.flags = Notification.FLAG_ONGOING_EVENT;
            notify.icon = R.drawable.music_bg;
        }
        manager.notify(100, notify);
    }
    Notification notify;
}
