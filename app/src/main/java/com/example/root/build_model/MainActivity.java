package com.example.root.build_model;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.NotificationManager;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.root.build_model.data.MusicFile;
import com.orhanobut.hawk.Hawk;

import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import BuilderTest.Person;
import Observer.Observable;
import Observer.Observer;
import Observer.Weather;
import Recycler.MyAdapter;
import SingleModelTest.Singleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dragger2.Students;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import retrofit2.Retrofit;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    TextView builderTv,cloneableTv,addHobbyTv,singleTv,databindTv;
    CloneableTest.Person person;
    @Inject
    Students student;
//    @BindView(R.id.search)
//    SearchView searchView;
//    @BindView(R.id.test_layout)
//    LinearLayout testLayout;
    @BindView(R.id.vp_content)
    ViewPager viewPager;
    @BindView(R.id.iv_title_one)
    ImageView imageView1;
    @BindView(R.id.iv_title_two)
    ImageView imageView2;
    @BindView(R.id.iv_title_three)
    ImageView imageView3;
    @BindView(R.id.iv_title_four)
    ImageView imageView4;
    private List<ImageView> imageViews = new ArrayList<>();
    private ExitReceiver myBroadcastReceiver;
    private IntentFilter intentFilter;
//    @Inject
//    Package aPackage;
//  static {
//    System.loadLibrary("MyJNI");
//    }
//    public native static String getString_from_native();
//    public native static String getString_from_c();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int re = requestCode;
        int rs = resultCode;
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.iv_title_menu,R.id.iv_title_one,R.id.iv_title_three,R.id.iv_title_four,R.id.iv_title_two})
    public void onClick(View view){
        Log.e("", "onClick: ===============");
        switch (view.getId()){
            case R.id.iv_title_menu:
                break;
            case R.id.iv_title_one:
                if(viewPager.getCurrentItem() != 0){
                    BlankFragment blankFragment = (BlankFragment)myFragmentPagerAdapter.instantiateItem(viewPager,0 );
                    blankFragment.onResume();
                    setCurrentPager(0);
                }
                break;
            case R.id.iv_title_two:
                if(viewPager.getCurrentItem() != 1){
                    PlayFragment playFragment = (PlayFragment)myFragmentPagerAdapter.instantiateItem(viewPager,1 );
                    playFragment.onResume();
                    setCurrentPager(1);
                }
                break;
            case R.id.iv_title_three:
                if(viewPager.getCurrentItem() != 2){
                    setCurrentPager(2);
                    FavourFragment favourFragment = (FavourFragment) myFragmentPagerAdapter.instantiateItem(viewPager,2);
                    favourFragment.onResume();
                }
                break;
            case R.id.iv_title_four:
                if(viewPager.getCurrentItem() != 3){
                    setCurrentPager(3);
                    OnlinePlayFragment onlinePlayFragment = (OnlinePlayFragment) myFragmentPagerAdapter.instantiateItem(viewPager,3);
                    onlinePlayFragment.onResume();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        showExitDailog();
//        super.onBackPressed();
    }
    private void showExitDailog() {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog().Builder(this);
        customAlertDialog.setMsg("Exit Application!?")
                .setPostionButton("yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setNegativeButton("no", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }


    private void setCurrentPager(int pos){
        for (ImageView imageView:imageViews){
            imageView.setAlpha(0.5f);
            imageView.setScaleX(1.0f);
            imageView.setScaleY(1.0f);

        }
        ImageView temp = imageViews.get(pos);
            viewPager.setCurrentItem(pos);
//            temp.setAlpha(0.3f);
//            temp.setScaleX(1.5f);
//            temp.setScaleY(1.5f);
        PropertyValuesHolder p0 = PropertyValuesHolder.ofFloat("alpha",0.5f,1.0f);
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("rotation",0.0f,360.0f);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("scaleX",1.0f,0,1.5f);
        PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("scaleY",1.0f,0,1.5f);
        ObjectAnimator.ofPropertyValuesHolder(temp,p0,p1,p2,p3).setDuration(500).start();
//        ObjectAnimator.ofFloat(temp, "rotation",0.0f,360.0f).setDuration(300).start();
    }
    Handler mHandler;
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new BlankFragment());
        fragmentList.add(new PlayFragment());
        fragmentList.add(new FavourFragment());
        fragmentList.add(new OnlinePlayFragment());
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        Log.e(TAG, "onCreate: ----size-"+fragmentList.size()+
                ",myFragmentPagerAdapter = "+myFragmentPagerAdapter+",viewPager "+viewPager);

        viewPager.setAdapter(myFragmentPagerAdapter);
//        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(this);
        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        imageViews.add(imageView4);

        setCurrentPager(0);



//        Toast.makeText(this,"Bug is Fix" , Toast.LENGTH_LONG).show();
        Hawk.init(getApplicationContext()).build();
        if(mSQLiteUtils == null)
        mSQLiteUtils = SQLiteUtils.getInstance();
        mHandler = new ProcessHandler(this);

        person = new CloneableTest.Person();
        person.setName("json");
        person.setAge(26);
        person.setHeight(172.0);
        person.setWeight(62.0);
        ArrayList<String> hobbies =  new ArrayList<String>();
        hobbies.add("run exercise");
        hobbies.add("sing song");
        hobbies.add("climb mountains");
        hobbies.add("watch TV");
        hobbies.add("swimming");
        person.setHobby(hobbies);
        builderTv = (TextView)findViewById(R.id.builder);
        builderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person.Builder builder = new Person.Builder();
                Person person = builder.name("jake").age(25).height(170.0).weight(56.0).build();
                builderTv.setText(person.print());

//                Log.e("", "onCreate: "+ person);
            }
        });
        cloneableTv = (TextView)findViewById(R.id.cloneable);
        cloneableTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cloneableTv.setText(person.print());
            }
        });
        addHobbyTv = (TextView)findViewById(R.id.add_hobby);
        addHobbyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloneableTest.Person p1 = (CloneableTest.Person)person.clone();
                p1.setName("aminal");
                p1.getHobby().add("play game");
                addHobbyTv.setText(p1.print());
            }
        });

        singleTv = (TextView)findViewById(R.id.single_Tv);
        singleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObserverTest();
               final CountDownLatch latch = new CountDownLatch(1);
               int threadCount = 1000;
               for (int i = 0;i<threadCount;i++){
                   new Thread(){
                       @Override
                       public void run() {
                           try {
                               latch.await();
                               Log.e("", "run: ======"+Singleton.getInstance().hashCode());

                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
//                           singleTv.setText(Singleton.getInstance().hashCode());
                       }
                   }.start();
               }
            }
        });
        databindTv = (TextView)findViewById(R.id.databind);
        databindTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                testObervable();
                testFlowable();
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this,DatabindActivity.class);
//                startActivity(intent);
            }
        });

