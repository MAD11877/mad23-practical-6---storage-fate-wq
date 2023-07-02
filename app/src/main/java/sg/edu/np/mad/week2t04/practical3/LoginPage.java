package sg.edu.np.mad.week2t04.practical3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        mAuth = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.button);
        populateFirebaseDatabase();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameEditText = findViewById(R.id.editTextText);
                EditText passwordEditText = findViewById(R.id.editTextText2);

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginPage.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    signIn(username, password);
                }
            }
        });
    }
    private void populateFirebaseDatabase() {
        String username = "mad";
        String password = "118777";

        mAuth.createUserWithEmailAndPassword(username + "@gmail.com", password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginPage.this, "Firebase database populated with user credentials", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginPage.this, "Failed to populate Firebase database", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void signIn(String username, String password) {
        mAuth.signInWithEmailAndPassword(username + "@example.com", password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginPage.this, "Login successful! Welcome " + username, Toast.LENGTH_SHORT).show();
                        // Start the ListActivity
                        Intent intent = new Intent(LoginPage.this, ListActivity.class);
                        startActivity(intent);
                    } else {
                        // Login failed
                        Toast.makeText(LoginPage.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
