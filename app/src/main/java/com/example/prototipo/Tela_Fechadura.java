package com.example.prototipo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tela_Fechadura extends AppCompatActivity {

    private Button voltarbt, acionar, obter_registros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_fechadura);
        voltarbt = findViewById(R.id.back);
        acionar = findViewById(R.id.open_close);
        obter_registros = findViewById(R.id.registros);
        Intent intent = getIntent();
        String trancaId = intent.getStringExtra("TrancaId");

        voltarbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Tela_Fechadura.this, Tela_Principal.class);
                startActivity(i);
                finish();
            }
        });

        obter_registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Tela_Fechadura.this, Tela_Registros.class);
                i.putExtra("TrancaId", trancaId);
                startActivity(i);
            }
        });


        acionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trancaId != null) {
                    DatabaseReference trancasReference = FirebaseDatabase.getInstance().getReference().child("Trancas").child(trancaId);
                    trancasReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(Tela_Fechadura.this, "Dados da tranca encontrados", Toast.LENGTH_SHORT).show();
                                Fechadura fechadura = dataSnapshot.getValue(Fechadura.class);
                                if (fechadura != null) {
                                    trancasReference.child("porta").setValue(!fechadura.getPorta(), new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                            Toast.makeText(Tela_Fechadura.this, "Acionando Porta com Sucesso", Toast.LENGTH_SHORT).show();
                                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                            FirebaseUser currentUser = mAuth.getCurrentUser();
                                            if (currentUser != null) {
                                                String userID = currentUser.getUid();
                                                DatabaseReference usuariosRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(userID);
                                                usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot Usersnapshot) {
                                                        if (Usersnapshot.exists()) {
                                                            Usuario user = Usersnapshot.getValue(Usuario.class);
                                                            Registro registro = new Registro();
                                                            registro.setTranca_id(fechadura.getId());
                                                            registro.setData(pegarData());
                                                            registro.setUser_id(user.getID());
                                                            registro.setEmail_user(user.getEmail());
                                                            registro.setUser_name(user.getUsername());
                                                            registro.setPorta_status(!fechadura.getPorta());
                                                            registro.salvar();
                                                            Toast.makeText(Tela_Fechadura.this, "Registro Arquivado", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(Tela_Fechadura.this, "Usuario Vazio", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        Toast.makeText(Tela_Fechadura.this, "Erro no Usuario", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(Tela_Fechadura.this, "Erro no Usuario.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Tela_Fechadura.this, "Erro ao carregar Fechadura.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private String pegarData() {
        Date dataAtual = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formato.format(dataAtual);
    }
}