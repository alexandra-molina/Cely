package com.example.alexandramolina.cely;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Transalation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transalation);
        TranslationTask tt= new TranslationTask();
        try {
            String s= tt.execute("Turkey has since threatened to attack the SDF-controlled town of Manbij, which was taken from IS in 2016 - and where the US has stationed troops.\n" +
                    "\n" +
                    "The Turkish national security council issued an ultimatum on Wednesday, saying it would act if Kurdish fighters did not leave immediately.\n" +
                    "\n" +
                    "Sebastian Usher, Middle East Editor for the BBC World Service, says the US dimension to the conflict does not appear to have been resolved as yet.\n" +
                    "\n" +
                    "There is no sign that American troops have pulled back from Manbij, where they helped the SDF in its successful campaign against IS, our correspondent reports.\n" +
                    "\n").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
    public class TranslationTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String inputString=urls[0];
            TranslateOptions options = TranslateOptions.newBuilder()
                    .setApiKey("AIzaSyBArteM1ejf2C6fCBZojg0JZkD7ZZ9IiQk")
                    .build();
            Translate translate = options.getService();
            final Translation translation =
                    translate.translate(inputString,
                            Translate.TranslateOption.targetLanguage("spa"));
            Log.d("PRUEBA",translation.getTranslatedText());

            return translation.getTranslatedText();
        }

    }





}
