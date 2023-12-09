package com.example.prototipo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tela_Registros extends AppCompatActivity {

    private ArrayList<Registro> listaRegistros;
    private AdapterRegistro adapter;
    private ListView lista_registros;
    private Button voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_registros);
        Intent intent = getIntent();
        String trancaId = intent.getStringExtra("TrancaId");
        voltar = findViewById(R.id.volta);
        listaRegistros = new ArrayList<>();
        lista_registros = findViewById(R.id.lista1);
        loadRegistros(trancaId);
        adapter = new AdapterRegistro(listaRegistros);
        lista_registros.setAdapter(adapter);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Tela_Registros.this, Tela_Fechadura.class);
                i.putExtra("TrancaId",trancaId);
                startActivity(i);
                finish();
            }
        });

    }
    public void loadRegistros(String trancaId) {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference primeiroNodoRef = database.getReference("Registros");
            DatabaseReference segundoNodoRef = primeiroNodoRef.child(trancaId);
            segundoNodoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        listaRegistros.clear();
                        for (DataSnapshot objetoSnapshot : snapshot.getChildren()) {
                            String id_registro = objetoSnapshot.child("id_registro").getValue(String.class);
                            String tranca_id = objetoSnapshot.child("tranca_id").getValue(String.class);
                            String data = objetoSnapshot.child("data").getValue(String.class);
                            String user_id = objetoSnapshot.child("user_id").getValue(String.class);
                            String email_user = objetoSnapshot.child("email_user").getValue(String.class);
                            String user_name = objetoSnapshot.child("user_name").getValue(String.class);
                            String porta = objetoSnapshot.child("porta").getValue(String.class);
                            Registro registro = new Registro();
                            registro.setData(data);
                            registro.setId_registro(id_registro);
                            registro.setTranca_id(tranca_id);
                            registro.setUser_id(user_id);
                            registro.setUser_name(user_name);
                            registro.setPorta(porta);
                            registro.setEmail_user(email_user);
                            listaRegistros.add(registro);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Tela_Registros.this, "Busca vazia", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Tela_Registros.this,"Erro ao carregar dados do Firebase",Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(Tela_Registros.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}