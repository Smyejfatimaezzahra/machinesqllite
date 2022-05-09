package service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import classes.Machine;
import classes.Salle;
import classes.User;
import util.MySQLiteHelper;

public class UserService {
    private static final String TABLE_NAME ="users";

    private static final String KEY_ID = "id";
    private static final String KEY_NOM = "name";
    private static final String KEY_Pass ="password";
    private static MachineService instance;
    private static String [] COLUMNS = {KEY_ID,KEY_NOM, KEY_Pass};
    private MySQLiteHelper helper;

    public UserService(Context context) {
        this.helper = new MySQLiteHelper(context);
    }



    public User findById(int id){
       User e = null;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c;
        c = db.query(TABLE_NAME,
                COLUMNS,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if(c.moveToFirst()){
            e = new User();
            e.setUserId(c.getInt(0));
            e.setUsername(c.getString(1));
            e.setPassword(c.getString(2));
        }
        db.close();
        return e;
    }
    public Boolean findByPassAndName(String name,String pass){
       User e = null;
        List<User> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME+" "+"where name='"+name+"' and password='"+pass+"'";
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
         if(c.moveToFirst()) return  true;
         else return false;
    }


    public List<User> findAll(){
        List<User> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
       User e = null;
        if(c.moveToFirst()){
            do{
                e = new User();
                e.setUserId(c.getInt(0));
                e.setUsername(c.getString(1));
                e.setPassword(c.getString(2));
                eds.add(e);
                Log.d("id = ", e.getUsername()+"");
            }while(c.moveToNext());
        }
        return eds;
    }
    public List<User> fillDatabaseWithData() {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        if(findAll().isEmpty()) {

            String[] name = {"ahmed", "admin", "ftmz"};
            String[] pass = {"123456", "123456", "123456"};


            ContentValues values = new ContentValues();
            for (int i=0; i < name.length; i++) {
                // Put column/value pairs into the container.
                // put() overrides existing values.
                values.put(KEY_NOM, name[i]);
                values.put(KEY_Pass, pass[i]);
                db.insert(TABLE_NAME, null, values);
            }
            Log.d("insert", "is called");
            db.close();
        }
        return  findAll();
    }
}
