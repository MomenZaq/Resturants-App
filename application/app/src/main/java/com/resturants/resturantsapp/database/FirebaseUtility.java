package com.resturants.resturantsapp.database;

import android.content.Context;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resturants.resturantsapp.model.RateModel;
import com.resturants.resturantsapp.utils.GetAllRatesInterface;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtility {

    public static final String RATES = "rates";
    public static final String BTN_RATES = "btnRates";

    public static DatabaseReference getDatabaseReferenceRates() {
        //get the path of database
        return FirebaseDatabase.getInstance().getReference().child(RATES);
    }

    public static DatabaseReference getDatabaseReferenceBtnRates() {
        //get the path of database
        return FirebaseDatabase.getInstance().getReference().child(BTN_RATES);
    }

    public static void getRates(final Context context, String itemName, GetAllRatesInterface getAllRatesInterface) {
        List<RateModel> rateModels = new ArrayList<>();
        try {

            final DatabaseReference mDatabase = getDatabaseReferenceRates().child(itemName + "");
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
                                rateModels.add(rateModel);

                            } catch (Exception e) {
//                                Toast.makeText(context, context.getResources().getString(R.string.error_please_try_again), Toast.LENGTH_SHORT).show();
                                System.out.println("THEERRORINCATCHIS22: " + e.getMessage());
                            }


                        }
                        //return the list of rates by the interface
                        getAllRatesInterface.finish(rateModels);

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


//    public static void getBtnRates(final Context context, String itemName, GetAllRatesInterface getAllRatesInterface) {
//        List<RateModel> rateModels = new ArrayList<>();
//        try {
//
//            final DatabaseReference mDatabase = getDatabaseReferenceBtnRates().child(itemName + "");
//            mDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@Nullable DataSnapshot dataSnapshot1) {
//                    try {
//                        System.out.println("THEDATAIGOTIS22: " + dataSnapshot1.toString());
//                        for (DataSnapshot dataSnapshot : dataSnapshot1.getChildren()) {
//// get all database children
//                            try {
////get RateModel object
//                                RateModel rateModel = dataSnapshot.getValue(RateModel.class);
//                                rateModels.add(rateModel);
//
//                            } catch (Exception e) {
////                                Toast.makeText(context, context.getResources().getString(R.string.error_please_try_again), Toast.LENGTH_SHORT).show();
//                                System.out.println("THEERRORINCATCHIS22: " + e.getMessage());
//                            }
//
//
//                        }
//                        //return the list of rates by the interface
//                        getAllRatesInterface.finish(rateModels);
//
//                    } catch (Exception e) {
//                        System.out.println("ERRORNOWC: " + e.getMessage());
//                    }
//                    mDatabase.removeEventListener(this);
//
//
//                }
//
//                @Override
//                public void onCancelled(@Nullable DatabaseError databaseError) {
//
//                }
//            });
//        } catch (Exception e) {
//            System.out.println("ERRORINFIREBASESETTING7: " + e.getMessage());
//        }
//    }

    public static void addRates(Context context, RateModel rateModel, OnSuccessListener<? super Void> successListener, OnFailureListener failureListener) {
        try {

            // set the path of database, databaseName > itemName > userEmail ; it should be unique.
            DatabaseReference mDatabase = getDatabaseReferenceRates()
                    .child(rateModel.getItemName() + "")
                    //Firebase Database paths must not contain '.'
                    .child(rateModel.getUserEmail().replace(".", "-") + "");


            mDatabase.setValue(rateModel).
                    addOnSuccessListener(successListener).
                    addOnFailureListener(failureListener);

        } catch (Exception e) {
            System.out.println("Add Rate8: " + e.getMessage());
        }

    }

//    public static void addBtnRates(Context context, RateModel rateModel, OnSuccessListener<? super Void> successListener, OnFailureListener failureListener) {
//        try {
//
//            // set the path of database, databaseName > itemName > userEmail ; it should be unique.
//            DatabaseReference mDatabase = getDatabaseReferenceBtnRates()
//                    .child(rateModel.getItemName() + "")
//                    //Firebase Database paths must not contain '.'
//                    .child(rateModel.getUserEmail().replace(".", "-") + "");
//
//
//            mDatabase.setValue(rateModel).
//                    addOnSuccessListener(successListener).
//                    addOnFailureListener(failureListener);
//
//        } catch (Exception e) {
//            System.out.println("Add Rate8: " + e.getMessage());
//        }
//
//    }

    public static void removeRates(Context context, String itemName) {
        try {
            context = context.getApplicationContext();

            DatabaseReference mDatabase = getDatabaseReferenceRates().child(itemName + "");


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
