package com.gurgur.mynoteapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class NoteList extends Fragment {
    Button openAddFragment;
    Button settingsFragment;
    ImageView noDataimage;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    List<NoteModel> NoteModelList;

    EditText NotAraEdt;

    ArrayList<HashMap<String, String>> not_liste;
    String note_basliklar[];
    String note_icerik[];
    String note_tarih[];
    String note_id[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notelist,container,false);

        NotAraEdt = view.findViewById(R.id.edtNotAra);

        noDataimage = view.findViewById(R.id.imageView4);
        openAddFragment = view.findViewById(R.id.addNotebtn);
        settingsFragment = view.findViewById(R.id.settings);
        openAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new FragmentNoteAdd());
            }
        });
        settingsFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new FragmentSettings());
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
            note_tarih = new String[not_liste.size()];
            note_id = new String[not_liste.size()];
            for(int i=0;i<not_liste.size();i++){
                note_basliklar[i] = not_liste.get(i).get("not_basligi");
                NoteModelList.add(new NoteModel(not_liste.get(i).get("not_basligi"),not_liste.get(i).get("not_icerik"),not_liste.get(i).get("not_id"),not_liste.get(i).get("not_tarih")));
            }

        }

        noteAdapter =new NoteAdapter(NoteModelList, getActivity(), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                NoteModelList = NoteAdapter.user_listfiltered;
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





        NotAraEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                noteAdapter.getFilter().filter(s.toString());


            }
        });



        return view;
    }


    public void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }


}
