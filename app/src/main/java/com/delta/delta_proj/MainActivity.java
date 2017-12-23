package com.delta.delta_proj;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class MainActivity extends AppCompatActivity {

        String[] find = {"the","two"};







        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public  void taskdoing(String[] args) throws FileNotFoundException, IOException {

            Map m1 = new HashMap();

            try (BufferedReader br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory() + "/DCIM/" + "app3.txt"))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    String[] words = line.split(" ");//those are your words
                    for (int i = 0; i < words.length; i++) {
                        if (m1.get(words[i]) == null) {
                            m1.put(words[i], 1);
                        } else {
                            int newValue = Integer.valueOf(String.valueOf(m1.get(words[i])));
                            newValue++;
                            m1.put(words[i], newValue);
                        }
                    }
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
            }
            Map<String, String> sorted = new TreeMap<String, String>(m1);
            for (Object key : sorted.keySet()) {
                System.out.println("Word: " + key + "\tCounts: " + m1.get(key));
            }
        }



    StringBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Main3Activity.class);
                startActivity(i);
            }
        });

        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,Main6Activity.class);
                startActivity(i);


            }
        });

        Button b3 = (Button) findViewById(R.id.task);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doing temp =new doing();
                temp.execute();


            }
        });

        Button b4 = (Button)findViewById(R.id.taskk);
        b4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                try {
                    taskdoing(find);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });









    }


    class doing extends AsyncTask<Void,Void,String> {


        Document document;

        @Override
        protected String doInBackground(Void... params) {


            String url = "http://indiatoday.intoday.in/section/120/1/top-stories.html";
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return url;

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            Elements results = document.select(".box");

            for (Element result : results)
            {

                String temp=result.select(".innerbox p").toString();


                try {
                    File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/" + "app3.txt");
                    FileOutputStream stream = new FileOutputStream(file,true);
                    stream.write(temp.getBytes());
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            Toast.makeText(getBaseContext(),"Loaded to file",Toast.LENGTH_SHORT).show();





        }
    }



}
