package com.example.prototipo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tela_Trancas extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button registrar, voltar;
    private EditText identificador, senha, nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_trancas);

        registrar = findViewById(R.id.register);
        voltar = findViewById(R.id.back);
        identificador = findViewById(R.id.id_lock);
        nome = findViewById(R.id.name_lock);
        senha = findViewById(R.id.pass_lock);
        auth = FirebaseAuth.getInstance();

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Tela_Trancas.this, Tela_Principal.class);
                startActivity(i);
                finish();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = identificador.getText().toString().trim();
                String senhaFechadura = senha.getText().toString().trim();
                String nickname = nome.getText().toString().trim();
                String userId = auth.getCurrentUser().getUid();

                if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(senhaFechadura)) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference lockReference = databaseReference.child("Trancas").child(id);

                    lockReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Fechadura fechadura = dataSnapshot.getValue(Fechadura.class);

                                if (fechadura.getPassword().equals(senhaFechadura)) {
                                    DatabaseReference userReference = databaseReference.child("Usuarios").child(userId);

                                    // Verifique se o ID da fechadura já existe no ArrayList id_trancas.
                                    DatabaseReference finalUserReference = userReference;
                                    userReference.child("id_trancas").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot idTrancasSnapshot) {
                                            if (!idTrancasSnapshot.hasChild(id)) {
                                                // O ID da fechadura não existe no ArrayList, adicione-o.
                                                finalUserReference.child("id_trancas").child(id).setValue(true);

                                                Toast.makeText(Tela_Trancas.this, "Fechadura registrada e associada ao usuário", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(Tela_Trancas.this, Tela_Principal.class);
                                                startActivity(i);
                                                finish();
                                            } else {
                                                Toast.makeText(Tela_Trancas.this, "A fechadura já está associada ao usuário", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(Tela_Trancas.this, "Erro ao verificar o ID da fechadura", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(Tela_Trancas.this, "Senha incorreta para a fechadura", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Fechadura novaFechadura = new Fechadura();
                                novaFechadura.setId(id);
                                novaFechadura.setNome(nickname);
                                novaFechadura.setPassword(senhaFechadura);
                                novaFechadura.salvar();
                                // Adicione o ID da fechadura ao ArrayList id_trancas do objeto Usuario.
                                DatabaseReference newUserReference = databaseReference.child("Usuarios").child(userId);
                                newUserReference.child("id_trancas").child(id).setValue(true);

                                Toast.makeText(Tela_Trancas.this, "Fechadura registrada e associada ao usuário", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Tela_Trancas.this, Tela_Principal.class);
                                startActivity(i);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(Tela_Trancas.this, "Erro ao verificar o ID da fechadura", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(Tela_Trancas.this, "Preencha todos os campos!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}