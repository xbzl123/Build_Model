package com.example.root.build_model;

import androidx.annotation.NonNull;

public class LrcContent implements Comparable<LrcContent>{
    private String LrcStr;
    private int LrcTime;

    public String getLrcStr() {
        return LrcStr;
    }

    public void setLrcStr(String lrcStr) {
        LrcStr = lrcStr;
    }

    public int getLrcTime() {
        return LrcTime;
    }

    public void setLrcTime(int lrcTime) {
        LrcTime = lrcTime;
    }

    @Override
    public int compareTo(@NonNull LrcContent o) {
        int itr = this.getLrcTime() - o.getLrcTime();
        return itr;
    }
}
