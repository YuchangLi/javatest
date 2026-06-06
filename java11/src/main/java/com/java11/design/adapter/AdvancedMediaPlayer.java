package com.java11.design.adapter;

/**
 * 高级媒体播放器接口 - 需要被适配的接口
 */
public interface AdvancedMediaPlayer {
    /**
     * 播放VLC格式文件
     */
    void playVlc(String fileName);
    
    /**
     * 播放MP4格式文件
     */
    void playMp4(String fileName);
}
