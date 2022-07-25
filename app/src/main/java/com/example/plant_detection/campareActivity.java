package com.example.plant_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;



public class campareActivity extends AppCompatActivity {

    TextView tvClass,tv_airID,tv_airDIS,tv_waterID,tv_waterDis,tv_humId,tv_humDis,tv_hightId,tv_hightDis,tv_tempID,tv_tempDis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campare);

//        tv_airID =(TextView) findViewById(R.id.tv_airId);
//        tv_waterID =(TextView) findViewById(R.id.tv_waterID);
//        tv_hightId =(TextView) findViewById(R.id.tv_hightID);
//        tv_tempID =(TextView) findViewById(R.id.tv_tempID);
//        tv_humId =(TextView) findViewById(R.id.tv_humID);
        tvClass =(TextView) findViewById(R.id.tv_class);
        tv_waterDis =(TextView) findViewById(R.id.tv_waterDis);
        tv_humDis =(TextView) findViewById(R.id.tv_humDis);
        tv_hightDis =(TextView) findViewById(R.id.tv_hightDis);
        tv_tempDis =(TextView) findViewById(R.id.tv_tempDis);
        tv_airDIS =(TextView) findViewById(R.id.tv_airDis);

        tvClass.setText("");
        tv_waterDis.setText("");
        tv_humDis.setText("");
        tv_hightDis.setText("");
        tv_tempDis.setText("");
        tv_airDIS.setText("");


        Random random = new Random();
        int randint= random.nextInt(8);



        String[] watering ={"Weekly, prefer to water deeply and infrequently",
                            "Weekly",
                            "Moderately in summers & sparingly in winters",
                            "Infrequent weekly",
                            "Moderately in growth stages",
                            "Moderately in summers & sparingly in winters",
                            "Moderately in summers & sparingly in winters",
                            "Water moderately"
                            };

        String[] classes ={ "Aloe Brevifolia",
                            "Aloe Aristata",
                            "Aloe x Principis",
                            "Aloe viridiflora",
                            "Aloe vera",
                            "Aloe Broomii",
                            "Aloe Striatula",
                            "Aloe massawana"
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

        String[] temperature= {
                                "68° – 80° F in day",
                                "50° – 70° F at nights",
                                "50° – 60° F",
                                "50° – 60° F",
                                "55° – 60° F",
                                "65° – 70° F ",
                                "68° – 75°  F",
                                "55° – 85°  F"
                                };
        String[] hight ={
                                "Up to 10 cm",
                                "0.1–0.5 metres",
                                "1.8–2.7 metres",
                                "0.60 metres",
                                "0.5–1 metres",
                                "1.5 metres",
                                "0.5–1 metres",
                                "0.91 metres"
                        };
        String[] humidity={
                "40%",
                "40%",
                "40%",
                "40%",
                "40%",
                "40%",
                "30-40%",
                "40%",
        };

        tvClass.setText(classes[randint]);
        tv_waterDis.setText(watering[randint]);
        tv_humDis.setText(humidity[randint]);
        tv_hightDis.setText(hight[randint]);
        tv_tempDis.setText(temperature[randint]);
        tv_airDIS.setText(air_purifer[randint]);


    }
}