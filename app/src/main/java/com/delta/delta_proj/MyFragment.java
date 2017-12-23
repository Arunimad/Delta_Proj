package com.delta.delta_proj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ME & MSI on 7/22/2017.
 */

public  class MyFragment extends Fragment{





    ImageView newsimage;
    TextView newstext;
    String message;
    String temp;






    JSONObject job;








    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";



    public static final MyFragment newInstance(String message)
    {
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         message = getArguments().getString(EXTRA_MESSAGE);
        View v = inflater.inflate(R.layout.myfragment_layout, container, false);




        // TextView messageTextView = (TextView)v.findViewById(R.id.textView);
        // messageTextView.setText(message);

        newsimage = (ImageView)v.findViewById(R.id.newsimage);
        newstext = (TextView)v.findViewById(R.id.newstext);













        RetrieveFeedTask temp = new RetrieveFeedTask();
        temp.execute();

        return v;
    }




    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {


        StringBuilder builder;


        protected void onPreExecute() {

        }

        Document document;

        @Override
        protected String doInBackground(Void... params) {


            String url = message;
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return url;

        }

        protected void onPostExecute(String response) {





            if (document.select(".mediumcontent .storyimgclose [alt]").attr("src").toString().isEmpty()) {


                Picasso.with(getContext()).load("http://dumkhum.com/wp-content/uploads/2012/08/Headlines-Today-Logo.jpg").into(newsimage);
               newstext.setText(document.select(".right-story-container p").text());








                //newstext.setText(document.select(".mediumcontent .storyimgclose [alt]").attr("src").toString());

            }else {


                Picasso.with(getContext()).load(document.select(".mediumcontent .storyimgclose [alt]").attr("src").toString()).into(newsimage);
                newstext.setText(document.select(".right-story-container p").text());
               // builder.append(document.select(".right-story-container p").text()).append("\n");







            }







        }


    }












}
