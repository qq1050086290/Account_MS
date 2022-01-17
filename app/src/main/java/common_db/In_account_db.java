package common_db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.constraintlayout.solver.state.State;

import java.util.ArrayList;
import java.util.List;

import model.In_account_J;

public class In_account_db {
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    public In_account_db(Context context){
        helper = new DBOpenHelper(context);   //初始化DBOpenHelper对象
    }

    /**
     * 添加收入信息
     * @apiNote ?依次对应new Object对象中的5个元素（猜测）
     * @param in_account_J
     */
    public void add(In_account_J in_account_J){
        db = helper.getWritableDatabase();  //初始化SQLiteDatabase对象

        //执行添加收入的操作
        db.execSQL("insert into tb_in_account(id, money, date, type, handler, mark) values (?, ?, ?, ?, ?, ?)", new Object[]{
                in_account_J.getId(),
                in_account_J.getMoney(),
                in_account_J.getDate(),
                in_account_J.getHandler(),
                in_account_J.getMark()
        });
    }

    /**
     * 更新收入信息
     * @param in_account_J
     */
    public void update(In_account_J in_account_J){
        db = helper.getWritableDatabase();  //初始化SQLiteDatabase对象

        //执行更新操作
        db.execSQL("update In_account_J set money = ?, date = ?, type = ?, handler = ?, mark = ?", new Object[]{
                in_account_J.getId(),
                in_account_J.getMoney(),
                in_account_J.getDate(),
                in_account_J.getHandler(),
                in_account_J.getMark()
        });
    }

    /**
     * 查找收入信息
     * @param id
     */
    @SuppressLint("Range")
    public In_account_J find(int id){
        db = helper.getWritableDatabase();  //初始化SQLiteDatabase对象
        //对数据库新型行检索
        Cursor cursor = db.rawQuery("select id, money, date, type, handler, mark from tb_in_account where id = ?", new String[]{
                String.valueOf(id)  //将id转化为字符串，并纯出道cursor对象中(猜测这里的id被?替代)
        });

        if (cursor.moveToNext()){
            //如果存在下一条recode，则将信息存储到in_account_J类中
            return new In_account_J(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("handler")),
                    cursor.getString(cursor.getColumnIndex("mark")));
        } else
        {
            return null;
        }
    }

    /**
     * 删除收入信息
     * @param ids
     */
    public void delete(Integer... ids){
        if (ids.length > 0){
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ids.length; i++){
                sb.append('?').append(',');  //将删除条件添加到sb中
            }
            sb.deleteCharAt(sb.length() - 1);  //去掉最后一个','字符
            db = helper.getWritableDatabase();  //初始化SQLiteDatabase对象
            db.execSQL("delete from tb_in_account where id in ("+sb+")", (Object[]) ids);
        }
    }

    /**
     * @param start 起始位置
     * @param count 每页显示数量
     * @return
     */
    @SuppressLint("Range")
    public List<In_account_J> getScrollData(int start, int count){
        List<In_account_J> tb_inaccount = new ArrayList<In_account_J>(); //创建集合对象
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_in_account limit ?,?", new String[]{
                String.valueOf(start),String.valueOf(count)
        });

        while(cursor.moveToNext()){
            tb_inaccount.add(
                    new In_account_J(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getDouble(cursor.getColumnIndex("money")),
                            cursor.getString(cursor.getColumnIndex("date")),
                            cursor.getString(cursor.getColumnIndex("type")),
                            cursor.getString(cursor.getColumnIndex("handler")),
                            cursor.getString(cursor.getColumnIndex("mark"))));
        }

        return tb_inaccount;
    }

    /**
     * @return
     */
    public long getCount(){
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(id) from tb_in_account", null);
        if (cursor.moveToNext()){
            return cursor.getLong(0);
        } else {
            return 0;
        }
    }
}
