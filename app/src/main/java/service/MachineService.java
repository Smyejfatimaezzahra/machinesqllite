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

public class MachineService {

    private static final String TABLE_NAME ="machine";

    private static final String KEY_ID = "id";
    private static final String KEY_NOM = "nom";
    private static final String KEY_PRICE ="price";
    private static MachineService instance;
    private static String [] COLUMNS = {KEY_ID, KEY_NOM, KEY_PRICE};

    private MySQLiteHelper helper;

  private MachineService(Context context) {
        this.helper = new MySQLiteHelper(context);
    }

   public static MachineService getInstance(Context context) {
       if(instance==null) {instance=new MachineService(context);}
       return instance;

    }

    public void create(Machine e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, e.getNom());
        values.put(KEY_PRICE, e.getPrice());
        values.put("salle", e.getS().getId());
        db.insert(TABLE_NAME,
                null,
                values);
        Log.d("insert", e.getNom());
        db.close();
    }

    public void update(Machine e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, e.getId());
        values.put(KEY_NOM, e.getNom());
        values.put(KEY_PRICE, e.getPrice());

        db.update(TABLE_NAME,
                values,
                "id = ?",
                new String[]{e.getId()+""});
        db.close();
    }
    public Machine findById(int id){
        Machine e = null;
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
            e = new Machine();
            e.setId(c.getInt(0));
            e.setNom(c.getString(1));
            e.setPrice(c.getDouble(2));
        }
        db.close();
        return e;
    }
    public  List<Machine> findBySalle(Context ct){
        List<Machine> eds = new ArrayList<>();
        Machine e = null;
        SalleService ss=new SalleService(ct);
        String req ="select salle ,count(*) from machine  group by salle";
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        if(c.moveToFirst()){
            do{
                e = new Machine();
                e.setS(ss.findById(c.getInt(0)));
                e.setCount(c.getInt(1));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }
        return eds;



    }

    public void delete(Machine e){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(TABLE_NAME,
                "id = ?",
                new String[]{String.valueOf(e.getId())});
        db.close();
    }

    public List<Machine> findAll(){
        List<Machine> eds = new ArrayList<>();
        String req ="select * from "+TABLE_NAME;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Machine e = null;
        if(c.moveToFirst()){
            do{
                e = new Machine();
                e.setId(c.getInt(0));
                e.setNom(c.getString(1));
                e.setPrice(c.getDouble(2));
                eds.add(e);
                Log.d("id = ", e.getId()+"");
            }while(c.moveToNext());
        }
        return eds;
    }
    public List<Machine> fillDatabaseWithData(Context ct) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        SalleService ss=new SalleService(ct);
        ss.fillDatabaseWithData();
        if(findAll().isEmpty()) {

           String[] machine = {"Android", "m1", "m2", "m3",
                    "toshibe", "hp", "samsung",
                    "toshibe", "hp", "samsung"};
               int []salle={1,2,3,1,2,3,1,2,3,1};
        Double[] price={100.0 ,10.04,100.0 ,10.04,100.0 ,10.04,100.0 ,10.04,100.0 ,10.04};
        ContentValues values = new ContentValues();
        for (int i=0; i < machine.length; i++) {
            // Put column/value pairs into the container.
            // put() overrides existing values.
            values.put("nom", machine[i]);
            values.put("price",price[i]);
            values.put("salle",salle[i]);
            db.insert("machine", null, values);
        }
        Log.d("insert", "is called");
        db.close();
        }
        return  findAll();
    }
}
