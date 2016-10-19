package alexwilkinson.co.sqlitepractice;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etPassword, etUsername;
    Button buLogin, buCheck;
    ContentValues values;
    DBManager dbManager;
    ArrayList<AdapterItems> listData = new ArrayList<>();
    MyListAdapter myadapter;

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

            //selectionArgs is used to check the database querying in SQL
            String[] selectionArgs={"%"+etUsername.getText().toString()+"%"};
            listData.clear();
            //SQL query "Username Like ?" with selectionArgs
            Cursor cursor = dbManager.query(null,"Username like ?",selectionArgs,DBManager.colUsername);

            //run through the table and return all of the values in Toast format
            if(cursor.moveToFirst()){
                String tableData = "";
                do {
                    /*
                    tableData += cursor.getString(cursor.getColumnIndex(DBManager.colUsername)) + ","
                            + cursor.getString(cursor.getColumnIndex(DBManager.colPassword)) + ":";
                    */
                    listData.add(new AdapterItems(
                            cursor.getString(cursor.getColumnIndex(DBManager.colID)),
                            cursor.getString(cursor.getColumnIndex(DBManager.colUsername)),
                            cursor.getString(cursor.getColumnIndex(DBManager.colPassword)))
                    );
                }
                while(cursor.moveToNext());
                //Toast.makeText(getApplicationContext(),tableData,Toast.LENGTH_LONG).show();
            }

            myadapter = new MyListAdapter(listData);

            ListView lvdata = (ListView) findViewById(R.id.lvData);
            lvdata.setAdapter(myadapter);
        }
        else{

        }

    }

    private class MyListAdapter extends BaseAdapter{
        ArrayList<AdapterItems> dataAdapterList;

        public MyListAdapter(ArrayList<AdapterItems> dataAdapterList){
            this.dataAdapterList = dataAdapterList;
        }

        @Override
        public int getCount() {
            return dataAdapterList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater myInflator = getLayoutInflater();
            View myView = myInflator.inflate(R.layout.layout_listview, null);

            final AdapterItems adapI = dataAdapterList.get(i);

            TextView tvid = (TextView) myView.findViewById(R.id.tvID);
            tvid.setText(adapI.id);

            TextView tvUsername = (TextView)myView.findViewById(R.id.tvUsername);
            tvUsername.setText(adapI.username);

            TextView tvPassword = (TextView)myView.findViewById(R.id.tvPassword);
            tvPassword.setText(adapI.password);

            return myView;
        }
    }

}
