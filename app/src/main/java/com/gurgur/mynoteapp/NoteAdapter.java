package com.gurgur.mynoteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
   static public List<NoteModel> user_listfiltered;
    private Context context;
    private CustomItemClickListener listener;
    NoteAdapter(List<NoteModel> user_list, Context context,CustomItemClickListener listener){
        this.user_list = user_list;
        this.user_listfiltered = user_list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vr = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_note_list_item,parent,false);
        final ViewHolder view_holder = new ViewHolder(vr);

        vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,view_holder.getPosition());
            }
        });

        return view_holder;
    }



    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    user_listfiltered = user_list;
                } else {
                    List<NoteModel> filteredList = new ArrayList<>();
                    for (NoteModel row : user_list) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNoteBaslik().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    user_listfiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = user_list;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                user_list = (ArrayList<NoteModel>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }






    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.baslik.setText(user_listfiltered.get(position).getNoteBaslik());
    }

    @Override
    public int getItemCount() {
        return user_listfiltered.size();
    }




}
