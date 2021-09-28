package com.itsram.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Button startBtn, cBtn, cppBtn, javaBtn, pythonBtn;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        textView=findViewById(R.id.appTitle);

        textView.setText("    ?Just \n </>Code \n        It !");

   //     Toolbar toolbar = findViewById(R.id.toolBarMain);
      //  setSupportActionBar(toolbar);
        //    drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.appbar_scrolling_view_behavior,R.string.appbar_scrolling_view_behavior);

    /*    getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_info);*/
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);

        cBtn = findViewById(R.id.btnC);
        cppBtn = findViewById(R.id.cpp);
        javaBtn = findViewById(R.id.java);
        pythonBtn = findViewById(R.id.python);


        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Cprog = "C";
                Intent cProg = new Intent(MainActivity.this, TheoryActivity.class);
                cProg.putExtra("language", Cprog);
                startActivity(cProg);
            }
        });
        cppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CppProg = "Cpp";
                Intent cProg = new Intent(MainActivity.this, TheoryActivity.class);
                cProg.putExtra("language", CppProg);
                startActivity(cProg);
            }
        });
        javaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String java = "Java";
                Intent cProg = new Intent(MainActivity.this, TheoryActivity.class);
                cProg.putExtra("language", java);
                startActivity(cProg);
            }
        });
        pythonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String python = "Python";
                Intent cProg = new Intent(MainActivity.this, TheoryActivity.class);
                cProg.putExtra("language", python);
                startActivity(cProg);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        return true;

                    case R.id.menu_question:
                        Intent categoriesActivity = new Intent(MainActivity.this, CategoriesActivity.class);
                        startActivity(categoriesActivity);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_save:

                        Intent bookmarkIntent = new Intent(MainActivity.this, SaveActivity.class);
                        startActivity(bookmarkIntent);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.menu_history_bottom:
                        Intent intentHistory = new Intent(MainActivity.this, HistoryActivity.class);
                        startActivity(intentHistory);
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.menu_code:
                        Intent intentProgram = new Intent(MainActivity.this, ProgramActivity.class);
                        startActivity(intentProgram);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }
    private static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
public void ClickLogo(View view)
{
    closeDrawer(drawerLayout);
}
private static void closeDrawer(DrawerLayout drawerLayout)
{
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
        drawerLayout.closeDrawer(GravityCompat.START);

    }

}
public void ClickHome(View view){
        recreate();
}
}


        //  getSupportActionBar().setDisplayShowTitleEnabled(true);






     /*   startBtn=findViewById(R.id.start_btn);
        frameLayout=findViewById(R.id.frameLayout);
        textViewApp=findViewById(R.id.textViewApp);
        savedQuestionBtn=findViewById(R.id.save_btn);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_left);
        startBtn.startAnimation(animation);


        Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.item_up);
        frameLayout.setAnimation(animation1);


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent categoriesActivity=new Intent(MainActivity.this,CategoriesActivity.class);
                startActivity(categoriesActivity);
            }
        });


       savedQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent bookmarkIntent=new Intent(MainActivity.this,SaveActivity.class);
                startActivity(bookmarkIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_home:
              Intent home=new Intent(this,MainActivity.class);
              startActivity(home);
              return true;


            case R.id.menu_history:
                Intent intentHistory=new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intentHistory);


                return true;
            case R.id.menu_feedback:
               Intent feedbackEmail=new Intent(Intent.ACTION_SENDTO);
               feedbackEmail.setData(Uri.parse("mailto:"));
               feedbackEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{"itsram0204@gmail.com"});
               feedbackEmail.putExtra(Intent.EXTRA_SUBJECT,"Quiz App \n feedback");
              // startActivity(Intent.createChooser(feedbackEmail,"Send feedback"));

                if (feedbackEmail.resolveActivity(getPackageManager())!=null){
                    startActivity(feedbackEmail);
                }

                return true;
            case R.id.menu_info:
                Intent info=new Intent(MainActivity.this,InfoActivity.class);
                startActivity(info);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


