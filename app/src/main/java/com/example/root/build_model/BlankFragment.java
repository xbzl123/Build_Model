package com.example.root.build_model;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.root.build_model.data.FavourMusics;
import com.example.root.build_model.data.MusicFile;
import com.example.root.build_model.databinding.FragmentBlankBinding;
import com.example.root.build_model.dummy.DummyContent;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Recycler.MyAdapter;
import Recycler.MyItemAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class BlankFragment extends Fragment implements MyAdapter.onChildClickListener{
    Handler mHandler;
    MyMediaPlayer.ProcessHandler mediaHandler;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.search_recycler)
    RecyclerView recyclerViewResult;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentBlankBinding fragmentBlankBinding;
    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private MyMediaPlayer mediaPlayer;
    private LocalBroadcastReceiver myBroadcastReceiver;
    private IntentFilter intentFilter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if(mediaPlayer == null)
        mediaPlayer = MyMediaPlayer.getInstance();
        mediaHandler = new MyMediaPlayer.ProcessHandler(mediaPlayer);
        mediaPlayer.setProcessHandler(mediaHandler);
        mObservable = mediaPlayer.getmObservable();
        Log.e("", "onCreate: *****************888" +mObservable);

        myBroadcastReceiver = new LocalBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intents.ACTION_PLAY_MODE_CHANGE);
        intentFilter.addAction(Intents.ACTION_PLAY_NEXT);
        intentFilter.addAction(Intents.ACTION_PLAY_PREVIOUS);
        intentFilter.addAction(Intents.ACTION_PLAY);
        intentFilter.addAction(Intents.ACTION_PAUSE);
        intentFilter.addAction(Intents.ACTION_FAVOUR);

        getContext().registerReceiver(myBroadcastReceiver,intentFilter);
    }
    @OnClick({R.id.expand_more,R.id.scan_media,R.id.playhistory,R.id.recover})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.expand_more:
//                if(testLayout.getVisibility() == View.GONE){
//                    testLayout.setVisibility(View.VISIBLE);
//                }else{
//                    testLayout.setVisibility(View.GONE);
//                }
                break;
            case R.id.scan_media:
                musicFiles.clear();
                getMusicFloder();
                mHandler.sendEmptyMessage(Intents.MUSIC_SCARCH_FINISH);
                break;

            case R.id.playhistory:
                musicFiles.clear();
                musicFiles = mSQLiteUtils.selectMusicIsPlayed();
                mHandler.sendEmptyMessage(Intents.MUSIC_SCARCH_FINISH);
                break;
            case R.id.recover:
                retrofitHttpGet();
