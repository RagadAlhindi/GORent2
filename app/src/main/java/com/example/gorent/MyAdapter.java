package com.example.gorent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyviewHolder> {

    private Context context;
    private ArrayList model  , rent , photo;

    ArrayList <VehicleModel> vehicle;

    public MyAdapter(Context context, ArrayList model, ArrayList rent , ArrayList photo ) {
        this.context = context;
        this.model = model;
        this.photo=photo;
        this.rent = rent;

    }

    public MyAdapter(Context context, ArrayList vehicle) {
        this.context = context;
        this.vehicle = vehicle;

    }



    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.display,parent,false);
        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        holder.model.setText(String.valueOf(model.get(position)));
        holder.rent.setText(String.valueOf(rent.get(position)));
        byte [] convert = (byte[]) photo.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(convert,0,convert.length);
        holder.photo.setImageBitmap(bitmap);







    }

    @Override
    public int getItemCount() {

        return model.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView model , rent;
        ImageView photo;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            model = itemView.findViewById(R.id.textModel);
            rent = itemView.findViewById(R.id.textPrice);
            photo = itemView.findViewById(R.id.Vimage);

        }
    }
}
