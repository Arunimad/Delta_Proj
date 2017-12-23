package com.delta.delta_proj;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {


    TextView text1;
    ListView list1;
    ListView queslist;
    ArrayAdapter adapter;
    ArrayList<String> items = new ArrayList<>();
    String s;
    AdapterViewCustom adapternew1;
    AdapterViewCustom adapternew2;



    TextView text2;
    ListView list2;
    ArrayAdapter adapter2;
    ArrayList<String> items2 = new ArrayList<>();
    ArrayList<row_elements> pairs_ques = new ArrayList<>();
    ArrayList<row_elements> pairs_ans = new ArrayList<>();



    public class row_elements {

        public String name;
        public String img;
        public String time;


        public String novotes;





        public row_elements(String name, String img , String time , String novotes) {
            this.name = name;
            this.img = img;
            this.time = time;

            this.novotes = novotes;


        }

    }


    public class ViewHolder {

        public TextView txt;
        public ImageView img;
        public TextView time;

        public  TextView novotes;



    }


    public class AdapterViewCustom extends BaseAdapter {


        private Activity context_1;
        public ArrayList<Main2Activity.row_elements> pairs;


        public AdapterViewCustom(Activity context,
                                 ArrayList<Main2Activity.row_elements> pairs) {
            context_1 = context;
            this.pairs = pairs;

        }

        @Override
        public int getCount() {
            return pairs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }




        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Main2Activity.ViewHolder viewHolder ;
            if (convertView == null) {
                convertView = LayoutInflater.from(context_1).inflate(
                        R.layout.custom_row3, null);
                viewHolder = new Main2Activity.ViewHolder();

                viewHolder.img = (ImageView) convertView
                        .findViewById(R.id.log_img);


                viewHolder.txt = (TextView) convertView
                        .findViewById(R.id.tv_view);

                viewHolder.time = (TextView) convertView
                        .findViewById(R.id.time);



                viewHolder.novotes = (TextView) convertView
                        .findViewById(R.id.novotes);




                convertView.setTag(viewHolder);
            } else {

                viewHolder = (Main2Activity.ViewHolder) convertView.getTag();
            }

            viewHolder.txt.setText(pairs.get(position).name);
            Picasso.with(getBaseContext()).load(pairs.get(position).img).resize(64,64).into(viewHolder.img);
            viewHolder.time.setText(pairs.get(position).time);

            viewHolder.novotes.setText(pairs.get(position).novotes);






            return convertView;
        }


    }

    public void add_ques(Main2Activity.row_elements e) {
        pairs_ques.add(e);

    }

    public void add_ans(Main2Activity.row_elements e) {
        pairs_ans.add(e);

    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Intent i =getIntent();
        s = i.getExtras().getString("url");


        queslist = (ListView) findViewById(R.id.queslist);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        list1 = (ListView) findViewById(R.id.list1);


        text2 = (TextView) findViewById(R.id.text3);
        adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items2);
        list2 = (ListView) findViewById(R.id.list2);


        adapternew1 = new AdapterViewCustom(Main2Activity.this,pairs_ques);
        adapternew2 = new AdapterViewCustom(Main2Activity.this,pairs_ans);


        doing a = new doing();
        a.execute();


    }


    class doing extends AsyncTask<Void, Void, String> {


        Document document;




        @Override
        protected String doInBackground(Void... params) {
            String url = "https://stackoverflow.com"+s;
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


            Elements questions = document.select("#question ");
            for(Element question : questions){

                row_elements temp_ques =new row_elements(question.select(".post-text").text(),question.select(".gravatar-wrapper-32 img").attr("src"),question.select(".user-action-time .relativetime").text(),question.select(".vote-count-post").text());
                add_ques(temp_ques);
            }


            //text1.setText(question);
            queslist.setAdapter(adapternew1);




            Elements answerers = document.select("#answers .user-details a");
            for (Element answerer : answerers) {
                Log.wtf("How many times u in ??","done");

                items.add(answerer.text());


            }
            list1.setAdapter(adapter);

            Elements answers = document.select("#answers .answer");
            for(Element answer : answers){

                Log.wtf("am i in","done");

                //items2.add(answer.text());
                row_elements temp_ans =new row_elements(answer.select(".post-text").text(),answer.select(".gravatar-wrapper-32 img").attr("src"),answer.select(".user-action-time .relativetime").text(),null);
                add_ans(temp_ans);

            }



            list2.setAdapter(adapternew2);



        }
    }

}
