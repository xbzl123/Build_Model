package com.example.root.build_model.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FavourMusics {
    @Id
    private long id;
    private String SongName;
    private String SongPath;
    private long addTime;
    @Generated(hash = 809003333)
    public FavourMusics(long id, String SongName, String SongPath, long addTime) {
        this.id = id;
        this.SongName = SongName;
        this.SongPath = SongPath;
        this.addTime = addTime;
    }
    @Generated(hash = 1822285626)
    public FavourMusics() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getSongName() {
        return this.SongName;
    }
    public void setSongName(String SongName) {
        this.SongName = SongName;
    }
    public String getSongPath() {
        return this.SongPath;
    }
    public void setSongPath(String SongPath) {
        this.SongPath = SongPath;
    }
    public long getAddTime() {
        return this.addTime;
    }
    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

}
