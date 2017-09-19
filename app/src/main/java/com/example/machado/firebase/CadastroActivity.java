package com.example.machado.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static android.R.attr.tag;

public class CadastroActivity extends AppCompatActivity {

    private static final String TAG = "Cadastro Activity";

    private TextWatcher cpfMask;

    Pessoa pessoa;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Pessoas");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        final EditText cpf = (EditText) findViewById(R.id.cpf_edit_text_cad);
        cpfMask = Mask.insert(cpf);
        cpf.addTextChangedListener(cpfMask);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        TextView login = (TextView) findViewById(R.id.login_text_view_intent);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void cadastrar(View view) {
        EditText text = (EditText) findViewById(R.id.e_mail_edit_text_cad);
        String e_mail = text.getText().toString();

        text = (EditText) findViewById(R.id.nome_edit_text_cad);
        String nome = text.getText().toString();

        text = (EditText) findViewById(R.id.cpf_edit_text_cad);
        String cpf = text.getText().toString();

        text = (EditText) findViewById(R.id.password_edit_text_cad);
        String password = text.getText().toString();

        pessoa = new Pessoa(nome, cpf, e_mail);
        
        //::
        mAuth.createUserWithEmailAndPassword(e_mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(CadastroActivity.this, "Erro ao tentar cadastrar", Toast.LENGTH_SHORT).show();
                        } else {
                            myRef.push().setValue(pessoa);
                            Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}
