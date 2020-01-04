package com.technology.givol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.technology.givol.adapter.PersonalExpiredAdapter;
import com.technology.givol.adapter.PersonalInfoAdapter;
import com.technology.givol.model.PersonalInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.technology.givol.R.*;

public class PersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView per_back_img;
    TextView user_name,user_email;
    private PersonalInfoAdapter active_recyclerAdapter;
    private PersonalExpiredAdapter expired_recyclerAdapter;
    private ArrayList<PersonalInfoModel> listdata;
    private ArrayList<PersonalInfoModel> listdata1;
    RecyclerView recyclerView;
    RecyclerView recycer_view1;
    String cover_url=  "http://findnearby.biz/contest-app/contest-cover-images/";
    String result_img_url;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_personal_info);
        initViews();

    }
    private void initViews() {
        user_name=(TextView)findViewById(id.user_name);
        user_email=(TextView)findViewById(id.user_email);
         recyclerView = (RecyclerView) findViewById(id.recycer_view);
         recycer_view1 = (RecyclerView) findViewById(id.recycer_view1);
        recyclerView.setHasFixedSize(true);
        recycer_view1.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recycer_view1.setLayoutManager(layoutManager1);
        loadContestList();
        per_back_img = (ImageView) findViewById(id.per_back_img);
        per_back_img.setOnClickListener(this);
//        adapter.setOnItemClickListener(new PersonalInfoAdapter.OnItemClickListener1() {
//            public void onItemClick(View itemView, int position) {
//               startActivity(new Intent(getApplicationContext(), ContestHolderActivity.class));
//            }
//        });
//        adapter1.setOnItemClickListener(new PersonalInfoAdapter.OnItemClickListener1() {
//            public void onItemClick(View itemView, int position) {
//                startActivity(new Intent(getApplicationContext(),ContestFinalActivity.class));
//            }
//        });
    }



    public void onClick(View v) {
        if (v.getId() == id.per_back_img) {
            startActivity(new Intent(this, NavActivity.class));
            finish();
        }
    }


    private void loadContestList() {

        listdata=new ArrayList<>();
        listdata1=new ArrayList<>();
        progressDialog = new ProgressDialog(PersonalInfoActivity.this,R.style.CustomDialog);
        progressDialog.setMessage("Data Loading..");
        progressDialog.show();
        //getting the progressbar
        //final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        // progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        String url = "http://findnearby.biz/contest-app/apis/user-detail.php?ukey=zcdVJ2VRKAEHNBK2B8JqMyITugtRUFqz&user_id=" + "1";
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
                                JSONObject data=obj.getJSONObject("data");
                                JSONObject jsonUSER=data.getJSONObject("user");
                                String user_id=jsonUSER.getString("id");
                                String first_name=jsonUSER.getString("first_name");
                                String email=jsonUSER.getString("email");
                                user_name.setText(first_name);
                                user_email.setText(email);
                                //we have the array named hero inside the object
                                //so here we are getting that json array
                                JSONArray active_contest = data.getJSONArray("active_contests");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < active_contest.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject child_data = active_contest.getJSONObject(i);
                                    PersonalInfoModel item = new PersonalInfoModel();
                                    item.setActive_contest_id(child_data.getString("id"));
                                    item.setActive_title(child_data.getString("title"));
                                    item.setActive_category(child_data.getString("category"));
                                    item.setActive_amount(child_data.getString("amount"));
                                    String cover=child_data.getString("cover");
                                    if(cover.equalsIgnoreCase("null"))
                                    {

                                       // result_img_url=cover_url+"f78b5107ee228fe3797589c538b3d741.png";
                                       // item.setActive_cover(result_img_url);
                                    }
                                    else if(!cover.equalsIgnoreCase("null"))
                                    {
                                        result_img_url=cover_url+cover;
                                        item.setActive_cover(result_img_url);
                                    }

                                    item.setActive_end_date(child_data.getString("end_date"));
                                    listdata.add(item);
                                    //  recyclerAdapter.notifyDataSetChanged();
                                }
                                JSONArray expired_contest = data.getJSONArray("expired_contests");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < expired_contest.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject child_data1 = expired_contest.getJSONObject(i);
                                    PersonalInfoModel item1 = new PersonalInfoModel();
                                    item1.setExpired_contest_id(child_data1.getString("id"));
                                    item1.setExpired_title(child_data1.getString("title"));
                                    item1.setExpired_is_winner( child_data1.getBoolean("is_winner") ?"Winner":"No Win");
                                    item1.setExpired_amount(child_data1.getString("amount"));
                                    item1.setExpired_cover(cover_url+child_data1.getString("cover"));
                                    item1.setExpired_end_date(child_data1.getString("end_date"));
                                    listdata1.add(item1);
                                    //  recyclerAdapter.notifyDataSetChanged();
                                }



                            }
//                            else if(success.equalsIgnoreCase("false"))
//                            {
//                                Toast.makeText(PersonalInfoActivity.this,obj.getString("message"), Toast.LENGTH_SHORT).show();
//                            }
                            active_recyclerAdapter = new PersonalInfoAdapter(PersonalInfoActivity.this, listdata, new PersonalInfoAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(PersonalInfoModel item) {
                                    String cat_id=item.getActive_title();
                                    Intent intent=new Intent(PersonalInfoActivity.this,ContestHolderActivity.class);
                                    intent.putExtra("CATEGORY_ID",cat_id);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            expired_recyclerAdapter = new PersonalExpiredAdapter(PersonalInfoActivity.this, listdata1, new PersonalExpiredAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(PersonalInfoModel item) {
                                    String cat_id=item.getExpired_title();
                                    Intent intent=new Intent(PersonalInfoActivity.this,ContestFinalActivity.class);
                                    intent.putExtra("CATEGORY_ID",cat_id);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                            recyclerView.setAdapter(active_recyclerAdapter);
                            recycer_view1.setAdapter(expired_recyclerAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(PersonalInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

        //creating a request queue
        Volley.newRequestQueue(PersonalInfoActivity.this).add(stringRequest);

        //adding the string request to request queue
        // requestQueue.add(stringRequest);
    }

}

