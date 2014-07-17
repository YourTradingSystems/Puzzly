package com.mobilez365.puzzly.global;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import com.mobilez365.puzzly.R;

public class SoundManager {
    private static SoundPool wordSoundPool;
    private static MediaPlayer backgroundMusicPlayer;

    private static int wordSoundId = -1;
    private static boolean isMenuMusic = true;

    private static void prepareSoundPool() {
        if (wordSoundPool != null && wordSoundId != -1)
            wordSoundPool.unload(wordSoundId);
        else
            wordSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }

    public static void initWordSound(Context context, String soundName) {
        boolean soundEffectsEnabled = AppHelper.getPlaySound(context);
        if (soundEffectsEnabled) {
            prepareSoundPool();
            int wordId = context.getResources().getIdentifier(soundName, "raw", context.getPackageName());
            wordSoundId = wordSoundPool.load(context, wordId, 1);
        }
    }

    public static void playWordSound() {
        if (wordSoundPool != null && wordSoundId != -1) {
            int streamId = wordSoundPool.play(wordSoundId, 1, 1, 0, 0, 1);
            if (streamId != 0)
                wordSoundPool.setPriority(streamId, 1);
        }
    }

    public static void releaseWordPlayer(){
        if(wordSoundPool != null) {
            wordSoundPool.release();
            wordSoundPool = null;
            wordSoundId = -1;
        }
    }

    public static void playBackgroundMusic(Context context, boolean _isMenuMusic) {
        boolean backgroundMusicEnabled = AppHelper.getPlayBackgroundMusic(context);

        if (backgroundMusicEnabled) {
            if (isMenuMusic != _isMenuMusic)
                stopBackgroundMusic();

            if (backgroundMusicPlayer == null) {
                int backgroundMusicResource = _isMenuMusic ? R.raw.menu_background_music : R.raw.game_background_music;
                backgroundMusicPlayer = MediaPlayer.create(context, backgroundMusicResource);
                backgroundMusicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                backgroundMusicPlayer.setLooping(true);
                backgroundMusicPlayer.start();
            } else
                resumeBackgroundMusic();

            isMenuMusic = _isMenuMusic;
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer.release();
            backgroundMusicPlayer = null;
        }
    }

    public static void pauseBackgroundMusic() {
        if (backgroundMusicPlayer != null)
            backgroundMusicPlayer.pause();
    }

    public static void resumeBackgroundMusic() {
        if (backgroundMusicPlayer != null)
            backgroundMusicPlayer.start();
    }

}
