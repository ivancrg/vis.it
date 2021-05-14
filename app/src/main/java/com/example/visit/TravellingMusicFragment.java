package com.example.visit;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import kotlinx.coroutines.ObsoleteCoroutinesApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TravellingMusicFragment extends Fragment {

    public TravellingMusicFragment() {
        // Required empty public constructor
    }

    public static TravellingMusicFragment newInstance() {
        TravellingMusicFragment fragment = new TravellingMusicFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    View view;
    TextView playerPosition, playerDuration, name;
    SeekBar seekBar;
    ImageView rewind, play, fastForward, pause;
    ImageView avatar;
    String track = "";
    Button next;
    MediaPlayer player;
    Handler handler = new Handler();
    int check = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_travelling_music, container, false);

        playerPosition = view.findViewById(R.id.player_position);
        playerDuration = view.findViewById(R.id.player_duration);
        name = view.findViewById(R.id.name);
        seekBar = view.findViewById(R.id.seek_bar);
        rewind = view.findViewById(R.id.btn_rewind);
        play = view.findViewById(R.id.btn_play);
        fastForward = view.findViewById(R.id.btn_ff);
        pause = view.findViewById(R.id.btn_pause);
        avatar = view.findViewById(R.id.avatar);
        next = view.findViewById(R.id.next_song);

        //get music from API
        getMusicAPI();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMusicAPI();
            }
        });

        return view;
    }

    public void getMusicAPI(){

    }

    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}