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
import util.MySQLiteHelper;

public class SalleService {

    private static final String TABLE_NAME ="salle";

    private static final String KEY_ID = "id";
    private static final String KEY_NOM = "nom";
    private static final String KEY_REF ="ref";
    private static MachineService instance;
    private static String [] COLUMNS = {KEY_ID, KEY_REF};
    private MySQLiteHelper helper;

    public SalleService(Context context) {
        this.helper = new MySQLiteHelper(context);
    }

    public void create(Salle s){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REF, s.getRef());
        db.insert(TABLE_NAME,
                null,
                values);
        db.close();
    }

    public void update(Salle e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, e.getId());
        values.put(KEY_REF, e.getRef());

        db.update(TABLE_NAME,
                values,
                "id = ?",
                new String[]{e.getId()+""});
        db.close();
    }
    public Salle findById(int id){
        Salle e = null;
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
            e = new Salle();
            e.setId(c.getInt(0));
            e.setRef(c.getString(1));
        }
        db.close();
        return e;
    }

    public void delete(Machine e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(TABLE_NAME,
                "id = ?",
                new String[]{String.valueOf(e.getId())});
        db.close();
    }

    public List<Salle> findAll(){
        List<Salle> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Salle e = null;
        if(c.moveToFirst()){
            do{
                e = new Salle();
                e.setId(c.getInt(0));
                e.setRef(c.getString(1));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }
        return eds;
    }
    public List<Salle> fillDatabaseWithData() {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        Log.d("Beforeinsert", "is called");

        if(findAll().isEmpty()) {

            String[] salle = {"s1", "s2", "ss2"};


            ContentValues values = new ContentValues();
            for (int i=0; i < salle.length; i++) {
                // Put column/value pairs into the container.
                // put() overrides existing values.
                values.put(KEY_REF, salle[i]);
                db.insert(TABLE_NAME, null, values);
            }
            Log.d("insert", "is called");
            db.close();
        }
        return  findAll();
    }
}
