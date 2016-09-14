package com.example.adi.jsonparsing1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_SUCCESS = "success", TAG_MOBILE = "mobile", TAG_EMAIL = "email", TAG_ARR = "arr";
    TextView result;
    Button caller_but;
    JSONParser jsonParser = new JSONParser();
    Integer flag=0;
    String uname = "";
    String pword = "";
    String name_tag = "";
    String mob_tag = "";
    String mail_tag = "";
    JSONArray arr_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("CaLL STATUS","PERFECT");
        result=(TextView) findViewById(R.id.json_result);
        caller_but=(Button) findViewById(R.id.caller_button);
        caller_but.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        uname="This is a username";
        pword="This is a password";
        Log.d("CLICK CHECK",uname);
        userRegister();
    }

    void userRegister(){
        Log.d("CALL CHECK",uname);
        new UserRegister(this,this).execute();
        Log.d("CALL CHECK 2",uname);
    }

    private void performCheck() {
        if (flag == 1) {
            result.setText("Heloo Json " + name_tag + mail_tag);
            //Converting our json array to a string
            String s = arr_tag.toString();
            //Forming a string array from that string [by splitting that string from comma(,)]
            String x[] = s.split(",");//Here we get x as our required string array
            System.out.println(s);
            for (String y : x) {
                System.out.println(y);
            }

        } else {
            Toast.makeText(getApplicationContext(), "It didn't work", Toast.LENGTH_SHORT).show();
        }
    }

    private class UserRegister extends AsyncTask<String, Void, String> {

        private Activity activity;
        private Context context;
        private ProgressDialog pDialog;

        public UserRegister(Activity activity, Context context) {
            this.activity = activity;
            this.context = context;
        }

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
                arr_tag = json.getJSONArray(TAG_ARR);
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
}
