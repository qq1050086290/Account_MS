package common_db;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.tb_flag_J;

public class FlagDAO {
	private DBOpenHelper helper;// 创建DBOpenHelper对象
	private SQLiteDatabase db;// 创建SQLiteDatabase对象

	public FlagDAO(Context context){// 定义构造函数
		helper = new DBOpenHelper(context);// 初始化DBOpenHelper对象
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
	}

	/**
	 * 添加便签信息
	 *
	 * @param tb_flag_J
	 */
	public void add(tb_flag_J tb_flag_J) {
		db.execSQL("insert into tb_flag_J (id,flag) values (?,?)", new Object[] {
				tb_flag_J.getId(), tb_flag_J.getFlag() });// 执行添加便签信息操作
	}

	/**
	 * 更新便签信息
	 *
	 * @param tb_flag_J
	 */
	public void update(tb_flag_J tb_flag_J) {
//		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		db.execSQL("update tb_flag_J set flag = ? where id = ?", new Object[] {
				tb_flag_J.getFlag(), tb_flag_J.getId() });// 执行修改便签信息操作
	}

	/**
	 * 查找便签信息
	 *
	 * @param id
	 * @return
	 */
	@SuppressLint("Range")
	public tb_flag_J find(int id) {
//		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery(
				"select id,flag from tb_flag_J where id = ?",
				new String[] { String.valueOf(id) });// 根据编号查找便签信息，并存储到Cursor类中
		if (cursor.moveToNext()){// 遍历查找到的便签信息
			// 将遍历到的便签信息存储到tb_flag_J类中
			return new tb_flag_J(cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("flag")));
		}
		cursor.close();// 关闭游标
		return null;// 如果没有信息，则返回null
	}

	/**
	 * 刪除便签信息
	 *
	 * @param ids
	 */
	public void detele(Integer... ids) {
		if (ids.length > 0){// 判断是否存在要删除的id
			StringBuffer sb = new StringBuffer();// 创建StringBuffer对象
			for (int i = 0; i < ids.length; i++){// 遍历要删除的id集合
				sb.append('?').append(',');// 将删除条件添加到StringBuffer对象中
			}
			sb.deleteCharAt(sb.length() - 1);// 去掉最后一个“,“字符
//			db = helper.getWritableDatabase();// 创建SQLiteDatabase对象
			// 执行删除便签信息操作
			db.execSQL("delete from tb_flag_J where id in (" + sb + ")",
					(Object[]) ids);
		}
	}

	/**
	 * 获取便签信息
	 *
	 * @param start
	 *            起始位置
	 * @param count
	 *            每页显示数量
	 * @return
	 */
	@SuppressLint("Range")
	public List<tb_flag_J> getScrollData(int start, int count) {
		List<tb_flag_J> listb_flag_Js = new ArrayList<tb_flag_J>();// 创建集合对象
//		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		// 获取所有便签信息
		Cursor cursor = db.rawQuery("select * from tb_flag_J limit ?,?",
				new String[] { String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext()){// 遍历所有的便签信息
			// 将遍历到的便签信息添加到集合中
			listb_flag_Js.add(new tb_flag_J(cursor.getInt(cursor
					.getColumnIndex("id")), cursor.getString(cursor
					.getColumnIndex("flag"))));
		}
		cursor.close();// 关闭游标
		return listb_flag_Js;// 返回集合
	}

	/**
	 * 获取总记录数
	 *
	 * @return
	 */
	public long getCount() {
//		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select count(id) from tb_flag_J", null);// 获取便签信息的记录数
		if (cursor.moveToNext()){// 判断Cursor中是否有数据
			return cursor.getLong(0);// 返回总记录数
		}
		cursor.close();// 关闭游标
		return 0;// 如果没有数据，则返回0
	}

	/**
	 * 获取便签最大编号
	 *
	 * @return
	 */
	public int getMaxId() {
//		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select max(id) from tb_flag_J", null);// 获取便签信息表中的最大编号
		while (cursor.moveToLast()) {// 访问Cursor中的最后一条数据
			return cursor.getInt(0);// 获取访问到的数据，即最大编号
		}
		cursor.close();// 关闭游标
		return 0;// 如果没有数据，则返回0
	}
}
