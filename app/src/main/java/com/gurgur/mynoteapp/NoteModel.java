package com.gurgur.mynoteapp;

public class NoteModel {

    private String NoteBaslik;
    private String Noteicerik;
    private String Noteid;
    private String Notedate;

    public NoteModel(String noteBaslik, String noteicerik,String noteid,String Notedate) {
        NoteBaslik = noteBaslik;
        Noteicerik = noteicerik;
        Noteid = noteid;
        Notedate = Notedate;
    }

    public String getNotedate() {
        return Notedate;
    }

    public void setNotedate(String notedate) {
        Notedate = notedate;
    }

    public String getNoteid() {
        return Noteid;
    }

    public void setNoteid(String noteid) {
        Noteid = noteid;
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
