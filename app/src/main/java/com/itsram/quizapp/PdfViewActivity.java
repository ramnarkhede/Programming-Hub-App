package com.itsram.quizapp;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.webkit.WebView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class PdfViewActivity extends AppCompatActivity {
    private PDFView pdfView;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);


        pdfView = findViewById(R.id.viewPdf);
        String fileurl = getIntent().getStringExtra("fileurl");
        String filename = getIntent().getStringExtra("filename");

        pd = new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("Opening...");
        new RetrivePdfStream().execute(fileurl);
        pd.show();
    }

    class RetrivePdfStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
            pd.dismiss();

        }
    }
}
