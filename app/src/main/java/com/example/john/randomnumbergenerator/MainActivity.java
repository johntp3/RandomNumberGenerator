package com.example.john.randomnumbergenerator;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Int3;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView bigNumber;
    private EditText low;
    private EditText high;
    private Button randomButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bigNumber = findViewById(R.id.number);
        low = findViewById(R.id.low);
        high = findViewById(R.id.high);
        randomButton = findViewById(R.id.button);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean validNum() {
        try {
            Integer.parseInt(low.getText().toString());
            Integer.parseInt(high.getText().toString());
            return true;
        }
        catch(NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "You need to type in a number between -2^31 and 2^31", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void randomButton(View view) {


        Random rand = new Random();
        if (validNum()) {
            int lowNum = Integer.parseInt(low.getText().toString());
            int highNum = Integer.parseInt(high.getText().toString());
            int temp;

            if (highNum < lowNum) {
                temp = lowNum;
                lowNum = highNum;
                highNum = temp;
            }
            Multithreaded m = new Multithreaded();
            m.execute(lowNum, highNum);
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class Multithreaded extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            randomButton.setEnabled(false);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            Integer randnum = 0;
            for (int i = 0; i < 50; i++) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Random r = new Random();
                randnum = r.nextInt(integers[1] - integers[0] + 1) + integers[0];
                publishProgress(randnum);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            bigNumber.setText(progress[0].toString());
        }

        @Override
        protected void onPostExecute(Integer result) {
            randomButton.setEnabled(true);
        }
    }

}