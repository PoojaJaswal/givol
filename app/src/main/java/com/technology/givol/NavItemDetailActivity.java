package com.technology.givol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.codesgood.views.JustifiedTextView;
import com.technology.givol.adapter.Adapter_drawer;
import com.technology.givol.adapter.RecyclerAdapter;
import com.technology.givol.adapter.ViewPagerAdapterdot;
import com.technology.givol.model.Data;
import com.technology.givol.model.SliderUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NavItemDetailActivity extends AppCompatActivity implements View.OnClickListener  {
    DrawerLayout drawerLayout;
    ListView drawerList;
    ListView drawerList1;
    LinearLayout drawerView;
    ImageView imageMenu;
    ImageView imageMenu1;
    LinearLayout linear_header;
    String[] titlesArray;
    String[] titlesArray1;
    Adapter_drawer adapter_drawer;
    Adapter_drawer adapter_drawer1;
    Fragment mFragment = null;
    ImageView givol_arrow_back;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    ViewPagerAdapterdot viewPagerAdapter;
    List<SliderUtils> sliderImg;
    RequestQueue rq;
    TextView title_txt,amount_txt,participant_out_txt,count_down_txt;
    JustifiedTextView description_txt;
    Button user_participant_btn;
    String c_value;
    //FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_item_detail);
        Bundle bundle=getIntent().getExtras();
         c_value=bundle.getString("CATEGORY_ID");
       // Toast.makeText(NavItemDetailActivity.this,"Val1"+c_value,Toast.LENGTH_SHORT).show();
      //  Bundle data = new Bundle();//create bundle instance
       // data.putString("key_value",c_value);
//        FragmentManager mFragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//        BlankFragment blankFragment=new BlankFragment();
//       // blankFragment.setArguments(data);
//        fragmentTransaction.replace(R.id.frame_layout_1,blankFragment).commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerView = (LinearLayout) findViewById(R.id.drawerView);
        drawerList = (ListView) findViewById(R.id.drawerList);
        drawerList1 = (ListView) findViewById(R.id.drawerList1);
        imageMenu = (ImageView) findViewById(R.id.imageMenu);
        imageMenu1 = (ImageView) findViewById(R.id.imageMenu1);
        givol_arrow_back=(ImageView)findViewById(R.id.givol_arrow_back);
        linear_header = (LinearLayout) findViewById(R.id.linear_header);
        titlesArray = getResources().getStringArray(R.array.drawertitles);
        titlesArray1 = getResources().getStringArray(R.array.drawertitles1);
        adapter_drawer = new Adapter_drawer(this, this.titlesArray);
        adapter_drawer1 = new Adapter_drawer(this, this.titlesArray1);
        drawerList.setAdapter(adapter_drawer);
        drawerList1.setAdapter(adapter_drawer1);
        imageMenu.setOnClickListener(this);
        givol_arrow_back.setOnClickListener(this);

       // loadContestDetail();
        rq = CustomVolleyRequest.getInstance(this).getRequestQueue();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        title_txt=(TextView)findViewById(R.id.title_txt);
        amount_txt=(TextView)findViewById(R.id.amount_txt);
        participant_out_txt=(TextView)findViewById(R.id.participant_out_txt);
        count_down_txt=(TextView)findViewById(R.id.count_down_txt);
        description_txt=(JustifiedTextView)findViewById(R.id.description_txt);
        user_participant_btn=(Button)findViewById(R.id.user_participant_btn);
        user_participant_btn.setOnClickListener(this);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        loadContestDetail(c_value);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        drawerList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                      //  textHomeBar.setText("Home");
                        mFragment = new BlankFragment();
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(), ExpandListActivity.class));
                        finish();
                        break;
                }
//                if (mFragment != null) {
//                    FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    mFragmentTransaction.replace(R.id.frame_layout_1, mFragment);
//                    mFragmentTransaction.addToBackStack(null);
//                    mFragmentTransaction.commit();
//                }

                /* Closing Drawer*/
                drawerLayout.closeDrawer(drawerView);
