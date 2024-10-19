package com.sashtek.madword;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUserName;
    private Button buttonSaveName;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Username
        editTextUserName = findViewById(R.id.editTextUserName);
        buttonSaveName = findViewById(R.id.buttonSaveName);
        sharedPreferences = getSharedPreferences("MADWordPrefs", MODE_PRIVATE);

        String savedName = sharedPreferences.getString("userName", "");
        if (!savedName.isEmpty()) {
            editTextUserName.setText(savedName);
        }

        // Save Username and Navigate to Next View
        buttonSaveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editTextUserName.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", userName);
                editor.apply();

                // Navigate to the GameActivity
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
}
