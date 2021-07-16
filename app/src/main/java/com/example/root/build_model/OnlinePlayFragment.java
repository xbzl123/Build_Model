package com.example.root.build_model;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.root.build_model.bean.MusicResultBean;
import com.example.root.build_model.bean.resultBean;
import com.example.root.build_model.databinding.FragmentItemList2Binding;
import com.example.root.build_model.dummy.DummyContent;
import com.example.root.build_model.dummy.DummyContent.DummyItem;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class OnlinePlayFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private FragmentItemList2Binding binding;

    @BindView(R.id.online_search)
    SearchView searchView;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    private MyMediaPlayer mediaPlayer;
    private MyMediaPlayer.ProcessHandler mediaPlayerHandler;
    private MyApplication application;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OnlinePlayFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OnlinePlayFragment newInstance(int columnCount) {
        OnlinePlayFragment fragment = new OnlinePlayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list2, container, false);
//        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_item_list2);
        ButterKnife.bind(this,view);
        Hawk.init(getContext()).build();
        initDevice();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

        }
        return view;
    }

    private void initDevice() {
        if(mediaPlayer == null)
            mediaPlayer =  MyMediaPlayer.getInstance();
        if(mediaPlayerHandler == null)
            mediaPlayerHandler = new MyMediaPlayer.ProcessHandler(mediaPlayer);
        if(application == null)
            application = MyApplication.getInstances();
    }

    private void showRecyclerView(){
        List<DummyItem> items = new ArrayList<>();
        for(MusicResultBean bean:beanList){
            DummyItem dummyItem = new DummyItem(bean.name+" - "+bean.artists.name,
                    "专辑 "+(bean.album.name.isEmpty()?"无":bean.album.name),bean.album.picUrl);
            items.add(dummyItem);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(items, mListener,true));
    }
    List<MusicResultBean> beanList = new ArrayList<>();
    private int retrofitHttpGet(String songName) {
        beanList.clear();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://s.music.163.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api=retrofit.create(Api.class);
        Map<String,String> map = new HashMap<>();
        map.put("type", "1");
        map.put("s", songName);
        map.put("limit", "20");
        map.put("offset", "0");
        Call<resultBean> responseBodyCall = api.contributorBySimpleGetCall(map);
        responseBodyCall.enqueue(new Callback<resultBean>() {
            @Override
            public void onResponse(Call<resultBean> call, Response<resultBean> response) {
                Log.e("", "onResponse: "+ response.body().result.songs);
                List<DummyItem> items = new ArrayList<>();

                for(resultBean.result.songs bean:response.body().result.songs){
                    DummyItem dummyItem = new DummyItem(bean.name+" - "+bean.artists[0].name,
                            "专辑 "+(bean.album.name.isEmpty()?"无":bean.album.name),bean.album.picUrl);
                    items.add(dummyItem);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new MyItemRecyclerViewAdapter(items, mListener,true));
            }

            @Override
            public void onFailure(Call<resultBean> call, Throwable t) {

            }
        });
        return beanList.size();
    }
    @Override
    public void onResume() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                beanList.clear();
                retrofitHttpGet(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    MainFragment.OnListFragmentInteractionListener mListener = new MainFragment.OnListFragmentInteractionListener(){

        @Override
        public void onListFragmentInteraction(DummyContent.DummyItem item,View view) {
            int pos = recyclerView.getChildAdapterPosition(view);
            playOnlineSong(pos);
            Toast.makeText(getContext(),item.content+",id ="+item.id,Toast.LENGTH_LONG).show();
        }
    };

    private void playOnlineSong(int pos){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource("http://music.163.com/song/media/outer/url?id="+beanList.get(pos).id+".mp3");
            mediaPlayer.setSongPath("http://music.163.com/song/media/outer/url?id="+beanList.get(pos).id+".mp3");
            mediaPlayer.prepare();

//            application.getiMyAidlInterface().addPerson(new Person(beanList.get(pos).name));

            mediaPlayerHandler.sendEmptyMessage(Intents.MUSIC_PLAY_START);
            mediaPlayer.createNotificaton(getContext(), beanList.get(pos).name,beanList.get(pos).artists.name);
            Hawk.put("onlineplay", true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
