package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ActivityTestingData extends AppCompatActivity {

  private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
  private DatabaseReference usersRef = mDatabase.getReference("users");
  private DatabaseReference transRef = mDatabase.getReference("transaction");
  private SwitchDateTimeDialogFragment dateTimeDialogFragment;
  private Date dateTime;
  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_testing_data);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance("Select date and time",
            "OK", "Cancel"); // Initialize date and time dialog

    // Assign values
    dateTimeDialogFragment.startAtCalendarView();
    dateTimeDialogFragment.set24HoursMode(true);
    dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
    dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2099, Calendar.DECEMBER, 31).getTime());
    dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar().getTime());
    dateTime = new Date();

    mAuth = FirebaseAuth.getInstance();

    Button dateTimeButton = (Button) findViewById(R.id.testing_dateTime);
    dateTimeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Define new day and month format
        try {
          dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
          Log.e("dateTime: ", e.getMessage());
        }

        // Set listener
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
          @Override
          public void onPositiveButtonClick(Date date) {
            dateTime = date;
          }

          @Override
          public void onNegativeButtonClick(Date date) {}
        });

        // Show
        dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");
      }
    });

//    final View dialogView = View.inflate(ActivityTestingData.this, R.layout.ActivityTestingData, null);
//    final AlertDialog alertDialog = new AlertDialog.Builder(ActivityTestingData.this).create();

    FloatingActionButton fab = findViewById(R.id.testing_fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String uid = mAuth.getUid();
        String username = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_username)).getText().toString();
//        String counter = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_counter)).getText().toString();
        String fullName = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_fullName)).getText().toString();
        String pw = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_pw)).getText().toString();
        String uplineUid = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_upline)).getText().toString();
        String downlineUid = ((EditText) ActivityTestingData.this.findViewById(R.id.testing_downline)).getText().toString();

        String all = "UID: " + uid + "\nUsername: " + username +
//                "\nCounter: " + counter +
                "\nFull Name:" + fullName + "\nPassword :" + pw + "\nUpline: " + uplineUid +
                "\nDownline: " + downlineUid + "\nTimecode: " + dateTime.toString();

        Toast.makeText(view.getContext(), "Details: \n" + all + "\nWill start add to database now", Toast.LENGTH_SHORT).show();

        // Start database adding
        usersRef.child(uid).child("uN").setValue(username);
        usersRef.child(uid).child("fN").setValue(fullName);
        usersRef.child(uid).child("pw").setValue(pw);
        usersRef.child(uid).child("upId").setValue(uplineUid);

        // Get latest balance to do sum
        final int[] tempBalance = new int[1];
        usersRef.child(uid).child("b").addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            tempBalance[0] = Integer.valueOf(dataSnapshot.getValue().toString());
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        usersRef.child(uid).child("b").setValue(tempBalance[0] + 50);
        usersRef.child(uid).child("dwId").child(downlineUid).push();


//        DatePicker datePicker = (DatePicker) view.findViewById(R.id.testing_date);
//        TimePicker timePicker = (TimePicker) view.findViewById(R.id.testing_time);
//
//        Calendar cal = new GregorianCalendar(datePicker.getYear(),
//                datePicker.getMonth(),
//                datePicker.getDayOfMonth(),
//                timePicker.getHour(),
//                timePicker.getMinute());
//
//        long time = cal.getTimeInMillis();
//        String temp = String.valueOf(datePicker.getYear() +
//                datePicker.getMonth() + datePicker.getDayOfMonth() +
//                timePicker.getHour() + timePicker.getMinute());
//        //SimpleDateFormat();
      }});
//    dialogView.findViewById(R.id.testing_date).setOnClickListener();
//    alertDialog.setView(dialogView);
//    alertDialog.show();
  }

}
