package com.itsram.quizapp;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;

public class QuestionsActivity extends AppCompatActivity {

    public static final String FILE_NAME="QUIZZAPP";
    public static final String KEY_NAME="QUESTIONS";
    public static final String FILE2_NAME="QUIZZ";
    public static final String KEY2_NAME="INCORRECTQUESTION";

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myref=database.getReference();


        private CountDownTimer countDownTimer;
        private TextView question,noIndicator,textViewCountDown;
        private FloatingActionButton bookmarkBtn;
        private LinearLayout optionContainer;
        private Button startbtn,nextBtn;
        private int count=0;
        private List<QuestionModel> list;
        private int position=0;
        private int score=0;
        private String category;
        private int setNo;
        private Dialog loadingDialog;

        private  List<QuestionModel>savedQuestionList;
        private List<QuestionModel>incorrectList;


        private SharedPreferences preferences,preferences2;
        private SharedPreferences.Editor editor,editor2;
        private Gson gson,gson2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
       // Toolbar toolbar=findViewById(R.id.toolbarQuestion);
       // setSupportActionBar(toolbar);

        question=findViewById(R.id.question);
        noIndicator=findViewById(R.id.question_indicator);
        optionContainer=findViewById(R.id.option_container);
      //  startbtn=findViewById(R.id.start_btn);
        nextBtn=findViewById(R.id.next_btn);
        textViewCountDown=findViewById(R.id.timer);

        preferences=getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        gson=new Gson();



        preferences2=getSharedPreferences(FILE2_NAME,Context.MODE_PRIVATE);
        editor2=preferences2.edit();
        gson2=new Gson();


        getSavedQuestion();
        getIncorrectQuestion();

        category=getIntent().getStringExtra("category");
        setNo=getIntent().getIntExtra("setNo",1);

        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        list=new ArrayList<>();
        loadingDialog.show();

        myref.child("SETS").child(category).child("questions").orderByChild("setNo").equalTo(setNo)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot:snapshot.getChildren())
                        {
                            list.add(dataSnapshot.getValue(QuestionModel.class));
                        }
                        startCountDown();
                        if (list.size()>0)
                        {
                            for (int i=0;i<4;i++)
                            {
                                optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        checkAnswer((Button) v);
                                    }
                                });
                            }

                            playAnim(question,0,list.get(position).getQuestion());
                            nextBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    nextBtn.setEnabled(false);
                                    nextBtn.setAlpha(0.7f);
                                    enableOption(true);
                                    position++;
                                    if (position==list.size())
                                    {

                                        scoreAct();

                                    }else {

                                        count = 0;
                                        playAnim(question, 0, list.get(position).getQuestion());
                                    }
                                }
                            });

                        }else {
                            countDownTimer.cancel();
                            Toast.makeText(QuestionsActivity.this, "No Questions", Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(QuestionsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                        finish();
                    }
                });



    }
   /* @Override
    protected void onPause() {
        super.onPause();
        storeSaveQue();
    }*/

    private void playAnim(final View view, final int value, final String data)
    {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value==0 && count<4){
                    String option="";
                    if(count==0) {
                        option=list.get(position).getOptionA();
                    }else if (count==1){
                        option=list.get(position).getOptionB();
                    }else if(count==2){
                        option=list.get(position).getOptionC();
                    }else if(count==3){
                        option=list.get(position).getOptionD();
                    }
                    playAnim(optionContainer.getChildAt(count),0,option);
                    count++;
                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if(value==0)
                {
                    try {
                        ((TextView)view).setText(data);
                        noIndicator.setText(position+1+"/"+list.size());

                    }catch (ClassCastException ex)
                    {
                        ((Button)view).setText(data);
                    }

                    view.setTag(data);
                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void checkAnswer(Button selectedOption)
    {
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if(selectedOption.getText().toString().equals(list.get(position).getCorrectAns()))
        {
            //Correct
            score++;
          selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));


        }else
        {
            //incorrest
           selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));



            Button correctOption= (Button) optionContainer.findViewWithTag(list.get(position).getCorrectAns());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));

            savedQuestionList.add(list.get(position));
            storeSaveQue();

           incorrectList.add(list.get(position));
            storeIncorrectQue();
        }
    }
    private void enableOption(boolean enable)
    {
        for (int i=0;i<4;i++)
        {
            optionContainer.getChildAt(i).setEnabled(enable);
            if(enable)
            {
                optionContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));

            }

        }
    }

    private void startCountDown()
    {
        countDownTimer=new CountDownTimer(40000,1000)
        {

            @Override
            public void onTick(long l) {

                textViewCountDown.setText("Time:"+l/1000);
                if (l<10000)
                {
                    textViewCountDown.setTextColor(Color.RED);
                }


            }

            @Override
            public void onFinish() {


                scoreAct();
                Toast.makeText(QuestionsActivity.this, "Time Up...", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }


    private void scoreAct()
    {

            //Score activity
            Intent scoreIntent=new Intent(QuestionsActivity.this,ScoreActivity.class);
            scoreIntent.putExtra("score",score);
            scoreIntent.putExtra("total",list.size());
            scoreIntent.putExtra("category",category);
            scoreIntent.putExtra("setNo",setNo);
            countDownTimer.cancel();
            startActivity(scoreIntent);
            finish();

            return;

    }

    private void getSavedQuestion()
    {
        String json=preferences.getString(KEY_NAME,"");
        Type type=new TypeToken<List<QuestionModel>>(){}.getType();
        savedQuestionList=gson.fromJson(json,type);
        if (savedQuestionList==null)
        {
            savedQuestionList=new ArrayList<>();
        }


    }
  /*  private boolean modelMatch()
    {
        boolean match=false;
        for (QuestionModel model:savedQuestionList){
            if (model.getQuestion().equals(list.get(position).getQuestion())
            && model.getCorrectAns().equals(list.get(position).getCorrectAns())
            && model.getSetNo()==list.get(position).getSetNo())
            {
                match=true;
            }
        }
        return match;
    }*/
    private void storeSaveQue(){
        String json=gson.toJson(savedQuestionList);


        editor.putString(KEY_NAME,json);
        editor.commit();


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
}
