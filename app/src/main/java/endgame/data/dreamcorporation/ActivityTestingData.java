package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityTestingData extends AppCompatActivity {

  private String selectedUserUid;
  private FirebaseAuth mAuth;
  private Spinner adminMode;
  private Encryption encryption = new Encryption();

  private EditText userName;
  private EditText fullName;
  private EditText password;
  private EditText balance;
  private EditText upId;
  private EditText dwId;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_testing_data);

    mAuth = FirebaseAuth.getInstance();

    if (!mAuth.getUid().equals(GetFirebase.getAdminUid())) onBackPressed();

    userName = (EditText) findViewById(R.id.testing_username);
    fullName = (EditText) findViewById(R.id.testing_fullName);
    password = (EditText) findViewById(R.id.testing_pw);
    balance = (EditText) findViewById(R.id.testing_balance);
    upId = (EditText) findViewById(R.id.testing_upline);
    dwId = (EditText) findViewById(R.id.testing_downline);

    Toolbar toolbar = findViewById(R.id.testing_toolbar);
    setSupportActionBar(toolbar);

    //get the spinner from the xml.
    adminMode = findViewById(R.id.testing_mode);
    //create a list of items for the spinner.
    String[] adminList = new String[]{"Create", "Retrieve", "Update", "Delete"};
    //create an adapter to describe how the items are displayed, adapters are used in several places in android.
    //There are multiple variations of this, but this is the basic variant.
    ArrayAdapter<String> adminModeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, adminList);
    //set the spinners adapter to the previously created one.
    adminMode.setAdapter(adminModeAdapter);

    final Spinner usersListSpinner = findViewById(R.id.testing_usersList);
    String[] usersList = GetFirebase.usersUid.toArray(new String[GetFirebase.usersUid.size()]);
//    ArrayAdapter<String> usersListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, usersList);
    ArrayAdapter<String> usersListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
    usersListAdapter.add("Select user");
    usersListAdapter.addAll(usersList);

    usersListSpinner.setAdapter(usersListAdapter);
    usersListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Users thisUser;
        if (position > 0) selectedUserUid = GetFirebase.usersUid.get(position - 1);

        if (position == 0) thisUser = new Users("","", 0);
        else thisUser = GetFirebase.getUsers(GetFirebase.usersUid.get(position - 1)); // TODO check this

        userName.setText(thisUser.getUserName());
        fullName.setText(encryption.decodeDirectly(thisUser.getFullName()));
        encryption = new Encryption();
        balance.setText(String.valueOf(thisUser.getBalance()));
        upId.setText(thisUser.getUplineUid());
        dwId.setText(thisUser.getDownlineUid().toString());
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) { }
    });

//    dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance("Select date and time",
//            "OK", "Cancel"); // Initialize date and time dialog
//
//    // Assign values
//    dateTimeDialogFragment.startAtCalendarView();
//    dateTimeDialogFragment.set24HoursMode(true);
//    dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
//    dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2099, Calendar.DECEMBER, 31).getTime());
//    dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar().getTime());
//    dateTime = new Date();



//    Button dateTimeButton = (Button) findViewById(R.id.testing_dateTime);
//    dateTimeButton.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        // Define new day and month format
//        try {
//          dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
//        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
//          Log.e("dateTime: ", e.getMessage());
//        }
//
//        // Set listener
//        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
//          @Override
//          public void onPositiveButtonClick(Date date) {
//            dateTime = date;
//          }
//
//          @Override
//          public void onNegativeButtonClick(Date date) {}
//        });
//
//        // Show
//        dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");
//      }
//    });

