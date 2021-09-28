package com.itsram.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.itsram.quizapp.QuestionsActivity.FILE2_NAME;
import static com.itsram.quizapp.QuestionsActivity.KEY2_NAME;


public class ScoreActivity extends AppCompatActivity {

    public static final String FILEHISTORY_NAME="SCOREHISTORY";
    public static final String KEYHISTORY_NAME="HISTORY";

    private TextView scored,total;
    private Button doneBtn;
    private ImageView share;
    private LinearLayout layout;
    private RecyclerView recyclerView;
private int position=0;
    private String category;
    private int setNo;
    private List<HistoryData>list;
    private SharedPreferences preferences2,preferencesHistory;
    private SharedPreferences.Editor editor2,editorHstory;
    private Gson gson2,gsonHistory;

    private List<QuestionModel>incorrectList;
    private List<HistoryData>scrHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        scored=findViewById(R.id.scored);
        total=findViewById(R.id.total);
        doneBtn=findViewById(R.id.doneBtn);
        layout=findViewById(R.id.scoreLayout);
        share=findViewById(R.id.share_btn);

        recyclerView=findViewById(R.id.recyclerViewIncorrect);

        preferences2=getSharedPreferences(FILE2_NAME, Context.MODE_PRIVATE);
        editor2=preferences2.edit();
        gson2=new Gson();

        final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        final String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        preferencesHistory=getSharedPreferences(FILEHISTORY_NAME, Context.MODE_PRIVATE);
        editorHstory=preferencesHistory.edit();
        gsonHistory=new Gson();

        list=new ArrayList<>();

        getIncorrectQuestion();
        getHistory();

      //  category=getIntent().getStringExtra("category");
      //  setNo=getIntent().getIntExtra("setNo",1);

        Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_up);
        layout.setAnimation(animation1);

        final String scoredMarks,totalMarks;

        scoredMarks=String.valueOf(getIntent().getIntExtra("score",0));
        totalMarks="OUT OF "+String.valueOf(getIntent().getIntExtra("total",0));
        category=getIntent().getStringExtra("category");
        setNo=getIntent().getIntExtra("setNo",1);
        scored.setText(scoredMarks);
        total.setText(totalMarks);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);


        ScoreAdapter adapter=new ScoreAdapter(incorrectList);
        recyclerView.setAdapter(adapter);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent intent=new Intent(ScoreActivity.this,SetsActivity.class);
                // startActivity(intent);
                if (incorrectList.size() > 0) {
                    incorrectList.clear();
                    Toast.makeText(ScoreActivity.this, "size"+ incorrectList.size(), Toast.LENGTH_SHORT).show();
                    finish();
                    scrHistory.add(new HistoryData("Date :"+currentDate+" Time:"+currentTime,
                            "CATSET\n"+"Category :"+category+ "\n SetNo"+setNo,
                            "SCORE\n"+"score :"+scoredMarks+"\n OutOF :"+totalMarks));
                    storeHistory();
                } else {
                    finish();
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg="*****Quiz App*****\n Category :"+category+"\nFrom set No :"+setNo+"\nScored "+scoredMarks+" "+totalMarks;

                Intent shareIntent=new Intent(Intent.ACTION_SENDTO);
                shareIntent.setData(Uri.parse("mailto:"));
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Quiz App Result");
                shareIntent.putExtra(Intent.EXTRA_TEXT,msg);
               // startActivity(Intent.createChooser(shareIntent,"Share using"));
                if (shareIntent.resolveActivity(getPackageManager())!=null){
                    startActivity(shareIntent);
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        storeIncorrectQue();

    }


    private void getIncorrectQuestion(){
        String json=preferences2.getString(KEY2_NAME,"");
        Type type=new TypeToken<List<QuestionModel>>(){}.getType();
        incorrectList=gson2.fromJson(json,type);
        if (incorrectList==null)
        {
            incorrectList=new ArrayList<>();
        }
    }
    private void storeIncorrectQue(){
        String json2=gson2.toJson(incorrectList);
        editor2.putString(KEY2_NAME,json2);
        editor2.commit();
    }


    //history

    private void getHistory(){
        String json=preferencesHistory.getString(KEYHISTORY_NAME,"");
        Type type=new TypeToken<List<HistoryData>>(){}.getType();
        scrHistory=gsonHistory.fromJson(json,type);
        if (scrHistory==null)
        {
            scrHistory=new ArrayList<>();
        }
    }
    private void storeHistory(){
        String json2=gsonHistory.toJson(scrHistory);
        editorHstory.putString(KEYHISTORY_NAME,json2);
        editorHstory.commit();
    }
}
