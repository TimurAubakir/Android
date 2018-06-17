package com.example.android.miwok;

/**
 * Created by User on 15.12.2017.
 */

public class word {
    private String mMiwokT;
    private String mEnglishT;
    private int mImageResource=-1;
    private int mMusicRes;
    private int X=-1;

    public word(String EnglishT, String MiwokT, int MusicRes){
        mEnglishT = EnglishT;
        mMiwokT = MiwokT;
        mMusicRes=MusicRes;
    }
    public word(String EnglishT, String MiwokT,int Imageresource, int MusicRes){
        mEnglishT = EnglishT;
        mMiwokT = MiwokT;
        mImageResource=Imageresource;
        mMusicRes=MusicRes;
    }

    public String getEnglishT(){
        return mEnglishT;
    }
    public String getMiwokT(){
        return mMiwokT;
    }
    public int getImageResource(){return mImageResource;}
    public boolean hasImage(){return mImageResource!=X;}
    public int getmMusicRes(){return mMusicRes;}

}
