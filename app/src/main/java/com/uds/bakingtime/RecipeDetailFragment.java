package com.uds.bakingtime;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.uds.bakingtime.databinding.FragmentRecipeDetailBinding;
import com.uds.bakingtime.model.Steps;


public class RecipeDetailFragment extends Fragment {

    public static final String CURRENT_POSITION = "current_position";
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private TextView mDescription;
    private Steps step;

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getStepsData();
        }
    }

    private void getStepsData() {
        Bundle b = getArguments();
        if (b != null && b.containsKey(CURRENT_POSITION)) {
            step = getArguments().getParcelable(CURRENT_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        playerView = rootView.findViewById(R.id.player_view);
        mDescription = rootView.findViewById(R.id.description_for_step_tv);
        mDescription.setText(step.getDescription());
        return rootView;
    }

    private void initializePlayer() {
        String videoUrl = step.getVideoUrl();
        if (TextUtils.isEmpty(videoUrl)) {
            playerView.setVisibility(View.GONE);
        } else {
            if (player == null) {
                DefaultTrackSelector trackSelector = new DefaultTrackSelector(getContext());
                trackSelector.setParameters(
                        trackSelector.buildUponParameters().setMaxVideoSizeSd());
                player = new SimpleExoPlayer.Builder(getContext())
                        .setTrackSelector(trackSelector)
                        .build();
            }
            playerView.setPlayer(player);
            MediaItem mediaItem = new MediaItem.Builder()
                    .setUri(videoUrl)
                    .build();
            player.setMediaItem(mediaItem);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
            player.prepare();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getStepsData();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mDescription.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mDescription.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }


}