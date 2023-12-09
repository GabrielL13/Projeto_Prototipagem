package com.example.prototipo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fechadura {
    private String nome;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String password;
    private Boolean porta;

    Fechadura() {
        porta = Boolean.FALSE;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void salvar() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Trancas").child(getId()).setValue(this);
    }

    public Boolean getPorta() {
        return porta;
    }

    public void setPorta(Boolean porta) {
        this.porta = porta;
    }

    public String getPassword() {
        return this.password;
    }

}
