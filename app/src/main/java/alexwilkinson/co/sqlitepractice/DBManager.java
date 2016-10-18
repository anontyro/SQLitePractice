package alexwilkinson.co.sqlitepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * database helper class that will build the database and the structure of the table
 * Created by Alex on 18/10/2016.
 */

public class DBManager {
    //object made to allow SQLite intergration
    private SQLiteDatabase sqlDB;
    //Google mandated formatting for the create request
    static final String dbName = "Employees";
    static final String tableName ="Login";
    static final String colUsername = "Username";
    static final String colPassword = "Password";
    static final int dbVersion = 1;

    //simple SQL creation string that will make the table if it does not exist
    static final String createTable = "CREATE TABLE IF NOT EXISTS " +tableName
            +" (ID integer PRIMARY KEY AUTOINCREMENT, " + colUsername + " TEXT, "
            + colPassword + " TEXT);";

    //constructor that is used to build the database manager
    public DBManager(Context context){
        DatabaseHelper db = new DatabaseHelper(context);
        sqlDB = db.getWritableDatabase();
    }

    //insert method that is used to add the values to the database and returns 0 on failure
    public long insert(ContentValues values){
        long id = sqlDB.insert(tableName,"",values);

        return id;
    }

    //simple query that will check the table for values supplied
    public Cursor query(String[]projection, String selection, String[]selectionArgs,
                        String sortORder){

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(tableName);

        Cursor cursor = qb.query(sqlDB,projection,selection,selectionArgs,null,null,sortORder);

        return cursor;
    }



    // inner class that is used to execute the SQL from the helper class
    static class DatabaseHelper extends SQLiteOpenHelper{
        private Context context;

        //constructor used to setup the database intergration
        public DatabaseHelper(Context context){
            super(context,dbName,null,dbVersion);
            this.context = context;

        }

        //what happens on creation
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(createTable);
        }

        //what will happen when the version is updated
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
            onCreate(db);
        }
    }

}
