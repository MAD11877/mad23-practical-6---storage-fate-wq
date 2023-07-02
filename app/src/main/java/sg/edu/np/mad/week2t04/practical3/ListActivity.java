package sg.edu.np.mad.week2t04.practical3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the user adapter
        userAdapter = new UserAdapter();
        recyclerView.setAdapter(userAdapter);

        // Set item click listener
        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                User user = userAdapter.getUser(position);
                int randomNumber = userAdapter.getRandomNumber(user);

                // Show AlertDialog with name
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("Profile");
                builder.setMessage(user.getName());
                builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Launch MainActivity and pass the user data and position
                        Intent intent = new Intent(ListActivity.this, MainActivity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("randomNumber", randomNumber);
                        startActivity(intent);


                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // Fetch user data from the database
        MyDBHandler dbHandler = new MyDBHandler(this);
        List<User> userList = dbHandler.getUsers();
        userAdapter.setUserList(userList);
    }

}
