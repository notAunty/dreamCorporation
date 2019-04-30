package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class activity_testing_data extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_testing_data);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

//    final View dialogView = View.inflate(activity_testing_data.this, R.layout.activity_testing_data, null);
//    final AlertDialog alertDialog = new AlertDialog.Builder(activity_testing_data.this).create();

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        DatePicker datePicker = view.findViewById(R.id.testing_date);
        TimePicker timePicker = view.findViewById(R.id.testing_time);

        Calendar cal = new GregorianCalendar(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                timePicker.getHour(),
                timePicker.getMinute());

        long time = cal.getTimeInMillis();

        Snackbar.make(view, datePicker.getYear() +
                        datePicker.getMonth() + datePicker.getDayOfMonth() +
                        timePicker.getHour() + timePicker.getMinute(), Snackbar.LENGTH_LONG).show();
      }});



    //dialogView.findViewById(R.id.testing_date).setOnClickListener();
//    alertDialog.setView(dialogView);
//    alertDialog.show();
  }

}