//                initMediaPlayer(Hawk.get("lastpos",-1));
                break;
        }
    }

    @Override
    public void childClick(RecyclerView recyclerView, View view, int pos, String data) {
        Log.e("", "childClick: ======llll="+data);
        Toast.makeText(getActivity(),data,Toast.LENGTH_LONG).show();
//                    String[] temp = data.split("-");

        initMediaPlayer(pos);
        mHandler.sendEmptyMessage(Intents.MUSIC_PLAY_ALL_LOOPING);
    }

    @Override
    public void playClick(View v) {
        switch (v.getId()){
            case R.id.play:
                mediaHandler.sendEmptyMessage(Intents.MUSIC_PLAY_START);
//                if(!mediaPlayer.isPlaying()){
//                    mediaPlayer.start();
//                    testObervable();
//                }
                break;
            case R.id.pause:
//                if(mediaPlayer.isPlaying()){
//                    mediaPlayer.pause();
//                }
                mediaHandler.sendEmptyMessage(Intents.MUSIC_PLAY_PAUSE);
                break;
            case R.id.stop:
                mediaHandler.sendEmptyMessage(Intents.MUSIC_PLAY_STOP);
//                if(mediaPlayer.isPlaying()){
//                    mediaPlayer.stop();
//                }
                break;
            case R.id.sound_up:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.sound_down:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.play_mode:
                mode++;
                if(mode % 3 == 1) {
                    mHandler.sendEmptyMessage(Intents.MUSIC_PLAY_LOOPING);
                }else if(mode % 3 == 2){
                    mHandler.sendEmptyMessage(Intents.MUSIC_PLAY_RANDOM);
                }else{
                    mHandler.sendEmptyMessage(Intents.MUSIC_PLAY_ALL_LOOPING);
                }
                mediaPlayer.setMode(mode);
                break;
            case R.id.favorite:
                ImageView imageView = v.findViewById(R.id.favorite);
                MusicFile musicFile = musicFiles.get(curPos);
                long isFavourite =  musicFile.getIsFavTime();
                if(isFavourite!=0){
                    mSQLiteUtils.removeFavourMusicFile(musicFile.getName());
                    musicFile.setIsFavTime(0);
                }else{
                    musicFile.setIsFavTime(System.currentTimeMillis());
                    mSQLiteUtils.addFavourMusicFile(
                            new FavourMusics(mSQLiteUtils.selectAllFavMusic().size(),
                                    musicFile.getName(),musicFile.getDir(),System.currentTimeMillis()));
                }
                mSQLiteUtils.updateMusicFile(musicFile);
                imageView.setImageResource(isFavourite!=0?
                        R.drawable.ic_favorite_black_24dp:R.drawable.ic_favorite_border_black_24dp);
                refreshNotification();
                break;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_blank, container,false);
        fragmentBlankBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, container, false);
        ButterKnife.bind(this,fragmentBlankBinding.getRoot());
        Log.e("00000", "onCreateView: ===========");
        Hawk.init(getContext()).build();
        if(mSQLiteUtils == null)
            mSQLiteUtils = SQLiteUtils.getInstance();
        mHandler = new ProcessHandler(this);

        searchView.setOnQueryTextListener(listener);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerViewResult.setVisibility(View.INVISIBLE);
                AppUtils.hideSoftInput(getActivity());
                return true;
            }
        });
        mediaPlayer.setmMainObserver(mObserver);
        Log.e("", "onCreateView: ===============mObserver="+mObserver.getClass().toString());
        return fragmentBlankBinding.getRoot();
    }

    @Override
    public void onResume() {
        io.reactivex.Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                if(Hawk.get("scaned",false)){
                    musicFiles.clear();
                    musicFiles.addAll(mSQLiteUtils.selectAllMusic());
                }else{
                    getMusicFloder();
                }
                mHandler.sendEmptyMessage(Intents.MUSIC_SCARCH_FINISH);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer aLong) throws Exception {
                    }
                });
        super.onResume();
    }

    Map<Integer,String> stringMap = new HashMap<Integer,String>();
    private void retrofitHttpGet() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://guolin.tech/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api=retrofit.create(Api.class);
        Call<ResponseBody> responseBodyCall = api.contributorBySimpleGetCall1("api", "china");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("1返回的数据是：",call.toString());

                try {
                    String respon = response.body().source().readUtf8();
//                    Log.e("3返回的数据是：",response.body().source().readUtf8());
                    if(respon.length()!= 0){
                    String tmp = respon.substring(2,respon.length()-2).replace("},{","@");
                    String[] temp = tmp.split("@");
                    for (String s:temp){
                        int id = Integer.parseInt(s.substring(5,s.indexOf(",")));
                        String name = s.substring(s.indexOf("me")+5,s.length()-1);
                        stringMap.put(id, name);
                    }
                    int a= 1;
                    }
//                    String[] re =
                 } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener(){

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if(newText.equals("")){
                recyclerViewResult.setVisibility(View.INVISIBLE);
                return false;
            }
            searchmusicFiles.clear();
            List<MusicFile> list = mSQLiteUtils.selectChooseResult(newText);
            Log.e("", "onQueryTextChange: ========lll="+list.size());
            for(int i = 0;i < list.size();i++){
                MusicFile musicFile = new MusicFile();
                musicFile.setId(i);
                musicFile.setName(list.get(i).getName());
                musicFile.setDir(list.get(i).getDir());
                musicFile.setMusicDuration(list.get(i).getMusicDuration());
                searchmusicFiles.add(musicFile);
            }
            mHandler.sendEmptyMessage(Intents.MUSIC_SCARCH_FINISH_BY);
            return false;
        }
    };


    @Override
    public void onDetach() {
        mediaPlayer.release();
        getContext().unregisterReceiver(myBroadcastReceiver);
        super.onDetach();
    }


    Disposable disposable;
    private io.reactivex.Observable<Long> mObservable;
    private io.reactivex.Observer<Long>
        mObserver = new io.reactivex.Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long aLong) {
                if(adapter == null){
                    return;
                }
                adapter.setCurTime(mediaPlayer.getCurrentPosition());
                adapter.notifyDataSetChanged();
                if(mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition() < 1000){
                    mHandler.sendEmptyMessageDelayed(Intents.MUSIC_PLAY_FINISH,1000);
                    return;
                }
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


    private void setTimePlayOver() {
        adapter.setCurTime(mediaPlayer.getDuration());
        adapter.notifyDataSetChanged();
    }
    @Override
    public void dragSeekBar(View view, MotionEvent event, int postion) {
        SeekBar seekBar = (SeekBar)view.findViewById(R.id.play_seekBar);
        if(event.getAction() == MotionEvent.ACTION_UP){
            seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        }
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mediaPlayer.seekTo(progress*1000);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private int mode = 0;
    static int curPos = -1;
    static long curProgress = -1;
    private SQLiteUtils mSQLiteUtils;
    private void initMediaPlayer(int pos) {
        mediaPlayer.reset();
        try {
            File file = new File(musicFiles.get(pos).getDir());
            Log.e("", "initMediaPlayer: ============"+ musicFiles.get(pos).getMusicDuration());
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
            mediaHandler.sendEmptyMessage(Intents.MUSIC_PLAY_START);
//            testObervable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (MusicFile m :musicFiles){
            if(m.getId() == pos){
                m.setIsPlayTime(System.currentTimeMillis());
                mSQLiteUtils.updateMusicFile(m);
            }
        }

        curPos = pos;
        adapter.setPos(pos);
        adapter.notifyDataSetChanged();
        try {
            Thread.sleep(100);
            Hawk.put("onlineplay", false);

            startNotification();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void stopMediaPlayer(){
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private List<MusicFile> musicFiles=new ArrayList<MusicFile>();
    private List<MusicFile> searchmusicFiles=new ArrayList<MusicFile>();

    private void getMusicFloder() {
        Log.e("0000", "getMusicFloder: =============" );
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getActivity(), "没有sdcard", Toast.LENGTH_LONG).show();
            return;
        }
        //查询音乐
        Uri mImageUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = getContext().getContentResolver();
        String selection = MediaStore.Audio.Media.MIME_TYPE + "=? ";
        String[] selectionArgs = new String[]{"audio/mpeg"};
        Cursor mCursor = mContentResolver.query(mImageUri, null, selection, selectionArgs, MediaStore.Audio.Media.DATE_MODIFIED);
        int i = 0;
        mSQLiteUtils.deleteAllMusicFile();
        while (mCursor.moveToNext()) {
            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            int duration = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            MusicFile musicFile = new MusicFile();
            musicFile.setDir(path);
            String[] temp = path.split("/");
            musicFile.setName(temp[temp.length-1].replace(".mp3", " "));
            musicFile.setId(i);
            musicFile.setMusicDuration(duration);
            i++;
            musicFiles.add(musicFile);
            mSQLiteUtils.addMusicFile(musicFile);
        }
        mCursor.close();
        // 通知Handler扫描完成
        Hawk.put("scaned",true);
    }
    static class ProcessHandler extends Handler {
        WeakReference<BlankFragment> weakReference = null;
        public ProcessHandler(BlankFragment mainActivity) {
            weakReference = new WeakReference<BlankFragment>(mainActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            BlankFragment mainActivity = null;
            if(weakReference != null){
                mainActivity = weakReference.get();
            }
            switch (msg.what){
                case Intents.MUSIC_SCARCH_FINISH:
                    mainActivity.initRecycler();
                    mainActivity.smoothMoveToPosition(mainActivity.recyclerView, curPos);
                    break;
                case Intents.MUSIC_SCARCH_FINISH_BY:
                    mainActivity.showSearchResult();
                    break;
                case Intents.MUSIC_PLAY_LOOPING:
                    mainActivity.playSongLooping();
                    break;
                case Intents.MUSIC_PLAY_ALL_LOOPING:
                    mainActivity.playSongAllLooping();
                    break;
                case Intents.MUSIC_PLAY_FINISH:
                    mainActivity.setTimePlayOver();
                    mainActivity.disposable.dispose();
                    mainActivity.mediaHandler.sendEmptyMessage(Intents.MUSIC_PLAY_FINISH);
                    break;
                case Intents.MUSIC_PLAY_RANDOM:
                    mainActivity.playSongRandom();
                    break;
                case Intents.MUSIC_REFRESH_NOTIFICATION:
                    mainActivity.refreshNotification();
                    break;
            }

            super.handleMessage(msg);
        }
    }

    private void refreshNotification() {
        startNotification();
    }

    private void showSearchResult() {
        recyclerViewResult.setVisibility(View.VISIBLE);
            List<DummyContent.DummyItem> itemList = new ArrayList<>();
            for(MusicFile m:searchmusicFiles){
                DummyContent.DummyItem item = new DummyContent.DummyItem(""+(m.getId()+1),m.getName(),"0");
                itemList.add(item);
            }
            LinearLayoutManager layoutParams = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerViewResult.setLayoutManager(layoutParams);
        recyclerViewResult.setAdapter(new MyItemRecyclerViewAdapter(itemList,listFragmentInteractionListener,false));

    }
    MainFragment.OnListFragmentInteractionListener listFragmentInteractionListener = new MainFragment.OnListFragmentInteractionListener(){

        @Override
        public void onListFragmentInteraction(DummyContent.DummyItem item,View view) {
            int pos = 0;
            for(MusicFile m:musicFiles){
                if(m.getName().equals(item.content)){
                    pos=(int)m.getId();
                }
            }
            initMediaPlayer(pos);
            recyclerViewResult.setVisibility(View.INVISIBLE);
            searchView.onActionViewCollapsed();
            searchmusicFiles.clear();
            smoothMoveToPosition(recyclerView, pos);
//            Toast.makeText(getContext(),item.content+",id ="+pos,Toast.LENGTH_LONG).show();

        }
    };
    private void startNotification() {
        if(Hawk.get("onlineplay",false)){
            return;
        }
        MusicFile musicFile = musicFiles.get(curPos);
        mediaPlayer.createNotificaton(getContext(),musicFile);

    }

    private void playSongLooping(){
        if(mediaPlayer.isPlaying()&& !mediaPlayer.isLooping()){
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mObservable.unsubscribeOn(Schedulers.io());
//                    curPos++;
                    initMediaPlayer(curPos);
//                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }
            });
        }
    }
    private void playSongAllLooping() {
        if(mediaPlayer.isPlaying()&& curPos != -1){

            mediaPlayer.setLooping(false);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mObservable.unsubscribeOn(Schedulers.io());
                    curPos++;
                    initMediaPlayer(curPos);

                    Intent intent = new Intent();
                    intent.setAction(Intents.MUSIC_PLAY_OVER);
                    getActivity().sendBroadcast(intent);
                }
            });
        }
    }

    private void playSongRandom() {
        if(mediaPlayer.isPlaying()&& curPos != -1){
            mediaPlayer.setLooping(false);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mObservable.unsubscribeOn(Schedulers.io());
                    curPos = (int)(Math.random()*musicFiles.size());
                    initMediaPlayer(curPos);
                    Intent intent = new Intent();
                    intent.setAction(Intents.MUSIC_PLAY_OVER);
                    getActivity().sendBroadcast(intent);
                    smoothMoveToPosition(recyclerView,curPos);
                }
            });
        }
    }
    MyAdapter adapter;
    private void initRecycler(){
        Log.e("00000", "initRecycler: ===========");
//        List<String> list = new ArrayList<>();
//        for(int i = 0;i<musicFiles.size();i++){
//            list.add(String.format(Locale.CHINA,"No.%03d  %s",musicFiles.get(i).getId()+1,musicFiles.get(i).getName()));
//        }
        adapter = new MyAdapter(getContext(),musicFiles);
        adapter.setPos(curPos);
        adapter.setMode(mediaPlayer.getMode());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3,RecyclerView.VERTICAL,false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0 ){
                    return 3;
                }
                return 1;
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        MyItemAnimator defaultItemAnimator = new MyItemAnimator();
        defaultItemAnimator.setMoveDuration(3000);
        defaultItemAnimator.setAddDuration(3000);
        defaultItemAnimator.setRemoveDuration(3000);
        defaultItemAnimator.setChangeDuration(3000);
        defaultItemAnimator.setSupportsChangeAnimations(true);
        recyclerView.setItemAnimator(defaultItemAnimator);
        /*recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                c.drawColor(Color.BLACK);
            }
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                c.drawBitmap(bitmap,400,400,null);
            }
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                if(pos % 2 == 0)
                outRect.set(0,5*pos,0,5*pos);
            }
        });*/
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll) {
                    mShouldScroll = false;
                    smoothMoveToPosition(recyclerView, mToPosition);
                }
            }
        });
        adapter.setOnChildClickListener(this);
    }
    /**
     * 目标项是否在最后一个可见项之后
     */
    private boolean mShouldScroll; /**
     * 记录目标项位置
     */
    private int mToPosition;
    /**
     * 滑动到指定位置
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        }else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(curPos);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    @Override
    public void onStop() {
        Hawk.put("lastpos",curPos);
        super.onStop();
    }


    public class LocalBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Intents.ACTION_PLAY_MODE_CHANGE:
                    mode = mediaPlayer.getMode();
                    adapter.setMode(mode);
                    if(mode % 3 == 1){
                        mHandler.sendEmptyMessage(Intents.MUSIC_PLAY_LOOPING);
                    }else if(mode % 3 == 2){
                        mHandler.sendEmptyMessage(Intents.MUSIC_PLAY_RANDOM);
                    }else{
                        mHandler.sendEmptyMessage(Intents.MUSIC_PLAY_ALL_LOOPING);
                    }
                    break;
                case Intents.ACTION_PLAY_NEXT:
                    if(curPos != musicFiles.size()) {
                        curPos = curPos + 1;
                    }else{
                        curPos=0;
                    }
                    initMediaPlayer(curPos);

                    break;
                case Intents.ACTION_PLAY:
                    mediaHandler.sendEmptyMessage(Intents.MUSIC_PLAY_START);
                    mHandler.sendEmptyMessage(Intents.MUSIC_REFRESH_NOTIFICATION);
                    break;
                case Intents.ACTION_PAUSE:
                    mediaHandler.sendEmptyMessage(Intents.MUSIC_PLAY_PAUSE);
                    mHandler.sendEmptyMessage(Intents.MUSIC_REFRESH_NOTIFICATION);
                    break;
                case Intents.ACTION_PLAY_PREVIOUS:
                    if(curPos != 0){
                    curPos = curPos-1;
                    initMediaPlayer(curPos);
                    }
                    break;
                case Intents.ACTION_FAVOUR:
                    dealFavourite(intent);
                    mHandler.sendEmptyMessage(Intents.MUSIC_REFRESH_NOTIFICATION);
                    break;
            }
        }
    }

    private void dealFavourite(Intent intent) {
        boolean isOnline = Hawk.get("onlineplay",false);
        if(isOnline){
            mSQLiteUtils.addFavourMusicFile(
                    new FavourMusics(mSQLiteUtils.selectAllFavMusic().size(),
                            intent.getStringExtra("songname"),intent.getStringExtra("songpath"),System.currentTimeMillis()));
        }else{
            MusicFile musicFile = musicFiles.get(curPos);
            if(musicFile.getIsFavTime()!=0){
                musicFile.setIsFavTime(0);
                mSQLiteUtils.removeFavourMusicFile(musicFile.getName());
            }else{
                musicFile.setIsFavTime(System.currentTimeMillis());
                mSQLiteUtils.addFavourMusicFile(
                        new FavourMusics(mSQLiteUtils.selectAllFavMusic().size(),
                                musicFile.getName(),musicFile.getDir(),System.currentTimeMillis()));
            }
            mSQLiteUtils.updateMusicFile(musicFile);
            }
    }
}
