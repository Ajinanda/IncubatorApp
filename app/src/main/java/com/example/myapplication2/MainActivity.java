package com.example.myapplication2;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

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
 * <h1>Layar Inkubasi/Layar Utama</h1>
 * Layar ini dibuat sebagai tampilan utama dari aplikasi android Inkubator telur.
 * <p>
 * Layar ini berguna untuk menampilkan tampilan Inkubasi dimana pengguna
 * bisa melihat proses inkubasi yang sedang berlangsung dan dapat pula
 * digunakan untuk memulai proses inkubasi baru.
 *
 * @author Ajinanda Nugraha Putra
 * @version 0.1 (alpha)
 * @since 2019-5-20
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabaseInkubasi;
    private FirebaseRecyclerAdapter mAdapter;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Inkubasi");

        mDatabaseInkubasi = FirebaseDatabase.getInstance()
                .getReference()
                .child("Inkubasi");
        mDatabaseInkubasi.keepSynced(true);

        mRecyclerView=(RecyclerView)findViewById(R.id.inkubasiRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*CardView sebagai tombol untuk membuka tampilan incubation form*/
        CardView addIncubationCardView = (CardView) findViewById(R.id.addIncubationCardView);
        addIncubationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent startIntent = new Intent(getApplicationContext(), IncubationForm.class);
                    startActivity(startIntent);
                } catch (Exception e) {

                }
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
        fetch();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    private void fetch(){
        FirebaseRecyclerOptions<IncubationData> options =
                new FirebaseRecyclerOptions.Builder<IncubationData>()
                        .setQuery(mDatabaseInkubasi, IncubationData.class)
                        .build();
        mAdapter = new FirebaseRecyclerAdapter<IncubationData, MainActivity.IncubationViewHolder>(options) {
            @NonNull
            @Override
            public MainActivity.IncubationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.added_incubation, parent, false);

                return new MainActivity.IncubationViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(@NonNull MainActivity.IncubationViewHolder incubationViewHolder, final int position, @NonNull final IncubationData incubationData) {
                location = mAdapter.getRef(position).getKey();
                incubationViewHolder.setNamaInkubasi(incubationData.getNamaInkubasi());
                incubationViewHolder.setTanggalInkubasi(incubationData.getTanggalInkubasi());
                incubationViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("W4K","Click-"+position);
                        Intent startIntent = new Intent(getApplicationContext(), CompleteIncubation.class);
                        startIntent.putExtra("namaInkubasi", incubationData.getNamaInkubasi());
                        startActivity(startIntent);

                    }
                });
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    public static class IncubationViewHolder extends RecyclerView.ViewHolder{
        public View mView;
        CardView cardView;

        public IncubationViewHolder(View itemView){
            super(itemView);
            mView=(itemView);
            this.mView = mView;
            this.cardView= (CardView) mView.findViewById(R.id.incubatedEggCardView);
        }

        public void setNamaInkubasi(String namaInkubasi){
            TextView post_nama = (TextView) mView.findViewById(R.id.namaInkubasiTextView);
            post_nama.setText(namaInkubasi);
        }

        public void setTanggalInkubasi(String tanggalInkubasi){
            TextView post_Temp = (TextView) mView.findViewById(R.id.tanggalInkubasiTextView);
            post_Temp.setText(tanggalInkubasi);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
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
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent completeIncubation = new Intent(getApplicationContext(), CompleteIncubation.class);
            startActivity(completeIncubation);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
