package com.example.root.build_model;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class StartupActivity extends AppCompatActivity {

    private static final String TAG = StartupActivity.class.getSimpleName();
    @BindView(R.id.count)
    TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        StartupActivityPermissionsDispatcher.showMainViewWithPermissionCheck(this);
        ButterKnife.bind(this);
        Log.e(TAG, "onCreate: ====aaaa============");

    }
    @OnClick(R.id.count)
    public void onClick(){
        gotoNextPage();
    }
    Disposable sm;
    @Override
    protected void onResume() {
        sm = Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.io())
                .subscribe(s-> {
                        Log.e("", "accept: ====="+ s);
                         Long time = 4 - s;
                         final String timese = "跳过 "+time;
                         if(count!= null){
                        count.post(()-> count.setText(timese));
                         }
                        if(s == 4){
                            gotoNextPage();
                        }
                });
//        com.example.root.build_model.bean.Person person = new com.example.root.build_model.bean.Person("test");
//        try {
//            iMyAidlInterface.addPerson(person);
//            Log.e("", "onResume: "+iMyAidlInterface.getPersonList().toString());
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void gotoNextPage(){
        Intent intent = new Intent(this,MainActivity.class);
        Log.e(TAG, "onCreate: ---------------size-");

        startActivityForResult(intent,100);
        sm.dispose();

    }
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.VIBRATE,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY
    })
    void showMainView() {

//        RxTimerUtil.timer(300, this);
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.VIBRATE,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY})
    void showDenyDialog() {

//        AlertDialog alertDialog =
//        new AlertDialog(this)
//                .setTitle(R.string.app_name)
//                .setMsg(R.string.hello_blank_fragment)
//                .setPositiveButton("ok", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View arg0) {
                        StartupActivityPermissionsDispatcher.
                                showMainViewWithPermissionCheck(StartupActivity.this);
//                    }
//                })
//                .setCancelable(false)
//                .show();
    }
    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.VIBRATE,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY})
    void showNeverAskDialog() {
        Toast.makeText(this,"ppppppppppppp",Toast.LENGTH_LONG).show();
//        new AlertDialog(this).builder()
//                .setTitle(R.string.LIVE_ALERM_ALERT_TITLE)
//                .setMsg(R.string.PERMISSION_DENIED)
//                .setPositiveButton(R.string.PERMISSION_GO_TO_SETUP, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View arg0) {
//                        AppUtil.gotoAppSettingPage(StartupActivity.this);
//                        finish();
//                    }
//                })
//                .setNegativeButton(R.string.PERMISSION_EXIT_APP, new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View arg0) {
//                        finish();
//                    }
//                })
//                .setCancelable(false)
//                .show();
    }
}
