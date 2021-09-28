package com.itsram.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.itsram.quizapp.QuestionsActivity.FILE_NAME;
import static com.itsram.quizapp.QuestionsActivity.KEY_NAME;

public class SaveActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Dialog loadingDialog;
    private  List<QuestionModel>savedQuestionList;

    private ImageButton deleteBtn;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Saved Wrong Attempt");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deleteBtn=findViewById(R.id.deleteBtn);

        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        recyclerView=findViewById(R.id.recyclerViewBookmark);

        preferences=getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        gson=new Gson();
        getSaved();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);


         SaveAdpter adapter=new SaveAdpter(savedQuestionList);
        recyclerView.setAdapter(adapter);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(savedQuestionList.size()>0)
               {
                   savedQuestionList.clear();
                   Toast.makeText(SaveActivity.this,"List is cleared",Toast.LENGTH_SHORT).show();
                   finish();

               }else {
                   Toast.makeText(SaveActivity.this,"No Questions to clear",Toast.LENGTH_SHORT).show();
               }

            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        storeSaveQue();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void getSaved()
    {
        String json=preferences.getString(KEY_NAME,"");
        Type type=new TypeToken<List<QuestionModel>>(){}.getType();
        savedQuestionList=gson.fromJson(json,type);
        if (savedQuestionList==null)
        {
            savedQuestionList=new ArrayList<>();
        }
    }

    private void storeSaveQue(){
        String json=gson.toJson(savedQuestionList);

        editor.putString(KEY_NAME,json);
        editor.commit();
    }
}
