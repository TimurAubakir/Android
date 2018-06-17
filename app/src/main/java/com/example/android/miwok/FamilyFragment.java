package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class FamilyFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener afChangelListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }else if(focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT){
                mediaPlayer.start();
            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                releaseMediaPlayer();
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> numbers = new ArrayList<word>();
        numbers.add(new word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        numbers.add(new word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        numbers.add(new word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        numbers.add(new word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        numbers.add(new word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        numbers.add(new word("younger bother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        numbers.add(new word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        numbers.add(new word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        numbers.add(new word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        numbers.add(new word("grandfahter", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));


        wordAdapter itemsAdapter = new wordAdapter(getActivity(),numbers,R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                word music = numbers.get(position);
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(afChangelListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), music.getmMusicRes());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
        return rootView;
    }


    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(afChangelListener);
        }
    }
}
