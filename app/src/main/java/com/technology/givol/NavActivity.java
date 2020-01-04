package com.technology.givol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.technology.givol.adapter.Adapter_drawer;
import com.technology.givol.adapter.CustomAdapter;
import com.technology.givol.adapter.CustomListAdapter;
import com.technology.givol.adapter.RecyclerAdapter;
import com.technology.givol.adapter.TabAdapter;
import com.technology.givol.adapter.ViewPagerAdapter;
import com.technology.givol.model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NavActivity extends AppCompatActivity implements View.OnClickListener {
    Adapter_drawer adapter_drawer;
    Adapter_drawer adapter_drawer1;
    //TabAdapter adapter;
    String[] arraySpinner;
    String[] arraySpinner1;
    String[] arraySpinner2;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ListView drawerList1;
    LinearLayout drawerView;
    ImageView imageMenu;
    ImageView imageMenu1;
    LinearLayout linear_header;
    Fragment mFragment = null;
    Spinner simpleSpinner1;
    Spinner simpleSpinner2;

    TextView textHomeBar;
    String[] titlesArray;
    String[] titlesArray1;
    private ViewPager viewPager;
    String str = "Category1";
    String str2 = "Category2";
    String[] countryNames={"Category","Participants","End Time"};

    TabLayout tabLayout;
    private RecyclerAdapter recyclerAdapter,recyclerAdapter1;
    private RecyclerView recyclerView;
    private ArrayList<Data> listdata;
    private ArrayList<Data> tempList;
    private GridLayoutManager gridLayoutManager;
    int no_of_categories = -1;
    private RequestQueue mRequestQueue,mRequestQueue1;
    private StringRequest mStringRequest,mStringRequest1;
    private String url = "http://findnearby.biz/contest-app/apis/all_categories.php?ukey=HA5TadtD3dF8lAwK0BS7OVOL2YiBl7ZZ";
    private String[] country = {"Category","Participants","End Time"};
    private Spinner spCountry,spCountry1,spCountry2;
    ProgressDialog progressDialog;
    //ViewPagerAdapter adapter;
//    String category_name;
   String category_name1;
    String[] currencies=new String[]{"Category","Participants","End Time"};
    Spinner spinner,spinner1,spinner2;
    int po;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        progressDialog = new ProgressDialog(NavActivity.this,R.style.CustomDialog);
        progressDialog.setMessage("Data Loading..");
        progressDialog.show();
//        arraySpinner = new String[]{"Category", str, str2};
//        arraySpinner1 = new String[]{"Participants", str, str2};
//        arraySpinner2 = new String[]{"End Time", str, str2};
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerView = (LinearLayout) findViewById(R.id.drawerView);
        drawerList = (ListView) findViewById(R.id.drawerList);
        drawerList1 = (ListView) findViewById(R.id.drawerList1);
        imageMenu = (ImageView) findViewById(R.id.imageMenu);
        imageMenu1 = (ImageView) findViewById(R.id.imageMenu1);
        linear_header = (LinearLayout) findViewById(R.id.linear_header);
        titlesArray = getResources().getStringArray(R.array.drawertitles);
        titlesArray1 = getResources().getStringArray(R.array.drawertitles1);
       // CustomListAdapter adapter = new CustomListAdapter(this, titlesArray);
      //  CustomListAdapter adapter1 = new CustomListAdapter(this, titlesArray1);
       adapter_drawer = new Adapter_drawer(getApplicationContext(),titlesArray);
        adapter_drawer1 = new Adapter_drawer(getApplicationContext(),titlesArray1);
        drawerList.setAdapter(adapter_drawer);
        drawerList1.setAdapter(adapter_drawer1);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(viewPager);
        sendAndRequestResponse();
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        //   startCountDown(dob);
        //tempList = new ArrayList<Data>();
        //  listdata = new ArrayList<Data>();
        // loadHeroList(pro);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String category=tab.getText().toString();
                // loadHeroList1(category);
                loadHeroList(category);
//                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        spinner=(Spinner)findViewById(R.id.spinner);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        ArrayAdapter adapter = new ArrayAdapter(NavActivity.this, R.layout.spinner_item, currencies);
        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setPopupBackgroundResource(R.color.colorWhite);
            spinner1.setPopupBackgroundResource(R.color.colorWhite);
            spinner2.setPopupBackgroundResource(R.color.colorWhite);
        }
        imageMenu.setOnClickListener(this);
        drawerList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    mFragment = new Tab1();
                } else if (position == 1) {
                    startActivity(new Intent(getApplicationContext(), ExpandListActivity.class));
                    finish();
                }
                else if (position == 2) {
                    startActivity(new Intent(getApplicationContext(), PriPolicyActivity.class));
                    finish();
                }
               // NavActivity.this.drawerLayout.closeDrawer((View) NavActivity.this.drawerView);
            }
        });
        imageMenu1.setOnClickListener(this);
        linear_header.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(drawerView)) {
            drawerLayout.closeDrawer(drawerView);
        } else {
            finish();
        }
    }

    /* Toggle Menu*/
    public void toggleMenu() {
        if (drawerLayout.isDrawerOpen(drawerView)) {
            drawerLayout.closeDrawer(drawerView);
        } else {
            drawerLayout.openDrawer(drawerView);
        }
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageMenu /*2131296416*/:
                toggleMenu();
                return;
            case R.id.imageMenu1 /*2131296417*/:
                onBackPressed();
                break;
            case R.id.linear_header /*2131296438*/:
                startActivity(new Intent(this, PersonalInfoActivity.class));
                break;
            default:
                return;
        }

    }

    public void sendAndRequestResponse() {


        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject childData = data.getJSONObject(i);
                        String category_name = childData.getString("name");
                        // setupViewPager(viewPager);
                        // ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                        //adapter.addFragment(new BlankFragment(), category_name);
                        // adapter.addFragment(new TwoFragment(), "TWO");
                        //adapter.addFragment(new ThreeFragment(), "THREE");
                        tabLayout.addTab(tabLayout.newTab().setText(category_name));


                        //Creating our pager adapter
                    }







                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //  Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error :" + error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        });

        mRequestQueue.add(mStringRequest);

    }

    private void loadHeroList(final String cat_value) {

        listdata=new ArrayList<>();
        final String imgae_base_url=  "http://findnearby.biz/contest-app/contest-cover-images/";
        String url = "http://findnearby.biz/contest-app/apis/contests_by_conditions.php?ukey=St40LV9smSI0IFjy7vOt0N1yxzMfQV2i&category="+cat_value;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);


                        try {
                            progressDialog.dismiss();
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            String success=obj.getString("success");
                            if(success.equalsIgnoreCase("true")) {
                                //we have the array named hero inside the object
                                //so here we are getting that json array
                                JSONArray data = obj.getJSONArray("data");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < data.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject child_data = data.getJSONObject(i);
                                    Data item = new Data();
                                    item.setCategory_id(child_data.getString("id"));
                                    item.setId(child_data.getString("amount"));
                                    item.setJudul(child_data.getString("participant"));
                                    item.setHarga(child_data.getString("title"));
                                    item.setThubnail(imgae_base_url+child_data.getString(("cover")));
                                    item.setEnd_date(child_data.getString("end_date"));
                                    listdata.add(item);
                                    //  recyclerAdapter.notifyDataSetChanged();
                                }


                            }
                            else if(success.equalsIgnoreCase("false"))
                            {
                                Toast.makeText(NavActivity.this,obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            recyclerAdapter = new RecyclerAdapter(NavActivity.this, listdata, new RecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Data item) {
                                    String cat_id=item.getCategory_id();
                                    Intent intent=new Intent(NavActivity.this,NavItemDetailActivity.class);
                                    intent.putExtra("CATEGORY_ID",cat_id);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            recyclerView.setAdapter(recyclerAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(NavActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

        //creating a request queue
        Volley.newRequestQueue(NavActivity.this).add(stringRequest);

        //adding the string request to request queue
        // requestQueue.add(stringRequest);
    }


}
