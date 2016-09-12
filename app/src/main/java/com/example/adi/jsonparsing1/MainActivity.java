package com.example.adi.jsonparsing1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button caller_but;
    JSONParser jsonParser = new JSONParser();

    Integer flag=0;
    String uname="",pword="",name_tag="",mob_tag="",mail_tag="";

    private static final String TAG_SUCCESS="success",TAG_MOBILE="mobile",TAG_EMAIL="email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname="This is a username";
        pword="This is a password";
        Log.d("CLICK CHECK",uname);
        userRegister();
        Log.d("CaLL STATUS","PERFECT");
        caller_but=(Button) findViewById(R.id.caller_button);
        caller_but.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    void userRegister(){
        Log.d("CALL CHECK",uname);
        new UserRegister(this,this).execute();
        Log.d("CALL CHECK 2",uname);
    }

    private class UserRegister extends AsyncTask<String, Void, String> {

        private Activity activity;
        private Context context;

        public UserRegister(Activity activity, Context context) {
            this.activity = activity;
            this.context = context;
        }

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Registering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put("username", uname);
                params.put("password",pword);

                //Log for Debugging
                Log.d("REQUEST_CHECK",uname);

                JSONObject json = jsonParser.makeHttpRequest("http://192.168.56.1/json_test/parse_one.php", "POST", params);

                //Log for Debugging
                Log.d("JSON_SUCCESSFUL", json.toString());

                int success = json.getInt(TAG_SUCCESS);
                name_tag=uname;
                mob_tag=json.getString(TAG_MOBILE);
                mail_tag=json.getString(TAG_EMAIL);

                Log.d("TAG_CHECK",mob_tag+mail_tag);
                if (success == 1) {
                    flag = 1;
                }

                else{

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String jsonStr){
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            performCheck();
        }
    }

    private void performCheck() {
        if(flag==1){
            Intent i=new Intent(getApplicationContext(),SecondActivity.class);
            startActivity(i);
        }
        else{
            Toast.makeText(getApplicationContext(),"It didn't work",Toast.LENGTH_SHORT).show();
        }
    }
}
