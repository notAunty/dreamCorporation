package endgame.data.dreamcorporation;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Balance {

  private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private static FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
  private static DatabaseReference usersRef = mDatabase.getReference("users");
  private static DatabaseReference transRef = mDatabase.getReference("transactions");
  private static DatabaseReference adminRef = mDatabase.getReference("admin");
  private static String[] uplines = new String[5];
  private static double[] commission_rate= new double[5];
  private static double[] oldBalance= new double[5];
  private static double first = 0.5;
  private static double second = 0.12 + 0.03;
  private static double fee;

  public static void calcRev() {
    // Sum the value and add to upline
    getUplines(mAuth.getUid());
    calculate();

    for (int i = 0; i < 5; i++) {
      usersRef.child(uplines[i]).child("b").setValue(oldBalance[i] + 12.5);
    }
  }

  public static void getOldBalance() {
    // mAuth.getUid() will get current logged in user punya UID
    // This block of code will get the latest balance of the user from Firebase
    usersRef.child(mAuth.getUid()).child("b").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        oldBalance[0] = (double) dataSnapshot.getValue();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {}
    });
  }

  public static void getUplines(String uid) {
    // To get upline and uplines' upline
    for (int i = 0; i < 5; i++) {
      usersRef.child(uid).child("upline").addListenerForSingleValueEvent(new ValueEventListener() {
        int temp=0;
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          uplines[temp] = dataSnapshot.getValue().toString();
          temp++;
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {}
      });
      uid = uplines[i];
    }
  }



  public static void calculate(){
    // This block of code will get the latest fee
    adminRef.child("fee").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        fee = (double) dataSnapshot.getValue();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {}
    });

    for (int i = 0; i < 5; i++) {
      switch (i) {
        case 0:
          commission_rate[i] = fee*first;
          break;
        default:
          commission_rate[i] = fee * (second - (i * 0.03));
          break;
      }
    }
  }
}
