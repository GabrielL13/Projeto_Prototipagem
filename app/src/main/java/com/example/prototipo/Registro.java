package com.example.prototipo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Registro{

    private String id_registro;
    private String tranca_id;
    private String data;
    private String user_id;
    private String email_user;
    private String user_name;
    private String porta_status;

    public Registro() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data){ this.data = data;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPorta() {
        return porta_status;
    }

    public void setPorta_status(boolean porta) {
        if(porta){
            this.porta_status = "Aberta";
        }else{
            this.porta_status = "Fechada";
        }
    }

    public void setPorta(String porta) {
        this.porta_status = porta;
    }

    public String getId_registro() {
        return id_registro;
    }

    public void setId_registro(String id_registro) {
        this.id_registro = id_registro;
    }

    public String getTranca_id() {
        return tranca_id;
    }

    public void setTranca_id(String tranca_id) {
        this.tranca_id = tranca_id;
    }

    public String salvar() {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference trancaRegistrosRef = databaseReference.child("Registros").child(getTranca_id());
            DatabaseReference novoRegistroRef = trancaRegistrosRef.push();
            setId_registro(novoRegistroRef.getKey());
            novoRegistroRef.setValue(this);
            return "Deu certo";
        } catch (Exception e) {
            String mensagemErro = e.getMessage();
            return mensagemErro;
        }
    }
}
