package com.example.prototipo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class Tela_Login extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button trocasenha, cadastrar, logando;
    private EditText passwordlg, emaillg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        emaillg = findViewById(R.id.email);
        passwordlg = findViewById(R.id.password);
        trocasenha = findViewById(R.id.forgotpass);
        cadastrar = findViewById(R.id.transicaologin);
        logando = findViewById(R.id.loginbtn);

        trocasenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Tela_Login.this, "Falta Implementar", Toast.LENGTH_SHORT).show();
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Tela_Login.this, Tela_Cadastro.class);
                startActivity(i);
                finish();
            }
        });

        logando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emaillg.getText().toString().trim();
                String password = passwordlg.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Tela_Login.this, task -> {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(Tela_Login.this, Tela_Principal.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(Tela_Login.this, "Login Unsuccessful!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    Toast.makeText(Tela_Login.this, "Fill in all the spaces!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
