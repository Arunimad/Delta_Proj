package com.delta.delta_proj;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class Main3Activity extends AppCompatActivity {


    ListView list1;
    ArrayAdapter adapter;
    ArrayList<String> items2 = new ArrayList<>();
    ArrayList<row_elements> pairs = new ArrayList<>();

    AdapterViewCustom adapternew;
    EditText edit1;
    TextView text1;
    String s;
    doing a;
    int value;
    String fortask1;
    String fortask2 = null;
    String[] fortask11;
    int[] countno = null;
    File file;
    BufferedWriter writer;
    FileOutputStream stream;


    public class row_elements {

        public String name;
        public String img;
        public String time;
        public String anss;
        public String noviews;
        public String novotes;
        public String tags;
        public String excerpts;



        public row_elements(String name, String img , String time , String anss , String noviews , String novotes,String tags , String excerpts) {
            this.name = name;
            this.img = img;
            this.time = time;
            this.anss = anss;
            this.noviews=noviews;
            this.novotes = novotes;
            this.tags =tags;
            this.excerpts = excerpts;

        }

    }


    public class ViewHolder {

        public TextView txt;
        public ImageView img;
        public TextView time;
        public  TextView anss;
        public TextView noviews;
        public  TextView novotes;
        public  TextView tags;
        public  TextView excerpts;


    }


    public class AdapterViewCustom extends BaseAdapter {


        private Activity context_1;
        public ArrayList<row_elements> pairs;


        public AdapterViewCustom(Activity context,
                                 ArrayList<row_elements> pairs) {
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
            ViewHolder viewHolder ;

            if (convertView == null) {
                convertView = LayoutInflater.from(context_1).inflate(
                        R.layout.custom_row2, null);
                viewHolder = new ViewHolder();

                viewHolder.img = (ImageView) convertView
                        .findViewById(R.id.log_img);


                viewHolder.txt = (TextView) convertView
                        .findViewById(R.id.tv_view);

                viewHolder.time = (TextView) convertView
                        .findViewById(R.id.time);

                viewHolder.anss = (TextView) convertView
                        .findViewById(R.id.anss);

                viewHolder.noviews = (TextView) convertView
                        .findViewById(R.id.noviews);

                viewHolder.novotes = (TextView) convertView
                        .findViewById(R.id.novotes);

                viewHolder.tags = (TextView) convertView
                        .findViewById(R.id.tags);

                viewHolder.excerpts = (TextView) convertView
                        .findViewById(R.id.excerpts);


                convertView.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txt.setText(pairs.get(position).name);
            Picasso.with(getBaseContext()).load(pairs.get(position).img).resize(64,64).into(viewHolder.img);
            viewHolder.time.setText(pairs.get(position).time);
            viewHolder.anss.setText(pairs.get(position).anss);
            viewHolder.noviews.setText(pairs.get(position).noviews);
            viewHolder.novotes.setText(pairs.get(position).novotes);
            viewHolder.tags.setText(pairs.get(position).tags);
            viewHolder.excerpts.setText(pairs.get(position).excerpts);





            return convertView;
        }


    }

    public void add(row_elements e) {
        pairs.add(e);

    }

    public void task(String[] words, String string)
    {

        int count1 =0;
        int count2=0;


        while (count2 < words.length) {

            while (string.substring(count1).isEmpty()) {

                if (words[count2].equals(string.substring(count1))) {
                    countno[count2] +=1;

                }
                count1++;

            }
            count1++;
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        edit1 = (EditText) findViewById(R.id.edit1);

        adapternew = new AdapterViewCustom(Main3Activity.this, pairs);
        list1 = (ListView) findViewById(R.id.list1);


        text1 = (TextView) findViewById(R.id.text2);



        Button b1 = (Button) findViewById(R.id.button1);
        //file = new File(Environment.getExternalStorageDirectory() + "/DCIM/" + "countcheck.txt");
        //try {

           // stream = new FileOutputStream(file,true); writer = new BufferedWriter(new FileWriter(file, true));
     //   } catch (IOException e) {
          //  e.printStackTrace();
        //}


        b1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              s = edit1.getText().toString();


              try {
                  a = new doing();
              } catch (IOException e) {
                  e.printStackTrace();
              }
              a.execute();
          }
      });


        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                value = position;
                //Toast.makeText(Main3Activity.this,""+value,Toast.LENGTH_LONG).show();
                //text1.setText(position);
                String url =items2.get(value);
                Intent i =new Intent(Main3Activity.this,Main2Activity.class);
                i.putExtra("url",url);
                startActivity(i);

            }
        });








    }

    class doing extends AsyncTask<Void, Void, String> {

        Document document;

        doing() throws IOException {
        }


        @Override
        protected String doInBackground(Void... params) {
            String url = "https://stackoverflow.com/search?q=" + s;

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


            //text1.setText(s);


            if (document.body().hasClass("tagged-questions-page new-topbar")) {
                Toast.makeText(Main3Activity.this, "Loading-tagged", Toast.LENGTH_LONG).show();


                Elements results = document.select("#questions .question-summary ");



                    for (Element result : results) {
                        Log.wtf("wtf", "done");



                            row_elements temp = new row_elements(result.select(".summary .question-hyperlink").text(), result.select(".gravatar-wrapper-32 img").attr("src"), result.select(".summary .user-action-time .relativetime").text(),result.select(" .statscontainer strong").eq(1).text(),result.select(".statscontainer .views").text(), result.select(".vote-count-post").text(),result.select(".summary a.post-tag").text(),result.select(".summary .excerpt").text());
                            add(temp);
                            //fortask1= result.select(".summary .question-hyperlink").text();
                            //fortask11 = fortask1.split(" ");


                           // row_elements temp = new row_elements(result.select(".summary .question-hyperlink").text(), result.select(".gravatar-wrapper-32 img").attr("src"), result.select(".summary .user-action-time .relativetime").text(), result.select(" .statscontainer .status.answered strong").text(),result.select(".statscontainer .views").text(),result.select(".vote-count-post").text());
                           // add(temp);





                    }
                    list1.setAdapter(adapternew);
                    adapternew.notifyDataSetChanged();






                Elements results2 = document.select("#questions .question-summary .summary .question-hyperlink");

                for (Element result : results2) {
                    Log.wtf("wtf", "done");
                    items2.add(result.attr("href"));


                }


            } else {

                String ss = document.select("#mainbar p").toString();
                Toast.makeText(Main3Activity.this, ss, Toast.LENGTH_LONG).show();


                if (ss.equals("<p>Your search returned no matches.</p>")) {

                    Toast.makeText(Main3Activity.this, "0 Results Found", Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(Main3Activity.this, "Loading-alternate", Toast.LENGTH_LONG).show();

                    Elements results = document.select("#mainbar .question-summary");

                    for (Element result : results) {
                        Log.wtf("wtf", "done");
                        //items.add(result.select(".result-link a").text());

                        if (result.hasClass("status answered-accepted")) {
                            row_elements temp = new row_elements(result.select(".result-link a").text(), "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_xfmgi1dawpzBp7DPuTDLmvgm2gbPdkFnjw4AemYdgk_WsHYN", result.select(".relativetime").text(), result.select(" .statscontainer strong").eq(1).text(), result.select(".statscontainer .views").text(), result.select(".vote-count-post").text(),result.select(".summary a.post-tag").text(),result.select(".summary .excerpt").text());
                            add(temp);
                        }else {
                            row_elements temp = new row_elements(result.select(".result-link a").text(), "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_xfmgi1dawpzBp7DPuTDLmvgm2gbPdkFnjw4AemYdgk_WsHYN", result.select(".relativetime").text(), result.select(" .statscontainer strong").eq(1).text(), result.select(".statscontainer .views").text(), result.select(".vote-count-post").text(),result.select(".summary a.post-tag").text(),result.select(".summary .excerpt").text());
                            add(temp);
                        }

                        //fortask2 += result.select(".result-link a").text();


                    }


                    list1.setAdapter(adapternew);
                    adapternew.notifyDataSetChanged();


                    Elements results2 = document.select(" .result-link a");
                    for (Element result : results2) {
                        Log.wtf("wtf2", "done");
                        items2.add(result.attr("href"));


                    }


                }


            }

            //task(fortask11,fortask2);



           // try {
                //stream.write(fortask1.getBytes());
              //  stream.write("\n".getBytes());
              //  stream.write(countno.toString().getBytes());
               // stream.close();

           // } catch (IOException e) {
               // e.printStackTrace();
          //  }




        }





    }












}
