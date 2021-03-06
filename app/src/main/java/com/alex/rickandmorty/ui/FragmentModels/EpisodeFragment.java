package com.alex.rickandmorty.ui.FragmentModels;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.alex.rickandmorty.R;
import com.alex.rickandmorty.ui.Adapters.CharacterAdapter;
import com.alex.rickandmorty.ui.Adapters.EpisodeAdapter;
import com.alex.rickandmorty.ui.Models.CharacterModel;
import com.alex.rickandmorty.ui.Models.EpisodeModel;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EpisodeFragment extends Fragment {

    private String baseUrl = "https://rickandmortyapi.com/api/episode?page=";
    private RecyclerView mRecyclerView;
    private List<EpisodeModel> modelList;
    private RecyclerView.Adapter mAdapter;
    AVLoadingIndicatorView avi;
    EditText query;
    private EpisodeModel episodeModel;
    View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.fragment_episode, container, false);


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
                    for (int i = 1; i <= 3; ++i) {
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
                                    String episode = jsonArray.getJSONObject(i).getString("episode");
                                    String date = jsonArray.getJSONObject(i).getString("air_date");



                                    EpisodeModel model = new EpisodeModel(name,date,episode+"  "+id);
                                    modelList.add(model);


                                } catch (Exception ex) {
                                }
                            }

                            avi.hide();
                            mAdapter = new EpisodeAdapter(modelList, root.getContext());
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