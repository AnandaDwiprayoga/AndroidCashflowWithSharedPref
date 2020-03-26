package com.anandadp.cashflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anandadp.cashflow.models.Session;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.et_username);
        editTextPassword = findViewById(R.id.et_password);

        session = Application.getSession();

        if (session.isKeepUsername()){
            editTextUsername.setText(session.getUsername());
        }
    }

    public void handleLogin(View view) {
        if (dataIsValid()){
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            boolean success = session.validate(username, password);
            if (success) {
//                if (session.isKeepUsername()){
                    session.setUsername(username);
//                }

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Snackbar.make(view, "Authentication Failed", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private boolean dataIsValid(){
        if (TextUtils.isEmpty(editTextUsername.getText())){
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(editTextPassword.getText())){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void handleReset(View view) {
        editTextPassword.setText("");
        editTextUsername.setText("");
    }
}
