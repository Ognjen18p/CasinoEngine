package com.audio;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class AudioManager {
    private Media media;

    private MediaPlayer backgroundMusic;
    private AudioClip cardDealSound;
    private AudioClip cardFlipSound;
    private AudioClip chipSound;
    private AudioClip winSound;
    private AudioClip loseSound;

    public AudioManager() {
        loadSounds();
    }

    private void loadSounds(){}
}

