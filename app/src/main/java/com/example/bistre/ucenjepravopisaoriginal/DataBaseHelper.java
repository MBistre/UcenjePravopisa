package com.example.bistre.ucenjepravopisaoriginal;

/**
 * Created by Bistre on 4.9.2016..
 */

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class DataBaseHelper extends SQLiteOpenHelper {

    //all static variables

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Rijeci.db";
    private static final String DATABASE_PATH = "/databases/";
    static Context ctx;
    public DataBaseHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }


    //dohvati sve rijeci iz tablice RijeciCC
    public List<Rijec> sveRijeciCC() {
        List<Rijec> rijeci = new ArrayList<Rijec>();

        String selectQuery = "SELECT * FROM RijeciCC ORDER BY RANDOM()";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Rijec rijec = new Rijec();
                rijec.setID(Integer.parseInt(cursor.getString(0)));
                rijec.setTocno(cursor.getString(1));
                rijec.setNetocno(cursor.getString(2));

                rijeci.add(rijec);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return rijeci;
    }



    //get number od data
    public long getNumberofDataCC() {

        SQLiteDatabase db = this.getReadableDatabase();
        long broj = DatabaseUtils.queryNumEntries(db, "RijeciCC");
        db.close();
        return broj;
    }

    public long getNumberofDataIJEJE() {

        SQLiteDatabase db = this.getReadableDatabase();
        long broj = DatabaseUtils.queryNumEntries(db, "RijeciIJEJE");
        db.close();
        return broj;
    }



    public void CopyDataBaseFromAssets() throws IOException {
        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

        //path to the just created empty DB
        String outFileName = getDataBasePath();

        //if the path doesn't exists, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DATABASE_PATH);
        if(!f.exists())
            f.mkdir();

        //open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private static String getDataBasePath() {
        return ctx.getApplicationInfo().dataDir + DATABASE_PATH + DATABASE_NAME;
    }


    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAssets();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

