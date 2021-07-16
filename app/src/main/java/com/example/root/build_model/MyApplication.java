package com.example.root.build_model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;

import databindingtest.DaoMaster;
import databindingtest.DaoSession;

public class MyApplication extends MultiDexApplication {
    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public static MyApplication instance;
//    public ApplicationLike tinkerApplicationLike;

//    public IMyAidlInterface getiMyAidlInterface() {
//        return iMyAidlInterface;
//    }
//
//    private IMyAidlInterface iMyAidlInterface;
//
//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
//            if(iMyAidlInterface != null){
//                try {
//                    if(iMyAidlInterface.getPersonList().size()>0){
//                    Person p = iMyAidlInterface.getPersonList().get(0);
//                    Log.e("--getPerson--",p.toString());
//                    }
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }

//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            iMyAidlInterface = null;
//        }
//    };
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setDataBase();
        if (BuildConfig.DEBUG)
        Stetho.initializeWithDefaults(this);
        else
        Bugly.init(this,"f2a14c2227",true);
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);

//        Intent intent = new Intent(this,MyService.class);
//        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
//        if (BuildConfig.TINKER_ENABLE) {
//             // 我们可以从这里获得Tinker加载过程的信息
//             tinkerApplicationLike = com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
//
//             // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
//             com.tinkerpatch.sdk.TinkerPatch.init(tinkerApplicationLike)
//                     .reflectPatchLibrary()
//                     .setPatchRollbackOnScreenOff(true)
//                     .setPatchRestartOnSrceenOff(true);
//
//             // 每隔3个小时去访问后台时候有更新,通过handler实现轮训的效果
//             new FetchPatchHandler().fetchPatchWithInterval(1);
//         }
    }

    public static MyApplication getInstances(){
        return instance;
    }
    /* 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
    可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
    注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。*/

    private void setDataBase() {
        helper = new DaoMaster.DevOpenHelper(this,"music.db",null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
//        Beta.installTinker();
    }
}
