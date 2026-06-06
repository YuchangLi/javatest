package com.java11.design.adapter;

/**
 * 目标接口 - 客户端期望的接口
 */
public interface MediaPlayer {
    /**
     * 播放音频文件
     * @param audioType 音频类型 (mp3, mp4, vlc等)
     * @param fileName 文件名
     */
    void play(String audioType, String fileName);
}
