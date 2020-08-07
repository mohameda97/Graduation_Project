package com.example.breast_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Diagnostic_Tests extends AppCompatActivity {

    private ListView listView;
    private String description[] = {"الماموجرام", "تصوير الثدي بالرنين المغناطيسي",
            "فحص نسيج الثدى", "اختبار وراثي", "الاشعه فوق الصوتية"};
    int[] images = {R.drawable.mammography, R.drawable.mri, R.drawable.breast_biopsy, R.drawable.genetic, R.drawable.ultrasound};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic__tests);

        listView = findViewById(R.id.diadnostic_list);
        // now create an adapter class

        MyAdapter adapter = new MyAdapter(this, description, images);
        listView.setAdapter(adapter);

        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    startActivity(new Intent(getApplicationContext(), mammo_info.class));
                    //Toast.makeText(getApplicationContext(), description[position], Toast.LENGTH_SHORT).show();
                }
                if (position ==  1) {
                    startActivity(new Intent(getApplicationContext(), breast_info.class));
                    //Toast.makeText(getApplicationContext(), description[position], Toast.LENGTH_SHORT).show();
                }
                if (position ==  2) {
                    startActivity(new Intent(getApplicationContext(), biopsy_info.class));
                    //Toast.makeText(getApplicationContext(), description[position], Toast.LENGTH_SHORT).show();
                }
                if (position ==  3) {
                    startActivity(new Intent(getApplicationContext(), genetic_info.class));
                    //Toast.makeText(getApplicationContext(), description[position], Toast.LENGTH_SHORT).show();
                }
                if (position ==  4) {
                    startActivity(new Intent(getApplicationContext(), sound_info.class));
                    //Toast.makeText(getApplicationContext(), description[position], Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        Context context;
        String[] rDescription;
        int[] rImgs;

        MyAdapter (Context c, String[] description, int[] imgs) {
            this.context = c;
            this.rDescription = description;
            this.rImgs = imgs;

        }

        @Override
        public int getCount() {
            return rDescription.length;
        }

        @Override
        public Object getItem(int i) {
            // because of there is no ArrayList ==> 2 ordinary arrays.
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View row = layoutInflater.inflate(R.layout.diagnostic_item, null);
            ImageView images = row.findViewById(R.id.icon);
            TextView myDescription = row.findViewById(R.id.desc);

            // now set our resources on views
            //images.setImageResource(rImgs[position]);
            myDescription.setText(rDescription[position]);
            images.setImageDrawable(getDrawable(rImgs[position]));

            return row;
        }
    }
}
