package com.mindvis.geekvis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signup extends AppCompatActivity {


    Button btnlogin;
    LoginButton btnfb;
    EditText txtemail,txtpass;
    TextView txtregister;
    JSONObject jsondic,jsonsend;
    private CallbackManager callbackManager;
    JSONObject jsonfb,jsonfbsend;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    void init(){
        btnfb=(LoginButton) findViewById(R.id.btnfb);
        txtemail=(EditText)findViewById(R.id.txtlemail);
        txtpass=(EditText)findViewById(R.id.txtlpass);
        btnlogin=(Button)findViewById(R.id.btnlogin);
        txtregister=(TextView)findViewById(R.id.txtnewacc);
        btnlogin.setOnClickListener(clickListener);
        txtregister.setOnClickListener(clickListener);
        btnfb.setReadPermissions("email");
        jsonfb=new JSONObject();
        jsonfbsend=new JSONObject();
        btnfb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken=loginResult.getAccessToken();
                Profile profile=Profile.getCurrentProfile();

                Log.i("authtoken",accessToken.getToken().toString());
                if(profile!=null){

                    try {
                        jsonfb.put("provider","facebook-oauth2");
                        jsonfb.put("authorizationCode",accessToken.getToken().toString());
                        jsonfb.put("redirectUri","http://mech.mindvis.in");
                        jsonfbsend.put("pitch","social");
                        jsonfbsend.put("data",jsonfb);
                        handler.sendEmptyMessage(200);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Signup.this,profile.getName(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Signup.this,"error"+"--"+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });



    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
            switch (id){
                case R.id.btnlogin:
                    Toast.makeText(Signup.this,"login",Toast.LENGTH_SHORT).show();
                    jsondic=new JSONObject();
                    jsonsend=new JSONObject();
                    try {
                        jsondic.put("email",txtemail.getText().toString().trim());
                        jsondic.put("password",txtpass.getText().toString().trim());
                        jsonsend.put("pitch","login");
                        jsonsend.put("data",jsondic);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(100);
                    break;
                case R.id.txtnewacc:
                    Intent register=new Intent(Signup.this,Register.class);
                    startActivity(register);
                    break;
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences(keys.sharedpreferences, MODE_PRIVATE);
        callbackManager=CallbackManager.Factory.create();
        init();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                String url="http://geekvis.in/api/pitch";
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, jsonsend, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String token = null;
                        String message=null;
                        AlertDialog.Builder builder=new AlertDialog.Builder(Signup.this);
                        try {

                            //JSONArray jsonuser=jsonObject.getJSONArray("user");
                            if(jsonObject.toString().contains("message")){
                                message=jsonObject.getString("message");
                                builder.setMessage(message);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                            }else{
                                token=jsonObject.getString("token");
                                editor=sharedPreferences.edit();
                                editor.putString(keys.usertoken,token);
                                editor.putString(keys.loginshow,"no");
                                editor.commit();
                                JSONObject user=jsonObject.getJSONObject("user");
                                Log.i("lotoken",user.length()+"");
                                String name=user.getString("name");
                                String email=user.getString("email");
                                editor.putString(keys.username,name);
                                editor.putString(keys.useremail,email);
                                editor.commit();
                                Toast.makeText(Signup.this,name+"--"+email,Toast.LENGTH_SHORT).show();
                                for(int i=0;i<user.length();i++){
                                }
                                Intent userwall=new Intent(Signup.this,Userwall.class);
                                startActivity(userwall);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i("lresponse",token+""+jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("luserresponse",volleyError.toString());
                    }
                });

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
            }
            else if(msg.what==200){

                String url="http://mech.mindvis.in/api/pitch";
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, jsonfbsend, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String token = null;
                        try {

                            //JSONArray jsonuser=jsonObject.getJSONArray("user");
                            token=jsonObject.getString("token");
                            JSONObject user=jsonObject.getJSONObject("user");
                            Log.i("lotoken",user.length()+"");
                            for(int i=0;i<user.length();i++){
                                String name=user.getString("name");
                                String email=user.getString("email");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i("lresponse",token+""+jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("luserresponse",volleyError.toString());
                    }
                });

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
            }
        }
    };






}
