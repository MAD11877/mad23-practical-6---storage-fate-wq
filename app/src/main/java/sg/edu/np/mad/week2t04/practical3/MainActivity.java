package sg.edu.np.mad.week2t04.practical3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView descriptionTextView;
    private Button followButton;
    private Button messageButton;
    private User user;
    private MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new MyDBHandler(this);
        // Retrieve the user data from the intent extras
        user = getIntent().getParcelableExtra("user");
        int randomNumber = getIntent().getIntExtra("randomNumber", 0);


        nameTextView = findViewById(R.id.textView);
        descriptionTextView = findViewById(R.id.lorem_ipsum_textview);
        followButton = findViewById(R.id.btn2);
        messageButton = findViewById(R.id.btn3);

        // Set the name, description, and ID in the UI elements
        nameTextView.setText(user.getName() + "-" + randomNumber);
        descriptionTextView.setText(user.getDescription());

        // Set the text and click listener for the Follow button
        updateFollowButton();

        // Set click listener for the Message button
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessageGroup.class);
                startActivity(intent);
            }
        });
    }

    private void updateFollowButton() {
        if (user.isFollowed()) {
            followButton.setText("Unfollow");
        } else {
            followButton.setText("Follow");
        }

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the value of the followed variable
                user.setFollowed(!user.isFollowed());

                // Update the text and toast message based on the value of the followed variable
                updateFollowButton();
                dbHandler.updateUser(user);
                if (user.isFollowed()) {
                    Toast.makeText(MainActivity.this, "Followed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unfollowed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
