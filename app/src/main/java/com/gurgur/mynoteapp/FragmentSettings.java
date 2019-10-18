package com.gurgur.mynoteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentSettings extends Fragment implements View.OnClickListener{
    Button back;

    TextView deleteAll;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        back = view.findViewById(R.id.backbtn);
        back.setOnClickListener(this);
        deleteAll = view.findViewById(R.id.textView4);
        deleteAll.setOnClickListener(this);
        return view;
    }



    public void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backbtn:
                changeFragment(new NoteList());
                break;
            case R.id.textView4:
                 //hepsini sil

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Uyarı!");
                builder.setMessage("Kayıtlı bütün notların silinecek. Silinen notlar geri getirilemez. Silmek istediğine emin misin?");
                builder.setNegativeButton("İptal", null);
                builder.setPositiveButton("Evet, Sil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Database db = new Database(getActivity());
                        db.tabloyu_bosalt();
                        Toast.makeText(getActivity(), "Tüm notlar silindi", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


                break;
        }
    }
}
