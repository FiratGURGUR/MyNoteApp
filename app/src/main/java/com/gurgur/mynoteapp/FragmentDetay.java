package com.gurgur.mynoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentDetay extends Fragment {
    EditText baslik,detay;

    HashMap<String, String> not;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detay,container,false);


        baslik = view.findViewById(R.id.notbaslik);
        detay = view.findViewById(R.id.notdetay);


        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            String myValue = bundle.getString("firat", "123");
            Toast.makeText(getActivity(), myValue, Toast.LENGTH_SHORT).show();
            Database db = new Database(getActivity());

            int note_post = Integer.parseInt(myValue);
            // Db bağlantısı oluşturuyoruz. İlk seferde database oluşturulur.
            not = db.kitapDetay(note_post);
            baslik.setText(not.get("not_basligi"));
            detay.setText(not.get("not_icerik"));

        }






        return view;
    }


}
