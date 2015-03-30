package zenproject.meditation.android.sketch.performers.music;

import android.media.MediaPlayer;

import com.juankysoriano.rainbow.core.event.RainbowInputController;

import zenproject.meditation.android.ContextRetriever;
import zenproject.meditation.android.R;
import zenproject.meditation.android.sketch.performers.StepPerformer;

public class MusicPerformer implements StepPerformer {
    private static final float MUSIC_STEP = 0.01f;
    private static final float MIN_VOLUME = 0.05f;
    private MediaPlayer mediaPlayer;
    private final RainbowInputController rainbowInputController;
    private float volume;

    protected MusicPerformer(MediaPlayer mediaPlayer, RainbowInputController rainbowInputController) {
        this.mediaPlayer = mediaPlayer;
        this.rainbowInputController = rainbowInputController;
    }

    public static MusicPerformer newInstance(RainbowInputController rainbowInputController) {
        return new MusicPerformer(null, rainbowInputController);
    }

    @Override
    public void doStep() {
        if (rainbowInputController.isScreenTouched()) {
            increaseVolume();
        } else {
            decreaseVolume();
        }
    }

    private void increaseVolume() {
        if (isPlaying()) {
            volume += MUSIC_STEP;
            volume = Math.min(1f, volume);
            mediaPlayer.setVolume(volume, volume);
        }
    }

    private void decreaseVolume() {
        if (isPlaying()) {
            volume -= MUSIC_STEP;
            volume = Math.max(MIN_VOLUME, volume);
            mediaPlayer.setVolume(volume, volume);
        }
    }

    @Override
    public void reset() {
        volume = MIN_VOLUME;
        if (isMediaPlayerReleased()) {
            mediaPlayer = createMediaPlayer();
        }
        start();
    }

    private MediaPlayer createMediaPlayer() {
        MediaPlayer mediaPlayer = MediaPlayer.create(ContextRetriever.INSTANCE.getCurrentContext(), R.raw.zen);
        mediaPlayer.setVolume(volume, volume);
        mediaPlayer.setLooping(true);

        return mediaPlayer;
    }

    @Override
    public void disable() {
        stop();
    }

    private boolean isMediaPlayerReleased() {
        return mediaPlayer == null;
    }

    private void stop() {
        if (isPlaying()) {
            mediaPlayer.stop();
            releaseMediaPlayer();
        }
    }

    private boolean isPlaying() {
        return !isMediaPlayerReleased() && mediaPlayer.isPlaying();
    }

    private void releaseMediaPlayer() {
        if (!isMediaPlayerReleased()) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void enable() {
        reset();
    }

    private void start() {
        if (!isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void release() {
        mediaPlayer.release();
    }
}
