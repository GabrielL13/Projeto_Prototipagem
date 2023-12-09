package com.example.prototipo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tela_Principal extends AppCompatActivity {
    private Button logout, adicionar;
    private ListView lista_trancas;
    private Adapter adapter;
    private ArrayList<Fechadura> listaDeFechaduras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        logout = findViewById(R.id.logout);
        adicionar = findViewById(R.id.adicionar);
        lista_trancas = findViewById(R.id.lista);
        listaDeFechaduras = new ArrayList<>();
        adapter = new Adapter(listaDeFechaduras);
        lista_trancas.setAdapter(adapter);
        loadTrancasFromFirebase();

        lista_trancas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fechadura fechadura = listaDeFechaduras.get(position);
                Intent i = new Intent(Tela_Principal.this, Tela_Fechadura.class);
                i.putExtra("TrancaId", fechadura.getId());
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(Tela_Principal.this, Tela_Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Tela_Principal.this, Tela_Trancas.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void loadTrancasFromFirebase() {
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userTrancasReference = usersReference.child(userId).child("id_trancas");
        ValueEventListener valueEventListener = userTrancasReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeFechaduras.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String trancaid = snapshot.getKey();
                    if (snapshot.getValue(Boolean.class)) {
                        DatabaseReference trancasReference = FirebaseDatabase.getInstance().getReference().child("Trancas").child(trancaid);
                        trancasReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot trancaSnapshot) {
                                if (trancaSnapshot.exists()) {
                                    Fechadura fechadura = trancaSnapshot.getValue(Fechadura.class);
                                    listaDeFechaduras.add(fechadura);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Tela_Principal.this, "Erro ao buscar as trancas", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Tela_Principal.this, "Erro ao buscar as trancas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
