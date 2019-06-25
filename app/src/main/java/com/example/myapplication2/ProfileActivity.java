package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <h1>Profile Unggas</h1>
 * Layar ini berguna untuk menampilkan tampilan profil unggas dimana pengguna
 * bisa melihat profil unggas yang tersedia dan dapat pula
 * digunakan untuk menambah profil unggas baru.
 *
 * @author Ajinanda Nugraha Putra
 * @version 0.1 (alpha)
 * @since 2019-5-27
 */

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mProfileDataList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Profile Unggas");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile");
        mDatabase.keepSynced(true);

        mProfileDataList=(RecyclerView)findViewById(R.id.profileRecyclerView);
        //mProfileDataList.setHasFixedSize(true);
        mProfileDataList.setLayoutManager(new LinearLayoutManager(this));

        /*CardView sebagai tombol untuk membuka tampilan incubation form*/
        CardView addProfileCardView = (CardView) findViewById(R.id.addProfileCardView);
        addProfileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ProfileForm.class);
                startActivity(startIntent);
            }
        });
        /*CardView*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<ProfileData> options =
                new FirebaseRecyclerOptions.Builder<ProfileData>()
                .setQuery(mDatabase, ProfileData.class)
                .build();
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<ProfileData, ProfileViewHolder>(options) {
            @NonNull
            @Override
            public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.added_profile, parent, false);

                return new ProfileViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(@NonNull ProfileViewHolder profileViewHolder, int i, @NonNull ProfileData profileData) {
                profileViewHolder.setNama(profileData.getNama());
                profileViewHolder.setMinTemp(profileData.getMinTemp());
                profileViewHolder.setMaxTemp(profileData.getMaxTemp());
                profileViewHolder.setMoist(profileData.getMinMoist());
                profileViewHolder.setTimeIncubation(profileData.getTimeIncubation());
            }
        };
        mProfileDataList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public ProfileViewHolder(View itemView){
            super(itemView);
            mView=(itemView);
        }

        public void setNama(String name){
            TextView post_nama = (TextView) mView.findViewById(R.id.namaInkubasiTextView);
            post_nama.setText(name);
        }

        public void setMinTemp(int minTemp){
            TextView post_Temp = (TextView) mView.findViewById(R.id.tempInkubatorTextView);
            post_Temp.setText(minTemp+" C");
        }

        public void setMaxTemp(int maxTemp){
            TextView post_Temp = (TextView) mView.findViewById(R.id.maxTempTextView);
            post_Temp.setText(maxTemp+" C");
        }

        public void setMoist(int minMoist){
            TextView post_minMoist = (TextView) mView.findViewById(R.id.moistInkubatorTextView);
            post_minMoist.setText(minMoist+" %");
        }

        public void setTimeIncubation(int timeIncubation){
            TextView post_timeIncubation = (TextView) mView.findViewById(R.id.incubationTimeTextView);
            post_timeIncubation.setText(timeIncubation+" Days");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_incubation) {
            // Handle the camera action
            Intent incubationScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(incubationScreen);
        } else if (id == R.id.nav_profile) {
            Intent profileScreen = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(profileScreen);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
