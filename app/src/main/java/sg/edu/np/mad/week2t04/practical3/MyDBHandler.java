package sg.edu.np.mad.week2t04.practical3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB.db";

    public static final String TABLE_USER = "user";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FOLLOWED = "followed";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the "user" table
        String createTableQuery = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_FOLLOWED + " INTEGER)";

        db.execSQL(createTableQuery);

        // Insert 20 user objects into the "user" table
        Random random = new Random();
        Faker faker = new Faker();

        for (int i = 0; i < 20; i++) {
            String name = faker.name().firstName();
            String description = "Description " + (i + 1);
            int id = i + 1;
            boolean followed = random.nextBoolean();
            int followedValue = followed ? 1 : 0;

            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_DESCRIPTION, description);
            values.put(COLUMN_ID,id);
            values.put(COLUMN_FOLLOWED, followedValue);

            db.insert(TABLE_USER, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create a new table
        onCreate(db);
    }
    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int followedIndex = cursor.getColumnIndex(COLUMN_FOLLOWED);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String description = cursor.getString(descriptionIndex);
            int followedValue = cursor.getInt(followedIndex);
            boolean isFollowed = followedValue == 1;

            User user = new User(name, description, id, isFollowed);
            userList.add(user);
        }

        cursor.close();
        db.close();

        return userList;
    }
    public void updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        values.put(COLUMN_ID,user.getId());
        values.put(COLUMN_FOLLOWED, user.isFollowed() ? 1 : 0);

        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(user.getId())};

        db.update(TABLE_USER, values, whereClause, whereArgs);
        int rowsAffected = db.update(TABLE_USER, values, whereClause, whereArgs);
        Log.d("MyDBHandler", "Rows affected: " + rowsAffected);

        db.close();
    }


}
