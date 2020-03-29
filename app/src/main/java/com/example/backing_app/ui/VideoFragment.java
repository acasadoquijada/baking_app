package com.example.backing_app.ui;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.backing_app.R;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class VideoFragment extends Fragment {

    private static final String TAG = VideoFragment.class.getSimpleName();
    private static final String MEDIA_URL_KEY = "media_url";
    private static final String PLAYER_CURRENT_POS_KEY = "player_current_pos";
    private static final String PLAYER_IS_READY_KEY = "player_is_ready";

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private boolean mReady = true;
    private long mPos;

    private String mMediaURL;

    public VideoFragment(){

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null){
            mMediaURL = savedInstanceState.getString(MEDIA_URL_KEY);
            mPos = savedInstanceState.getLong(PLAYER_CURRENT_POS_KEY);
            mReady = savedInstanceState.getBoolean(PLAYER_IS_READY_KEY);
        }



        //        Log.d(TAG,mMediaURL);
        View rootView = inflater.inflate(R.layout.video_fragment,container,false);

        mSimpleExoPlayerView = rootView.findViewById(R.id.fragment_video_player_view);

        initializePlayer();

        return rootView;
    }

    public void setMediaURL(String mediaURL) {
        this.mMediaURL = mediaURL;
    }

    private void initializePlayer(){

        if(mExoPlayer == null){

            Log.d(TAG, "I CREATE");
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            mSimpleExoPlayerView.setPlayer(mExoPlayer);

//            mMediaURL = "https://finofilipino.org/wp-content/uploads/2020/03/qwerqwerfwqertg3.mp4";

            if(mMediaURL.equals(getString(R.string.step_no_video))) {
                mSimpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                        (getResources(), R.drawable.no_video));
            }

            Uri mediaUri = Uri.parse(mMediaURL).buildUpon().build();

            String userAgent = Util.getUserAgent(getContext(),getString(R.string.app_name));

            MediaSource mediaSource =
                    new ExtractorMediaSource(
                            mediaUri,
                            new DefaultDataSourceFactory(getContext(), userAgent),
                            new DefaultExtractorsFactory(),null,null);


            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(MEDIA_URL_KEY,mMediaURL);
        //outState.putLong(PLAYER_CURRENT_POS_KEY, Math.max(0, mExoPlayer.getCurrentPosition()));
        //outState.putBoolean(PLAYER_IS_READY_KEY, mExoPlayer.getPlayWhenReady());
    }

    private void releasePlayer(){
        if(mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
            Log.d(TAG, "I SHOULD HAVE RELEASED THE PLAYER");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Stop");
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Pause");
        releasePlayer();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroy");
        releasePlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Detach");
        releasePlayer();
    }
}































