package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EditAndDeleteInkubasi extends AppCompatActivity {

    private FirebaseDatabase mDatabaseProfile;
    private DatabaseReference myRef, myRef2;
    private ListView mListView;
    private String name;

    private EditText namaInkubasiEditText;
    private EditText jenisUnggasEditText;
    private EditText jumlahTelurEditText;
    private EditText masaInkubasiEditText;
    private EditText masaMembalikTelurEditText;
    private EditText siklusPembalikanTelurEditText;
    private EditText minTempEditText;
    private EditText maxTempEditText;
    private EditText moistEditText;
    private EditText jadwalPertamaEditText;
    private EditText jadwalKeduaEditText;
    private EditText jadwalKetigaEditText;
    private EditText tanggalAwalPembalikanEditText;
    private EditText tanggalAkhirPembalikanEditText;
    private Button buttonEditIncubation;
    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    String namaInkubasi, jenisUnggas;
    int jumlahTelur, masaInkubasi, masaMembalikTelur, siklusPembalikanTelur,
            minTemp, maxTemp, moist;
    int[][] jadwal = new int[3][2];
    int[][] tanggalPembalikan = new int[2][3];
    private Button editProfileButton;
    private Button deleteProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_delete_inkubasi);
        //name = getIntent().getStringExtra("name");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        /*Memanggil EditText dari form XML*/
        namaInkubasiEditText = (EditText) findViewById(R.id.editNamaInkubasiEditText);
        jenisUnggasEditText = (EditText) findViewById(R.id.editJenisUnggasEditText);
        jumlahTelurEditText = (EditText) findViewById(R.id.editJumlahTelurEditText);
        masaInkubasiEditText = (EditText) findViewById(R.id.editMasaInkubasiEditText);
        masaMembalikTelurEditText = (EditText) findViewById(R.id.editMasaMembalikTelurEditText);
        siklusPembalikanTelurEditText = (EditText) findViewById(R.id.editSiklusPembalikanTelurEditText);
        minTempEditText = (EditText) findViewById(R.id.editMinTempEditText);
        maxTempEditText = (EditText) findViewById(R.id.editMaxTempEditText);
        moistEditText = (EditText) findViewById(R.id.editMoistEditText);
        jadwalPertamaEditText = (EditText) findViewById(R.id.editJadwalPertamaEditText);
        jadwalKeduaEditText = (EditText) findViewById(R.id.editJadwalKeduaEditText);
        jadwalKetigaEditText = (EditText) findViewById(R.id.editJadwalKetigaEditText);
        tanggalAwalPembalikanEditText = (EditText) findViewById(R.id.editTanggalAwalPembalikanEditText);
        tanggalAkhirPembalikanEditText = (EditText) findViewById(R.id.editTanggalAkhirPembalikanEditText);
        buttonEditIncubation = (Button) findViewById(R.id.buttonEditIncubation);
        /*Memanggil EditText dari form XML*/

        /*memanggil DatePickerDialog dan TimePickerDialog*/
        jadwalPertama();
        jadwalKedua();
        jadwalKetiga();
        tanggalAwalPembalikanDatepicker();
        tanggalAkhirPembalikanDatepicker();
        /*memanggil DatePickerDialog dan TimePickerDialog*/

        mDatabaseProfile = FirebaseDatabase.getInstance();
        myRef = mDatabaseProfile.getReference().child("CONTROLLING").child("Inkubasi");
        myRef2 = mDatabaseProfile.getReference().child("CONTROLLING").child("atursuhu");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData1(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonEditIncubation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Edit Profile","Anda Yakin Ingin Mengedit Profile Ini?", "cancelEdit", "okEdit");
            }
        });

        /*deleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Delete Profile","Anda Yakin Ingin Menghapus Profile Ini?", "cancelDelete", "okDelete");
            }
        });*/



    }

    private void cancelEdit(){
        toastMessage("Edit Canceled");
    }

    private void cancelDelete(){
        toastMessage("Delete Canceled");
    }

    private void okEdit(){
        try{
            /*Memasukkan data dari form kedalam variable*/
            namaInkubasi = namaInkubasiEditText.getText().toString();
            jenisUnggas = jenisUnggasEditText.getText().toString();
            jumlahTelur = Integer.valueOf(jumlahTelurEditText.getText().toString());
            masaInkubasi = Integer.valueOf(masaInkubasiEditText.getText().toString());
            masaMembalikTelur = Integer.valueOf(masaMembalikTelurEditText.getText().toString());
            siklusPembalikanTelur = Integer.valueOf(siklusPembalikanTelurEditText.getText().toString());
            minTemp = Integer.valueOf(minTempEditText.getText().toString());
            maxTemp = Integer.valueOf(maxTempEditText.getText().toString());
            moist = Integer.valueOf(moistEditText.getText().toString());
            /*Memasukkan data dari form kedalam variable*/

            IncubationData startIncubation = new IncubationData(namaInkubasi, jenisUnggas, jumlahTelur, masaInkubasi, masaMembalikTelur, siklusPembalikanTelur, minTemp, maxTemp, moist, jadwal, tanggalPembalikan);
            myRef.child("Atursuhu").updateChildren(startIncubation.atursuhuMap());
            myRef.child("Inkubasi").updateChildren(startIncubation.inkubasiMap());
            myRef.child("RTC").updateChildren(startIncubation.rtcMap());
            Intent startIntent = new Intent(getApplicationContext(), CompleteIncubation.class);
            startActivity(startIntent);
            toastMessage("Incubation Data Edited");
        } catch (Exception e){

        }
    }

    private void okDelete(){
        try {
            //myRef.child("Profile").child(name).removeValue();
            Intent startIntent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(startIntent);
            toastMessage("Profile Deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param title
     * @param message
     * @param cancelMethod
     * @param okMethod
     */
    public void customDialog(String title, String message, final String cancelMethod, final String okMethod){
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cancelMethod.equals("cancelEdit")){
                            cancelEdit();
                        }
                        else if (cancelMethod.equals("cancelDelete")){
                            cancelDelete();
                        }
                    }
                }
        );

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(okMethod.equals("okEdit")){
                            okEdit();
                        }
                        else if(okMethod.equals("okDelete")){
                            okDelete();
                        }
                    }
                });


        builderSingle.show();
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()){

            namaInkubasi = ds.getValue(IncubationData.class).getNamaInkubasi();
            jenisUnggas = ds.getValue(IncubationData.class).getJenisUnggas();
            jumlahTelur = ds.getValue(IncubationData.class).getJumlahTelur();
            masaInkubasi = ds.getValue(IncubationData.class).getMasaInkubasi();
            masaMembalikTelur = ds.getValue(IncubationData.class).getMasaMembalikTelur();

        }

    }

    private void showData1(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()){

            minTemp = ds.getValue(IncubationData.class).getMinTemp();
            maxTemp = ds.getValue(IncubationData.class).getMaxTemp();
            moist = ds.getValue(IncubationData.class).getMoist();

        }

    }




    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    /*Timepicker Untuk Memasukkan Jadwal Pertama Pemutaran Telur*/
    private void jadwalPertama(){
        jadwalPertamaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(EditAndDeleteInkubasi.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jadwalPertamaEditText.setText(hourOfDay + ":" + minute);
                        jadwal[0][0] = hourOfDay;
                        jadwal[0][1] = minute;
                    }
                }, hour, minute, true);
                tpd.show();
            }
        });
    }

    /*Timepicker Untuk Memasukkan Jadwal Kedua Pemutaran Telur*/
    private void jadwalKedua(){
        jadwalKeduaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(EditAndDeleteInkubasi.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jadwalKeduaEditText.setText(hourOfDay + ":" + minute);
                        jadwal[1][0] = hourOfDay;
                        jadwal[1][1] = minute;
                    }
                }, hour, minute, true);
                tpd.show();
            }
        });
    }

    /*Timepicker Untuk Memasukkan Jadwal Ketiga Pemutaran Telur*/
    private void jadwalKetiga(){
        jadwalKetigaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(EditAndDeleteInkubasi.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jadwalKetigaEditText.setText(hourOfDay + ":" + minute);
                        jadwal[2][0] = hourOfDay;
                        jadwal[2][1] = minute;
                    }
                }, hour, minute, true);
                tpd.show();
            }
        });
    }

    /*DatePickerDialog Untuk Memasukkan Tanggal Awal Pembalikan Telur*/
    private void tanggalAwalPembalikanDatepicker(){
        tanggalAwalPembalikanEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(EditAndDeleteInkubasi.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tanggalAwalPembalikanEditText.setText(dayOfMonth + "/"+month+"/"+year);
                        tanggalPembalikan[0][0]= dayOfMonth;
                        tanggalPembalikan[0][1]= month;
                        tanggalPembalikan[0][2]= year;
                    }
                }, year, month, day);
                dpd.show();
            }
        });
    }

    /*DatePickerDialog Untuk Memasukkan Tanggal Akhir Pembalikan Telur*/
    private void tanggalAkhirPembalikanDatepicker(){
        tanggalAkhirPembalikanEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(EditAndDeleteInkubasi.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tanggalAkhirPembalikanEditText.setText(dayOfMonth + "/"+month+"/"+year);
                        tanggalPembalikan[1][0]= dayOfMonth;
                        tanggalPembalikan[1][1]= month;
                        tanggalPembalikan[1][2]= year;
                    }
                }, year, month, day);
                dpd.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivityForResult(myIntent,0);
        return true;
    }
}
