package android.example.com.bakingapp.RecipeDetail;

import android.content.res.Configuration;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.data.Steps;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;




import com.google.android.exoplayer2.ExoPlayerFactory;


import com.google.android.exoplayer2.SimpleExoPlayer;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;

import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

import com.google.android.exoplayer2.trackselection.TrackSelector;

import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;




/**
 * Created by Mircea.
 */

public class DetailedStepsFragment extends Fragment {
    public static final String PLAYBACK_POSITION = "playback_position";
    public static final String PLAY_WHEN_READY = "play_when_ready";

    private Steps step;
    private SimpleExoPlayer mExoPlayer;
    private long playbackPosition;
    private boolean playWhenReady;
    private PlayerView mPlayerView;
    private PlaybackStateCompat.Builder mStateBuilder;


    private String videoURL;

    public DetailedStepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detailed_step_fragment, container, false);

        TextView description = rootView.findViewById(R.id.detailed_textview);
        ImageView thumbnailImage = rootView.findViewById(R.id.image_thumbnail);
        TextView stepidTextview = rootView.findViewById(R.id.step_id_textview);
        mPlayerView = rootView.findViewById(R.id.playerView);


        if (this.getArguments() != null) {
            step = this.getArguments().getParcelable("steps");
            description.setText(step.getDescription());
        } else {
            description.setText("description");

        }

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }

        stepidTextview.setText(String.valueOf("Step " + step.getSteps_id()));
        // Set a background image until video is ready
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.noimage));


        videoURL = step.getVideoURL();
        String thumbnailURL = step.getThumbnailURL();

        if (!TextUtils.isEmpty(thumbnailURL)) {
            thumbnailImage.setVisibility(View.GONE);

        } else {
            thumbnailImage.setVisibility(View.VISIBLE);

            if (!thumbnailURL.equals("")) {
                Picasso.get()
                        .load(thumbnailURL)
                        .error(R.drawable.noimage)
                        .into(thumbnailImage);
            } else {
                Picasso.get()
                        .load(R.drawable.noimage)
                        .into(thumbnailImage);
            }
        }

        if (!TextUtils.isEmpty(videoURL)) {
            initializePlayer(Uri.parse(videoURL));
            thumbnailImage.setVisibility(View.GONE);
            mExoPlayer.setPlayWhenReady(true);

        } else {
            mPlayerView.setVisibility(View.GONE);
        }
        return rootView;
    }

    private void initializePlayer(Uri uri) {

        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            mPlayerView.setPlayer(mExoPlayer);


        }


        MediaSource mediaSource = buildMediaSource(uri);
        mExoPlayer.prepare(mediaSource, true, false);


        // Resume playing state and playing position
        if (playbackPosition != 0) {
            mExoPlayer.seekTo(playbackPosition);
            mExoPlayer.setPlayWhenReady(playWhenReady);
        } else {
            // Otherwise, if position is 0, the video never played and should start by default
            mExoPlayer.setPlayWhenReady(true);
        }












    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(getString(R.string.app_name))).
                createMediaSource(uri);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(Uri.parse(videoURL));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer(Uri.parse(videoURL));
        }
    }


    @Override
    public void onPause() {
        super.onPause();

        if (mExoPlayer != null) {
            playWhenReady = mExoPlayer.getPlayWhenReady();
            playbackPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYBACK_POSITION, playbackPosition);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
    }


}