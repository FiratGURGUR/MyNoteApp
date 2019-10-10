package com.gurgur.mynoteapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentDetay extends Fragment implements View.OnClickListener{
    EditText baslik,detay;
    HashMap<String, String> not;


    Button geri;
    Button silbtn;
    Button guncelle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detay,container,false);


        baslik = view.findViewById(R.id.notbaslik);
        detay = view.findViewById(R.id.notdetay);
        geri = view.findViewById(R.id.button);
        geri.setOnClickListener(this);
        silbtn = view.findViewById(R.id.button3);
        silbtn.setOnClickListener(this);
        guncelle = view.findViewById(R.id.button2);
        guncelle.setOnClickListener(this);

        Database db = new Database(getActivity());


        Bundle bundle = this.getArguments();
        if (bundle != null)
        {

            String value = getArguments().getString("position");

            int note_post = Integer.parseInt(value);
            // Db bağlantısı oluşturuyoruz. İlk seferde database oluşturulur.
            not = db.kitapDetay(note_post);
            baslik.setText(not.get("not_basligi"));
            detay.setText(not.get("not_icerik"));

        }






        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.button:
                    changeFragment(new NoteList());
                break;
            case  R.id.button3:
                    showSilDialog();
                break;
            case R.id.button2:
                String value = getArguments().getString("position");
                Database db = new Database(getActivity());
                int note_post = Integer.parseInt(value);
                String yeni_baslik = baslik.getText().toString().trim();
                String yeni_icerik = detay.getText().toString().trim();
                db.notDuzenle(yeni_baslik,yeni_icerik,note_post);
                Toast.makeText(getActivity(), "Not güncellendi", Toast.LENGTH_SHORT).show();
                changeFragment(new NoteList());
                //firat gurgur

                break;
        }
    }







    public void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }




    public void showSilDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.custom_delete_card_view, null);
        Button cancel_ = view.findViewById(R.id.cancelbtn);
        Button exit_ = view.findViewById(R.id.silbtn);
        builder.setView(view);
        final AlertDialog dialogExit = builder.create();
        dialogExit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlpLogin = dialogExit.getWindow().getAttributes();
        wmlpLogin.gravity = Gravity.CENTER;
        dialogExit.show();
        dialogExit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancel_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogExit.cancel();
            }
        });
        exit_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = getArguments().getString("position");
                Database db = new Database(getActivity());
                int note_post = Integer.parseInt(value);
                db.notSil(note_post);
                changeFragment(new NoteList());
                dialogExit.dismiss();
            }
        });
    }

}
