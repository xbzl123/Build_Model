package com.example.root.build_model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.root.build_model.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service{
    private List<Person> mPersons;


    private IBinder iBinder = new IMyAidlInterface.Stub(){
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void addPerson(Person person) throws RemoteException {
            mPersons.add(person);
            Log.e("addPerson", "addPerson: "+person.toString() );
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return mPersons;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        mPersons = new ArrayList<Person>();
        return iBinder;
    }
}
