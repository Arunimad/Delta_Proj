package com.delta.delta_proj;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Main5Activity extends AppCompatActivity {



    ListView list1;
    ArrayAdapter adapter;
    ArrayList<String> items = new ArrayList<>();
    ArrayList<row_elements> pairs = new ArrayList<>();

    Main5Activity.AdapterViewCustom adapternew;



    public class row_elements {

        public String name;
        public String img;



        public row_elements(String name, String img ) {
            this.name = name;
            this.img = img;


        }

    }




    public class ViewHolder {

        public TextView txt;
        public ImageView img;


    }



    public class AdapterViewCustom extends BaseAdapter {


        private Activity context_1;
        public ArrayList<Main5Activity.row_elements> pairs;


        public AdapterViewCustom(Activity context,
                                 ArrayList<Main5Activity.row_elements> pairs) {
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
            Main5Activity.ViewHolder viewHolder = null;

            if (convertView == null) {
                convertView = LayoutInflater.from(context_1).inflate(
                        R.layout.custom_row, null);
                viewHolder = new Main5Activity.ViewHolder();

                viewHolder.img = (ImageView) convertView
                        .findViewById(R.id.log_img);


                viewHolder.txt = (TextView) convertView
                        .findViewById(R.id.tv_view);


                convertView.setTag(viewHolder);
            } else {

                viewHolder = (Main5Activity.ViewHolder) convertView.getTag();
            }

            viewHolder.txt.setText(pairs.get(position).name);
            Picasso.with(getBaseContext()).load(pairs.get(position).img).resize(480,480).into(viewHolder.img);







            // BitmapFactory.Options bmOptions = new BitmapFactory.Options();



            //Bitmap bmp = BitmapFactory.decodeFile(pairs.get(position).img);
            //viewHolder.img.setImageBitmap(Bitmap.createScaledBitmap(bmp, 480, 480, false));



            return convertView;
        }


    }
    public void add(Main5Activity.row_elements e) {
        pairs.add(e);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);







    }
}
