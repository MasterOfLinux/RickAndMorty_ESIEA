package com.alex.rickandmorty.utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.hassan3217.alexgolf.rickandmorty.CharacterDetail;
import com.hassan3217.alexgolf.rickandmorty.R;

import java.io.File;
import java.util.ArrayList;

public class fvrt extends AppCompatActivity {
    ListView myLsit;
    ArrayList<String> listl = new ArrayList<>();
    String selectedPref;
    static String baseURl, id;
    static String name;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fvrt);



        myLsit = findViewById(R.id.myLsit);

        loaddata();

        myLsit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPref = (String) parent.getItemAtPosition(position);

                openDetail();
            }
        });

    }

    private void openDetail() {

        SharedPreferences openPref = getSharedPreferences(selectedPref, MODE_PRIVATE);

        String status = openPref.getString("name", "none");
        baseURl = openPref.getString("baseUrl", "none");
        id = openPref.getString("id", "none");


        Intent i = new Intent(fvrt.this, CharacterDetail.class);
        i.putExtra("curUrlKey", baseURl);
        i.putExtra("curIdKey", id);

        startActivity(i);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Toasty.success(fvrt.this, "resuem", Toast.LENGTH_SHORT, true).show();
        listl.clear();
        loaddata();


    }

    public void loaddata() {
        File prefsdir = new File(getApplicationInfo().dataDir, "shared_prefs");
        if (prefsdir.exists() && prefsdir.isDirectory()) {
            String[] list = prefsdir.list();

            for (int i = 0; i < list.length; i++) {

                String preffile = list[i].substring(0, list[i].length() - 4);

                SharedPreferences prefs = getSharedPreferences(preffile, MODE_PRIVATE);

                name = prefs.getString("name", "No name defined");

                listl.add(name);

                //    String status = prefs.getString("status", "No name defined");
                //     boolean ischk = prefs.getBoolean("aded",false);
                //   if (ischk){shineButton.setChecked(true); }

            }
        }


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listl);
        //  Spinner sp = (Spinner) findViewById(R.id.spinner1);
        //sp.setAdapter(adapter);

        myLsit.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    //    Toasty.success(fvrt.this, "restar", Toast.LENGTH_SHORT, true).show();
        listl.clear();
        loaddata();
    }
}