package com.bullethell.game.systems;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class SoundManager implements Disposable {
    private Map<String, Sound> soundMap;
    private Music backgroundMusic;

    public SoundManager() {
        soundMap = new HashMap<>();
        loadSounds();
        loadMusic();
    }

    private void loadSounds() {
        // Load all sounds
        soundMap.put("player-shoot", Gdx.audio.newSound(Gdx.files.internal("Sounds/12012_buttlet_swish.wav")));
        soundMap.put("enemy-shoot", Gdx.audio.newSound(Gdx.files.internal("Sounds/snd_bullet.wav")));
        soundMap.put("explosion", Gdx.audio.newSound(Gdx.files.internal("Sounds/mixkit-arcade-game-explosion-1699.wav")));
        //soundMap.put("kill-them-all",Gdx.audio.newSound(Gdx.files.internal("Sounds/kill-them-all.wav")));
        soundMap.put("will-kill-you",Gdx.audio.newSound(Gdx.files.internal("Sounds/i-will-kill-you-scary-whisper.OGG")));
        soundMap.put("level-up-bonus",Gdx.audio.newSound(Gdx.files.internal("Sounds/level-up-bonus-sequence.mp3")));
        soundMap.put("power-up",Gdx.audio.newSound(Gdx.files.internal("Sounds/powering_up_funny_sci-fi_dry.mp3")));
        soundMap.put("player-win",Gdx.audio.newSound(Gdx.files.internal("Sounds/win-sfx.mp3")));
        soundMap.put("player-lose",Gdx.audio.newSound(Gdx.files.internal("Sounds/8-bit-video-game-lose-sound-version.mp3")));
    }

    private void loadMusic() {
        // Load background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/8bit-music-for-game.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.0f);
        backgroundMusic.play();
    }

    public void playSound(String soundName) {
        Sound sound = soundMap.get(soundName);
        if (sound != null) {
            sound.play(0.5f);
        }
    }

    @Override
    public void dispose() {
        // Dispose of sounds and music
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
        backgroundMusic.dispose();
    }
}