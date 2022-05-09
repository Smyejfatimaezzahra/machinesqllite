package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "tests";
    private static final String CREATE_TABLE_SALLE = "create table salle(" +
            "id INTEGER primary key autoincrement," +
            "ref TEXT" +
            ")";
    private static final String CREATE_TABLE_User = "create table users(" +
            "id INTEGER primary key autoincrement," +
            "name TEXT," +
            "password TEXT" +
            ")";
    private static final String CREATE_TABLE_MACHINE = "create table machine(" +
            "id INTEGER primary key autoincrement," +
            "nom TEXT," +
            "price DOUBLE," +
            " salle INTEGER," +
            "  FOREIGN KEY (salle) REFERENCES salle(id) )";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SALLE);
        db.execSQL(CREATE_TABLE_MACHINE);
        db.execSQL(CREATE_TABLE_User);


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if exists salle");
        db.execSQL("DROP table if exists machine");
        db.execSQL("DROP table if exists users");
        this.onCreate(db);
    }
}
