package com.gurgur.mynoteapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class FragmentNoteAdd extends Fragment {
    Button btnGeri;
    Button btnekle;

    String baslik,icerik;
    EditText e_baslik,e_icerik;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note,container,false);

        e_baslik = view.findViewById(R.id.editText2);
        e_icerik = view.findViewById(R.id.editText);

        btnGeri = view.findViewById(R.id.buttongeri);
        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new NoteList());
            }
        });

        btnekle = view.findViewById(R.id.buttonEkle);

        btnekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baslik = e_baslik.getText().toString().trim();
                icerik = e_icerik.getText().toString().trim();

                if (!baslik.equals("") && !icerik.equals("")){
                    Database database = new Database(getActivity());
                    database.notEkle(baslik,icerik);

                    Toast.makeText(getActivity(), "Kayıt işlemi başarılı", Toast.LENGTH_SHORT).show();
                    changeFragment(new NoteList());
                }else {
                    Toast.makeText(getActivity(), "Lütfen alanları doldurunuz", Toast.LENGTH_SHORT).show();
                }



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
