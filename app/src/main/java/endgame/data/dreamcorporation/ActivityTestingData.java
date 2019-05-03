package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityTestingData extends AppCompatActivity {

  private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
  private DatabaseReference myRef = mDatabase.getReference("users");
  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_testing_data);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    mAuth = FirebaseAuth.getInstance();

//    final View dialogView = View.inflate(ActivityTestingData.this, R.layout.ActivityTestingData, null);
//    final AlertDialog alertDialog = new AlertDialog.Builder(ActivityTestingData.this).create();

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


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
