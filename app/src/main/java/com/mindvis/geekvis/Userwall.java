package com.mindvis.geekvis;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Userwall extends AppCompatActivity {

    ListView walllist;
    ArrayList<wallbean> wallbeanlist;
    Walladapter walladapter;


    void init(){
        walllist=(ListView)findViewById(R.id.listwall);
        wallbeanlist=new ArrayList<>();

        for(int i=0;i<10;i++){
            if(i<3){
                wallbeanlist.add(new wallbean("deepak"+i,"This is my status","http://www.planwallpaper.com/static/images/wallpaper-of-love.jpg","http://commentphotos.com/gallery/CommentPhotos.com_1391968775.jpg",1,0));
            }else if(i>5){
                wallbeanlist.add(new wallbean("deepak"+i,"This is my status","http://wishesmessages.com/wp-content/uploads/2013/04/Heart-i-love-you-never-leave-me-message-640x480.jpg","http://commentphotos.com/gallery/CommentPhotos.com_1391968775.jpg",1,0));
            }else{
                wallbeanlist.add(new wallbean("deepak"+i,"This is my status","https://image.freepik.com/free-vector/declaration-of-love_23-2147517078.jpg","http://commentphotos.com/gallery/CommentPhotos.com_1391968775.jpg",1,0));
            }
        }
        walladapter=new Walladapter(this,R.layout.walllayout,wallbeanlist);
        walllist.setAdapter(walladapter);
        walllist.setOnItemClickListener(itemClickListener);

    }

    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(Userwall.this,position+"",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userwall);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

}
