package com.example.breast_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.breast_app.new_amin.HistoryOfExamination;
import com.example.breast_app.new_amin.NewExamination;
import com.example.breast_app.new_amin.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    private List<MovieModel> movieList = new ArrayList<>();
    private List<MovieModel> blogList = new ArrayList<>();
    private MoviesAdapter mAdapter;
    private MoviesAdapter blogAdapter;
    private ListView listView;
    private String description[] = {"الفحص الذاتى للثدى","فحص جديد","تقاريرى"};
    int images[] = {  R.drawable.breast_lump,R.drawable.check_risk_1,R.drawable.my_reports };

    // mohamed amin code .....
    GoogleSignInClient mGoogleSignInClient;
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this,"Signed out!",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // mohamed amin code .....
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // mohamed gamal code .....
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new MoviesAdapter(movieList, false, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareMovieData();

        // mohamed gamal code .....
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        blogAdapter = new MoviesAdapter(blogList, true, this);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(blogAdapter);
        prepareMovieData2();



        listView = findViewById(R.id.listView);
        // now create an adapter class

        MyAdapter adapter = new MyAdapter(this, description, images);
        listView.setAdapter(adapter);

        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (position ==  0) {
                    startActivity(new Intent(getApplicationContext(), self.class));
                    //startActivity(new Intent(getApplicationContext(), self_mid.class));
                }
                if (position ==  1) {
                    startActivity(new Intent(getApplicationContext(), NewExamination.class));
                    //startActivity(new Intent(getApplicationContext(), self_mid.class));
                }
                if (position ==  2) {
                    startActivity(new Intent(getApplicationContext(), HistoryOfExamination.class));
                    //startActivity(new Intent(getApplicationContext(), self_mid.class));
                }


            }
        });
        // so item click is done now check list view
    }

    private void prepareMovieData() {
        Drawable[] images = {
                getDrawable(R.drawable.video_know), getDrawable(R.drawable.video_reason)
                , getDrawable(R.drawable.video_facts), getDrawable(R.drawable.video_rights)
        };
        MovieModel movie;
        movie = new MovieModel("تعرفى على سرطان الثدى", "Dream TV Egypt", images[0]);
        movieList.add(movie);
        movie = new MovieModel("أسباب الإصابة بمرض سرطان الثدي | هي وبس", " برنامج هي وبس", images[1]);
        movieList.add(movie);
        movie = new MovieModel("حقائق عن سرطان الثدي", "Riyad Bank بنك الرياض", images[2]);
        movieList.add(movie);
        movie = new MovieModel("ست الحسن - حق الحمل والإنجاب لمريضة \"سرطان الثدي\" .. أ. د. محمد العشري", "ON", images[3]);
        movieList.add(movie);
        mAdapter.notifyDataSetChanged();
    }

    private void prepareMovieData2() {
        String[] articals = {"سرطان الثدي (Breast Cancer) هو مرض يصيب النساء بالتحديد، لكنه قد يصيب الرجال أيضا، وإن كان بنسبة أقل بكثير ..."
                , "الوعي واليقظة للأعراض والعلامات المبكرة من سرطان الثدي يمكن أن ينقذا حياتك.\n" +
                "\n" +
                "فحين يتم الكشف عن المرض في مراحله الأولية المبكرة ..."
                , "سرطان الثدي يعني أن عددا من خلايا الثدي بدأت تتكاثر بشكل غير طبيعي. هذه الخلايا تنقسم بسرعة أكبر من الخلايا السليمة ويمكن أن تبدأ في الانتشار (نقيلات - Metastasis) في جميع أنحاء نسيج الثدي، إلى داخل الغدد الليمفاوية ..."
                , "اذا لاحظتِ وجود كتلة أو تغيّر، أيا كان، في ثديك - حتى لو كانت نتيجة التصوير الشعاعي الأخير للثدي (ماموغرافيا - Mammography) سليمة - عليك الاتصال بالطبيب لتقييم الوضع ..."
                , "إن إبلاغك بأنه قد تم تشخيص إصابتك بمرض سرطان الثدي هو من التجارب الأكثر صعوبة التي يمكن للإنسان أن يواجهها. فبالإضافة إلى مواجهة مرض يشكل خطرا حياتيا، عليك اتخاذ قرارات بشأن برنامج علاجي غير سهل، على الإطلاق ..."
                , "لا شيء يمكنه أن يضمن عدم الإصابة سرطان الثدي، ولكن هناك العديد من الخطوات التي يمكن اتخاذها للحد من خطر الإصابة بسرطان الثدي ..."};

        String[] headers = {"ما هو سرطان الثدي", "أعراض سرطان الثدي", "أسباب وعوامل خطر سرطان الثدي"
                , "تشخيص سرطان الثدي", "علاج سرطان الثدي", "الوقاية من سرطان الثدي"};

        Drawable[] images = {
                  getDrawable(R.drawable.what), getDrawable(R.drawable.appears)
                , getDrawable(R.drawable.reasons), getDrawable(R.drawable.examination)
                , getDrawable(R.drawable.treatment), getDrawable(R.drawable.save)
        };
        Drawable topImage = getDrawable(R.drawable.with_photo);
        MovieModel movie;
        movie = new MovieModel(headers[0], articals[0], images[0], topImage);
        blogList.add(movie);
        movie = new MovieModel(headers[1], articals[1], images[1], topImage);
        blogList.add(movie);
        movie = new MovieModel(headers[2], articals[2], images[2], topImage);
        blogList.add(movie);
        movie = new MovieModel(headers[3], articals[3], images[3], topImage);
        blogList.add(movie);
        movie = new MovieModel(headers[4], articals[4], images[4], topImage);
        blogList.add(movie);
        movie = new MovieModel(headers[5], articals[5], images[5], topImage);
        blogList.add(movie);
        blogAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, com.example.breast_app.new_amin.MainActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // close all application completely
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


       if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            startActivity(intent);
        } else if (id == R.id.nav_tools3) {
            //signOut();
             FirebaseAuth.getInstance().signOut();
             mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {

                 }
             });
             finish();
             Intent intent =new Intent(MainActivity.this, com.example.breast_app.new_amin.MainActivity.class);
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(intent);
            Intent logoutIntent = new Intent(this, com.example.breast_app.new_amin.MainActivity.class);
            startActivity(logoutIntent);
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(getApplicationContext(), Diagnostic_Tests.class);
            startActivity(intent);

        } else if (id == R.id.nav_send2) {
            Intent intent = new Intent(getApplicationContext(), Who_is_at_high_risk.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void steps_activity(View view) {
        startActivity(new Intent(this, foot_steps.class));
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
            View row = layoutInflater.inflate(R.layout.row, null);
            ImageView images = row.findViewById(R.id.imageView2);
            TextView myDescription = row.findViewById(R.id.textView2);

            // now set our resources on views
            images.setImageResource(rImgs[position]);
            myDescription.setText(rDescription[position]);

            return row;
        }
    }

}
