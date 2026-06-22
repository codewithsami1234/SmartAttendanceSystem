package com.example.trackattendance;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etUsername;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            if (!email.contains("@")) {
                etEmail.setError("Enter valid email");
                return;
            }
            if (username.isEmpty()) {
                etUsername.setError("Enter username");
                return;
            }
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
            // SAVE LOGIN STATE
            getSharedPreferences("login", MODE_PRIVATE)
                    .edit()
                    .putBoolean("loggedIn", true)
                    .apply();
            // GO TO DASHBOARD
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}