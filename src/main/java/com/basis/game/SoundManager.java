package com.basis.game;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class SoundManager {
    private Media media;

    private MediaPlayer backgroundMusic;
    private AudioClip cardDealSound;
    private AudioClip cardFlipSound;
    private AudioClip chipSound;
    private AudioClip winSound;
    private AudioClip loseSound;

    public SoundManager() {
        loadSounds();
    }

    private void loadSounds(){}
}

