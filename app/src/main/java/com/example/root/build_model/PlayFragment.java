package com.example.root.build_model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.root.build_model.data.FavourMusics;
import com.example.root.build_model.data.MusicFile;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayFragment} interface
 * to handle interaction events.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SQLiteUtils mSQLiteUtils;
    private List<MusicFile> list;
    @BindView(R.id.music_title)
    TextView musicTitle;
    @BindView(R.id.timese)
    TextView timeSe;
    @BindView(R.id.music_lrc)
    TextView musicLrc;
    @BindView(R.id.play_seekBar)
    SeekBar seekBar;
    private MyMediaPlayer myMediaPlayer;

    @BindView(R.id.music_lrc_all)
    LinearLayout musicLrcAll;
    @BindView(R.id.music_scrollview)
    ScrollView scrollView;
    @BindView(R.id.music_bg)
    ImageView musicBg;
    @BindView(R.id.play_mode)
    ImageView playMode;
    @BindView(R.id.favorite)
    ImageView favorite;
    private MyHandler myHandler;
    private MyMediaPlayer.ProcessHandler myMediaHandler;
    private localBroadCastRecevice networkChangeReceiver;
    private IntentFilter intentFilter;

    public PlayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayFragment newInstance(String param1, String param2) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onResume() {
        initData();
        initView();
        AppUtils.hideSoftInput(getActivity());
        super.onResume();
    }

    private MusicFile musicFile;
    private boolean isExitLrc;
    private void initView() {

        if(list.size()>0){
            if(BlankFragment.curPos == -1){
                musicFile = list.get(0);
            }else
            musicFile = list.get(BlankFragment.curPos);
            String name = musicFile.getName();
            myMediaHandler.sendEmptyMessage(Intents.MUSIC_PLAY_START);
            musicTitle.setText(name);
        String tmp = name.replace(".mp3","");
        String[] tmp1 = tmp.split("-");
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        if(tmp1.length>1){
            if(searchLyric1(tmp1[1].trim(),tmp1[0].trim())){
            if(lrcP.getLrcList() != null){
                isExitLrc = true;
            }
        }else if(searchLyric1(tmp1[0].trim(), tmp1[1].trim())){
            if(lrcP.getLrcList() != null){
                isExitLrc = true;
            }}
        }else {
            if (searchLyric1(tmp1[0].trim(), "")) {
                if (lrcP.getLrcList() != null) {
                    isExitLrc = true;
                }
            } else {
                reSearchLrc();
                isExitLrc = false;
            }
        }

        mode = myMediaPlayer.getMode();
        if(mode % 3 == 1) {
            playMode.setImageResource(R.drawable.ic_repeat_one_black_24dp);
        }else if(mode % 3 == 2){
            playMode.setImageResource(R.drawable.ic_shuffle_black_24dp);
        }else
            playMode.setImageResource(R.drawable.ic_repeat_black_24dp);
        myMediaPlayer.setmPlayObserver(mObserver);
        if(musicFile.getIsFavTime()!= 0){
            favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        }else{
            favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        }
    }

    private void reSearchLrc() {
//        Observable.in(1000, TimeUnit.MILLISECONDS, Schedulers.io());
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.e("", "onProgressChanged: ==========="+progress +"fromUser"+fromUser);
            if(fromUser)
            myMediaPlayer.seekTo(progress*1000);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.e("", "onStartTrackingTouch: ==========="+seekBar.getDrawingTime() );

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.e("", "onStopTrackingTouch: ==========="+seekBar.getDrawingTime() );

        }
    };
        private String getMusicTimeSe(long duration){
        SimpleDateFormat sdf=new SimpleDateFormat("mm:ss");
        return sdf.format(duration);
    }
    private void initData() {
        if(mSQLiteUtils == null){
            mSQLiteUtils = SQLiteUtils.getInstance();
        }
        list = mSQLiteUtils.selectAllMusic();
        if(myMediaPlayer == null)
        myMediaPlayer = MyMediaPlayer.getInstance();

        myMediaHandler = new MyMediaPlayer.ProcessHandler(myMediaPlayer);
        myHandler = new MyHandler(this);

        int i = myMediaPlayer.getCurrentPosition();
        networkChangeReceiver = new localBroadCastRecevice();
        //动态接受网络变化的广播接收器
        intentFilter = new IntentFilter();
//        intentFilter.addAction(Intents.MUSIC_PLAY_OVER);
        intentFilter.addAction(Intents.ACTION_PLAY_NEXT);
        intentFilter.addAction(Intents.ACTION_PLAY_PREVIOUS);
        intentFilter.addAction(Intents.ACTION_FAVOUR);
        intentFilter.addAction(Intents.ACTION_PLAY);
        getActivity().registerReceiver(networkChangeReceiver, intentFilter);
    }

    Disposable disposable;
        int mCurrentProgress = -1;
    private io.reactivex.Observable<Long> mObservable;
    private io.reactivex.Observer<Long>
            mObserver = new io.reactivex.Observer<Long>() {
        @Override
        public void onSubscribe(Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(Long aLong) {
            timeSe.setText(getMusicTimeSe(myMediaPlayer.getCurrentPosition())+"/"+getMusicTimeSe(musicFile.getMusicDuration()));
            if(lrcP != null && lrcP.getLrcList().size() > 0/* && isExitLrc*/){
            for (int i= 0;i<lrcP.getLrcList().size();i++){
                if(myMediaPlayer.getCurrentPosition()>lrcP.getLrcList().get(i).getLrcTime()
                        && i+1<lrcP.getLrcList().size()
//                        &&lrcP.getLrcList().get(i+1).getLrcTime()-lrcP.getLrcList().get(i).getLrcTime()>1000
                        && myMediaPlayer.getCurrentPosition()< lrcP.getLrcList().get(i+1).getLrcTime()
                        ) {
//                    scrollView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            scrollView.scrollTo(0, i*100);
                            scrollView.smoothScrollTo(0,i*120);
//                        }
//                    });
                    musicLrc.setText(lrcP.getLrcList().get(i).getLrcStr());
                }
            }}else{
                musicBg.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                musicLrc.setText("木有读取到歌词哦！");
            }
            mCurrentProgress = myMediaPlayer.getCurrentPosition();
            seekBar.setProgress(mCurrentProgress /1000);
            seekBar.setMax(musicFile.getMusicDuration()/1000);
            if(myMediaPlayer.getDuration() - myMediaPlayer.getCurrentPosition() < 1000)
                myHandler.sendEmptyMessageDelayed(Intents.MUSIC_PLAY_FINISH,1000);
        }


        @Override
        public void onError(Throwable e) {
            Log.e("", "onError: " );
        }

        @Override
        public void onComplete() {
            Log.e("", "onComplete: " );
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        getActivity().unregisterReceiver(networkChangeReceiver);
        super.onDetach();
    }

    private boolean isExitLrc(String lrcPath){
        File file = new File(lrcPath);
        if(file != null && file.exists()){
            return true;
        }
        return false;
    }
    public boolean searchLyric1(final String mMusicName,final String mMusicAutor){
        final OnlineLrcUtil onlineLrcUtil = OnlineLrcUtil.getInstance();
        final boolean[] b = new boolean[1];
        new Thread(){
            public void run() {
                String lrcURL = onlineLrcUtil.getLrcURL(mMusicName, mMusicAutor);
                Log.e("INFO", lrcURL + "lrcURL");
                //开始缓存歌词
                if(!isExitLrc(onlineLrcUtil.getLrcPath(mMusicName, mMusicAutor))) {
                    b[0] = onlineLrcUtil.wrtieContentFromUrl(lrcURL, onlineLrcUtil.getLrcPath(mMusicName, mMusicAutor));
                }
                Log.e("INFO", "缓存歌词"+ b[0]);
                Log.e("INFO", onlineLrcUtil.getLrcPath(mMusicName, mMusicAutor)+"地址");
                lrcP = new LrcProcess();
//            try {
//                String s = lrcP.readExternal(onlineLrcUtil.getLrcPath(mMusicName, mMusicAutor));
//                Log.e("", "run: ppppppppppppppppppppp="+s );
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
                lrcP.readLRC(onlineLrcUtil.getLrcPath(mMusicName, mMusicAutor));
                Log.e("INFO", "歌词"+lrcP.getLrcList().size());
                myHandler.sendEmptyMessage(Intents.MUSIC_SEARCH_LRC_FINISH);

            }
        }.start();
        return b[0];
    }
    LrcProcess lrcP ;
    static class MyHandler extends Handler{
        WeakReference<PlayFragment> weakReference;
        public MyHandler(PlayFragment playFragment) {
            weakReference = new WeakReference<PlayFragment>(playFragment);

        }

        @Override
        public void handleMessage(Message msg) {
            PlayFragment playFragment = null;
            if(weakReference != null)
                playFragment = weakReference.get();
            switch (msg.what){
                case Intents.MUSIC_PLAY_FINISH:
                    playFragment.setTimePlayOver();
                    playFragment.disposable.dispose();
                    break;
                    case Intents.MUSIC_SEARCH_LRC_FINISH:
                        playFragment.showAllLrc();
                        break;
            }
            super.handleMessage(msg);
        }
    }

    private void setTimePlayOver() {
        timeSe.setText(getMusicTimeSe(musicFile.getMusicDuration())+"/"+getMusicTimeSe(musicFile.getMusicDuration()));
    }

    private void showAllLrc(){
        if(lrcP.getLrcList().size()>0){
        musicBg.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        }
        String tmp = "";
        musicLrcAll.removeAllViews();
        if(lrcP.getLrcList().size()>0)
        for(int i= 0; i<lrcP.getLrcList().size();i++){
            LrcContent lrcContent = lrcP.getLrcList().get(i);
            String s = lrcContent.getLrcStr();
            if(s.equals(tmp))continue;
            TextView textView = new TextView(getContext());
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            textView.setText(s);
            textView.setPadding(0,30,0,30);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextSize(20);
            musicLrcAll.addView(textView);
            tmp = s;
        }
    }
    public class localBroadCastRecevice extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction())
            {
//                case Intents.ACTION_FAVOUR:
//
//                    break;
                    default:
                        musicTitle.setText("waiting ...");
                        initData();
                        initView();
                        break;
            }
            }
    }
    int mode = 0;
    @OnClick({R.id.play,R.id.pause,R.id.stop,R.id.play_mode,R.id.share,R.id.favorite})
    public void onClick(View v){
        switch(v.getId()){
            case R.id.play:
                myMediaHandler.sendEmptyMessage(Intents.MUSIC_PLAY_START);
                break;
            case R.id.pause:
                myMediaHandler.sendEmptyMessage(Intents.MUSIC_PLAY_PAUSE);
                break;
            case R.id.stop:
                myMediaHandler.sendEmptyMessage(Intents.MUSIC_PLAY_STOP);
                break;
            case R.id.favorite:
                if(musicFile != null){
                if(musicFile.getIsFavTime()!= 0){
                    favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    mSQLiteUtils.removeFavourMusicFile(musicFile.getName());
                    musicFile.setIsFavTime(0);

                }else {
                    favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    musicFile.setIsFavTime(System.currentTimeMillis());
                    mSQLiteUtils.addFavourMusicFile(
                            new FavourMusics(mSQLiteUtils.selectAllFavMusic().size(),
                                    musicFile.getName(),musicFile.getDir(),System.currentTimeMillis()));
                }
                mSQLiteUtils.updateMusicFile(musicFile);
                }
                break;
                case R.id.play_mode:
                    mode ++;
                    if(mode % 3 == 1) {
                        playMode.setImageResource(R.drawable.ic_repeat_one_black_24dp);
                    }else if(mode % 3 == 2){
                        playMode.setImageResource(R.drawable.ic_shuffle_black_24dp);
                    }else
                        playMode.setImageResource(R.drawable.ic_repeat_black_24dp);
                    myMediaPlayer.setMode(mode);
                    Intent intent = new Intent();
                    intent.setAction(Intents.ACTION_PLAY_MODE_CHANGE);
                    getActivity().sendBroadcast(intent);
                    break;
                    case R.id.share:
                        shareFile(musicFile.getDir());
                        break;
        }

    }
    private void shareFile(String path){
        File file = new File(path);
        if(null != file && file.exists()){

        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("text/plain");
//        share.setType("mp3");
//        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        share.putExtra(Intent.EXTRA_TEXT,list.get(BlankFragment.curPos).getName());
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        getContext().startActivity(Intent.createChooser(share, "分享"));
        }
    }
}