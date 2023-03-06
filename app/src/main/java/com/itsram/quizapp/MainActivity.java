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
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private Button startBtn, cBtn, cppBtn, javaBtn, pythonBtn;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private TextView textView;

    private Button cDs, cppDs, javaDs, pythonDs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        textView = findViewById(R.id.appTitle);

        textView.setText("    ?Just \n </>Code \n        It !");

        //Theory
        cBtn = findViewById(R.id.btnC);
        cppBtn = findViewById(R.id.cpp);
        javaBtn = findViewById(R.id.java);
        pythonBtn = findViewById(R.id.python);

        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Cprog = "C";
                Intent intent = new Intent(MainActivity.this, TheoryActivity.class);
                intent.putExtra("language", Cprog);
                startActivity(intent);
            }
        });
        cppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CppProg = "Cpp";
                Intent intent = new Intent(MainActivity.this, TheoryActivity.class);
                intent.putExtra("language", CppProg);
                startActivity(intent);
            }
        });
        javaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String java = "Java";
                Intent intent = new Intent(MainActivity.this, TheoryActivity.class);
                intent.putExtra("language", java);
                startActivity(intent);
            }
        });
        pythonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String python = "Python";
                Intent intent = new Intent(MainActivity.this, TheoryActivity.class);
                intent.putExtra("language", python);
                startActivity(intent);
            }
        });


        //Data Structure
        cDs = findViewById(R.id.cDs);
        cppDs = findViewById(R.id.cppDs);
        javaDs = findViewById(R.id.javaDs);
        pythonDs = findViewById(R.id.pythonDs);

        cDs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Cprog = "cDS";
                Intent intent = new Intent(MainActivity.this, DataStructureActivity.class);
                intent.putExtra("language", Cprog);
                startActivity(intent);
            }
        });
        cppDs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CppProg = "cppDS";
                Intent intent = new Intent(MainActivity.this, DataStructureActivity.class);
                intent.putExtra("language", CppProg);
                startActivity(intent);
            }
        });
        javaDs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String java = "javaDS";
                Intent intent = new Intent(MainActivity.this, DataStructureActivity.class);
                intent.putExtra("language", java);
                startActivity(intent);
            }
        });
        pythonDs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String python = "pythonDS";
                Intent intent = new Intent(MainActivity.this, DataStructureActivity.class);
                intent.putExtra("language", python);
                startActivity(intent);
            }
        });


        NavigationBarView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setSelectedItemId(R.id.menu_home);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        }

    }

    public void ClickHome(View view) {
        recreate();

    }
}

