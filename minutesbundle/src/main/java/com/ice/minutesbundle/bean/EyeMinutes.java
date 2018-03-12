package com.thinkwage.minutesbundle.bean;

/**
 * Created by ICE on 2017/9/11.
 */

public class EyeMinutes implements Minutes {

    /**
     * id : 8
     * user_id : 76
     * left_eye : 4
     * right_eye : 5
     * create_time : 1505099760
     */

    private int id;
    private String user_id;
    private String left_eye;
    private String right_eye;
    private long create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLeft_eye() {
        return left_eye;
    }

    public void setLeft_eye(String left_eye) {
        this.left_eye = left_eye;
    }

    public String getRight_eye() {
        return right_eye;
    }

    public void setRight_eye(String right_eye) {
        this.right_eye = right_eye;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    @Override
    public String getTimeYMD() {
        return null;
    }

    @Override
    public String getTimeHMS() {
        return null;
    }

    @Override
    public String getDataTitleLeft() {
        return "左眼";
    }

    @Override
    public String getDataLeft() {
        return left_eye;
    }

    @Override
    public String getDataTitleRight() {
        return "右眼";
    }

    @Override
    public String getDataRight() {
        return right_eye;
    }
}
