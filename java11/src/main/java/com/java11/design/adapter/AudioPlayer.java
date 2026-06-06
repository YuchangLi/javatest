package com.java11.design.adapter;

/**
 * 音频播放器 - 实现MediaPlayer接口的具体类
 * 
 * 这个类可以直接播放MP3格式，对于其他格式则使用适配器
 */
public class AudioPlayer implements MediaPlayer {
    
    private MediaAdapter mediaAdapter;

    @Override
    public void play(String audioType, String fileName) {
        // 内置支持MP3格式
        if ("mp3".equalsIgnoreCase(audioType)) {
            System.out.println("正在播放MP3格式文件: " + fileName);
        } 
        // 对于VLC和MP4格式，使用适配器
        else if ("vlc".equalsIgnoreCase(audioType) || "mp4".equalsIgnoreCase(audioType)) {
            mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType, fileName);
        } 
        // 不支持的格式
        else {
            System.out.println("不支持的媒体格式: " + audioType);
        }
    }
}
