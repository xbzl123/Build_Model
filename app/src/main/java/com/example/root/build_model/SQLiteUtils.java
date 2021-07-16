package com.example.root.build_model;

import android.database.sqlite.SQLiteConstraintException;

import com.example.root.build_model.data.FavourMusics;
import com.example.root.build_model.data.MusicFile;

import java.util.ArrayList;
import java.util.List;

import databindingtest.DaoSession;
import databindingtest.FavourMusicsDao;
import databindingtest.MusicFileDao;

public class SQLiteUtils {
    private static SQLiteUtils instance;
    MusicFileDao musicFileDao;
    FavourMusicsDao favourMusicsDao;
    DaoSession daoSession;

    public SQLiteUtils() {
        MyApplication application = MyApplication.getInstances();
        if(application != null){
            daoSession = application.getDaoSession();
            musicFileDao = daoSession.getMusicFileDao();
            favourMusicsDao = daoSession.getFavourMusicsDao();
        }
    }

    public static SQLiteUtils getInstance() {
        if(instance == null){
            synchronized (SQLiteUtils.class){
            if(instance == null){
                instance = new SQLiteUtils();
            }
            }
        }
        return instance;
    }

    public void addFavourMusicFile(FavourMusics favourMusics){
        try{
            favourMusicsDao.insert(favourMusics);
        }catch (SQLiteConstraintException e){
            e.printStackTrace();
        }
    }
    public void removeFavourMusicFile(String addTime){
        FavourMusics favourMusics = favourMusicsDao.queryBuilder().where(FavourMusicsDao.Properties.SongName.eq(addTime)).build().unique();
        favourMusicsDao.delete(favourMusics);
    }
    public void clearFavourMusicFile(){
        favourMusicsDao.deleteAll();
    }
    public void addMusicFile(MusicFile musicDetail){
        try{
        musicFileDao.insert(musicDetail);
        }catch (SQLiteConstraintException e){
            e.printStackTrace();
        }
    }

    public void removeMusicFile(MusicFile musicDetail){
        musicFileDao.delete(musicDetail);
    }

    public void updateMusicFile(MusicFile musicDetail){
        musicFileDao.update(musicDetail);
    }

    public List selectAllFavMusic(){
        favourMusicsDao.detachAll();
        List list1 = favourMusicsDao.queryBuilder().where(FavourMusicsDao.Properties.Id.isNotNull()).build().list();
        return list1== null?new ArrayList():list1;
    }

    public List selectAllMusic(){
        musicFileDao.detachAll();
        List list1 = musicFileDao.queryBuilder().where(MusicFileDao.Properties.Id.isNotNull()).build().list();
        return list1== null?new ArrayList():list1;
    }

    public MusicFile selectMusic(int id){
        musicFileDao.detachAll();
        MusicFile musicDetail = musicFileDao.queryBuilder().where(MusicFileDao.Properties.Id.eq(id)).build().unique();
        return musicDetail== null?new MusicFile(0,"","",0,0,0):musicDetail;
    }

    public MusicFile selectMusic(String name){
        musicFileDao.detachAll();
        MusicFile musicDetail = musicFileDao.queryBuilder().where(MusicFileDao.Properties.Name.eq(name)).build().unique();
        return musicDetail== null?new MusicFile(0,"","",0,0,0):musicDetail;
    }

    public List selectMusicIsPlayed(){
        musicFileDao.detachAll();
        List list1 = musicFileDao.queryBuilder().where(MusicFileDao.Properties.IsPlayTime.notEq(0)).build().list();
        return list1 == null?new ArrayList():list1;
    }

    public List selectMusicIsFavour(){
        musicFileDao.detachAll();
        List list1 = musicFileDao.queryBuilder().where(MusicFileDao.Properties.IsFavTime.notEq(0)).build().list();
        return list1 == null?new ArrayList():list1;
    }
    public List selectChooseResult(String str){
        musicFileDao.detachAll();
        String s = "%"+ str +"%";
        List list1 = musicFileDao.queryBuilder().where(MusicFileDao.Properties.Name.like(s)).build().list();
        return list1 == null?new ArrayList():list1;
    }
    public void deleteAllMusicFile(){
        musicFileDao.deleteAll();
    }
}
