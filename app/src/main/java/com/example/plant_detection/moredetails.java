package com.example.plant_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;


public class moredetails extends AppCompatActivity {

    TextView detail_type,tv_30,tv_31,tv_32,tv_33,tv_34,tv_35,tv_36,tv_37,tv_38,tv_39,tv_40,tv_41,tv_42,tv_43,tv_44,tv_45;
    TextView textView1,textView3,textView5,textView9,
            textView11,textView13,textView15,textView17,
            textView19,textView21,textView23,textView25,textView27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moredetails);





        detail_type=findViewById(R.id.detail_type);
        tv_30=(TextView) findViewById(R.id.tv_30);
        tv_31=(TextView) findViewById(R.id.tv_31);
        tv_32=(TextView) findViewById(R.id.tv_32);
       // tv_33=findViewById(R.id.tv_33);
        tv_34=(TextView) findViewById(R.id.tv_34);
        tv_35=(TextView) findViewById(R.id.tv_35);
        tv_36=(TextView) findViewById(R.id.tv_36);
        tv_37=(TextView) findViewById(R.id.tv_37);
        tv_38=(TextView) findViewById(R.id.tv_38);
        tv_39=(TextView) findViewById(R.id.tv_39);
        tv_40=(TextView) findViewById(R.id.tv_40);
        tv_41=(TextView) findViewById(R.id.tv_41);
        tv_42=(TextView) findViewById(R.id.tv_42);
        tv_43=(TextView) findViewById(R.id.tv_43);
        tv_44=(TextView) findViewById(R.id.tv_44);
        tv_45=(TextView) findViewById(R.id.tv_45);


        textView1=(TextView) findViewById(R.id.textView1);
        textView3=(TextView) findViewById(R.id.textView3);
        textView5=(TextView) findViewById(R.id.textView5);
        textView9=(TextView) findViewById(R.id.textView9);
        textView11=(TextView) findViewById(R.id.textView11);
        textView13=(TextView) findViewById(R.id.textView13);
        textView15=(TextView) findViewById(R.id.textView15);
        textView17=(TextView) findViewById(R.id.textView17);
        textView19=(TextView) findViewById(R.id.textView19);
        textView21=(TextView) findViewById(R.id.textView21);
        textView23=(TextView) findViewById(R.id.textView23);
        textView25=(TextView) findViewById(R.id.textView25);
        textView27= (TextView) findViewById(R.id.textView27);

        detail_type.setText("");
        tv_30.setText("");
        tv_31.setText("");
        tv_32.setText("");
        tv_34.setText("");
        tv_35.setText("");
        tv_36.setText("");
        tv_37.setText("");
        tv_38.setText("");
        tv_39.setText("");
        tv_40.setText("");
        tv_41.setText("");
        tv_42.setText("");
        tv_43.setText("");





//        for(int itv=30; itv<44;itv++){
//            TextView tv_{itv} =
//        }
        //            String[] types = {"Aloe brevifolia", "Aloe vera", "Aloe aristata",
