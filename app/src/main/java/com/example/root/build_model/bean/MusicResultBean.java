package com.example.root.build_model.bean;

public class MusicResultBean {
    public int localId;
    public String id;
    public String name;
    public Artists artists;
    public Album album;
    public String page;

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artists getArtists() {
        return artists;
    }

    public void setArtists(Artists artists) {
        this.artists = artists;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}

