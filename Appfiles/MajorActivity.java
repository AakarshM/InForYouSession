package aakarsh.inforyousession;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MajorActivity extends AppCompatActivity {

    EditText major;
    Button go;
    String Major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        major = (EditText) findViewById(R.id.editText);
        go = (Button) findViewById(R.id.button);
        go.setOnClickListener(buttonListener);
    }

    public View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View view) {
            Major = major.getText().toString().toLowerCase().trim();
            if (Major.matches(".*\\d+.*")) {
                Toast.makeText(getApplicationContext(), "Error, you've entered a number", Toast.LENGTH_SHORT).show();
                major.setText("");
            } else{
                    Intent goToList = new Intent(getApplicationContext(), ListActivity.class);
                    goToList.putExtra("MajorKey", Major); //DJ Khaled
                    startActivity(goToList);
                }



        }

    };

}
