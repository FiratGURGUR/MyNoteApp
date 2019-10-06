package com.gurgur.mynoteapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteList extends Fragment {
    Button openAddFragment;

    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    List<NoteModel> NoteModelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notelist,container,false);

        openAddFragment = view.findViewById(R.id.addNotebtn);
        openAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new FragmentNoteAdd());
            }
        });


        recyclerView = view.findViewById(R.id.recyle_note_list);
        NoteModelList = new ArrayList<>();

        NoteModelList.add(new NoteModel("Bugün yapılacaklar","icerik1"));
        NoteModelList.add(new NoteModel("Salı günü yapılacaklar","icerik1"));
        NoteModelList.add(new NoteModel("İzlemem gereken filmler","icerik1"));
        NoteModelList.add(new NoteModel("Alışveriş listesi","icerik1"));

        noteAdapter =new NoteAdapter(NoteModelList,getActivity());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);

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
