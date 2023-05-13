
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

public class OfeersAdapter extends RecyclerView.Adapter<OfeersAdapter.Holder> {

    public interface OnBtnClicked{
        void onBtnClickedListener(int pos);
    }



    private Context context;
    private ArrayList model,  type, photo , id;

     private OnBtnClicked listener;

    ArrayList<VehicleModel> vehicle;

    public OfeersAdapter(Context context, ArrayList model, ArrayList type,ArrayList id, ArrayList photo) {
        this.context = context;
        this.model = model;
        this.type=type;
        this.id=id;
        this.photo=photo;


    }
    public void SetBtn(OnBtnClicked onBtnClicked){
        listener = onBtnClicked;
    }




    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.displayuser, parent, false);
        return new Holder(v , listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

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

        Button deletebtn;

        public Holder(@NonNull View itemView,OnBtnClicked onBtnClicked) {
            super(itemView);
            model = itemView.findViewById(R.id.Textmodel2);
            type= itemView.findViewById(R.id.TextType2);
            photo = itemView.findViewById(R.id.Vimage2);
            deletebtn =itemView.findViewById(R.id.btndelete);



            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onBtnClickedListener(getAdapterPosition());
                }
            });

        }
    }
}






