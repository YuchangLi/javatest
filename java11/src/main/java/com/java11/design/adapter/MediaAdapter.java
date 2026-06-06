package com.java11.design.adapter;

/**
 * 媒体适配器类 - 将AdvancedMediaPlayer适配为MediaPlayer接口
 * 
 * 这是适配器模式的核心类，它实现了目标接口(MediaPlayer)，
 * 并内部持有被适配者(AdvancedMediaPlayer)的引用
 */
public class MediaAdapter implements MediaPlayer {
    
    private AdvancedMediaPlayer advancedMediaPlayer;

    /**
     * 根据音频类型创建相应的适配器
     */
    public MediaAdapter(String audioType) {
        if ("vlc".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer = new VlcPlayer();
        } else if ("mp4".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        if ("vlc".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer.playVlc(fileName);
        } else if ("mp4".equalsIgnoreCase(audioType)) {
            advancedMediaPlayer.playMp4(fileName);
        }
    }
}
