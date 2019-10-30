package com.resturants.resturantsapp.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.resturants.resturantsapp.R;
import com.resturants.resturantsapp.database.FirebaseUtility;
import com.resturants.resturantsapp.model.ItemModel;
import com.resturants.resturantsapp.model.RateModel;
import com.resturants.resturantsapp.network.NetworkUtil;
import com.resturants.resturantsapp.utils.ConstantString;
import com.resturants.resturantsapp.utils.GetAllRatesInterface;

import net.alhazmy13.wordcloud.ColorTemplate;
import net.alhazmy13.wordcloud.WordCloud;
import net.alhazmy13.wordcloud.WordCloudView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import co.lujun.myandroidtagview2.TagContainerLayout;

public class ItemPublicRateFragment extends Fragment {
    View v;
    boolean newCreation = true;
    Activity activity;
    ItemModel itemModel;
    private TextView txvRate;
    private RatingBar rate;
    private TextView txvNeg;
    private TextView txvPos;
    private TagContainerLayout tags;
    private WordCloudView wordCloud;


    public ItemPublicRateFragment() {
    }


    @SuppressLint("ValidFragment")
    public ItemPublicRateFragment(Activity activity, ItemModel itemModel) {
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
            v = inflater.inflate(R.layout.fragment_public_rate, container, false);


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
            txvRate = (TextView) v.findViewById(R.id.txv_rate);
            rate = (RatingBar) v.findViewById(R.id.rate);
            txvNeg = (TextView) v.findViewById(R.id.txv_neg);
            txvPos = (TextView) v.findViewById(R.id.txv_pos);
            tags = (TagContainerLayout) v.findViewById(R.id.tags);
            wordCloud = (WordCloudView) v.findViewById(R.id.wordCloud);


            txvRate.setText(itemModel.getRating() + "/5");
            rate.setRating(itemModel.getRating());


            //set comments tags
//            List<String> userWin = new ArrayList<>();
//            //just for test
////            userWin.add("     جميل     ");
////            userWin.add("  رائع   ");
////            userWin.add(" لذيذ ");
//            tags.setBackgroundColor(Color.TRANSPARENT);
//            tags.setTagBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
//            tags.setTagBorderColor(activity.getResources().getColor(R.color.colorAccent));
//            tags.setTagTextColor(activity.getResources().getColor(R.color.colorWhite));
//            tags.setBorderColor(Color.TRANSPARENT);
//            tags.setGravity(Gravity.CENTER);
//            Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "Changa-SemiBold.ttf");
//            tags.setTagTypeface(typeface);
//            tags.setTags(userWin);


            //get btn rates from firebase

            setReviews();
            setWordCloud();
        }
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

                List<String> rates = getReviewsFromResult(response);
                ;
                int posRateNum = 0;
                int negRateNum = 0;

                for (String rate : rates) {
                    //check if rate contain any positive rate
                    for (String posRate : ConstantString.positiveList) {
                        if (rate.contains(posRate)) {
                            System.out.println("THE CURRENT POS: " + posRate);
                            posRateNum++;
                        }
                    }


                    //check if rate contain any negative rate
                    for (String negRate : ConstantString.negativeList) {
                        if (rate.contains(negRate)) {
                            System.out.println("THE CURRENT NEG: " + negRate);
                            negRateNum++;
                        }
                    }


                }


                int total = posRateNum + negRateNum;
                int posRate = 0;
                int negRate = 0;

                if (posRateNum > 0) {
                    posRate = (posRateNum / total) * 100;
                }
                if (negRateNum > 0) {
                    negRate = (negRateNum / total) * 100;
                }
                txvPos.setText(posRate + " %");
                txvNeg.setText(negRate + " %");
            }
        });
        placesTask.execute(link);

    }

    private List<String> getReviewsFromResult(String response) {
        //parse the json result to get the reviews
        JSONObject jPlaces = null;
        List<String> reviewsText = new ArrayList<>();
        try {
            jPlaces = new JSONObject(response).getJSONObject("result");
            JSONArray reviews = jPlaces.getJSONArray("reviews");
            for (int i = 0; i < reviews.length(); i++) {
                System.out.println("THE JSON OBJECT: " + reviews.get(i).toString());
                reviewsText.add(reviews.getJSONObject(i).getString("text"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewsText;
    }

    public StringBuilder getAPILike() {


        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        sb.append("place_id=" + itemModel.getItemId());
        sb.append("&fields=" + "reviews");
        sb.append("&key=" + getResources().getString(R.string.api_key));

        return sb;
    }

    private void setWordCloud() {
        //get btn rates from firebase
        FirebaseUtility.getBtnRates(activity, itemModel.getItemName(), new GetAllRatesInterface() {
            @Override
            public void finish(List<RateModel> rateModels) {

                HashMap<String, Integer> hashMap = new HashMap<>();
                List<WordCloud> wordCloudList = new ArrayList<>();
                int min = 25;
                int max = 70;
                //add all words to hashmap to collect word's repetitive.
                for (RateModel rateModel : rateModels) {
                    System.out.println("THE RATE IS: " + rateModel.getComment());
                    if (hashMap.containsKey(rateModel.getComment())) {
                        int num = hashMap.get(rateModel.getComment()) + 1;
                        hashMap.remove(rateModel.getComment());
                        if (num > max) {
                            num = max;
                        }
                        hashMap.put(rateModel.getComment(), num);
                    } else {
                        hashMap.put(rateModel.getComment(), 30);

                    }

                }
                hashMap.put(".", min);

                //add the words and its repetitive number to list then display it.
                Iterator<String> iterator = hashMap.keySet().iterator();
                for (Iterator<String> it = iterator; it.hasNext(); ) {
                    String key = it.next();
                    int value = hashMap.get(key);
                    wordCloudList.add(new WordCloud(key, value));

                }

                wordCloud.setDataSet(wordCloudList);
                //set scale between 25 and max 70
                wordCloud.setScale(max, min);
                wordCloud.setColors(ColorTemplate.MATERIAL_COLORS);
                wordCloud.setSize(250, 300);
                wordCloud.notifyDataSetChanged();
            }
        });
    }


}
