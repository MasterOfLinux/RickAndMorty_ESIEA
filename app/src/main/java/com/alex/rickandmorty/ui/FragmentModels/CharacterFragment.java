package com.alex.rickandmorty.ui.FragmentModels;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.alex.rickandmorty.R;
import com.alex.rickandmorty.ui.Adapters.CharacterAdapter;
import com.alex.rickandmorty.ui.Models.CharacterModel;
import com.alex.rickandmorty.utils.fvrt;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class CharacterFragment extends Fragment {
    private String baseUrl = "https://rickandmortyapi.com/api/character";
    private RecyclerView mRecyclerView;
    private List<CharacterModel> modelList;
    private RecyclerView.Adapter mAdapter;
    AVLoadingIndicatorView avi;
    EditText query;
    final int x = 0;
    private CharacterModel characterModel;
    TextView clearF;
    private static final String[] paths = {"Name", "Status", "Species", "favorites"};
    View root;
    String filterType = "Name";
    ImageButton spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_character, container, false);

        avi = root.findViewById(R.id.avi);
        query = root.findViewById(R.id.edQuery);
        spinner = root.findViewById(R.id.filter);
        clearF = root.findViewById(R.id.clear_filter);
        mRecyclerView = root.findViewById(R.id.recylcerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        modelList = new ArrayList<>();

        getApiCall();


        clearF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApiCall();
                query.setText("");
                filterType = "Name";
                query.setHint("Enter Name To Search");
                Toasty.success(root.getContext(), "All filter cleared", Toast.LENGTH_SHORT, true).show();
            }
        });


        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(root.getContext(), spinner);
                popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String Sitem = (String) item.getTitle();
                        Toast.makeText(root.getContext(), "" + Sitem, Toast.LENGTH_SHORT).show();

                        switch (item.getItemId()){
                            case R.id.filter_by_name:
                                filterType = (paths[0]);
                                query.setHint("Enter Name To Search");
                                break;
                            case R.id.filter_by_status:
                                filterType = (paths[1]);
                                query.setHint("Enter Status To Search");
                                break;
                            case R.id.filter_by_specie:
                                filterType = (paths[2]);
                                query.setHint("Enter Species To Search");
                                break;
                            case R.id.filter_by_fvrt:
                                filterType = (paths[0]);
                                startActivity(new Intent(root.getContext(), fvrt.class));
                                break;
                        }


                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        return root;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void filter(String toString) {
        CharacterAdapter mAdapter;
        ArrayList<CharacterModel> filteredList = new ArrayList<>();

        for (CharacterModel item : modelList) {
            if (filterType.equals("Name")) {
                if (item.getName().toLowerCase().contains(toString.toLowerCase())) {
                    filteredList.add(item);
                }
            } else if (filterType.equals("Status")) {
                if (item.getStatus().toLowerCase().contains(toString.toLowerCase())) {
                    filteredList.add(item);
                }

            } else if (filterType.equals("Species")) {
                if (item.getSpecie().toLowerCase().contains(toString.toLowerCase())) {
                    filteredList.add(item);
                }

            } else {
            }

        }
        mAdapter = new CharacterAdapter(modelList, root.getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.filterList(filteredList);

    }

    private void getApiCall() {
        avi.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("results");
                    String pages = new JSONObject(response).getJSONObject("info").getString("count");
                    String next = new JSONObject(response).getJSONObject("info").getString("next");
                    for (int i = 1; i <= 33; ++i) {
                        singlepage(i);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());
        requestQueue.add(stringRequest);


    }

    public void singlepage(int i) {
        final String curentUrl = baseUrl + "?page=" + i;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, curentUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONObject(response).getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    avi.hide();
                                    String name = jsonArray.getJSONObject(i).getString("name");
                                    String id = jsonArray.getJSONObject(i).getString("id");
                                    String status = jsonArray.getJSONObject(i).getString("status");
                                    String specie = jsonArray.getJSONObject(i).getString("species");
                                    String img = jsonArray.getJSONObject(i).getString("image");


                                    CharacterModel model = new CharacterModel(name, status, specie, img, id, curentUrl, String.valueOf(i));
                                    modelList.add(model);


                                } catch (Exception ex) {
                                }
                            }

                            avi.hide();
                            mAdapter = new CharacterAdapter(modelList, root.getContext());
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();


                        } catch (Exception ex) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());
        requestQueue.add(stringRequest);
    }
}