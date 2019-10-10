package com.gurgur.mynoteapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NoteList extends Fragment {
    Button openAddFragment;
    ImageView noDataimage;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    List<NoteModel> NoteModelList;


    ArrayList<HashMap<String, String>> not_liste;
    String note_basliklar[];
    String note_icerik[];
    String note_id[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notelist,container,false);
        noDataimage = view.findViewById(R.id.imageView4);
        openAddFragment = view.findViewById(R.id.addNotebtn);
        openAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new FragmentNoteAdd());
            }
        });


        recyclerView = view.findViewById(R.id.recyle_note_list);
        NoteModelList = new ArrayList<>();


        Database db = new Database(getActivity()); // Db bağlantısı oluşturuyoruz. İlk seferde database oluşturulur.
        not_liste = db.kitaplar();


        if(not_liste.size()==0){//kitap listesi boşsa
            noDataimage.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Henüz Not Eklenmemiş.\nYukarıdaki + Butonundan Ekleyiniz", Toast.LENGTH_LONG).show();
        }else{
            noDataimage.setVisibility(View.GONE);
            note_basliklar = new String[not_liste.size()];
            note_icerik = new String[not_liste.size()];
            note_id = new String[not_liste.size()];
            for(int i=0;i<not_liste.size();i++){
                note_basliklar[i] = not_liste.get(i).get("not_basligi");
                NoteModelList.add(new NoteModel(not_liste.get(i).get("not_basligi"),not_liste.get(i).get("not_icerik"),not_liste.get(i).get("not_id")));
            }
        }

        noteAdapter =new NoteAdapter(NoteModelList, getActivity(), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                String value = NoteModelList.get(position).getNoteid();

                FragmentDetay fragment = new FragmentDetay ();
                Bundle args = new Bundle();
                args.putString("position", value);
                fragment.setArguments(args);

              changeFragment(fragment);

            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);

        Collections.reverse(NoteModelList);//en son eklenen not ilk basta gostermek icin


        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }


    public void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }


}
