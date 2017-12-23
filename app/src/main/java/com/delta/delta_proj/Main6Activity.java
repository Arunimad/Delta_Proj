package com.delta.delta_proj;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main6Activity extends FragmentActivity {



    ArrayList<row_elements> pairs = new ArrayList<>();



    public class mypageadapter extends FragmentPagerAdapter {


        private List<Fragment> fragments;
        public mypageadapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }
        @Override
        public int getCount() {
            return this.fragments.size();
        }




    }






    public class row_elements {


        public String link;



        public row_elements(String link ) {

            this.link=link;


        }

    }


    public void add(row_elements e) {
        pairs.add(e);

    }



    List<Fragment> flist;
    VerticalViewPager viewpager;

    mypageadapter pageadapter;
    int l;


    public List<Fragment> getFragments() {



        flist = new ArrayList<Fragment>();



        for( l=0;l < 10; l=l+1){

            flist.add(MyFragment.newInstance(String.valueOf(pairs.get(l).link)));



        }


        return flist;

    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);



        doing temp = new doing();
        temp.execute();















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

            //Elements results = document.select("#tab1 a");

            Elements results = document.select(".box");

            for (Element result : results)
            {
                //items.add(result.text());
               row_elements p =new row_elements(result.select(".innerbox a").attr("href"));
                add(p);


            }
            List<Fragment> fragments = getFragments();

            pageadapter = new mypageadapter(getSupportFragmentManager(),fragments);


            viewpager = (VerticalViewPager) findViewById(R.id.pager);
            viewpager.setAdapter(pageadapter);


        }
    }




}
