package endgame.data.dreamcorporation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class DialogQrScanner extends AppCompatActivity{

  private String tempUpline;
  private ArrayList<String> downlines;
//  SurfaceView qrPreview;


  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
  private DatabaseReference usersRef = mDatabase.getReference("users");
  private DatabaseReference transRef = mDatabase.getReference("transactions");

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dialog_qr_scanner);

    IntentIntegrator integrator = new IntentIntegrator(this);
    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
    integrator.setPrompt("Scan upline's QR Code.");
    integrator.setCameraId(0);
    integrator.setBeepEnabled(true);
    integrator.setBarcodeImageEnabled(false);
    integrator.initiateScan();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCoede, Intent intent) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCoede, intent);

    if (result != null) {
      if (result.getContents() == null) {
        Toast.makeText(this, "Scan cancelled.", Toast.LENGTH_LONG).show();
      } else {
        tempUpline = result.getContents();

        new AlertDialog.Builder(DialogQrScanner.this)
                .setTitle("Pay RM50?")  //TODO make the amount dynamic
                .setMessage("Are you sure you want to pay the membership fee?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                    Balance.calcRev(tempUpline);
                    addUpline();
                    uplineAccountAddDownline();

                    onBackPressed();
                  }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getBaseContext(), "Payment unsuccessful.", Toast.LENGTH_LONG).show();

                    onBackPressed();
                  }
                })
                .show();
      }
    } else {
      super.onActivityResult(requestCode, resultCoede, intent);
    }
  }

  public void addUpline() {
    usersRef.child(mAuth.getUid()).child("upId").setValue(tempUpline);
  }

  public void uplineAccountAddDownline() {
    // Get upline punya downline Array
    usersRef.child(tempUpline).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (!dataSnapshot.child("dwId").exists()) {
          downlines = new ArrayList<String>();
          downlines.add(mAuth.getUid());
        } else {
          downlines = (ArrayList<String>) dataSnapshot.getValue();
          downlines.add(mAuth.getUid());
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {}
    });
    usersRef.child(tempUpline).child("dwId").setValue(downlines);
  }
}
