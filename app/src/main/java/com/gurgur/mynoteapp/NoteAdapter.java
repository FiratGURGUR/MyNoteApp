package com.gurgur.mynoteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView baslik;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            baslik = itemView.findViewById(R.id.txtbaslik);



        }
    }

    private List<NoteModel> user_list;
    private Context context;

    NoteAdapter(List<NoteModel> user_list, Context context){
        this.user_list = user_list;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vr = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_note_list_item,parent,false);
        final NoteAdapter.ViewHolder view_holder = new NoteAdapter.ViewHolder(vr);



        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {

        holder.baslik.setText(user_list.get(position).getNoteBaslik());
    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }




}
