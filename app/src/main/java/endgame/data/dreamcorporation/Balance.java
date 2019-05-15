package endgame.data.dreamcorporation;

import android.util.Log;

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
  private static GetFirebase firebase = new GetFirebase();

  private static String adminUid;
  private static String[] uplines = new String[5];
  private static double[] commission= new double[5];
  private static double[] oldBalance= new double[5];
  private static double first = 0.5;
  private static double second = 0.12 + 0.03;
  private static double fee;
  private static double temporary;
  private static int tempbaby;

  public static void calcRev(String scannedUpId) {

    // Get latest fee
    fee = firebase.getFee();
    Log.e("Fee", String.valueOf(fee));

    // Get admin uid
    adminUid = firebase.getCompanyUid();

    // To set current user upId
    usersRef.child(mAuth.getUid()).child("upId").setValue(scannedUpId);


    getUplines(mAuth.getUid());
    getOldBalance();
    calculate();
    addBalance();

//    For debugging
//    for (int i = 0; i < 5; i++) {
//      Log.e("upId", i + ": " + uplines[i]);
//    }
  }

  public static void addBalance() {
    for (int i = 0; i < 5; i++) {
      tempbaby=i;
      oldBalance[i] = oldBalance[i] + commission[i];
//      Log.e("soh", String.valueOf(oldBalance[i]));
      usersRef.child(uplines[i]).child("b").setValue(oldBalance[i]);
      if (uplines[i].equals(adminUid)) {
        adminRef.child(mAuth.getUid()).child("b").addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            temporary = Double.parseDouble(dataSnapshot.getValue().toString());
            temporary = temporary + commission[tempbaby];
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {
          }
        });
        usersRef.child(adminUid).child("b").setValue(temporary);
      }
    }
  }

  public static void getOldBalance() {
    // mAuth.getUid() will get current logged in user punya UID
    // This block of code will get the latest balance of the user from Firebase
    for (int i = 0; i < 5; i++){
      final int temp=i;
      usersRef.child(mAuth.getUid()).child("b").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          oldBalance[temp] =(double) dataSnapshot.getValue();
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
      final int temp = i;
      final String tempUid = uid;
      firebase.haveUpline(uid, new GetFirebase.HaveUplineCallback() {
        @Override
        public void onCallback(boolean haveDownline) {
          if (haveDownline) {
            firebase.getUpId(tempUid, new GetFirebase.GetUpIdCallback() {
              @Override
              public void onCallback(String upId) {
                uplines[temp] = upId;
              }
            });
          } else uplines[temp] = adminUid;
        }
      });

//      usersRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//          if (!dataSnapshot.child("upId").exists()) {
//            uplines[temp] = adminUid;
//          } else {
////            Log.e("test", dataSnapshot.child("upId").getValue().toString());
//            uplines[temp] = dataSnapshot.child("upId").getValue().toString();
//          }
//
////          if (dataSnapshot.getValue() == adminUid) {
////            uplines[temp] = adminUid;
////            for (int j = temp; j < 5; j++) {
////              uplines[j] = adminUid;
////            }
////
////          } else uplines[temp] = dataSnapshot.getValue().toString();
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//        }
//      });
      uid = uplines[i];
//      Log.e("uid",uplines[i]);
//      System.out.println(uplines[i]);
    }
//    for (int i=0;i<uplines.length;i++){
//      System.out.println(uplines[i]);
//    }
  }


  public static void calculate(){
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
//  public static String getDirectUplineUid() {
//    return uplines[0];
//  }
}
