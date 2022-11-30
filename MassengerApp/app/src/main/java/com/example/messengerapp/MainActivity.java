package com.example.messengerapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    Toolbar toolbar;
    PageAdapter_main pageAdapter;
    TabLayout tabLayout;
    TabItem chats,group,camera,story;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tablayout_id);
        chats = findViewById(R.id.chats_id);
        group = findViewById(R.id.group_id);
        camera = findViewById(R.id.camera_id);
        story = findViewById(R.id.story_id);
        textView = findViewById(R.id.toolbarText_id);

        viewPager = findViewById(R.id.viewpager_id);
        toolbar = findViewById(R.id.toolbar_id);


        getSupportFragmentManager().beginTransaction().replace(R.id.viewpager_id,new chatsFragment1()).commit();
        pageAdapter = new PageAdapter_main(getSupportFragmentManager(),tabLayout.getTabCount());

         viewPager.setAdapter(pageAdapter);

         tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
             @Override
             public void onTabSelected(TabLayout.Tab tab) {
                 viewPager.setCurrentItem(tab.getPosition());

                 if (tab.getPosition()==0){
                     pageAdapter.notifyDataSetChanged();
                     textView.setText("Chats");
                 }
                 else if(tab.getPosition()==1){
                     pageAdapter.notifyDataSetChanged();
                     textView.setText("Groups");
                 }
                 else if(tab.getPosition()==2){
                     pageAdapter.notifyDataSetChanged();
                     textView.setText("Camera");
                 }
                 else if(tab.getPosition()==3){
                     pageAdapter.notifyDataSetChanged();
                     textView.setText("Story");
                 }

             }

             @Override
             public void onTabUnselected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabReselected(TabLayout.Tab tab) {

             }
         });
         viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menuitems,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}