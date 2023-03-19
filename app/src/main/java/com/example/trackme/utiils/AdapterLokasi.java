package com.example.trackme.utiils;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackme.R;

import java.util.List;

public class AdapterLokasi extends RecyclerView.Adapter<AdapterLokasi.HolderData> {

    private List<ListLokasiPengguna> listLokasiPenggunas;
    public AdapterLokasi(List<ListLokasiPengguna> listLokasiPenggunas){
        this.listLokasiPenggunas = listLokasiPenggunas;
    }


    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_url_lokasi, parent, false);
        return new HolderData(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ListLokasiPengguna listLokasiPengguna = listLokasiPenggunas.get(position);
        holder.nama_pengirim.setText(listLokasiPengguna.getName());
        holder.url_location.setText(listLokasiPengguna.getUrl());

    }

    @Override
    public int getItemCount() {
        return listLokasiPenggunas.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        public TextView nama_pengirim;
        public TextView url_location;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            nama_pengirim = itemView.findViewById(R.id.nama_pengirim);
            url_location = itemView.findViewById(R.id.url_location);
            url_location.setOnClickListener(view -> {
                Uri uri = Uri.parse(listLokasiPenggunas.get(getAdapterPosition()).getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                itemView.getContext().startActivity(intent);
            });
        }

    }
}
