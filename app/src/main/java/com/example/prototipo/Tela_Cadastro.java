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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Tela_Cadastro extends AppCompatActivity {

    private Button logar,cadastrar;
    private EditText emailC,usernameC,passordC1,passwordC2;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        logar = findViewById(R.id.transicaocadastro);
        cadastrar = findViewById(R.id.joinbtn);
        emailC = findViewById(R.id.Email);
        usernameC = findViewById(R.id.Username);
        passordC1 = findViewById(R.id.Password);
        passwordC2 = findViewById(R.id.Password1);
        auth = FirebaseAuth.getInstance();

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Tela_Cadastro.this,Tela_Login.class);
                startActivity(i);
                finish();
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usuario = new Usuario();
                usuario.setEmail(emailC.getText().toString().trim());
                usuario.setUsername(usernameC.getText().toString().trim());
                String senha1 = passordC1.getText().toString().trim();
                String senha2 = passwordC2.getText().toString().trim();
                if(!TextUtils.isEmpty(usuario.getEmail()) && !TextUtils.isEmpty(usuario.getUsername()) && !TextUtils.isEmpty(senha1) && !TextUtils.isEmpty(senha2)){
                    if (senha1.equals(senha2)) {
                        usuario.setSenha(senha1);
                        auth.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Tela_Cadastro.this, "User successfully registered!!", Toast.LENGTH_SHORT).show();
                                    usuario.setID(auth.getUid());
                                    usuario.salvar();
                                    Intent i = new Intent(Tela_Cadastro.this,Tela_Login.class);
                                    startActivity(i);
                                    finish();
                                }else{
                                    Toast.makeText(Tela_Cadastro.this, "User registration error!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Tela_Cadastro.this, "Passwords are not the same.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Tela_Cadastro.this, "Fill in all the spaces!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}