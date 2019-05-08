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

  private static String adminUid = "SpLQZFmB8KYkrq4h8NeuUfqQNW03";
  private static String[] uplines = new String[5];
  private static double[] commission= new double[5];
  private static double[] oldBalance= new double[5];
  private static double first = 0.5;
  private static double second = 0.12 + 0.03;
  private static double fee;

  public static void calcRev() {
    // Sum the value and add to upline
    getUplines(mAuth.getUid());
    getOldBalance();
    calculate();

    for (int i = 0; i < 5; i++) {
      oldBalance[i]=oldBalance[i]+commission[i];
      usersRef.child(uplines[i]).child("b").setValue(oldBalance[i]);


    }
  }

  public static void getOldBalance() {
    // mAuth.getUid() will get current logged in user punya UID
    // This block of code will get the latest balance of the user from Firebase
    for (int i = 0; i < 5; i++){
      int temp=0;
      usersRef.child(mAuth.getUid()).child("b").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          oldBalance[temp] =(double) dataSnapshot.getValue();
          temp++;
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
      });
  }
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
          commission[i] = fee*first;
          break;
        default:
          commission[i] = fee * (second - (i * 0.03));
          break;
      }
    }
  }
}
