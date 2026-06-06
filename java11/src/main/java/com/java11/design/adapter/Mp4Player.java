package com.java11.design.adapter;

/**
 * MP4播放器实现类 - 被适配者
 */
public class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        // 不执行任何操作，MP4播放器不支持VLC
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("正在播放MP4格式文件: " + fileName);
    }
}