//                    "Aloe x principis", "Aloe viridiflora", "Aloe Barbedensis",
//                    "Aloe Broomii", "Aloe Striatula", "Aloe massawana"};


        Intent i = getIntent();
        String[] details=i.getStringArrayExtra("details_array");

       // String[] details= {"Aloe vera","Healthy"};
       System.out.println("This is type: "+details[0]);

        detail_type.setText(details[0]);

        String[] ultimateSpeed = {"0.5–1 metres",
                "0.1–0.5 metres",
                "0.9–1.8 metres",
                "0.08 metres",
                "0.5–1 metres",
                "0.1–0.5 metres",
                "0.5–1 metres",
                "0.1 metres"
        };
        String[] ultimateHeight={
                "up to 10cm",
                "0.1–0.5 metres",
                "1.8–2.7 metres",
                "0.60 metres",
                "0.5–1 metres",
                "1.5 metres",
                "0.5–1 metres",
                "0.91 metres"
        };

        String[] etaheight ={
                "10–20 years",
                "5–10 years",
                "2-3 years",
                "3-4 years",
                "5–10 years",
                "2–5 years",
                "5–10 years",
                "4-5 years"
        };
        String[] RH={
                "40%",
                "40%",
                "40%",
                "40%",
                "40%",
                "40%",
                "30-40%",
                "40%"
        };
        String[] temperature= {
                "68° – 80° F in day",
                "50° – 70° F at nights",
                "50° – 60° F",
                "50° – 60° F",
                "55° – 60° F",
                "65° – 70° F ",
                "68° – 75° F",
                "55° – 85° F"
        };
        String[] air_purifer ={ "Excellent Air Purifier",
                "Average Air Purifier",
                "Excellent Air Purifier",
                "Excellent Air Purifier",
                "Excellent Air Purifier",
                "Average Air Purifier",
                "Excellent Air Purifier",
                "Excellent Air Purifier",
        };
        String[] fertilizers= {
                "Not Required",
                "Not Required",
                "yearly",
                "Not Required",
                "liquid 2 or 3 times",
                "Not Required",
                "Not Required",
                "Not Required "
        };
        String[] pruning={
                "Not Required",
                "Not Required",
                "required Yearly",
                "Not Required",
                "Required",
                "Deadhead - flowring",
                "Not Required",
                "Required"
        };
        String[] watering ={
                "infrequently and deeply",
                "Weekly",
                "Moderately in summers",
                "Infrequent weekly",
                "Moderately in growth stages",
                "Moderately in summers",
                "sparingly in winters",
                "Water moderately"
        };
        String[] repotting ={
                "if outgrowing root",
                "Every few years",
                "if outgrowing root",
                "rarely needs ",
                "Not Required",
                "every 2 years",
                "rarely needs ",
                "rarely needs "
        };

        String[] toxic ={
                "Mildly toxic",
                "Toxic to pets",
                "Mildly toxic",
                "Non toxic",
                "Potentially Toxic",
                "Potentially Toxic",
                "Potentially Toxic",
                "Mildly toxic"
        };
        String[] price ={
                "Rs. 1350/-",
                "Rs. 350/-",
                "Rs. 190/-",
                "Rs. 450/-",
                "Rs. 800/-",
                "Rs. 950/-",
                "Rs. 1500/-",
                "Rs. 130/-"
        };
        String[] viability={
          "up to 12 years",
          "5 to 25 years",
                "approx. 95 years",
                "3 to 4 years",
                "thousands of years",
                "5 to 10 years",
                "3 to 4 years",
                "more than 4 years"
        };
        String[] sun={
                    "6-8 hrs daily",
                "bright light, shade",
                "full sun",
                "indirect light",
                "indirect light",
                "direct light",
                "direct light",
                "bright sunny"
        };


        if(Objects.equals(details[0],"Aloe brevifolia")){
            tv_30.setText(ultimateSpeed[0]);
            tv_31.setText(ultimateHeight[0]);
            tv_32.setText(etaheight[0]);
            tv_34.setText(RH[0]);
            tv_35.setText(temperature[0]);
            tv_36.setText(air_purifer[0]);
            tv_37.setText(details[1]);
            tv_38.setText(fertilizers[0]);
            tv_39.setText(pruning[0]);
            tv_40.setText(watering[0]);
            tv_41.setText(repotting[0]);
            tv_42.setText(toxic[0]);
            tv_43.setText(price[0]);
            tv_44.setText(sun[0]);
            tv_45.setText(viability[0]);

        }
       else if (Objects.equals(details[0],"Aloe vera")){
            tv_30.setText(ultimateSpeed[1]);
            tv_31.setText(ultimateHeight[1]);
            tv_32.setText(etaheight[1]);
            tv_34.setText(RH[1]);
            tv_35.setText(temperature[1]);
            tv_36.setText(air_purifer[1]);
            tv_37.setText(details[1]);
            tv_38.setText(fertilizers[1]);
            tv_39.setText(pruning[1]);
            tv_40.setText(watering[1]);
            tv_41.setText(repotting[1]);
            tv_42.setText(toxic[1]);
            tv_43.setText(price[1]);
            tv_44.setText(sun[1]);
            tv_45.setText(viability[1]);

        }
        else if(Objects.equals(details[0], "Aloe aristata")){
            tv_30.setText(ultimateSpeed[2]);
            tv_31.setText(ultimateHeight[2]);
            tv_32.setText(etaheight[2]);
            tv_34.setText(RH[2]);
            tv_35.setText(temperature[2]);
            tv_36.setText(air_purifer[2]);
            tv_37.setText(details[1]);
            tv_38.setText(fertilizers[2]);
            tv_39.setText(pruning[2]);
            tv_40.setText(watering[2]);
            tv_41.setText(repotting[2]);
            tv_42.setText(toxic[2]);
            tv_43.setText(price[2]);
            tv_44.setText(sun[2]);
            tv_45.setText(viability[2]);


        }
        else if(Objects.equals(details[0], "Aloe x principis")){
            tv_30.setText(ultimateSpeed[3]);
            tv_31.setText(ultimateHeight[3]);
            tv_32.setText(etaheight[3]);
            tv_34.setText(RH[3]);
            tv_35.setText(temperature[3]);
            tv_36.setText(air_purifer[3]);
            tv_37.setText(details[1]);
            tv_38.setText(fertilizers[3]);
            tv_39.setText(pruning[3]);
            tv_40.setText(watering[3]);
            tv_41.setText(repotting[3]);
            tv_42.setText(toxic[3]);
            tv_43.setText(price[3]);
            tv_44.setText(sun[3]);
            tv_45.setText(viability[3]);

        }
        else if(Objects.equals(details[0], "Aloe Barbedensis")){
            tv_30.setText(ultimateSpeed[4]);
            tv_31.setText(ultimateHeight[4]);
            tv_32.setText(etaheight[4]);
            tv_34.setText(RH[4]);
            tv_35.setText(temperature[4]);
            tv_36.setText(air_purifer[4]);
            tv_37.setText(details[1]);
            tv_38.setText(fertilizers[4]);
            tv_39.setText(pruning[4]);
            tv_40.setText(watering[4]);
            tv_41.setText(repotting[4]);
            tv_42.setText(toxic[4]);
            tv_43.setText(price[4]);
            tv_44.setText(sun[4]);
            tv_45.setText(viability[4]);

        }
        else if(Objects.equals(details[0], "Aloe Broomii")){
            tv_30.setText(ultimateSpeed[5]);
            tv_31.setText(ultimateHeight[5]);
            tv_32.setText(etaheight[5]);
            tv_34.setText(RH[5]);
            tv_35.setText(temperature[5]);
            tv_36.setText(air_purifer[5]);
            tv_37.setText(details[1]);
            tv_38.setText(fertilizers[5]);
            tv_39.setText(pruning[5]);
            tv_40.setText(watering[5]);
            tv_41.setText(repotting[5]);
            tv_42.setText(toxic[5]);
            tv_43.setText(price[5]);
            tv_44.setText(sun[5]);
            tv_45.setText(viability[5]);

        }
        else if(Objects.equals(details[0], "Aloe Striatula")){
            tv_30.setText(ultimateSpeed[6]);
            tv_31.setText(ultimateHeight[6]);
            tv_32.setText(etaheight[6]);
            tv_34.setText(RH[6]);
            tv_35.setText(temperature[6]);
            tv_36.setText(air_purifer[6]);
            tv_37.setText(details[1]);
            tv_38.setText(fertilizers[6]);
            tv_39.setText(pruning[6]);
            tv_40.setText(watering[6]);
            tv_41.setText(repotting[6]);
            tv_42.setText(toxic[6]);
            tv_43.setText(price[6]);
            tv_44.setText(sun[6]);
            tv_45.setText(viability[6]);

        }
        else{
            tv_30.setText(ultimateSpeed[7]);
            tv_31.setText(ultimateHeight[7]);
            tv_32.setText(etaheight[7]);
            tv_34.setText(RH[7]);
            tv_35.setText(temperature[7]);
            tv_36.setText(air_purifer[7]);
            tv_37.setText(details[1]);
            tv_38.setText(fertilizers[7]);
            tv_39.setText(pruning[7]);
            tv_40.setText(watering[7]);
            tv_41.setText(repotting[7]);
            tv_42.setText(toxic[7]);
            tv_43.setText(price[7]);
            tv_44.setText(sun[7]);
            tv_45.setText(viability[7]);

        }








    }
}