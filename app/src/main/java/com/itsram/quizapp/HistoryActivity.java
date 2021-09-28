package com.itsram.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import static com.itsram.quizapp.ScoreActivity.FILEHISTORY_NAME;
import static com.itsram.quizapp.ScoreActivity.KEYHISTORY_NAME;

public class HistoryActivity extends AppCompatActivity {
    private List<HistoryData>scrList;

    private RecyclerView recyclerView;

    private ImageButton clearBtn;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar=findViewById(R.id.toolbarHistory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Score History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.historyView);
        clearBtn=findViewById(R.id.clear_btn);
        Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_up);
        recyclerView.setAnimation(animation1);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        preferences=getSharedPreferences(FILEHISTORY_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        gson=new Gson();
        getSavedHistory();
        HistoryAdapter adapter=new HistoryAdapter(scrList);
        recyclerView.setAdapter(adapter);



        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scrList.size()>0)
                {
                    scrList.clear();
                    Toast.makeText(HistoryActivity.this,"List is cleared",Toast.LENGTH_SHORT).show();
                    finish();

                }else {
                    Toast.makeText(HistoryActivity.this,"No History to clear",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    @Override
    protected void onPause() {
        super.onPause();
        storeSaveHistory();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void getSavedHistory()
    {
        String json=preferences.getString(KEYHISTORY_NAME,"");
        Type type=new TypeToken<List<HistoryData>>(){}.getType();
        scrList=gson.fromJson(json,type);
        if (scrList==null)
        {
            scrList=new ArrayList<>();
        }
    }

    private void storeSaveHistory(){
        String json=gson.toJson(scrList);

        editor.putString(KEYHISTORY_NAME,json);
        editor.commit();
    }
}
