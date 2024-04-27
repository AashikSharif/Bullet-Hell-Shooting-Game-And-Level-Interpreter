package com.bullethell.game.controllers;

import com.bullethell.game.systems.SoundManager;

public class SoundController {
    private SoundManager soundManager;

    public SoundController(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public void playPlayerShootSound() {
        soundManager.playSound("player-shoot");
    }

    public void playEnemyShootSound() {
        soundManager.playSound("enemy-shoot");
    }
    public void playExplosionSound() {
        soundManager.playSound("explosion");
    }

    public void playLostSound(){
        soundManager.playSound("player-lose");
    }
    public void playWinSound(){
        soundManager.playSound("player-win");
    }

}