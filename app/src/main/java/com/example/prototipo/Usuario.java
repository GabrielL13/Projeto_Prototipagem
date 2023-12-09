package com.example.prototipo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String email;
    private String ID;
    private String username;
    private String senha;
    private List<String> id_trancas;

    public Usuario(){
        id_trancas = new ArrayList<>();
    }
    public String getEmail() {
        return email;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSenha() {
        return senha;
    }

    public String getUsername() {
        return username;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void salvar(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Usuarios").child(getID()).setValue(this);
    }

    public void adicionarTranca(String id_tranca) {
        if (!id_trancas.contains(id_tranca)) {
            id_trancas.add(id_tranca);
        }
    }

    public List<String> getTrancas(){ return this.id_trancas;}

}