/*        DaggerMainComponent.builder()
                .build().inject(this);
//        DaggerMainComponent.builder()
//                .mainModule(new MainModule(this))
//                .build().inject(this);
        testDragger();*/
        myBroadcastReceiver = new ExitReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intents.ACTION_EXIT_APP);
//        intentFilter.addAction(Intents.ACTION_PLAY_MODE_CHANGE);
//        intentFilter.addAction(Intents.ACTION_PLAY_NEXT);
//        intentFilter.addAction(Intents.ACTION_PLAY_PREVIOUS);
//        intentFilter.addAction(Intents.ACTION_PLAY);
//        intentFilter.addAction(Intents.ACTION_PAUSE);
//        intentFilter.addAction(Intents.ACTION_FAVOUR);
        registerReceiver(myBroadcastReceiver,intentFilter );

    }


    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showContacts() {
        // NOTE: Perform action that requires the permission.
        // If this is run by PermissionsDispatcher, the permission will have been granted
        Toast.makeText(this,"ssss",Toast.LENGTH_LONG).show();

    }
    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
//    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener(){
//
//        @Override
//        public boolean onQueryTextSubmit(String query) {
//
//            return false;
//        }
//
//        @Override
//        public boolean onQueryTextChange(String newText) {
//            musicFiles.clear();
//            List<MusicFile> list = mSQLiteUtils.selectChooseResult(newText);
//            Log.e("", "onQueryTextChange: ========lll="+list.size());
//            for(int i = 0;i < list.size();i++){
//                MusicFile musicFile = new MusicFile();
//                musicFile.setId(i);
//                musicFile.setName(list.get(i).getName());
//                musicFile.setDir(list.get(i).getDir());
//                musicFile.setMusicDuration(list.get(i).getMusicDuration());
//                musicFiles.add(musicFile);
//            }
//            mHandler.sendEmptyMessage(MUSIC_SCARCH_FINISH);
//            return false;
//        }
//    };
    private void testFlowable1() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for(int i= 0;;i++){
                    emitter.onNext(i);
                }
            }
        },BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new FlowableSubscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(100);
            }

            @Override
            public void onNext(Integer integer) {
                Log.e("", "subscribe: ====i="+integer);

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    private  void writer() {
        // TODO Auto-generated method stub
        //创建文件对象，指定要写出的文件路径
        File file=new File("test.txt");
        try {
            //创建文件字节输出流对象，准备向d.txt文件中写出数据,true表示在原有的基础上增加内容
            FileOutputStream fout=new FileOutputStream(file,true);
            Scanner sc=new Scanner(System.in);

            System.out.println("请写出一段字符串:");
            String msg=sc.next()+"\r\n";;

            /******************(方法一)按字节数组写入**********************/
            //byte[] bytes = msg.getBytes();//msg.getBytes()将字符串转为字节数组

            //fout.write(bytes);//使用字节数组输出到文件
            /******************(方法一)逐字节写入**********************/
            byte[] bytes = msg.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                fout.write(bytes[i]);//逐字节写文件
            }
            fout.flush();//强制刷新输出流
            fout.close();//关闭输出流
            System.out.println("写入完成！");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    Subscription mSubscription;
    private void testFlowable() {

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                try {
                    FileReader reader = new FileReader("test.txt");
                    BufferedReader br = new BufferedReader(reader);
                    String str;
                    while ((str = br.readLine()) != null && !emitter.isCancelled()) {
                        while (emitter.requested() == 0) {
                            if (emitter.isCancelled()) {
                                break;
                            }
                        }
                        emitter.onNext(str);
                    }
                    br.close();
                    reader.close();
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.ERROR).
                subscribeOn(AndroidSchedulers.mainThread()).
                observeOn(Schedulers.io()).
                subscribe(new FlowableSubscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                mSubscription = s;
                s.request(1);
            }

            @Override
            public void onNext(String s) {
                Log.e("", "subscribe: ====i="+s);
                System.out.println(s);
                try {
                    Thread.sleep(1000);
                    mSubscription.request(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    Disposable disposable;
    private io.reactivex.Observable<Long> mObservable;
    private io.reactivex.Observer<Long> mObserver;

    private void testDragger(){
//        Toast.makeText(this,aPackage.toString(),Toast.LENGTH_LONG).show();
    }
    MyAdapter adapter;

    private void ObserverTest(){
        Observable<Weather> observable=new Observable<Weather>();
        Observer<Weather> observer1=new Observer<Weather>() {
            @Override
            public void onUpdate(Observable<Weather> observable, Weather data) {
                Log.e("", "onUpdate: "+"观察者1："+data.toString());
            }
        };
        Observer<Weather> observer2=new Observer<Weather>() {
            @Override
            public void onUpdate(Observable<Weather> observable, Weather data) {
                Log.e("", "onUpdate: "+"观察者2："+data.toString());
            }
        };

        observable.register(observer1);
        observable.register(observer2);
        Weather weather=new Weather("晴转多云");
        observable.notifyObservers(weather);
        Weather weather1=new Weather("多云转阴");
        observable.notifyObservers(weather1);
        observable.unregister(observer1);
        Weather weather2=new Weather("台风");
        observable.notifyObservers(weather2);
    }

//    public static OkHttpClient genericClient() {
////        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
////        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        Request.Builder requestBuilder = request.newBuilder();
//                        request = requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=GBK"),
//                                URLDecoder.decode(bodyToString(request.body()), "UTF-8")))
//                                .build();
//                        return chain.proceed(request);
//                    }
//                })
////                .addInterceptor(logging)
//                .connectTimeout(1000, TimeUnit.SECONDS)
//                .writeTimeout(1000, TimeUnit.SECONDS)
//                .readTimeout(1000, TimeUnit.SECONDS)
//                .build();
//        return httpClient;
//    }
    private void testretrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("").build();
    }

    @Override
    protected void onStop() {
//        for(MusicFile m:musicFiles){
//        mSQLiteUtils.updateMusicFile(m);
//        }
        Hawk.put("lastpos",curPos);
        Log.e("00000000000", "onStop: kkkkkkkkkk"+viewPager.getOffscreenPageLimit());

        super.onStop();
    }
//    private void setNightMode() {
//        //  获取当前模式
//        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//        //  将是否为夜间模式保存到SharedPreferences
//        AppUtils.setNightMode(this, currentNightMode == Configuration.UI_MODE_NIGHT_NO);
//        //  切换模式
//        getDelegate().setDefaultNightMode(currentNightMode == Configuration.UI_MODE_NIGHT_NO ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
//        UserInfoTools.setChangeNightMode(this,true);
//        //  重启Activity
//        recreate();
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private int mode = 0;
    private int curPos = -1;
    private SQLiteUtils mSQLiteUtils;
    private final static int MUSIC_SCARCH_FINISH = 1;//音乐扫描完成
    private final static int MUSIC_PLAY_ALL_LOOPING = 2;//音乐
    private final static int MUSIC_PLAY_FINISH = 3;//音乐扫描完成
    private final static int MUSIC_PLAY_RANDOM = 4;//音乐扫描完成
    private List<MusicFile> musicFiles=new ArrayList<MusicFile>();
    private void getMusicFloder() {
        Log.e("0000", "getMusicFloder: =============" );
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "没有sdcard", Toast.LENGTH_LONG).show();
            return;
        }
                //查询音乐
                Uri mImageUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = MainActivity.this.getContentResolver();
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
                    musicFile.setName(temp[temp.length-1]);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentPager(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    static class ProcessHandler extends Handler{
        WeakReference<MainActivity> weakReference = null;
        public ProcessHandler(MainActivity mainActivity) {
            weakReference = new WeakReference<MainActivity>(mainActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            MainActivity mainActivity = null;
            if(weakReference != null){
                mainActivity = weakReference.get();
            }
            switch (msg.what){
            }
            super.handleMessage(msg);
        }
    }

    RecyclerView recyclerView;
//    private void initRecycler(){
//        List<String> list = new ArrayList<>();
//        for(int i = 0;i<musicFiles.size();i++){
//            list.add(String.format(Locale.CHINA,"No.%03d  %s",musicFiles.get(i).getId()+1,musicFiles.get(i).getName()));
//        }
//        adapter = new MyAdapter(this,musicFiles);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL,false);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,LinearLayoutManager.VERTICAL,false);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if(position == 0 ){
//                    return 3;
//                }
//                return 1;
//            }
//        });
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        MyItemAnimator defaultItemAnimator = new MyItemAnimator();
//        defaultItemAnimator.setMoveDuration(3000);
//        defaultItemAnimator.setAddDuration(3000);
//        defaultItemAnimator.setRemoveDuration(3000);
//        defaultItemAnimator.setChangeDuration(3000);
//        defaultItemAnimator.setSupportsChangeAnimations(true);
//        recyclerView.setItemAnimator(defaultItemAnimator);
//        /*recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                super.onDraw(c, parent, state);
//                c.drawColor(Color.BLACK);
//            }
//            @Override
//            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                super.onDrawOver(c, parent, state);
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//                c.drawBitmap(bitmap,400,400,null);
//            }
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                int pos = parent.getChildAdapterPosition(view);
//                if(pos % 2 == 0)
//                outRect.set(0,5*pos,0,5*pos);
//            }
//        });*/
//        recyclerView.setAdapter(adapter);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (mShouldScroll) {
//                    mShouldScroll = false;
//                    smoothMoveToPosition(recyclerView, mToPosition);
//                }
//            }
//        });
////        adapter.setOnChildClickListener(this);
//    }
    private MediaPlayer mediaPlayer = new MediaPlayer();
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

    public class ExitReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Intents.ACTION_EXIT_APP:
                    MainActivity.this.finish();
                    NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    manager.cancelAll();
                    break;
            }
        }

    }

    @Override
    protected void onResume() {
        Log.e("", "onResume: 2222" );

        super.onResume();
    }
}
