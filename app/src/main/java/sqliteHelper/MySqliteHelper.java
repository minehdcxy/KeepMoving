package sqliteHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {

	public MySqliteHelper(Context context, String name, CursorFactory factory,
						  int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table hotel(_id integer primary key autoincrement,"
				+" name varchar(50),"
				+" sex varchar(10),"
				+" idCard varchar(50),"
				+" timeIn varchar(50),"
				+" timeSum varchar(50),"
				+" roomNum varchar(50),"
				+" money varchar(255))");
		db.execSQL("create table if not exists user(id integer primary key autoincrement,"
				+" username varchar(20),"
				+" pwd varchar(50))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		onCreate(db);
	}

}
