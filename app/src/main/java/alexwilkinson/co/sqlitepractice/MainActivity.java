package alexwilkinson.co.sqlitepractice;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etPassword, etUsername;
    Button buLogin, buCheck;
    ContentValues values;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //objects list ----------------------------->

        //declare the buttons
        buLogin =(Button) findViewById(R.id.buLogin);
        buLogin.setOnClickListener(this);

        buCheck =(Button) findViewById(R.id.buCheck);
        buCheck.setOnClickListener(this);

        //declare the text boxes
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);



        dbManager = new DBManager(this);

    }

    //button listener control structure
    @Override
    public void onClick(View v) {
        int getID = v.getId();

        //what happens when buLogin is pressed
        if(getID == R.id.buLogin){
            values = new ContentValues();
            //read the values from the fields to put into the database
            values.put(DBManager.colUsername,etUsername.getText().toString());
            values.put(DBManager.colPassword,etPassword.getText().toString());
            //checks the id value, if id==0 then the add failed
            long id = dbManager.insert(values);
            if(id>0){
                Toast.makeText(getApplicationContext(),"Success! " +
                        "Id added correctly: "+id,Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Error failed to add", Toast.LENGTH_LONG).show();
            }

        }
        //the check button will return the values found in the database
        else if(getID == R.id.buCheck){

            Cursor cursor = dbManager.query(null,null,null,DBManager.colUsername);

            //run through the table and return all of the values in Toast format
            if(cursor.moveToFirst()){
                String tableData = "";
                do {
                    tableData += cursor.getString(cursor.getColumnIndex(DBManager.colUsername)) + ","
                            + cursor.getString(cursor.getColumnIndex(DBManager.colPassword)) + ":";
                }
                while(cursor.moveToNext());
                Toast.makeText(getApplicationContext(),tableData,Toast.LENGTH_LONG).show();
            }
        }
        else{

        }

    }
}
