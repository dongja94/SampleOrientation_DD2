package com.begentgroup.sampleorientation;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String[] ORIENTATION = {"0", "90", "180", "270"};
    ListView listVIew;
    ArrayAdapter<String> mAdapter;
    boolean isLand = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portait", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        }

        Display display = getWindowManager().getDefaultDisplay();
        int rotation = display.getRotation();
        Toast.makeText(this, "rotation : " + ORIENTATION[rotation], Toast.LENGTH_SHORT).show();

        listVIew = (ListView)findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listVIew.setAdapter(mAdapter);
        if (findViewById(R.id.container) != null) {
            isLand = true;
        } else {
            isLand = false;
        }
        if (isLand) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, MessageFragment.newInstance("default"))
                        .commit();
            }
        }
        listVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String message = (String) listVIew.getItemAtPosition(i);
                if (isLand) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, MessageFragment.newInstance(message))
                            .commit();
                } else {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("message", message);
                    startActivity(intent);
                }
            }
        });

        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mAdapter.add("item " + i);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Display display = getWindowManager().getDefaultDisplay();
        int rotation = display.getRotation();
        Toast.makeText(this, "config change rotation : " + ORIENTATION[rotation], Toast.LENGTH_SHORT).show();
    }
}
