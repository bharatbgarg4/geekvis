package com.mindvis.geekvis;

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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Splash extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //printkey();
        sharedPreferences=getSharedPreferences(keys.sharedpreferences,MODE_PRIVATE);
        Log.e("packagename",getApplicationContext().getPackageName());
        String loginshow=sharedPreferences.getString(keys.loginshow,"yes");
        if(loginshow.equals("yes")){
            handler.sendEmptyMessageDelayed(100,2500);
        }else{
            handler.sendEmptyMessageDelayed(200,2500);
        }



    }


    void printkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("haskkey:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("hash",e.toString()+"--"+getApplicationContext().getPackageName());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                Intent signup=new Intent(Splash.this,Signup.class);
                startActivity(signup);
            }else if(msg.what==200){
                Intent signup=new Intent(Splash.this,Signup.class);
                startActivity(signup);
            }
        }
    };

}
