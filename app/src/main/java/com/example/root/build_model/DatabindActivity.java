package com.example.root.build_model;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import databindingtest.User;

public class DatabindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        User user = new User(001,"jackson","461543");
        setContentView(R.layout.activity_databind);
        super.onCreate(savedInstanceState);

    }


//    public View onCreateView(Inflater inflater,@Nullable ViewGroup viewGroup,@Nullable Bundle savedInstanceState) {

//        return DataBindingUtil.inflate(inflater,R.layout.activity_databind,viewGroup,false);
//    }
}
