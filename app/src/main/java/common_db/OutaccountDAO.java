package common_db;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Out_account_J;

public class OutaccountDAO {
	private DBOpenHelper helper;// 创建DBOpenHelper对象
	private SQLiteDatabase db;// 创建SQLiteDatabase对象

	public OutaccountDAO(Context context){// 定义构造函数
		helper = new DBOpenHelper(context);// 初始化DBOpenHelper对象
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
	}

	/**
	 * 添加支出信息
	 *
	 * @param Out_account_J
	 */
	public void add(Out_account_J Out_account_J) {
		// 执行添加支出信息操作
		db.execSQL(
				"insert into Out_account_J (id,money,time,type,handler,mark) values (?,?,?,?,?,?)",
				new Object[] { Out_account_J.getId(), Out_account_J.getMoney(),
						Out_account_J.getDate(), Out_account_J.getType(),
						Out_account_J.getHandler(), Out_account_J.getMark() });
	}

	/**
	 * 更新支出信息
	 *
	 * @param Out_account_J
	 */
	public void update(Out_account_J Out_account_J) {
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		// 执行修改支出信息操作
		db.execSQL(
				"update Out_account_J set money = ?,time = ?,type = ?,handler = ?,mark = ? where id = ?",
				new Object[] { Out_account_J.getMoney(),
						Out_account_J.getDate(), Out_account_J.getType(),
						Out_account_J.getHandler(), Out_account_J.getMark(),
						Out_account_J.getId() });
	}

	/**
	 * 查找支出信息
	 *
	 * @param id
	 * @return
	 */
	@SuppressLint("Range")
	public Out_account_J find(int id) {
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		Cursor cursor = db
				.rawQuery(
						"select id,money,time,type,handler,mark from Out_account_J where id = ?",
						new String[] { String.valueOf(id) });// 根据编号查找支出信息，并存储到Cursor类中
		if (cursor.moveToNext()){// 遍历查找到的支出信息
			// 将遍历到的支出信息存储到Out_account_J类中
			return new Out_account_J(
					cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getDouble(cursor.getColumnIndex("money")),
					cursor.getString(cursor.getColumnIndex("time")),
					cursor.getString(cursor.getColumnIndex("type")),
					cursor.getString(cursor.getColumnIndex("handler")),
					cursor.getString(cursor.getColumnIndex("mark")));
		}
		cursor.close();// 关闭游标
		return null;// 如果没有信息，则返回null
	}

	/**
	 * 刪除支出信息
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
			db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
			// 执行删除支出信息操作
			db.execSQL("delete from Out_account_J where id in (" + sb + ")",
					(Object[]) ids);
		}
	}
	/**
	 * 支出信息汇总
	 *
	 * @param id
	 * @return
	 */
	public Map<String,Float> getTotal() {
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		// 获取所有支出汇总信息
		Cursor cursor = db.rawQuery("select type,sum(money) from Out_account_J group by type",null);
		int count=0;
		count=cursor.getCount();
		Map<String,Float> map=new HashMap<String,Float>();	//创建一个Map对象
		cursor.moveToFirst();	//移动第一条记录
		for(int i=0;i<count;i++){// 遍历所有的收入汇总信息
			map.put(cursor.getString(0),cursor.getFloat(1));
			System.out.println("支出："+cursor.getString(0)+cursor.getFloat(1));
			cursor.moveToNext();//移到下条记录
		}
		cursor.close();// 关闭游标
		return map;// 返回Map对象
	}
	/**
	 * 获取支出信息
	 *
	 * @param start
	 *            起始位置
	 * @param count
	 *            每页显示数量
	 * @return
	 */
	@SuppressLint("Range")
	public List<Out_account_J> getScrollData(int start, int count) {
		List<Out_account_J> Out_account_J = new ArrayList<Out_account_J>();// 创建集合对象
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		// 获取所有支出信息
		Cursor cursor = db.rawQuery("select * from Out_account_J limit ?,?",
				new String[] { String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext()){// 遍历所有的支出信息
			// 将遍历到的支出信息添加到集合中
			Out_account_J.add(new Out_account_J(cursor.getInt(cursor
					.getColumnIndex("id")), cursor.getDouble(cursor
					.getColumnIndex("money")), cursor.getString(cursor
					.getColumnIndex("time")), cursor.getString(cursor
					.getColumnIndex("type")), cursor.getString(cursor
					.getColumnIndex("handler")), cursor.getString(cursor
					.getColumnIndex("mark"))));
		}
		cursor.close();// 关闭游标
		return Out_account_J;// 返回集合
	}

	/**
	 * 获取总记录数
	 *
	 * @return
	 */
	public long getCount() {
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select count(id) from Out_account_J",
				null);// 获取支出信息的记录数
		if (cursor.moveToNext()){// 判断Cursor中是否有数据
			return cursor.getLong(0);// 返回总记录数
		}
		cursor.close();// 关闭游标
		return 0;// 如果没有数据，则返回0
	}

	/**
	 * 获取支出最大编号
	 *
	 * @return
	 */
	public int getMaxId() {
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select max(id) from Out_account_J", null);// 获取支出信息表中的最大编号
		while (cursor.moveToLast()) {// 访问Cursor中的最后一条数据
			return cursor.getInt(0);// 获取访问到的数据，即最大编号
		}
		cursor.close();// 关闭游标
		return 0;// 如果没有数据，则返回0
	}
}
