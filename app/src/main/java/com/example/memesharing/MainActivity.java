package com.example.memesharing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
ImageView meme;
Button sharebtn,nextbtn;
String imageurl;
ProgressBar mprogressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meme = (ImageView) findViewById(R.id.image);
        sharebtn = (Button) findViewById(R.id.share);
        nextbtn = (Button) findViewById(R.id.next);
        mprogressbar=(ProgressBar)findViewById(R.id.progressbar);
        loaddata();
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,"checkout this cool meme\n"+imageurl);
                startActivity(Intent.createChooser(i,"Share this meme....."));

            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
loaddata();
            }
        });

    }
        public void loaddata()

        {
            mprogressbar.setVisibility(View.VISIBLE);
            String url = "https://meme-api.herokuapp.com/gimme";
RequestQueue queue= Volley.newRequestQueue(this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                imageurl= response.getString("url");
                                mprogressbar.setVisibility(View.GONE);
                                Glide.with(MainActivity.this).load(imageurl).listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable  GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        mprogressbar.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        mprogressbar.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).into(meme);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Unable to fetch", Toast.LENGTH_SHORT).show();
                        }

                        });
            queue.add(jsonObjectRequest);
        }
    }

