package com.technology.givol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.technology.givol.adapter.CustomAdapter;
import com.technology.givol.model.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CurrentLocationActivity extends AppCompatActivity implements View.OnClickListener {
        private static CustomAdapter adapter;
        Button btnCanada;
        Button btnUK;
        Button btnUSA;
        ArrayList<DataModel> dataModels;
        ListView listView;
        ListView listView1;
        ListView listView2;
        Button nextTxt;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://findnearby.biz/contest-app/apis/all_categories.php?ukey=HA5TadtD3dF8lAwK0BS7OVOL2YiBl7ZZ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        nextTxt = (Button) findViewById(R.id.nextTxt);

        listView1 = (ListView) findViewById(R.id.list_view1);
        listView = (ListView) findViewById(R.id.list_view);
        listView2 = (ListView) findViewById(R.id.list_view2);
        btnUSA = (Button) findViewById(R.id.btnUSA);
        btnUK = (Button) findViewById(R.id.btnUK);
        btnCanada = (Button) findViewById(R.id.btnCanada);
        nextTxt.setOnClickListener(this);
        btnUSA.setOnClickListener(this);
        btnUK.setOnClickListener(this);
        btnCanada.setOnClickListener(this);
        dataModels = new ArrayList<>();
        dataModels.add(new DataModel("London,UK"));
        dataModels.add(new DataModel("Liverpool,UK"));
        dataModels.add(new DataModel("Manchester,UK"));
        dataModels.add(new DataModel("Arsenal,UK"));
        dataModels.add(new DataModel("Stamford,UK"));
        adapter = new CustomAdapter(dataModels, getApplicationContext());
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataModel dataModel = dataModels.get(position);
            }
        } );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DataModel dataModel =dataModels.get(position);
            }
        });
        this.listView2.setAdapter(adapter);
        this.listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DataModel dataModel=dataModels.get(position);
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCanada:
                listView2.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                listView1.setVisibility(View.GONE);
                return;
            case R.id.btnUK:
                listView.setVisibility(View.VISIBLE);
                listView2.setVisibility(View.GONE);
                listView1.setVisibility(View.GONE);
                return;
            case R.id.btnUSA:
                listView1.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                listView2.setVisibility(View.GONE);
                return;
            case R.id.nextTxt:
                startActivity(new Intent(getApplicationContext(), NavActivity.class));
                finish();
                return;
            default:
                return;
        }
    }


}