//                if (position == 0) {
//                    mFragment = new BlankFragment();
//                } else if (position == 1) {
//                    startActivity(new Intent(getApplicationContext(), ExpandListActivity.class));
//                    finish();
//                }
//                else if (position == 2) {
//                    startActivity(new Intent(getApplicationContext(), PriPolicyActivity.class));
//                    finish();
//                }
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
            case R.id.givol_arrow_back:
                startActivity(new Intent(this, NavActivity.class));
                break;
            case R.id.user_participant_btn:
                userParticipant();
                break;

            default:
                return;
        }

    }


    private void loadContestDetail(final String c_value1) {
        final String server_url=  "http://findnearby.biz/contest-app/contest-cover-images/";
       // Toast.makeText(NavItemDetailActivity.this,"Val1"+c_value1,Toast.LENGTH_SHORT).show();
      //  listdata=new ArrayList<>();
        String url = "http://findnearby.biz/contest-app/apis/contest-detail.php?ukey=4nouFcZzeu0pB01PREnk02RmbWbtwYMZ&id="+Integer.parseInt(c_value1);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);
                        sliderImg = new ArrayList<>();

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            String success=obj.getString("success");
                           // String message=obj.getString("message");
                            if(success.equalsIgnoreCase("true")) {
                                //we have the array named hero inside the object
                                //so here we are getting that json array
                                JSONObject data = obj.getJSONObject("data");
                                //now looping through all the elements of the json array


                                String cover=data.getString("cover");
                                String cover2=data.getString("cover2");
                                String cover3=data.getString("cover3");
                                String cover4=data.getString("cover4");
                                String result_img_url=server_url+cover;
                                String result_img_url1=server_url+cover2;
                                String result_img_url12=server_url+cover3;
                                String result_img_url13=server_url+cover4;
                               String[] final_result=new String[]{result_img_url,result_img_url1,result_img_url12,result_img_url13};

                                for(int i = 0; i < final_result.length; i++)
                                {
                                    SliderUtils sliderUtils = new SliderUtils();
                                    sliderUtils.setSliderImageUrl(final_result[i]);
                                    Log.d("VALUE",final_result[i]);
//                                    sliderUtils.setSliderImageUrl(result_img_url1);
//                                    sliderUtils.setSliderImageUrl(result_img_url12);
//                                    sliderUtils.setSliderImageUrl(result_img_url13);
                                    sliderImg.add(sliderUtils);
                                }




                                viewPagerAdapter = new ViewPagerAdapterdot(sliderImg, NavItemDetailActivity.this);

                                viewPager.setAdapter(viewPagerAdapter);

                                dotscount = viewPagerAdapter.getCount();
                                dots = new ImageView[dotscount];

                                for(int i = 0; i < dotscount; i++){
                                    dots[i] = new ImageView(NavItemDetailActivity.this);
                                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(8, 0, 8, 0);

                                    sliderDotspanel.addView(dots[i], params);

                                }

                                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                                String title=data.getString("title");
                                String amount=data.getString("amount");
                                String participant=data.getString("participant");
                                String end_date=data.getString("end_date");
                                String description=data.getString("description");
                                title_txt.setText(title);
                                amount_txt.setText(amount);
                                participant_out_txt.setText(participant);
                                startCountDown(end_date);
                                description_txt.setText(description);

                            }
                            else
                            {
                                Toast.makeText(NavItemDetailActivity.this,"NO DATA",Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(NavItemDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        Volley.newRequestQueue(NavItemDetailActivity.this).add(stringRequest);

        //adding the string request to request queue
        // requestQueue.add(stringRequest);
    }


    public void startCountDown(String contest_end_dt ){
        Date end_date=null;
        try {
            end_date = new SimpleDateFormat("yyyy-MM-dd").parse(contest_end_dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date today = new Date();
        long diff =   end_date.getTime()-today.getTime();
        int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
        int hours = (int) (diff / (1000 * 60 * 60));
        int minutes = (int) (diff / (1000 * 60));
        int seconds = (int) (diff / (1000));
        new CountDownTimer(diff, 1000){

            @Override

            public void onTick(long millisUntilFinished) {
                /*            converting the milliseconds into days, hours, minutes and seconds and displaying it in textviews             */

                count_down_txt.setText(TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))+":"+(TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)))+":"
                        +(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)))+":"+(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                // hours_1.setText((TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)))+"");

                // mins_1.setText((TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)))+"");

                // seconds_1.setText((TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))+"");
            }

            @Override

            public void onFinish() {
                /*            clearing all fields and displaying countdown finished message             */

                count_down_txt.setText("Finished");
                // hours_1.setText("");
                // mins_1.setText("");
                // seconds_1.setText("");
            }
        }.start();
    }


    private void userParticipant(){
       String url_user="http://findnearby.biz/contest-app/apis/participate-in-contest.php";
       // final String username = etUname.getText().toString().trim();
       // final String password = etPass.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("success");
                             String message=jsonObject.getString("message");
                            if(success.equalsIgnoreCase("true"))
                            {

                                Toast.makeText(NavItemDetailActivity.this,"SUCCESSFULLY PARTICIPATED",Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Toast.makeText(NavItemDetailActivity.this,message,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NavItemDetailActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("ukey","9Eo3cI5qiPX6uZRv9jZWHhmvJfWNmHtP");
                params.put("user_id","1");
                params.put("contest_id",c_value);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




}


