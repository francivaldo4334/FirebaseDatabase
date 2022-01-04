package com.francivaldo.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreatePerfil extends AppCompatActivity {

    private EditText edt_email,edt_senha,edt_confirm_senha,edt_name;
    private Button bt_cadastrar;
    String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_perfil);

        edt_email = findViewById(R.id.edt_email_create_log);
        edt_senha = findViewById(R.id.edt_password_create_log);
        edt_confirm_senha = findViewById(R.id.edt_password_comfirm_create_log);
        edt_name = findViewById(R.id.edt_name_create_log);
        bt_cadastrar = findViewById(R.id.bt_create_perfil);

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _Email,_Senha,_Confirm_senha;
                _Email = edt_email.getText().toString();
                _Senha = edt_senha.getText().toString();
                _Confirm_senha = edt_confirm_senha.getText().toString();
                if(!TextUtils.isEmpty(_Email)&&!TextUtils.isEmpty(_Senha)&&!TextUtils.isEmpty(_Confirm_senha)&&_Senha.equals(_Confirm_senha)){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(_Email, _Senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        SalvarDadosUsuario();

                                        Toast.makeText(CreatePerfil.this, "cadastro realizado com sucesso",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(CreatePerfil.this,MainActivity.class));
                                        finish();
                                    } else {
                                        String erro = task.getException().getMessage();
                                        Toast.makeText(CreatePerfil.this, "" + erro, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void SalvarDadosUsuario() {
        String name = edt_name.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> usuario = new HashMap<>();

        usuario.put("nome",name);

        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        documentReference.set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db","sucesso au salvar os dados ");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_erro","erro au salvar os dados "+e.toString());
            }
        });


    }
}