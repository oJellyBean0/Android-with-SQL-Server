package s213329913.wrap302.nmmu.task6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        final EditText personName = (EditText) findViewById(R.id.addName);
        final EditText personSurname = (EditText) findViewById(R.id.addSurname);
        final EditText personTelephone = (EditText) findViewById(R.id.addTelephone);
        final EditText personCell = (EditText) findViewById(R.id.addCellNumber);

        final Button btnSave = (Button) findViewById(R.id.btnSave);

        Bundle bundle;
        if (((bundle = getIntent().getExtras()) != null) && (bundle.getSerializable("person") != null)) {

            }
         else {
            btnSave.setVisibility(View.VISIBLE);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = String.valueOf(personName.getText());
                    String surname = String.valueOf(personSurname.getText());
                    String phone = String.valueOf(personTelephone.getText());
                    String cell = String.valueOf(personCell.getText());

                    if (name.length() <= 0 || surname.length() <= 0 || phone.length() <= 0 || cell.length() <= 0) {
                        Toast.makeText(AddPersonActivity.this, "Please fill in all fields",
                                Toast.LENGTH_LONG).show();
                    } else {

                        Intent backIntent = new Intent();
                        backIntent.putExtra("newPersonName", name);
                        backIntent.putExtra("newPersonSurname", surname);
                        backIntent.putExtra("newPersonCell", cell);
                        backIntent.putExtra("newPersonPhone", phone);
                        setResult(RESULT_OK, backIntent);
                        finish();
                    }
                }
            });
        }
    }
}
