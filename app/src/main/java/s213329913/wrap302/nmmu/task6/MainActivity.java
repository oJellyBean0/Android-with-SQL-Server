package s213329913.wrap302.nmmu.task6;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Person> people = new ArrayList<>();
    PersonAdapter personAdapter;
    public Statement stmt = null;
    Connection con = null;
    public static final int add = 1;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lstPeople = (ListView) findViewById(R.id.lstPeople);
        final Button btnAdd = (Button)findViewById(R.id.btnAdd);
        btnDelete= (Button)findViewById(R.id.btnDelete);
        final Button btnConnect = (Button)findViewById(R.id.btnConnect);
        final Button btnDisconnect = (Button)findViewById(R.id.btnDisconnect);
        final Button btnRefresh = (Button)findViewById(R.id.btnRefresh);

        btnAdd.setEnabled(false);
        btnDelete.setEnabled(false);
        btnDisconnect.setEnabled(false);
        btnRefresh.setEnabled(false);

        //linking contact adapter
        personAdapter = new PersonAdapter(this, people);
        lstPeople.setAdapter(personAdapter);

        btnConnect.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


              Thread thread = new Thread(new Runnable() {

                  @Override
                  public void run() {
                      try  {
                          connectToDB();
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  btnConnect.setEnabled(false);
                                  btnDisconnect.setEnabled(true);
                                  btnAdd.setEnabled(true);
                                  btnDelete.setEnabled(false);
                                  btnRefresh.setEnabled(true);
                              }
                          });
                          showPeople();

                          btnAdd.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                  Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);
                                  //intent.putExtra("", "");
                                  if (intent.resolveActivity(getPackageManager()) != null) {
                                      startActivityForResult(intent, add);
                                  }
                              }
                          });
                          btnRefresh.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                      showPeople();
                              }
                          });
                          btnDisconnect.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  btnConnect.setEnabled(true);
                                  btnDisconnect.setEnabled(false);
                                  btnAdd.setEnabled(false);
                                  btnDelete.setEnabled(false);
                                  disconnectDB();
                              }
                          });


                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }
              });

              thread.start();

          }
          });
        lstPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Person cur = (Person) view.getTag();
                btnDelete.setEnabled(true);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletePerson(cur);

                    }
                });
            }
        });


    }

    private void showPeople() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                final ResultSet resultSet;
                try {

                    resultSet = stmt.executeQuery("SELECT * FROM Person ");
                    //ResultSetMetaData meta = resultSet.getMetaData();

                   // int columns = meta.getColumnCount();


                    System.out.println();
                    System.out.println("\tResult (Surname, Name, Phone, Cell)");
                    // while there are tuples in the result set, display them... using indices
                    int id;
                    String name, surname, cell, phone;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            personAdapter.clear();
                        }
                    });
                    while (resultSet.next()) {
                        // get values from current tuple
                        id = Integer.parseInt(resultSet.getString(1).trim());
                        surname = resultSet.getString(2).trim();
                        name = resultSet.getString(3).trim();
                        phone = resultSet.getString(4).trim();
                        cell  = resultSet.getString(5).trim();

                        people.add(new Person(id, name, surname, cell, phone));

                        System.out.println(name+" "+ surname+" "+ phone+" "+ cell);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            personAdapter.sortContacts();
                            personAdapter.notifyDataSetChanged();
                            btnDelete.setEnabled(false);
                        }
                    });


                }
                catch (SQLException e){
                    System.out.println("Error retrieving query information");
                    e.printStackTrace();
                }}
        });
        thread.start();



    }

    private void connectToDB(){
        Log.i("SQL", "Connecting...");
        String clazz = "net.sourceforge.jtds.jdbc.Driver";
        try {
            Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String connURL = "jdbc:jtds:sqlserver://10.113.0.14;instance=WRR;databaseName=WRAP301;";


        try {
            con = DriverManager.getConnection(connURL, "WRAP301User", "1");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(con != null)
            Log.i("SQL", "Connected...");
        else
            Log.e("SQL", "Error connecting to database...");
    }

    public void disconnectDB() {
        System.out.println("Disconnecting from database...");

        try {
            con.close();
        } catch (Exception ex) {
            System.out.println("   Unable to disconnect from database");
        }
    }

    public void deletePerson(final Person cur){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete Person");
        dialog.setMessage("Are you sure you would like to delete this person?");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletePersonFromDatabase(cur);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void deletePersonFromDatabase(final Person cur){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                int personID = cur.id;
                try {
                    stmt.execute("DELETE FROM Person WHERE  PID =" + personID + ";");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                showPeople();
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        personAdapter.clear();
                        personAdapter.notifyDataSetChanged();
                    }
                });*/

            }
        });

        thread.start();

    }

    public void addPerson(final String name, final String surname, final String cell, final String telephone){

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    String sql = "INSERT INTO Person VALUES ('" + surname + "','" + name + "', '" + telephone + "', '" + cell + "')";
                    stmt.execute(sql);
                } catch (SQLException e) {
                    System.out.println("Error adding person");
                    e.printStackTrace();
                }
                ResultSet resultSet = null;
                int pid = 0;
                try {
                    resultSet = stmt.executeQuery("SELECT PID FROM Person WHERE Name = '" + name + "' AND Surname = '" + surname + "' AND Cell = '" + cell + "' AND Phone = '" + telephone + "'");

                    if (resultSet != null && resultSet.last()) {
                        pid = resultSet.getInt("PID");

                        people.add(new Person(pid, name, surname, cell, telephone));

                        showPeople();
                    }
                } catch (SQLException e) {
                    System.out.println("Query for retrieving PID failed");
                    e.printStackTrace();
                }
            }
            });
            thread.start();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == add) {
                final String name = (String) data.getSerializableExtra("newPersonName");
                final String surname = (String) data.getSerializableExtra("newPersonSurname");
                final String cell = (String) data.getSerializableExtra("newPersonCell");
                final String phone = (String) data.getSerializableExtra("newPersonPhone");
                if (name != null&&surname!=null&&cell!=null&&phone!=null) {
                    addPerson(name, surname, cell, phone);

                }


            }
        }
    }
}
