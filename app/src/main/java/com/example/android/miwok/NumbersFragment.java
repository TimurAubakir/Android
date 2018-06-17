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


public class NumbersFragment extends Fragment {
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
        numbers.add(new word("One","Lutti",R.drawable.number_one,R.raw.number_one));
        numbers.add(new word("Two","Otiiko",R.drawable.number_two,R.raw.number_two));
        numbers.add(new word("Three","Tolookosu",R.drawable.number_three,R.raw.number_three));
        numbers.add(new word("Four","Oyyisa",R.drawable.number_four,R.raw.number_four));
        numbers.add(new word("Five","Massokko",R.drawable.number_five,R.raw.number_five));
        numbers.add(new word("Six","Temmokka",R.drawable.number_six,R.raw.number_six));
        numbers.add(new word("Seven","Kenekaku",R.drawable.number_seven,R.raw.number_seven));
        numbers.add(new word("Eight","Kawinta",R.drawable.number_eight,R.raw.number_eight));
        numbers.add(new word("Nine","Wo'e",R.drawable.number_nine,R.raw.number_nine));
        numbers.add(new word("Ten","Na'aacha",R.drawable.number_ten,R.raw.number_ten));

        wordAdapter itemsAdapter = new wordAdapter(getActivity(),numbers,R.color.category_numbers);

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
