package com.resturants.resturantsapp.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.activities.LoginActivity;
import com.resturants.resturantsapp.adapters.MainCommentAdapter;
import com.resturants.resturantsapp.database.FirebaseUtility;
import com.resturants.resturantsapp.model.ItemModel;
import com.resturants.resturantsapp.model.RateModel;
import com.resturants.resturantsapp.utils.GetAllRatesInterface;
import com.resturants.resturantsapp.utils.SharedPreferensessClass;

import java.util.ArrayList;
import java.util.List;

public class ItemRateFragment extends Fragment {
    View v;
    boolean newCreation = true;
    Activity activity;

    private ConstraintLayout constraintContainer;
    private TextView txvNoData;
    private EditText edtContent;
    private ImageButton btnSend;
    private RecyclerView recycle;
    private ProgressBar progress;

    ItemModel itemModel;
    List<RateModel> rateModelList = new ArrayList<>();
    MainCommentAdapter itemAdapter;

    public ItemRateFragment() {
    }


    @SuppressLint("ValidFragment")
    public ItemRateFragment(Activity activity, ItemModel itemModel) {
        this.activity = activity;
        this.itemModel = itemModel;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v == null) {
            newCreation = true;
            v = inflater.inflate(R.layout.fragment_item_rate, container, false);


        } else {
            newCreation = false;

        }

        return v;
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (activity == null) {
            return;
        }
        if (newCreation) {


            constraintContainer = (ConstraintLayout) v.findViewById(R.id.constraint_container);
            txvNoData = (TextView) v.findViewById(R.id.txv_no_data);
            edtContent = (EditText) v.findViewById(R.id.edt_content);
            btnSend = (ImageButton) v.findViewById(R.id.btn_send);
            recycle = (RecyclerView) v.findViewById(R.id.recycle);
            progress = (ProgressBar) v.findViewById(R.id.progress);


//set vertical layout manager for recyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
            recycle.setLayoutManager(layoutManager);


//            get all rates from firebase
            FirebaseUtility.getRates(activity, itemModel.getItemName(), new GetAllRatesInterface() {
                @Override
                public void finish(List<RateModel> rateModels) {
                    rateModelList.addAll(rateModels);
                    setAdapter();

                }
            });


            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addRate();

                }
            });

        }
    }

    private void setAdapter() {
        progress.setVisibility(View.GONE);
        if (rateModelList.isEmpty()) {
            txvNoData.setVisibility(View.VISIBLE);
        } else {
            txvNoData.setVisibility(View.GONE);
        }

        if (itemAdapter == null) {
            itemAdapter = new MainCommentAdapter(rateModelList, activity);
            recycle.setAdapter(itemAdapter);

        } else {
            itemAdapter.setList(rateModelList);
            itemAdapter.notifyDataSetChanged();
        }
    }

    private void addRate() {

//        if (SharedPreferensessClass.getInstance(activity).getRateItemName(itemModel.getItemName())) {
//            Toast.makeText(activity, getResources().getString(R.string.you_already_commented), Toast.LENGTH_LONG).show();
//            edtContent.setText("");
//            return;
//        }
        String userName = SharedPreferensessClass.getInstance(activity).getUserName();
        String userEmail = SharedPreferensessClass.getInstance(activity).getUserEmail();
        String comment = edtContent.getText().toString();
        //check if the user didn't sign in, go to login activity
        if (userEmail.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(activity.getResources().getString(R.string.add_new_comment));
            builder.setMessage(activity.getResources().getString(R.string.sorry_you_have_to_sign_in_to_comment));
            builder.setNegativeButton(activity.getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

            builder.setPositiveButton(activity.getResources().getString(R.string.login), (dialogInterface, i) -> {
                Intent intent = new Intent(activity, LoginActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(activity,
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
            });

//                builder.show();
            AlertDialog dialog = builder.create();
            dialog.show();
            TextView textView = dialog.findViewById(android.R.id.message);
            textView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6f, activity.getResources().getDisplayMetrics()), 1.0f);

            return;
        }
        //check if comment less than 4 letters.
        if (comment.length() < 4) {
            Toast.makeText(activity, getResources().getString(R.string.add_comment_more_than_4), Toast.LENGTH_SHORT).show();
            return;
        }


        RateModel rateModel = new RateModel(userName, userEmail, itemModel.getItemName(), comment);
        FirebaseUtility.addRates(activity, rateModel, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Add Rate: done");
                        edtContent.setText("");
                        //save the itemName to prevent comment another time
                        SharedPreferensessClass.getInstance(activity).setRateItemName(rateModel.getItemName());

//add the new comment to recyclerview
                        rateModelList.add(rateModel);
                        setAdapter();
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Add Rate " + e.getMessage());
                        edtContent.setText("");
                    }
                });


    }


}
