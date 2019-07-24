package com.example.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    public String namaInkubasi, jenisUnggas, tanggalInkubasi;
    public long jumlahTelur, masaInkubasi, masaMembalikTelur, siklusPembalikanTelur,
            minTemp, maxTemp, moist;
    public long[][] jadwal = new long[3][2];
    public long[][] tanggalPembalikan = new long[2][3];
    LayoutInflater inflter;
    String[] data;
    String[] labelData;

    public CustomAdapter(Context context, String[] data, String[] labelData) {
        this.context = context;
        this.data = data;
        this.labelData = labelData;
        inflter = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return labelData.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        view =inflter.inflate(R.layout.list_view,null);
        TextView d = (TextView) view.findViewById(R.id.dataTextView);
        TextView ld = (TextView) view.findViewById(R.id.nameDataTextView);
        d.setText(data[i]);
        ld.setText(labelData[i]);
        return view;
    }
}
