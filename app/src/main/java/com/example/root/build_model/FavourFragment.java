package com.example.root.build_model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.root.build_model.data.FavourMusics;
import com.example.root.build_model.data.MusicFile;
import com.example.root.build_model.dummy.DummyContent;
import com.example.root.build_model.dummy.DummyContent.DummyItem;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavourFragment} interface
 * to handle interaction events.
 * Use the {@link FavourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavourFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SQLiteUtils mSQLiteUtils;
    private MainActivity mainActivity;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FavourRecevicer recevicer;
    private IntentFilter intentFilter;
    public FavourFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavourFragment newInstance(String param1, String param2) {
        FavourFragment fragment = new FavourFragment();
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
        View view = inflater.inflate(R.layout.fragment_favour, container, false);
        ButterKnife.bind(this,view);
        recevicer = new FavourRecevicer();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intents.ACTION_FAVOUR);
        getContext().registerReceiver(recevicer,intentFilter );
        return view;
    }

    @Override
    public void onResume() {
        if(mSQLiteUtils == null)
            mSQLiteUtils = SQLiteUtils.getInstance();
        initRecycView();
//        mSQLiteUtils.clearFavourMusicFile();
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initRecycView(){
        List<FavourMusics> list = mSQLiteUtils.selectAllFavMusic();
        List<DummyItem> itemList = new ArrayList<>();

        for(FavourMusics m:list){
            DummyItem item = new DummyItem(""+(m.getId()+1),m.getSongName(),"0");
            itemList.add(item);
        }
        LinearLayoutManager layoutParams = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutParams);
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(itemList,listener,false));

    }

    MainFragment.OnListIconListener listener = new MainFragment.OnListIconListener(){

        @Override
        public void onListIconClick(DummyContent.DummyItem item,View view) {
            mSQLiteUtils.removeFavourMusicFile(item.content);
            MusicFile musicFile= mSQLiteUtils.selectMusic(item.content);
            musicFile.setIsFavTime(0);
            mSQLiteUtils.updateMusicFile(musicFile);
            initRecycView();
            Hawk.put("reload",true);
//            getContext().sendBroadcast(new Intent(Intents.ACTION_FAVOUR));
            Toast.makeText(getContext(),item.content+",id ="+item.id,Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public void onAttach(Context context) {
        mainActivity = (MainActivity)context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        mainActivity = null;
        super.onDetach();
    }

    class FavourRecevicer extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            initRecycView();
        }
    }

}
