package com.alex.rickandmorty;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.alex.rickandmorty.ui.Adapters.CharacterDetailAdapter;
import com.alex.rickandmorty.ui.Models.CharachterDeatilModel;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterDetail extends AppCompatActivity {
    TextView tempo;
    TextView name, status, species, type, gender, origin, location;
    ImageView img;
    public String MY_PREFS;
    private RecyclerView mRecyclerView;
    private List<CharachterDeatilModel> modelList;
    private RecyclerView.Adapter mAdapter;
    AVLoadingIndicatorView avi;
    String id, nameee;
    ShineButton shineButton;
    String baseUrl;
    CollapsingToolbarLayout toolBarLayout;
    //  String baseUrl = "https://rickandmortyapi.com/api/character?page=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("Loading.....");

        shineButton = (ShineButton) findViewById(R.id.shine_button);
        shineButton.init(com.alex.rickandmorty.CharacterDetail.this);

        avi = findViewById(R.id.avi);
        name = findViewById(R.id.char_d_name);
        status = findViewById(R.id.char_d_status);
        species = findViewById(R.id.char_d_species);
        type = findViewById(R.id.char_d_type);
        gender = findViewById(R.id.char_d_gender);
        origin = findViewById(R.id.char_d_origin);
        location = findViewById(R.id.char_d_location);
        img = findViewById(R.id.char_d_img);



        mRecyclerView = findViewById(R.id.recylcerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelList = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Choose to share", Snackbar.LENGTH_LONG)
                        .setAction("Cancel", null).show();

                Bitmap bitmap = getBitmapFromView(img);
                try {
                    //File file = new File(this.getExternalCacheDir(),File.separator+ "logicchip.png");
                    File file = new File(getExternalCacheDir(), "logicchip.png");
                    // File file = new File(this.getCacheDir(),File.separator+ "logicchip.png");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photoURI = FileProvider.getUriForFile(com.alex.rickandmorty.CharacterDetail.this, BuildConfig.APPLICATION_ID + ".provider", file);
                    // intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file.getC));
                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/png");
                    intent.setType("text/plain\"");
                    intent.putExtra(Intent.EXTRA_TEXT, name.getText().toString() + "\n" + status.getText().toString() + "\n" + species.getText().toString());
                    //startActivity(Intent.createChooser(intent, "Share image via"));
                    startActivityForResult(Intent.createChooser(intent, "Share image via"), 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        id = getIntent().getStringExtra("curIdKey");
        baseUrl = getIntent().getStringExtra("curUrlKey");


        int CURRENTID = Integer.parseInt(id);
       // Toast.makeText(CharacterDetail.this, "2 " + baseUrl, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    String nm = new JSONObject(response).getJSONArray("results").getJSONObject(CURRENTID).getString("name");
                    String stat = new JSONObject(response).getJSONArray("results").getJSONObject(CURRENTID).getString("status");
                    String spe = new JSONObject(response).getJSONArray("results").getJSONObject(CURRENTID).getString("species");
                    String typ = new JSONObject(response).getJSONArray("results").getJSONObject(CURRENTID).getString("type");
                    String gndr = new JSONObject(response).getJSONArray("results").getJSONObject(CURRENTID).getString("gender");
                    String orig = new JSONObject(response).getJSONArray("results").getJSONObject(CURRENTID).getJSONObject("origin").getString("name");
                    String loc = new JSONObject(response).getJSONArray("results").getJSONObject(CURRENTID).getJSONObject("location").getString("name");
                    String imgg = new JSONObject(response).getJSONArray("results").getJSONObject(CURRENTID).getString("image");
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("results").getJSONObject(CURRENTID).getJSONArray("episode");
                    MY_PREFS = nm;
                    toolBarLayout.setTitle(MY_PREFS);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        String epLink = jsonArray.getString(i);
                        //  Toast.makeText(CharacterDetail.this, "L " + ep , Toast.LENGTH_SHORT).show();
                        loadEpisodes(epLink);
                    }
                    checkSharedPref();

                    name.setText(nm);
                    status.setText(stat);
                    species.setText(spe);
                    type.setText(typ);
                    gender.setText(gndr);
                    origin.setText(orig);
                    location.setText(loc);
                    Picasso.get().load(imgg).into(img);
                    avi.hide();


                    //       loadEpisodes(baseUrl, CURRENTID);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(com.alex.rickandmorty.CharacterDetail.this, "3 " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(com.alex.rickandmorty.CharacterDetail.this);
        requestQueue.add(stringRequest);

        shineButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (shineButton.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
                    editor.putString("name", name.getText().toString());
                    editor.putString("baseUrl", baseUrl);
                    editor.putString("id", id);
                    editor.putBoolean("aded", true);
                    editor.commit();
                  //  Toast.makeText(CharacterDetail.this, "chk", Toast.LENGTH_SHORT).show();
                }
                else if (!shineButton.isChecked()){
                    String filePath = getApplicationContext().getFilesDir().getParent()+"/shared_prefs/"+MY_PREFS+".xml";
                    File deletePrefFile = new File(filePath );
                    deletePrefFile.delete();

                }
            }
        });



    }
    private void checkSharedPref() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        String name = prefs.getString("name", "No name defined");
        String status = prefs.getString("status", "No name defined");
        String filePath = getApplicationContext().getFilesDir().getParent()+"/shared_prefs/"+MY_PREFS+".xml";
        File deletePrefFile = new File(filePath );

    //    Toast.makeText(CharacterDetail.this, "c "+MY_PREFS, Toast.LENGTH_SHORT).show();
      //  boolean ischk = prefs.getBoolean("aded", false);
        if (deletePrefFile.exists()) {
            shineButton.setChecked(true);
        } else if (!deletePrefFile.exists()){
            shineButton.setChecked(false);
        }


    }

    private void loadEpisodes(String epLink) {

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, epLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    avi.hide();
                    String nm = new JSONObject(response).getString("name");
                    String eps = new JSONObject(response).getString("episode");

                    CharachterDeatilModel model = new CharachterDeatilModel(nm, eps);
                    modelList.add(model);

                    //avi.hide();
                    mAdapter = new CharacterDetailAdapter(modelList, com.alex.rickandmorty.CharacterDetail.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    // Toast.makeText(CharacterDetail.this, "L " + nm +" "+ eps , Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(com.alex.rickandmorty.CharacterDetail.this, "3 " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue2 = Volley.newRequestQueue(com.alex.rickandmorty.CharacterDetail.this);
        requestQueue2.add(stringRequest2);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
       // startActivity(new Intent(CharacterDetail.this  , fvrt.class));
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);

    }





    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }


}

