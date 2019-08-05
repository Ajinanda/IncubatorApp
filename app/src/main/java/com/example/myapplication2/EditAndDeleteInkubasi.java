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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditAndDeleteInkubasi extends AppCompatActivity {

    private FirebaseDatabase mDatabaseProfile;
    private DatabaseReference myRef, myRef2;
    private ListView mListView;
    private String name;

    private EditText namaInkubasiEditText;
    private EditText jumlahTelurEditText;
    private Spinner minTempSpinner;
    private Spinner maxTempSpinner;
    private Spinner moistSpinner;
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
    long jumlahTelur, masaInkubasi, masaMembalikTelur,
            minTemp, maxTemp, moist;
    long[][] jadwal = new long[3][2];
    long[][] tanggalPembalikan = new long[2][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_delete_inkubasi);
        //name = getIntent().getStringExtra("name");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        /*Memanggil EditText dari form XML*/
        namaInkubasiEditText = (EditText) findViewById(R.id.editNamaInkubasiEditText);
        jumlahTelurEditText = (EditText) findViewById(R.id.editJumlahTelurEditText);
        minTempSpinner = (Spinner) findViewById(R.id.editMinTempSpinner);
        maxTempSpinner = (Spinner) findViewById(R.id.editMaxTempSpinner);
        moistSpinner = (Spinner) findViewById(R.id.editMoistSpinner);
        jadwalPertamaEditText = (EditText) findViewById(R.id.editJadwalPertamaEditText);
        jadwalKeduaEditText = (EditText) findViewById(R.id.editJadwalKeduaEditText);
        jadwalKetigaEditText = (EditText) findViewById(R.id.editJadwalKetigaEditText);
        tanggalAwalPembalikanEditText = (EditText) findViewById(R.id.editTanggalAwalPembalikanEditText);
        tanggalAkhirPembalikanEditText = (EditText) findViewById(R.id.editTanggalAkhirPembalikanEditText);
        buttonEditIncubation = (Button) findViewById(R.id.buttonEditIncubation);
        /*Memanggil EditText dari form XML*/

        minTempSpinner.setAdapter(spinnerMinTempValue());
        maxTempSpinner.setAdapter(spinnerMaxTempValue());
        moistSpinner.setAdapter(spinnerMoistValue());

        mDatabaseProfile = FirebaseDatabase.getInstance();
        myRef = mDatabaseProfile.getReference().child("CONTROLLING");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //showData(dataSnapshot);
                namaInkubasi = (String) dataSnapshot.child("Inkubasi").child("namaInkubasi").getValue();
                jumlahTelur = (long) dataSnapshot.child("Inkubasi").child("jumlahTelur").getValue();
                minTemp = (long) dataSnapshot.child("Atursuhu").child("minsuhu").getValue();
                maxTemp = (long) dataSnapshot.child("Atursuhu").child("maxsuhu").getValue();
                moist = (long) dataSnapshot.child("Atursuhu").child("minlembab").getValue();
                jadwal[0][0] = (long) dataSnapshot.child("RTC").child("jadwal1").child("jam").getValue();
                jadwal[0][1] = (long) dataSnapshot.child("RTC").child("jadwal1").child("menit").getValue();
                jadwal[1][0] = (long) dataSnapshot.child("RTC").child("jadwal2").child("jam").getValue();
                jadwal[1][1] = (long) dataSnapshot.child("RTC").child("jadwal2").child("menit").getValue();
                jadwal[2][0] = (long) dataSnapshot.child("RTC").child("jadwal3").child("jam").getValue();
                jadwal[2][1] = (long) dataSnapshot.child("RTC").child("jadwal3").child("menit").getValue();
                tanggalPembalikan[0][0] = (long) dataSnapshot.child("RTC").child("tgl1").child("tanggal").getValue();
                tanggalPembalikan[0][1] = (long) dataSnapshot.child("RTC").child("tgl1").child("bulan").getValue();
                tanggalPembalikan[0][2] = (long) dataSnapshot.child("RTC").child("tgl1").child("tahun").getValue();
                tanggalPembalikan[1][0] = (long) dataSnapshot.child("RTC").child("tgl2").child("tanggal").getValue();
                tanggalPembalikan[1][1] = (long) dataSnapshot.child("RTC").child("tgl2").child("bulan").getValue();
                tanggalPembalikan[1][2] = (long) dataSnapshot.child("RTC").child("tgl2").child("tahun").getValue();

                String jadwal1, jadwal2, jadwal3, tanggalAwal, tanggalAkhir;
                jadwal1 = jadwal[0][0]+":"+jadwal[0][1];
                jadwal2 = jadwal[1][0]+":"+jadwal[1][1];
                jadwal3 = jadwal[2][0]+":"+jadwal[2][1];
                tanggalAwal = tanggalPembalikan[0][0]+"/"+tanggalPembalikan[0][1]+"/"+tanggalPembalikan[0][2];
                tanggalAkhir = tanggalPembalikan[1][0]+"/"+tanggalPembalikan[1][1]+"/"+tanggalPembalikan[1][2];

                int minTempPost = spinnerMinTempValue().getPosition(Integer.valueOf(String.valueOf(minTemp)));
                int maxTempPost = spinnerMaxTempValue().getPosition(Integer.valueOf(String.valueOf(maxTemp)));
                int moistPost = spinnerMoistValue().getPosition(Integer.valueOf(String.valueOf(moist)));


                namaInkubasiEditText.setText(namaInkubasi);
                jumlahTelurEditText.setText(String.valueOf(jumlahTelur));
                minTempSpinner.setSelection(minTempPost);
                maxTempSpinner.setSelection(maxTempPost);
                moistSpinner.setSelection(moistPost);
                jadwalPertamaEditText.setText(jadwal1);
                jadwalKeduaEditText.setText(jadwal2);
                jadwalKetigaEditText.setText(jadwal3);
                tanggalAwalPembalikanEditText.setText(tanggalAwal);
                tanggalAkhirPembalikanEditText.setText(tanggalAkhir);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        /*memanggil DatePickerDialog dan TimePickerDialog*/
        jadwalPertama();
        jadwalKedua();
        jadwalKetiga();
        tanggalAwalPembalikanDatepicker();
        tanggalAkhirPembalikanDatepicker();
        /*memanggil DatePickerDialog dan TimePickerDialog*/

        buttonEditIncubation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Edit Profile","Anda Yakin Ingin Mengedit Profile Ini?", "cancelEdit", "okEdit");
                Log.i("Test1", "onClick: Test On Click Button");
            }
        });

        /*deleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Delete Profile","Anda Yakin Ingin Menghapus Profile Ini?", "cancelDelete", "okDelete");
            }
        });*/



    }

    private ArrayAdapter<Integer> spinnerMinTempValue(){
        final List<Integer> temp = new ArrayList<Integer>();
        for (int i = 25; i <= 39; i++){
            int add = i;
            temp.add(add);
        }
        ArrayAdapter<Integer> tempValueAdapter =
                new ArrayAdapter<Integer>(EditAndDeleteInkubasi.this, android.R.layout.simple_spinner_item, temp);
        tempValueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return tempValueAdapter;
    }

    private ArrayAdapter<Integer> spinnerMaxTempValue(){
        final List<Integer> temp = new ArrayList<Integer>();
        for (int i = 26; i <= 40; i++){
            int add = i;
            temp.add(add);
        }
        ArrayAdapter<Integer> tempValueAdapter =
                new ArrayAdapter<Integer>(EditAndDeleteInkubasi.this, android.R.layout.simple_spinner_item, temp);
        tempValueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return tempValueAdapter;
    }

    private ArrayAdapter<Integer> spinnerMoistValue(){
        final List<Integer> moist = new ArrayList<Integer>();
        for (int i = 40; i <= 90; i+=5){
            int add = i;
            moist.add(add);

        }
        ArrayAdapter<Integer> moistValueAdapter =
                new ArrayAdapter<Integer>(EditAndDeleteInkubasi.this, android.R.layout.simple_spinner_item, moist);
        moistValueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return moistValueAdapter;
    }

    private void cancelEdit(){
        toastMessage("Edit Canceled");
    }

    private void cancelDelete(){
        toastMessage("Delete Canceled");
    }

    private void okEdit(){
        try{

            String a,b,c,d,e,f,g,h,i,j,k,l;
            a=namaInkubasiEditText.getText().toString();
            b="Ayam";
            c=jumlahTelurEditText.getText().toString();
            d=minTempSpinner.getSelectedItem().toString();
            e=maxTempSpinner.getSelectedItem().toString();
            f=moistSpinner.getSelectedItem().toString();
            g=jadwalPertamaEditText.getText().toString();
            h=jadwalKeduaEditText.getText().toString();
            i=jadwalKetigaEditText.getText().toString();
            j=tanggalAwalPembalikanEditText.getText().toString();
            k=tanggalAkhirPembalikanEditText.getText().toString();
            if (a.equals("")||b.equals("")||c.equals("")||d.equals("")||e.equals("")||f.equals("")||g.equals("")||h.equals("")||i.equals("")||j.equals("")||k.equals("")){
                customDialog2("Tidak Dapat Memulai Inkubasi", "Pastikan semua form telah diisi sebelum menekan tombol Edit", "formValidation");
            } else {
                /*Memasukkan data dari form kedalam variable*/
                namaInkubasi = namaInkubasiEditText.getText().toString();
                jenisUnggas = "Ayam";
                jumlahTelur = Long.valueOf(jumlahTelurEditText.getText().toString());
                masaInkubasi = 21L;
                masaMembalikTelur = 18L;
                minTemp = Long.valueOf(minTempSpinner.getSelectedItem().toString());
                maxTemp = Long.valueOf(maxTempSpinner.getSelectedItem().toString());
                moist = Long.valueOf(moistSpinner.getSelectedItem().toString());
                /*Memasukkan data dari form kedalam variable*/

                if (maxTemp <= minTemp){
                    customDialog2("Tidak Dapat Memulai Inkubasi","Max Temperatur tidak boleh kurang dari atau sama dengan Min Temperatur minTemp ="+minTemp+", maxTemp = "+maxTemp,"okValidation");
                }


                Log.i("Test3", "okEdit: Test Getting edittext value into Variable");
                IncubationData startIncubation = new IncubationData(namaInkubasi, jenisUnggas, jumlahTelur, masaInkubasi, masaMembalikTelur, minTemp, maxTemp, moist, jadwal, tanggalPembalikan);
                myRef.child("Atursuhu").updateChildren(startIncubation.atursuhuMap());
                myRef.child("Inkubasi").updateChildren(startIncubation.inkubasiMap());
                myRef.child("RTC").updateChildren(startIncubation.rtcMap());

                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
                toastMessage("Incubation Data Edited");
            }

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

    private void formValidation(){
        toastMessage("Form Tidak Terisi Dengan Benar");
    }

    private void okValidation(){
        toastMessage("Temperatur Salah");
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

    public void customDialog2(String title, String message, final String okMethod){
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);



        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(okMethod.equals("okValidation")){
                            okValidation();
                        }
                    }
                });

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(okMethod.equals("formValidation")){
                            formValidation();
                        }
                    }
                });


        builderSingle.show();
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
                        int m =month+1;
                        tanggalAwalPembalikanEditText.setText(dayOfMonth + "/"+m+"/"+year);
                        tanggalPembalikan[0][0]= dayOfMonth;
                        tanggalPembalikan[0][1]= month+1;
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
                        int m =month+1;
                        tanggalAkhirPembalikanEditText.setText(dayOfMonth + "/"+m+"/"+year);
                        tanggalPembalikan[1][0]= dayOfMonth;
                        tanggalPembalikan[1][1]= month+1;
                        tanggalPembalikan[1][2]= year;
                    }
                }, year, month, day);
                dpd.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), CompleteIncubation.class);
        startActivityForResult(myIntent,0);
        return true;
    }
}
