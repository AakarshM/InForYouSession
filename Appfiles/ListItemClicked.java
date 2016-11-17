package aakarsh.inforyousession;


import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListItemClicked extends AppCompatActivity {

    ArrayList<String> employerDetails = new ArrayList<>();
    TextView employer, place, start, end, date, room;
    Button description;
    String employerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_clicked);
        employer = (TextView)findViewById(R.id.employerLabel);
        employerName = getIntent().getExtras().getString("name");
        place = (TextView) findViewById(R.id.placeLabel);
        start = (TextView) findViewById(R.id.startLabel);
        end = (TextView) findViewById(R.id.endLabel);
        date = (TextView) findViewById(R.id.dateLabel);
        room = (TextView) findViewById(R.id.roomLabel);
        description = (Button) findViewById(R.id.description);
        employerDetails = (ArrayList<String>) getIntent().getSerializableExtra("list");
        System.out.println(employerDetails.toString());
        setDetails(employerDetails, employerName);
        description.setOnClickListener(buttonListener);

    }

    public void setDetails(ArrayList<String> details, String employername) {
        if (details.size() == 0) {
            Toast.makeText(getApplicationContext(), "Either nothing was found for your program within the time frame or you entered an incorrect input.", Toast.LENGTH_LONG).show();

        } else {
            date.setText(details.get(0));
            start.setText(details.get(1));
            end.setText(details.get(2));
            place.setText(details.get(3));
            room.setText(details.get(4));
            employer.setText(employername);
        }
    }

    public View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick (View view){
            descriptionDialog(employerDetails.get(5));

        }};


    public void descriptionDialog(String des){

        new AlertDialog.Builder(this)
                .setTitle("Event Description")
                .setMessage(des)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
}
