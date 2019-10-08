package com.gurgur.mynoteapp;

public class NoteModel {

    private String NoteBaslik;
    private String Noteicerik;

    public NoteModel(String noteBaslik, String noteicerik) {
        NoteBaslik = noteBaslik;
        Noteicerik = noteicerik;
    }

    public String getNoteBaslik() {
        return NoteBaslik;
    }

    public void setNoteBaslik(String noteBaslik) {
        NoteBaslik = noteBaslik;
    }

    public String getNoteicerik() {
        return Noteicerik;
    }

    public void setNoteicerik(String noteicerik) {
        Noteicerik = noteicerik;
    }
}
