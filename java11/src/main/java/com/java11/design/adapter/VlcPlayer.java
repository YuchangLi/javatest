package com.java11.design.adapter;

/**
 * VLC播放器实现类 - 被适配者
 */
public class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        System.out.println("正在播放VLC格式文件: " + fileName);
    }

    @Override
    public void playMp4(String fileName) {
        // 不执行任何操作，VLC播放器不支持MP4
    }
}
