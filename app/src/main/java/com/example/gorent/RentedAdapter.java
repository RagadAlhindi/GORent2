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

public class RentedAdapter extends RecyclerView.Adapter<RentedAdapter.Holder> {

    public interface OnReturnClicked{
        void onReturnClickedListener(int pos);
    }


    private Context context;
    private ArrayList model,  type, photo , id;

    private RentedAdapter.OnReturnClicked listener;

    ArrayList<VehicleModel> vehicle;

    public RentedAdapter(Context context, ArrayList model, ArrayList type,ArrayList id, ArrayList photo) {
        this.context = context;
        this.model = model;
        this.type=type;
        this.id=id;
        this.photo=photo;


    }

    public void SetBtn(RentedAdapter.OnReturnClicked onReturnClicked){
        listener = onReturnClicked;
    }


    @NonNull
    @Override
    public RentedAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.displayrented, parent, false);
        return new RentedAdapter.Holder(v , listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RentedAdapter.Holder holder, int position) {

        holder.model.setText(String.valueOf(model.get(position)));
        holder.type.setText(String.valueOf(type.get(position)));
        byte [] convert = (byte[]) photo.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(convert,0,convert.length);
        holder.photo.setImageBitmap(bitmap);







    }

    @Override
    public int getItemCount() {

        return model.size();
    }
    public class Holder extends RecyclerView.ViewHolder {
        TextView model, type;
        ImageView photo;

        Button returnbtn;

        public Holder(@NonNull View itemView, RentedAdapter.OnReturnClicked onReturnClicked) {

            super(itemView);

            model = itemView.findViewById(R.id.Textmodelreturn);
            type= itemView.findViewById(R.id.TextTypereturn);
            photo = itemView.findViewById(R.id.Vimagereturn);
            returnbtn =itemView.findViewById(R.id.btnreturn);



            returnbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onReturnClickedListener(getAdapterPosition());
                }
            });

        }
    }





}
