package model;

public class tb_flag_J {
    private int id;
    private String flag;

    public tb_flag_J(){
        super();
    }

    public tb_flag_J(int id, String flag){
        super();
        this.id = id;
        this.flag = flag;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setFlag(String flag){
        this.flag = flag;
    }

    public int getId(){
        return id;
    }

    public String getFlag(){
        return flag;
    }
}
