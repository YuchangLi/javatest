package com.java11.design.adapter;

/**
 * 适配器模式演示类
 * 
 * 适配器模式是一种结构型设计模式，它允许接口不兼容的类能够一起工作。
 * 
 * 主要角色：
 * 1. Target（目标接口）：客户端期望的接口 - MediaPlayer
 * 2. Adaptee（被适配者）：需要被适配的接口 - AdvancedMediaPlayer
 * 3. Adapter（适配器）：将Adaptee转换为Target接口 - MediaAdapter
 * 4. Client（客户端）：使用Target接口的对象 - AudioPlayer
 * 
 * 适用场景：
 * - 系统需要使用现有的类，但这些类的接口不符合系统的需要
 * - 想要建立一个可以重复使用的类，用于与一些彼此之间没有太大关联的一些类一起工作
 * - 需要一个统一的输出接口，而输入类型不可预知
 */
public class AdapterPatternDemo {
    
    public static void main(String[] args) {
        System.out.println("========== 适配器模式演示 ==========\n");
        
        // 创建音频播放器（客户端）
        AudioPlayer audioPlayer = new AudioPlayer();
        
        // 测试1: 播放MP3格式（原生支持）
        System.out.println("测试1: 播放MP3格式文件");
        audioPlayer.play("mp3", "song.mp3");
        System.out.println();
        
        // 测试2: 播放MP4格式（通过适配器）
        System.out.println("测试2: 播放MP4格式文件");
        audioPlayer.play("mp4", "video.mp4");
        System.out.println();
        
        // 测试3: 播放VLC格式（通过适配器）
        System.out.println("测试3: 播放VLC格式文件");
        audioPlayer.play("vlc", "movie.vlc");
        System.out.println();
        
        // 测试4: 播放不支持的格式
        System.out.println("测试4: 播放不支持的格式");
        audioPlayer.play("avi", "clip.avi");
        System.out.println();
        
        System.out.println("========== 演示结束 ==========");
    }
}