//    final View dialogView = View.inflate(ActivityTestingData.this, R.layout.ActivityTestingData, null);
//    final AlertDialog alertDialog = new AlertDialog.Builder(ActivityTestingData.this).create();

    FloatingActionButton fab = findViewById(R.id.testing_fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        adminMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent,final View view, int position, long id) {

            switch (position) {
              case 0:  //CREATE
                if (password.length() < 6 || userName.length() < 1) {
                  Toast.makeText(view.getContext(), "Password longer a bit can ah??",
                          Toast.LENGTH_SHORT).show();
                } else if (GetFirebase.existUser(userName.toString())) {
                  Toast.makeText(view.getContext(), "Username unavailable",
                          Toast.LENGTH_SHORT).show();
                } else {
                  mAuth.createUserWithEmailAndPassword(userName + "@asdfggfdsa.com", password.toString())
                          .addOnCompleteListener(ActivityTestingData.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                              if (task.isSuccessful()) {
                                // Add to database
                                String tempEncFn = encryption.encode(fullName.toString());
                                GetFirebase.usersRef.child(mAuth.getUid()).child("uN").setValue(userName);
                                GetFirebase.usersRef.child(mAuth.getUid()).child("fN").setValue(tempEncFn);
                                GetFirebase.usersRef.child(mAuth.getUid()).child("b").setValue(0);

                                // Sign in success, update UI with the signed-in user's information
                                Log.d("signup: ", "createUserWithEmail:success");
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        Toast.makeText(view.getContext(), "Signup completed. You may now login.",
//                                Toast.LENGTH_SHORT).show();
                              } else {
                                // If sign in fails, display a message to the user.
                                Log.w("signup: ", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(view.getContext(), "Signup failed.",
                                        Toast.LENGTH_SHORT).show();
//                      updateUI(null);
                              }
                              // ...
                            }
                          });
                }

              case 2: // UPDATE
                GetFirebase.usersRef.child(selectedUserUid).child("uN").setValue(userName);
                GetFirebase.usersRef.child(selectedUserUid).child("fN").setValue(encryption.encode(fullName.toString()));
                GetFirebase.usersRef.child(selectedUserUid).child("b").setValue(Double.parseDouble(balance.toString()));
                GetFirebase.usersRef.child(selectedUserUid).child("upId").setValue(upId);
                encryption = new Encryption();
              case 3: // DELETE
                // TODO
            }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) { }
        });
//        String uid = mAuth.getUid();
//        String username = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_username)).getText().toString();
////        String counter = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_counter)).getText().toString();
//        String fullName = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_fullName)).getText().toString();
//        String pw = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_pw)).getText().toString();
//        String uplineUid = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_upline)).getText().toString();
//        String downlineUid = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_downline)).getText().toString();
//
//        String all = "UID: " + uid + "\nUsername: " + username +
////                "\nCounter: " + counter +
//                "\nFull Name:" + fullName + "\nPassword :" + pw + "\nUpline: " + uplineUid +
//                "\nDownline: " + downlineUid + "\nTimecode: " + dateTime.toString();
//
//        Toast.makeText(view.getContext(), "Details: \n" + all + "\nWill start add to database now", Toast.LENGTH_SHORT).show();
//
//        // Start database adding
//        usersRef.child(uid).child("uN").setValue(username);
//        usersRef.child(uid).child("fN").setValue(fullName);
//        usersRef.child(uid).child("pw").setValue(pw);
//        usersRef.child(uid).child("upId").setValue(uplineUid);
//
//        // Get latest balance to do sum
//        final int[] tempBalance = new int[1];
//        usersRef.child(uid).child("b").addListenerForSingleValueEvent(new ValueEventListener() {
//          @Override
//          public void onDataChange(DataSnapshot dataSnapshot) {
//            tempBalance[0] = Integer.valueOf(dataSnapshot.getValue().toString());
//          }
//
//          @Override
//          public void onCancelled(@NonNull DatabaseError databaseError) {}
//        });
//
//        usersRef.child(uid).child("b").setValue(tempBalance[0] + 50);
//        usersRef.child(uid).child("dwId").child(downlineUid).push();
//
//
////        DatePicker datePicker = (DatePicker) view.findViewById(R.id.testing_date);
////        TimePicker timePicker = (TimePicker) view.findViewById(R.id.testing_time);
////
////        Calendar cal = new GregorianCalendar(datePicker.getYear(),
////                datePicker.getMonth(),
////                datePicker.getDayOfMonth(),
////                timePicker.getHour(),
////                timePicker.getMinute());
////
////        long time = cal.getTimeInMillis();
////        String temp = String.valueOf(datePicker.getYear() +
////                datePicker.getMonth() + datePicker.getDayOfMonth() +
////                timePicker.getHour() + timePicker.getMinute());
////        //SimpleDateFormat();
      }});
//    dialogView.findViewById(R.id.testing_date).setOnClickListener();
//    alertDialog.setView(dialogView);
//    alertDialog.show();
  }

}
