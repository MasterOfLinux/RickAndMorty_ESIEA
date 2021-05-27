package com.alex.rickandmorty.ui.FragmentModels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import com.alex.rickandmorty.ui.Adapters.LocationAdapter;
import com.alex.rickandmorty.ui.Models.LocationModel;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends Fragment {

    private String baseUrl = "https://rickandmortyapi.com/api/location?page=";
    private RecyclerView mRecyclerView;
    private List<LocationModel> modelList;
    private RecyclerView.Adapter mAdapter;
    AVLoadingIndicatorView avi;
    EditText query;
    private LocationModel locationModel;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_location, container, false);



        avi = root.findViewById(R.id.avi);
        query = root.findViewById(R.id.edQuery);
        mRecyclerView = root.findViewById(R.id.recylcerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        modelList = new ArrayList<>();

        getApiCall();
        return  root;

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
                    for (int i = 1; i <= 6; ++i) {
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
        // final String curentUrl = baseUrl + "?page=" + i;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl+i,
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
                                    String typ = jsonArray.getJSONObject(i).getString("type");
                                    String dim = jsonArray.getJSONObject(i).getString("dimension");



                                    LocationModel model = new LocationModel(name,typ+"  "+id,dim);
                                    modelList.add(model);


                                } catch (Exception ex) {
                                    //   Toast.makeText(root.getContext(), "1" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            avi.hide();
                            mAdapter = new LocationAdapter(modelList, root.getContext());
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();


                        } catch (Exception ex) {
                            //Toast.makeText(root.getContext(), "2" + ex.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(root.getContext(), "3" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());
        requestQueue.add(stringRequest);
    }
}