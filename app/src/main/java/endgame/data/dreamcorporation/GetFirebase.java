package endgame.data.dreamcorporation;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetFirebase {

  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private static FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
  private DatabaseReference adminRed = mDatabase.getReference("admin");
  private DatabaseReference usersRef = mDatabase.getReference("users");
  private DatabaseReference transRef = mDatabase.getReference("transactions");

  public static void getFirebase() { mDatabase.setPersistenceEnabled(true); }

  public double getUserBalance(String uid) {
    final double[] temp = new double[1];
    usersRef.child(uid).child("b").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Log.e("Double", dataSnapshot.getValue().toString());
        temp[0] = Double.valueOf(dataSnapshot.getValue().toString());
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {}
    });

    return temp[0];
  }

  public void getFullName(String uid, final GetFullNameCallback getFullNameCallback) {
    usersRef.child(uid).child("fN").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        getFullNameCallback.onCallback(dataSnapshot.getValue().toString());

//        Log.e("fN: ", dataSnapshot.getValue().toString());
//        tempFullName[0] = dataSnapshot.getValue().toString();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {}
    });
  }


  public interface GetFullNameCallback {
    void onCallback(String fullName);
  }

//  protected String getName(String u, String k) {
//    final String uid = u;
//    final String key = k;
//    final String[] tempFullName = new String[1];
//
//    Log.e("uid: ", uid);
//
//    usersRef.child(uid).child("fN").addListenerForSingleValueEvent(new ValueEventListener() {
//      @Override
//      public void onDataChange(DataSnapshot dataSnapshot) {
//        Log.e("fN: ", Encryption.decode(dataSnapshot.getValue().toString(), key));
//        tempFullName[0] = Encryption.decode(dataSnapshot.getValue().toString(), key);
//      }
//
//      @Override
//      public void onCancelled(@NonNull DatabaseError databaseError) {}
//    });
//    return tempFullName[0];
//  }
}
