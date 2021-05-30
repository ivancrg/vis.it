package com.example.visit;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

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
        return new TravellingMusicFragment();
    }

    View view;
    TextView playerPosition;
    TextView playerDuration;
    TextView name;
    SeekBar seekBar;
    ImageView rewind;
    ImageView play;
    ImageView fastForward;
    ImageView pause;
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

        next.setOnClickListener(v -> getMusicAPI());

        return view;
    }

    public void getMusicAPI() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://shazam.p.rapidapi.com/songs/list-recommendations?key=484129036")
                .method("GET", null)
                .addHeader("X-RapidAPI-Key", "e31191fb39mshee4aaa6d7f532c7p1091f7jsn9e94e37d2f84")
                .addHeader("X-RapidAPI-Host", "shazam.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject music = new JSONObject(myResponse);
                                JSONArray musicArr = music.getJSONArray("tracks");
                                //gets random song from list
                                int rand = ThreadLocalRandom.current().nextInt(0, 20);
                                JSONObject song = musicArr.getJSONObject(rand);
                                track = song.getJSONObject("hub").getJSONArray("actions")
                                        .getJSONObject(1).getString("uri");

                                //show song title
                                String title = song.getJSONObject("share").getString("subject");
                                name.setText(title);

                                //Initialize media player
                                player = MediaPlayer.create(getContext(), Uri.parse(track));

                                //Initialize runnable
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        //Set progress on seek bar
                                        seekBar.setProgress(player.getCurrentPosition());
                                        handler.postDelayed(this, 500);
                                    }
                                };

                                //getDuration
                                int duration = player.getDuration();
                                String stringDuration = convertFormat(duration);
                                playerDuration.setText(stringDuration);

                                play.setOnClickListener(v -> {
                                    play.setVisibility(View.GONE);
                                    pause.setVisibility(View.VISIBLE);
                                    player.start();
                                    seekBar.setMax(player.getDuration());
                                    handler.postDelayed(runnable, 0);
                                });

                                pause.setOnClickListener(v -> {
                                    pause.setVisibility(View.GONE);
                                    play.setVisibility(View.VISIBLE);
                                    player.pause();
                                    handler.removeCallbacks(runnable);
                                });

                                fastForward.setOnClickListener(v -> {
                                    int currentPosition = player.getCurrentPosition();
                                    int duration1 = player.getDuration();
                                    if (player.isPlaying() && currentPosition != duration1) {
                                        currentPosition = currentPosition + 5000;
                                        playerPosition.setText(convertFormat(currentPosition));
                                        player.seekTo(currentPosition);
                                    }
                                });

                                rewind.setOnClickListener(v -> {
                                    int currentPosition = player.getCurrentPosition();
                                    if (player.isPlaying() && currentPosition > 5000) {
                                        currentPosition = currentPosition - 5000;
                                        playerPosition.setText(convertFormat(currentPosition));
                                        player.seekTo(currentPosition);
                                    }
                                });

                                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        if (fromUser) {
                                            player.seekTo(progress);
                                        }
                                        playerPosition.setText(convertFormat(player.getCurrentPosition()));
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {
                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {
                                    }
                                });

                                player.setOnCompletionListener(mp -> {
                                    pause.setVisibility(View.GONE);
                                    play.setVisibility(View.VISIBLE);
                                    player.seekTo(0);
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Sorry! Music is not available right now, please come back later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
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