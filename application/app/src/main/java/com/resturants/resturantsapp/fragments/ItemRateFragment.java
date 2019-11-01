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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.activities.ItemProfileActivity;
import com.resturants.resturantsapp.activities.LoginActivity;
import com.resturants.resturantsapp.adapters.MainCommentAdapter;
import com.resturants.resturantsapp.database.FirebaseUtility;
import com.resturants.resturantsapp.model.ItemModel;
import com.resturants.resturantsapp.model.RateModel;
import com.resturants.resturantsapp.network.NetworkUtil;
import com.resturants.resturantsapp.utils.ConstantString;
import com.resturants.resturantsapp.utils.GetAllRatesInterface;
import com.resturants.resturantsapp.utils.SharedPreferensessClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class ItemRateFragment extends Fragment {
    View v;
    boolean newCreation = true;
    Activity activity;

    private ConstraintLayout constraintContainer;
    private TextView txvNoData;
    private EditText edtContent;
    private ImageButton btnSend;
    private Button btnComment1;
    private Button btnComment2;
    private Button btnComment3;
    private Button btnComment4;


    private RecyclerView recycle;
    private ProgressBar progress;

    ItemModel itemModel;
    List<RateModel> rateModelList = new ArrayList<>();
    MainCommentAdapter itemAdapter;


    boolean displayDataSuccessfully = false;


    HashMap<String, Integer> hashMap = new HashMap<>();
    int posRateNum = 0;
    int negRateNum = 0;


    @Override
    public void onResume() {
        super.onResume();
        try {
            if (!displayDataSuccessfully) {
                if (!hashMap.isEmpty()) {
                    ((ItemProfileActivity) getActivity()).itemPublicRateFragment.setStatisticsData(hashMap, posRateNum, negRateNum);
                    displayDataSuccessfully = true;
                }
            }
        } catch (Exception e) {
            displayDataSuccessfully = false;
        }
    }

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
            btnComment1 = (Button) v.findViewById(R.id.btn_comment_1);
            btnComment2 = (Button) v.findViewById(R.id.btn_comment_2);
            btnComment3 = (Button) v.findViewById(R.id.btn_comment_3);
            btnComment4 = (Button) v.findViewById(R.id.btn_comment_4);

//set vertical layout manager for recyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
            recycle.setLayoutManager(layoutManager);


//            get all rates from firebase
            FirebaseUtility.getRates(activity, itemModel.getItemName(), new GetAllRatesInterface() {
                @Override
                public void finish(List<RateModel> rateModels) {
                    rateModelList.addAll(rateModels);
                    setReviews();

                }
            });


            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addRate();

                }
            });


            btnComment1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterBtnRate(1);
                }
            });
            btnComment2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterBtnRate(2);
                }
            });
            btnComment3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterBtnRate(3);
                }
            });
            btnComment4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterBtnRate(4);
                }
            });
        }
    }

    private void filterBtnRate(int btnNum) {

//        String userName = SharedPreferensessClass.getInstance(activity).getUserName();
//        String userEmail = SharedPreferensessClass.getInstance(activity).getUserEmail();
        String comment = "";
        boolean newSelect = false;
        switch (btnNum) {
            case 1:
                if (btnComment1.getTag().equals("off")) {
                    newSelect = true;
                    comment = btnComment1.getText().toString();
                }
                markCurrentBtn(btnComment1, newSelect);
                break;
            case 2:
                if (btnComment2.getTag().equals("off")) {
                    newSelect = true;
                    comment = btnComment2.getText().toString();
                }
                markCurrentBtn(btnComment2, newSelect);
                break;
            case 3:
                if (btnComment3.getTag().equals("off")) {
                    newSelect = true;
                    comment = btnComment3.getText().toString();
                }
                markCurrentBtn(btnComment3, newSelect);
                break;
            case 4:
                if (btnComment4.getTag().equals("off")) {
                    newSelect = true;
                    comment = btnComment4.getText().toString();
                }
                markCurrentBtn(btnComment4, newSelect);
                break;
        }

        if (comment.equals("")) {
            //no one selected, display all comments
            setAdapter(rateModelList);
        } else {
            List<RateModel> newList = new ArrayList<>();
            for (RateModel rateModel : rateModelList) {
                //check if comment exist in object, add it to new list
                if (rateModel.getComment().contains(comment)) {
                    newList.add(rateModel);
                }
            }

            setAdapter(newList);
        }

//        //check if the user didn't sign in, go to login activity
//
//        if (!checkLogin(userEmail)) {
//            return;
//        }
//
//
//        RateModel rateModel = new RateModel(userName, userEmail, itemModel.getItemName(), comment);
//        FirebaseUtility.addBtnRates(activity, rateModel, new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        System.out.println("Add Btn Rate: done");
//                        //save the itemName to prevent comment another time
//                        SharedPreferensessClass.getInstance(activity).setBtnRateItemName(rateModel.getItemName());
//
//                    }
//                },
//                new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        System.out.println("Add Btn Rate " + e.getMessage());
//                    }
//                });

    }

    private void markCurrentBtn(Button selectedBtn, boolean newSelect) {

        btnComment1.setTag("off");
        btnComment2.setTag("off");
        btnComment3.setTag("off");
        btnComment4.setTag("off");
        btnComment1.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray));
        btnComment2.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray));
        btnComment3.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray));
        btnComment4.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray));

        if (newSelect){
            //put the tag to current btn "on" to mark it as a selected button
        selectedBtn.setTag("on");
        selectedBtn.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
        }
    }

    private void setAdapter(List<RateModel> rateModelList) {
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

        if (!checkLogin(userEmail)) {
            return;
        }
        //check if comment less than 4 letters.
        if (comment.length() < 3) {
            Toast.makeText(activity, getResources().getString(R.string.add_comment_more_than_3), Toast.LENGTH_SHORT).show();
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
                        setAdapter(rateModelList);
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


    private boolean checkLogin(String userEmail) {
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

            return false;
        }
        return true;
    }


    private void setReviews() {
//get review from google api
        String link = getAPILike().toString();
        System.out.println("THE PLACE LINK: " + link);
        NetworkUtil.PlacesTask placesTask = new NetworkUtil.PlacesTask(activity, false, new NetworkUtil.onFinishedConnection() {
            @Override
            public void onFinished(List<HashMap<String, String>> list) {

            }

            @Override
            public void onFinished(String response) {
                System.out.println("THE RESPONSE: " + response);
                List<RateModel> rates = getReviewsFromResult(response);
                rateModelList.addAll(rates);
                setAdapter(rateModelList);


                for (RateModel rate : rateModelList) {
                    //check if rate contain any positive rate
                    for (String posRate : ConstantString.positiveList) {
                        if (rate.getComment().contains(posRate)) {
                            System.out.println("THE CURRENT POS: " + posRate);
                            addWordToHashMap(hashMap, posRate);
                            posRateNum++;
                        }
                    }


                    //check if rate contain any negative rate
                    for (String negRate : ConstantString.negativeList) {
                        if (rate.getComment().contains(negRate)) {
                            System.out.println("THE CURRENT NEG: " + negRate);
                            addWordToHashMap(hashMap, negRate);
                            negRateNum++;
                        }
                    }


                }

                setMostWordToButtons();
                try {
                    if (!displayDataSuccessfully) {
                        if (!hashMap.isEmpty()) {
                            ((ItemProfileActivity) getActivity()).itemPublicRateFragment.setStatisticsData(hashMap, posRateNum, negRateNum);
                            displayDataSuccessfully = true;
                        }
                    }
                } catch (Exception e) {
                    displayDataSuccessfully = false;
                }

            }
        });
        placesTask.execute(link);

    }


    public LinkedHashMap<String, Integer> sortHashMapByValues(
            HashMap<String, Integer> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, Integer> sortedMap =
                new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            int val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                int comp1 = passedMap.get(key);
                int comp2 = val;

                if (comp1 == comp2) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    private void setMostWordToButtons() {
        btnComment4.setVisibility(View.GONE);
        btnComment3.setVisibility(View.GONE);
        btnComment2.setVisibility(View.GONE);
        btnComment1.setVisibility(View.GONE);

        //sort by value to get most repetitive words

        LinkedHashMap<String, Integer> sortedMap = sortHashMapByValues(hashMap);

        List<String> reverseOrderedKeys = new ArrayList<String>(sortedMap.keySet());
        Collections.reverse(reverseOrderedKeys);
        int i = 0;
        for (String word : reverseOrderedKeys) {
            System.out.println("THE WORD IS: " + word);
            i++;
            if (i == 1) {
                btnComment1.setText(word);
                btnComment1.setVisibility(View.VISIBLE);
            } else if (i == 2) {

                btnComment2.setText(word);
                btnComment2.setVisibility(View.VISIBLE);
            } else if (i == 3) {

                btnComment3.setText(word);
                btnComment3.setVisibility(View.VISIBLE);


            } else if (i == 4) {

                btnComment4.setText(word);
                btnComment4.setVisibility(View.VISIBLE);

                break;

            }

        }

    }


    private void addWordToHashMap(HashMap<String, Integer> hashMap, String word) {
        if (hashMap.containsKey(word)) {
            int num = hashMap.get(word) + 1;
            hashMap.remove(word);
            if (num > ((ItemProfileActivity) getActivity()).itemPublicRateFragment.maxWordCloudScale) {
                num = ((ItemProfileActivity) getActivity()).itemPublicRateFragment.maxWordCloudScale;

            }
            hashMap.put(word, num);
        } else {
            hashMap.put(word, 30);

        }
    }

    private List<RateModel> getReviewsFromResult(String response) {
        //parse the json result to get the reviews
        JSONObject jPlaces = null;
        List<RateModel> reviewsText = new ArrayList<>();
        try {
            jPlaces = new JSONObject(response).getJSONObject("result");
            JSONArray reviews = jPlaces.getJSONArray("reviews");
            for (int i = 0; i < reviews.length(); i++) {
                System.out.println("THE JSON OBJECT: " + reviews.get(i).toString());
                reviewsText.add(new RateModel(reviews.getJSONObject(i).getString("author_name"), reviews.getJSONObject(i).getString("text")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewsText;
    }

    public StringBuilder getAPILike() {


        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        sb.append("place_id=" + itemModel.getItemId());
        sb.append("&fields=" + "reviews,name");
        sb.append("&key=" + getResources().getString(R.string.api_key));

        return sb;
    }


}
