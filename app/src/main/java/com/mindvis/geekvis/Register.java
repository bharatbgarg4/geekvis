package com.mindvis.geekvis;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Dictionary;
import java.util.Hashtable;

public class Register extends AppCompatActivity {


    String name,email,phone,password;
    EditText txtname,txtemail,txtphone,txtpass;
    Button btnregister;
    JSONObject jsondic,jsonsenddic;
    ProgressDialog pd;

    void init(){
        pd=new ProgressDialog(this);
        txtname=(EditText)findViewById(R.id.txtname);
        txtemail=(EditText)findViewById(R.id.txtemail);
        txtphone=(EditText)findViewById(R.id.txtphone);
        txtpass=(EditText)findViewById(R.id.txtpassword);
        btnregister=(Button)findViewById(R.id.btnsignup);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=txtname.getText().toString().trim();
                email=txtemail.getText().toString().trim();
                phone=txtphone.getText().toString().trim();
                password=txtpass.getText().toString().trim();
                jsondic=new JSONObject();
                jsonsenddic=new JSONObject();
                pd.setMessage("Please Wait...");
                pd.show();
                try {
                    jsondic.put("name",name);
                    jsondic.put("email",email);
                    jsondic.put("phone",phone);
                    jsondic.put("password",password);
                    jsonsenddic.put("pitch","register");
                    jsonsenddic.put("data",jsondic);
                    handler.sendEmptyMessage(100);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                String url="http://www.geekvis.in/api/pitch";

                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, jsonsenddic, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String message=jsonObject.getString("message");
                            pd.dismiss();
                            AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                            if(message.equals("Verify Email")){
                                builder.setMessage(message);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                                builder.show();
                            }else if(message.equals("Email already Registered")){
                                builder.setMessage(message);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i("regiresponse",jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("userresponse",volleyError.toString());
                    }
                });

                /*
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                 */
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        0,-1,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);


            }
        }
    };

}
