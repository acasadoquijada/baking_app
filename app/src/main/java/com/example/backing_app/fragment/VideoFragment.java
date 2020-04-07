package com.example.backing_app.fragment;

import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.backing_app.R;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


/**
 * Fragment that reproduces the video associated to the step, if video is available
 */

public class VideoFragment extends Fragment implements ExoPlayer.EventListener{

    private static final String TAG = VideoFragment.class.getSimpleName();
    private static final String MEDIA_URL_KEY = "media_url";
    private static final String PLAYER_CURRENT_POS_KEY = "player_current_pos";
    private static final String PLAYER_IS_READY_KEY = "player_is_ready";

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private boolean mReady = true;
    private long mPos;
    private MediaSession mMediaSession;
    private PlaybackState.Builder mStateBuilder;

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

        View rootView = inflater.inflate(R.layout.video_fragment,container,false);

        mSimpleExoPlayerView = rootView.findViewById(R.id.fragment_video_player_view);

        // If no video, no player

        if(mMediaURL.equals("")) {
            ImageView image = rootView.findViewById(R.id.no_video_image);
            image.setVisibility(View.VISIBLE);
        } else{
            initializePlayer();
            initializeMediaSession();
        }

        return rootView;
    }

    public void setMediaURL(String mediaURL) {
        this.mMediaURL = mediaURL;
    }

    private void initializePlayer(){

        if(mExoPlayer == null){

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            mSimpleExoPlayerView.setPlayer(mExoPlayer);

            mSimpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            mExoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

            Uri mediaUri = Uri.parse(mMediaURL).buildUpon().build();

            String userAgent = Util.getUserAgent(getContext(),getString(R.string.app_name));

            MediaSource mediaSource =
                    new ExtractorMediaSource(
                            mediaUri,
                            new DefaultDataSourceFactory(getContext(), userAgent),
                            new DefaultExtractorsFactory(),null,null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(mPos);
            mExoPlayer.setPlayWhenReady(mReady);
        }
    }

    /**
     * Initialize the media session
     */
    private void initializeMediaSession() {

        mMediaSession = new MediaSession(getContext(), TAG);

        mMediaSession.setFlags(
                MediaSession.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackState.Builder()
                .setActions(
                        PlaybackState.ACTION_PLAY |
                                PlaybackState.ACTION_PAUSE |
                                PlaybackState.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackState.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback(new MySessionCallback());

        mMediaSession.setActive(true);
    }


        @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(MEDIA_URL_KEY,mMediaURL);
        outState.putLong(PLAYER_CURRENT_POS_KEY, mPos);
        outState.putBoolean(PLAYER_IS_READY_KEY, mReady);
    }

    private void releasePlayer(){
        if(mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer != null){
            mPos = Math.max(0, mExoPlayer.getCurrentPosition());
            mReady = mExoPlayer.getPlayWhenReady();
        }
        releasePlayer();

        if(mMediaSession != null){
            mMediaSession.setActive(false);
        }

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackState.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackState.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private class MySessionCallback extends MediaSession.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}































