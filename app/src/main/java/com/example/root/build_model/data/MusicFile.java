package com.example.root.build_model.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MusicFile {
    @Id
    private long id;
    private String dir;//音乐绝对路径
    private String name;//音乐名称
    private int musicDuration;//音乐时长
    private long isPlayTime;
    private long isFavTime;
    @Generated(hash = 1481831646)
    public MusicFile(long id, String dir, String name, int musicDuration,
            long isPlayTime, long isFavTime) {
        this.id = id;
        this.dir = dir;
        this.name = name;
        this.musicDuration = musicDuration;
        this.isPlayTime = isPlayTime;
        this.isFavTime = isFavTime;
    }
    @Generated(hash = 1290180758)
    public MusicFile() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDir() {
        return this.dir;
    }
    public void setDir(String dir) {
        this.dir = dir;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMusicDuration() {
        return this.musicDuration;
    }
    public void setMusicDuration(int musicDuration) {
        this.musicDuration = musicDuration;
    }
    public long getIsPlayTime() {
        return this.isPlayTime;
    }
    public void setIsPlayTime(long isPlayTime) {
        this.isPlayTime = isPlayTime;
    }
    public long getIsFavTime() {
        return this.isFavTime;
    }
    public void setIsFavTime(long isFavTime) {
        this.isFavTime = isFavTime;
    }
    
    
}
