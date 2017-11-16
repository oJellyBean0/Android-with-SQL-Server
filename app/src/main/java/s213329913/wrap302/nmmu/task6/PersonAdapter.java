package s213329913.wrap302.nmmu.task6;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Comparator;
import java.util.List;

/**
 * Created by s2133 on 2017/09/12.
 */

public class PersonAdapter extends ArrayAdapter<Person> {
    public PersonAdapter(@NonNull Context context, @NonNull List<Person> objects) {
        super(context, R.layout.person_layout, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // if this is a new view, then inflate it
        View personView = convertView;

        if (personView == null) {
            // get the inflater that will convert the person_layout.xml file
            // into an actual object (i.e. inflate it)
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // create a view to display the person's info
            personView = inflater.inflate(R.layout.person_layout, parent, false);
        }

        // keep track of person this view is working with
        personView.setTag(getItem(position));

        // get views that will hold data
        TextView txtSurname = (TextView) personView.findViewById(R.id.txtSurname);
        TextView txtFirstname = (TextView) personView.findViewById(R.id.txtName);
        TextView numCell = (TextView) personView.findViewById(R.id.numCell);
        TextView numPhone = (TextView) personView.findViewById(R.id.numPhone);



        // set data fields
        final Person person = getItem(position);
        txtSurname.setText(person.surname);
        txtFirstname.setText(person.name);
        numCell.setText(person.cellphone);
        numPhone.setText(person.telephone);


        // return view to ListView to display
        return personView;
    }

    public void sortContacts(){
        super.sort(new Comparator<Person>() {
            @Override
            public int compare(Person person, Person t1) {
                    return person.name.toUpperCase().compareTo(t1.name.toUpperCase());

            }
        });
    }
}

