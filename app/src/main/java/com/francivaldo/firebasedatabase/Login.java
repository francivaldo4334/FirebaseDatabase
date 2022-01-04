package com.francivaldo.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private Button bt_cadastro,bt_login;
    private EditText edt_Email,edt_Senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_cadastro = findViewById(R.id.bt_cadastro);
        bt_login = findViewById(R.id.bt_login);
        edt_Email = findViewById(R.id.edt_email_login);
        edt_Senha = findViewById(R.id.edt_password_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _email,_senha;
                _email = edt_Email.getText().toString();
                _senha = edt_Senha.getText().toString();
                if(!TextUtils.isEmpty(_email) || !TextUtils.isEmpty(_senha)) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(_email, _senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                String erro = task.getException().getMessage();
                                Toast.makeText(Login.this, "" + erro, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        bt_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,CreatePerfil.class);
                startActivity(intent);
                finish();
            }
        });
    }
}