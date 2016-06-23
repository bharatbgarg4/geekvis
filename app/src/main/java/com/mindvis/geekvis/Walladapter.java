package com.mindvis.geekvis;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deepak Bansal on 6/22/2016.
 */
public class Walladapter extends ArrayAdapter {
    Context cnt;
    ArrayList<wallbean> walllist;
    ProgressDialog pd;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    public Walladapter(Context context, int resource, ArrayList<wallbean> walllist) {
        super(context, resource, walllist);
        this.cnt=context;
        this.walllist=walllist;
        mRequestQueue = Volley.newRequestQueue(cnt);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=null;
        LayoutInflater layoutInflater=(LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.walllayout,parent,false);

        //ImageView imgprofilepic=(ImageView)view.findViewById(R.id.imgstudent);
        NetworkImageView imgprofilepic = (NetworkImageView)view.findViewById(R.id.imgstudent);
        TextView txtnamestudent=(TextView)view.findViewById(R.id.txtnamestudent);
        TextView txtstatus=(TextView)view.findViewById(R.id.txtstatus);
        NetworkImageView imgstatus = (NetworkImageView)view.findViewById(R.id.imgstatus);
        //ImageView imgstatus=(ImageView)view.findViewById(R.id.imgstatus);
        TextView txtnlikepost=(TextView)view.findViewById(R.id.txtnlikepost);
        TextView txtncommpost=(TextView)view.findViewById(R.id.txtncommpost);
        ImageView imglikepost=(ImageView)view.findViewById(R.id.imglikepost);
        ImageView imgcommpost=(ImageView)view.findViewById(R.id.imgcommpost);
        TextView txtupvote=(TextView)view.findViewById(R.id.txtlikepost);
        TextView txtcomment=(TextView)view.findViewById(R.id.txtcomment);
        TextView txtsharepost=(TextView)view.findViewById(R.id.txtsharepost);

        final wallbean wb=walllist.get(position);

        txtupvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cnt,"upvote-"+position,Toast.LENGTH_SHORT).show();
            }
        });
        txtcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cnt,"comment-"+position,Toast.LENGTH_SHORT).show();
            }
        });
        txtsharepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cnt,"share-"+position,Toast.LENGTH_SHORT).show();
            }
        });
        imgcommpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cnt,"imgcomment-"+position,Toast.LENGTH_SHORT).show();
            }
        });
        imglikepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cnt,"imglike-"+position,Toast.LENGTH_SHORT).show();
            }
        });


        if(!wb.getImgposturl().equals("")){
            imgstatus.setVisibility(View.VISIBLE);
            imgstatus.setImageUrl(wb.getImgposturl(),mImageLoader);
            //new LoadImage(imgstatus).execute(wb.getImgposturl());
            //imgstatus.setImageDrawable(Drawable.createFromPath(wb.getImgposturl()));
            txtnamestudent.setText(wb.getName());
            txtstatus.setText(wb.getStatus());
            txtnlikepost.setText(wb.getLikes()+"");
            txtncommpost.setText(wb.getComment()+"");
            if(wb.getProfilepic().equals("")){
                Toast.makeText(cnt,wb.getProfilepic()+"-hh-"+position,Toast.LENGTH_SHORT).show();
            }else{
                //load image directly
                //Toast.makeText(cnt,"--"+position,Toast.LENGTH_SHORT).show();
               // new LoadImage(imgprofilepic).execute(wb.getProfilepic());
                imgprofilepic.setImageUrl(wb.getProfilepic(),mImageLoader);
                //imgprofilepic.setImageDrawable(Drawable.createFromPath(wb.getProfilepic()));
            }
        }else{
            txtnamestudent.setText(wb.getName());
            txtstatus.setText(wb.getStatus());
            txtnlikepost.setText(wb.getLikes()+"");
            txtncommpost.setText(wb.getComment()+"");
            if(wb.getProfilepic().equals("")){
            }else{
                //new LoadImage(imgprofilepic).execute(wb.getProfilepic());
                imgprofilepic.setImageUrl(wb.getProfilepic(),mImageLoader);

                //imgprofilepic.setImageDrawable(Drawable.createFromPath(wb.getProfilepic()));
            }
        }

        return view;
    }


    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        ImageView img;
        Bitmap bitmap;
        public LoadImage(ImageView img){
            this.img=img;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected Bitmap doInBackground(String... args) {
            try {
                URL imageURL = new URL(args[0]);
                bitmap = BitmapFactory.decodeStream(imageURL.openStream());
                //bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
            }else{
                img.setImageResource(R.drawable.user);
            }
        }
    }
}
