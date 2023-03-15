package com.example.messengerapp.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.messengerapp.Fragments.chatsFragment1;
import com.example.messengerapp.PageAdapter_main;
import com.example.messengerapp.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    Toolbar toolbar;
    PageAdapter_main pageAdapter;
    TabLayout tabLayout;
    TabItem chats, group, story, calls;
    TextView textView;
    FirebaseAuth auth;
    FirebaseDatabase database;

    private final int CAMERA_REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tablayout_id);
        chats = findViewById(R.id.chats_id);
        group = findViewById(R.id.group_id);
        calls = findViewById(R.id.calls_id);
        story = findViewById(R.id.story_id);
        textView = findViewById(R.id.title_text_id);

        viewPager = findViewById(R.id.viewpager_id);
        toolbar = findViewById(R.id.toolbar_id);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        getSupportFragmentManager().beginTransaction().replace(R.id.viewpager_id, new chatsFragment1()).commit();
        pageAdapter = new PageAdapter_main(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0) {
                    pageAdapter.notifyDataSetChanged();
                    textView.setText("Chat");
                } else if (tab.getPosition() == 1) {
                    pageAdapter.notifyDataSetChanged();
                    textView.setText("Groups");
                } else if (tab.getPosition() == 2) {
                    pageAdapter.notifyDataSetChanged();
                    textView.setText("Camera");
                } else if (tab.getPosition() == 3) {
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
        getMenuInflater().inflate(R.menu.menuitems, menu);
        MenuItem menuItem = menu.findItem(R.id.searchmenu_id);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search here..");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchmenu_id:

                break;

            case R.id.camera_id:
                Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(icamera, CAMERA_REQ_CODE);
                break;
            case R.id.signout_id:
                auth.signOut();
                startActivity(new Intent(MainActivity.this, GetOTP_Activity3.class));
                SharedPreferences preferences = getSharedPreferences("userdata", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("open", false);
                editor.apply();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == CAMERA_REQ_CODE) {

                if (data != null) {
                    Uri uri = data.getData();
                }
            }
        }
    }
}

//        final DialogPlus dialogPlus = DialogPlus.newDialog(MainActivity.this)
//                .setContentBackgroundResource(R.layout.searchdialogview)
//                .setExpanded(true,120)
//                .create();
//        dialogPlus.show();