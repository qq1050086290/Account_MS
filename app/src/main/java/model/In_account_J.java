package model;


public class In_account_J {
    private int id;
    private double money;
    private String date;
    private String type;
    private String handler;
    private String mark;
    public In_account_J(){
        super();
    }

    public In_account_J(int id, double money, String date, String type, String handler, String mark){
        super();
        this.id = id;
        this.money = money;
        this.date = date;
        this.type  =type;
        this.handler = handler;
        this.mark = mark;
    }

    //获取用户ID
    public int getId(){
        return  id;
    }

    //获取金额
    public double getMoney(){
        return  money;
    }

    //获取时间
    public String getDate(){
        return  date;
    }

    //获取类别
    public String getType(){
        return  type;
    }

    //获取付款方
    public String getHandler(){
        return  handler;
    }

    //获取备注
    public String getMark(){
        return  mark;
    }

    //设置id
    public void setId(int id){
        this.id = id;
    }

    //设置金额
    public void setMoney(double money){
        this.money = money;
    }

    //设置时间
    public void setDate(String date){
        this.date = date;
    }

    //设置类别
    public void setType(String type){
        this.type = type;
    }

    //设置付款方
    public void setHandler(String handler){
        this.handler = handler;
    }

    //设置备注
    public void setMark(String mark){
        this.mark = mark;
    }
}
