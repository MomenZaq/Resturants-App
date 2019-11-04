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

    public int minWordCloudScale = 25;
    public int maxWordCloudScale = 70;

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

        }
    }


    public void setStatisticsData(HashMap<String, Integer> hashMap, int posRateNum, int negRateNum) {


        //set Positive and Negative rate
        int total = posRateNum + negRateNum;
        int posRate = 0;
        int negRate = 0;

        if (posRateNum > 0) {
            float perc = (float) (posRateNum / (total * 1.0));
            posRate = Math.round( (perc * 100));
        }
        if (negRateNum > 0) {
            float perc = (float) (negRateNum / (total * 1.0));
            negRate = Math.round( (perc * 100));
        }
        System.out.println("THE POSITIVE: " + posRateNum);
        System.out.println("THE NEGATIVe: " + negRateNum);
        System.out.println("THE POSITIVE2: " + posRate);
        System.out.println("THE NEGATIVe2: " + negRate);

        txvPos.setText(posRate + " %");
        txvNeg.setText(negRate + " %");


        //set WordCloud
        setWordCloud(hashMap);


    }

    private void setWordCloud(HashMap<String, Integer> hashMap) {
        List<WordCloud> wordCloudList = new ArrayList<>();


        //add the words and its repetitive number to list then display it.
        Iterator<String> iterator = hashMap.keySet().iterator();
        for (Iterator<String> it = iterator; it.hasNext(); ) {
            //the comment
            String key = it.next();
            // num of
            int value = hashMap.get(key);
            wordCloudList.add(new WordCloud(key, value));

        }
        wordCloudList.add(new WordCloud(".", minWordCloudScale));
        wordCloudList.add(new WordCloud(".", minWordCloudScale+1));

        wordCloud.setDataSet(wordCloudList);
        //set scale between 25 and max 70
        wordCloud.setScale(maxWordCloudScale, minWordCloudScale);

        wordCloud.setColors(ColorTemplate.MATERIAL_COLORS);
        if (wordCloudList.size() > 20) {
            wordCloud.setSize(800, 550);

        } else if (wordCloudList.size() > 10) {
            wordCloud.setSize(600, 450);

        } else {
            wordCloud.setSize(300, 350);

        }
        wordCloud.notifyDataSetChanged();
    }
}
