package com.resturants.resturantsapp.database;

import android.content.Context;

import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resturants.resturantsapp.model.RateModel;

public class FirebaseUtility {

    public static final String RATES = "rates";

    public static DatabaseReference getDatabaseReference() {
        //get the path of database
        return FirebaseDatabase.getInstance().getReference().child(RATES);
    }

    public static void getRates(final Context context) {
        try {

            final DatabaseReference mDatabase = FirebaseDatabase.getInstance()
                    .getReference().child(RATES);
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@Nullable DataSnapshot dataSnapshot1) {


                    try {

                        System.out.println("THEDATAIGOTIS22: " + dataSnapshot1.toString());
                        for (DataSnapshot dataSnapshot : dataSnapshot1.getChildren()) {
// get all database children
                            try {
//get RateModel object
                                RateModel rateModel = dataSnapshot.getValue(RateModel.class);


                            } catch (Exception e) {
//                                Toast.makeText(context, context.getResources().getString(R.string.error_please_try_again), Toast.LENGTH_SHORT).show();
                                System.out.println("THEERRORINCATCHIS22: " + e.getMessage());
                            }


                        }


                    } catch (Exception e) {
                        System.out.println("ERRORNOWC: " + e.getMessage());
                    }
                    mDatabase.removeEventListener(this);


                }

                @Override
                public void onCancelled(@Nullable DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            System.out.println("ERRORINFIREBASESETTING7: " + e.getMessage());
        }
    }

    public static void addRates(Context context, RateModel rateModel) {
        try {

            // set the path of database, databaseName > itemName > userEmail ; it should be unique.
            DatabaseReference mDatabase = getDatabaseReference()
                    .child(rateModel.getItemName() + "")
                    //Firebase Database paths must not contain '.'
                    .child(rateModel.getUserEmail().replace(".", "-") + "");


            mDatabase.setValue(rateModel).
                    addOnSuccessListener(aVoid ->
                            System.out.println("THESUBSCRIPTION2: done")).
                    addOnFailureListener(e ->
                            System.out.println("THESUBSCRIPTION2 " + e.getMessage()));

        } catch (Exception e) {
            System.out.println("ERRORINFIREBASESETTING8: " + e.getMessage());
        }

    }

    public static void removeRates(Context context, String itemName) {
        try {
            context = context.getApplicationContext();

            DatabaseReference mDatabase = getDatabaseReference().child(itemName + "");


            mDatabase.removeValue().
                    addOnSuccessListener(aVoid ->
                            System.out.println("THESUBSCRIPTION2: done")).
                    addOnFailureListener(e ->
                            System.out.println("THESUBSCRIPTION2 " + e.getMessage()));
        } catch (Exception e) {
            System.out.println("ERRORINFIREBASESETTING9: " + e.getMessage());
        }

    }


}
