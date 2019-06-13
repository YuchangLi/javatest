package com.liyc.demo.bootjsp.bootjsp;

/**
 * 可分享渠道：1-微博，2-微信，3-朋友圈
 * @author 李玉长
 * @date 2019/06/13
 */
public enum EnumShareChannel {

    /**
     * 1-微博
     */
    WEIBO(1,"微博"),
    /**
     * 2-微信
     */
    WECHAT(2,"微信"),
    /**
     * 3-朋友圈
     */
    MOMENTS(3,"朋友圈"),
    ;


    private int type;
    private String name;

    EnumShareChannel(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static EnumShareChannel get(int payType) {
        for (EnumShareChannel c : EnumShareChannel.values()) {
            if(c.type == payType){
                return c;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf ( this.name );
    }

    public static String get(String types) {
        if(types==null || types.equals("")){
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        for(String type : types.split(",")){
            sb.append(",").append(get(Integer.valueOf(type)).name);
        }
        return sb.toString().replaceFirst(",", "");
    }
}
