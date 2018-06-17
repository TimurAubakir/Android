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


public class PhrasesFragment extends Fragment {
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
        numbers.add(new word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        numbers.add(new word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        numbers.add(new word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        numbers.add(new word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        numbers.add(new word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        numbers.add(new word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        numbers.add(new word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        numbers.add(new word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        numbers.add(new word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        numbers.add(new word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        wordAdapter itemsAdapter = new wordAdapter(getActivity(),numbers,R.color.category_phrases);

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
