package com.example.account_ms.activity;

public class tb_flag_J {
    private int id;
    private String mark;

    public tb_flag_J(){
        super();
    }

    public tb_flag_J(int id, String mark){
        super();
        this.id = id;
        this.mark = mark;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setMark(String mark){
        this.mark = mark;
    }

    public int getId(){
        return id;
    }

    public String getMark(){
        return mark;
    }
}
