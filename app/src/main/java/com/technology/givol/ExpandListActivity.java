package com.technology.givol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.technology.givol.adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandListActivity extends AppCompatActivity implements View.OnClickListener {
     com.technology.givol.adapter.ExpandableListAdapter adapter;
     ExpandableListView expandableListView;
    HashMap<String, List<String>> hashMap;
    ArrayList<String> header;
    ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_list);
        expandableListView = (ExpandableListView) findViewById(R.id.simple_expandable_listview);
        expandableListView.setGroupIndicator(null);
        setItems();
        adapter = new ExpandableListAdapter(this, this.header, this.hashMap);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            public void onGroupExpand(int groupPosition) {
                Context applicationContext = ExpandListActivity.this.getApplicationContext();
                StringBuilder sb = new StringBuilder();
                sb.append((String) ExpandListActivity.this.header.get(groupPosition));
                sb.append(" Expanded");
                Toast.makeText(applicationContext, sb.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            public void onGroupCollapse(int groupPosition) {
                Context applicationContext = ExpandListActivity.this.getApplicationContext();
                StringBuilder sb = new StringBuilder();
                sb.append((String) ExpandListActivity.this.header.get(groupPosition));
                sb.append(" Collapsed");
                Toast.makeText(applicationContext, sb.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Context applicationContext = ExpandListActivity.this.getApplicationContext();
                StringBuilder sb = new StringBuilder();
                sb.append((String) ExpandListActivity.this.header.get(groupPosition));
                sb.append(" : ");
                sb.append((String) ((List) ExpandListActivity.this.hashMap.get(ExpandListActivity.this.header.get(groupPosition))).get(childPosition));
                Toast.makeText(applicationContext, sb.toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        final int[] prevExpandPosition = {-1};
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (prevExpandPosition[0] >= 0 && prevExpandPosition[0] != groupPosition) {
                    expandableListView.collapseGroup(prevExpandPosition[0]);
                }
                prevExpandPosition[0] = groupPosition;
            }
        });

        back_img=(ImageView)findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
    }

    /* access modifiers changed from: 0000 */
    public void setItems() {
        String str;
        this.header = new ArrayList<>();
        String str2 = "Contest Formats";
        this.header.add(str2);
        this.header.add("Contest Prizes");
        this.header.add("Registration");
        this.header.add("Eligibility");
        this.header.add("Contest Plan");
        this.header.add("Selection & Verification");
        this.header.add("Condition relating to prizes");
        List<String> child1 = new ArrayList<>();
        List<String> child2 = new ArrayList<>();
        List<String> child3 = new ArrayList<>();
        List<String> child4 = new ArrayList<>();
        List<String> child5 = new ArrayList<>();
        List<String> child6 = new ArrayList<>();
        List<String> child7 = new ArrayList<>();



        this.hashMap = new HashMap<>();
        int i = 1;
        while (true) {
            str = "Child";
            if (i >= 5) {
                break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(i);
            child1.add(sb.toString());
            i++;
        }
        for (int i2 = 1; i2 < 5; i2++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(i2);
            child2.add(sb2.toString());
        }
        for (int i3 = 1; i3 < 6; i3++) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(i3);
            child3.add(sb3.toString());
        }
        for (int i4 = 1; i4 < 7; i4++) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append(i4);
            child4.add(sb4.toString());
        }
        for (int i5 = 1; i5 < 5; i5++) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str);
            sb5.append(i5);
            child5.add(sb5.toString());
        }
        for (int i6 = 1; i6 < 5; i6++) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(str);
            sb6.append(i6);
            child6.add(sb6.toString());
        }
        for (int i7 = 1; i7 < 5; i7++) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append(str);
            sb7.append(i7);
            child7.add(sb7.toString());
        }
        this.hashMap.put(this.header.get(0), child1);
        this.hashMap.put(this.header.get(1), child2);
        this.hashMap.put(this.header.get(2), child3);
        this.hashMap.put(this.header.get(3), child4);
        this.hashMap.put(this.header.get(4), child5);
        this.hashMap.put(this.header.get(5), child6);
        this.hashMap.put(this.header.get(6), child7);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_img:
                startActivity(new Intent(getApplicationContext(), NavActivity.class));
                break;
        }

    }
}
