package com.example.plant_detection;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plant_detection.ml.PlantModel;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.model.Model;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class plant_detection extends AppCompatActivity {

    Button picbtn,gallerybtn;
    ImageView imageView;
    TextView tv_type, tv_status,tv_dis,tv_hum,tv_growth,tv_suc,tv_leaf;
    int IMAGE_SIZE=96;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detection);
        picbtn = (Button) findViewById(R.id.picbtn);
        gallerybtn = (Button) findViewById(R.id.gallerybtn);
        imageView= (ImageView) findViewById(R.id.imageView);

        tv_type= (TextView) findViewById(R.id.tv_type);
        tv_status= (TextView) findViewById(R.id.tv_status);
        tv_dis = (TextView) findViewById(R.id.tv_dis);
        tv_hum= (TextView) findViewById(R.id.tv_hum);
        tv_growth= (TextView) findViewById(R.id.tv_growth);
        tv_suc= (TextView) findViewById(R.id.tv_suc);
        tv_leaf= (TextView) findViewById(R.id.tv_leaf);

        tv_type.setText("");
        tv_status.setText("");
        tv_dis.setText("");
        tv_hum.setText("");
        tv_growth.setText("");
        tv_suc.setText("");
        tv_leaf.setText("");


        picbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraintent,3);
                }else{
                    requestPermissions(new String[]{Manifest.permission.CAMERA},100);
                }

            }
        });
        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                tv_type.setText("");
                tv_status.setText("");
                tv_dis.setText("");
                tv_hum.setText("");
                tv_growth.setText("");
                tv_suc.setText("");
                tv_leaf.setText("");

                Intent galleryIntent= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,1);
//                classified.setText("");
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==3){
                Bitmap image= (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(),image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image,dimension,dimension);
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image,IMAGE_SIZE,IMAGE_SIZE,false);
                classifyImage(image);
            }else{
                Uri dat = data.getData();
                Bitmap image= null;
                try {
                     image = MediaStore.Images.Media.getBitmap(this.getContentResolver(),dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(image);
                image = Bitmap.createScaledBitmap(image,IMAGE_SIZE,IMAGE_SIZE,false);
                classifyImage(image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void classifyImage(Bitmap image) {

        try {
            PlantModel model = PlantModel.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 96, 96, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer= ByteBuffer.allocateDirect(4*IMAGE_SIZE * IMAGE_SIZE *3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intvalues = new int[IMAGE_SIZE * IMAGE_SIZE];
            image.getPixels(intvalues,0,image.getWidth(),0,0,image.getWidth(),image.getHeight());
            int pixel=0;
            for(int i=0;i<IMAGE_SIZE;i++){
                for (int j =0; j < IMAGE_SIZE;j++){
                    int val = intvalues[pixel++]; //RGB
                    byteBuffer.putFloat(((val>>16) & 0xFF)*(1.f/255));
                    byteBuffer.putFloat(((val>>8) & 0xFF)*(1.f/255));
                    byteBuffer.putFloat((val & 0xFF)*(1.f/255));

                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            PlantModel.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidence = outputFeature0.getFloatArray();

            int maxtype=0;
            float typeConfidence=0;
            for (int i =0; i< 9;i++){
                if(confidence[i] > typeConfidence){
                    typeConfidence= confidence[i];
                    maxtype=i;
                }
            }
            String[] types = {"Aloe brevifolia", "Aloe vera", "Aloe aristata",
                    "Aloe x principis", "Aloe viridiflora", "Aloe Barbedensis",
                    "Aloe Broomii", "Aloe Striatula", "Aloe massawana"};

            int maxStatus=0;
            float statusConfidence=0;
            int iterS =0;
            for (int i =9; i< 11;i++){
                if(confidence[i] > statusConfidence){
                    statusConfidence= confidence[i];
                    maxStatus=iterS;
                    iterS++;
                }
            }

            String[] status= {"Healthy","Non-Healthy"};

            int disStatus=0;
            float disConfidence=0;
            int iterD =0;
            for (int i =11; i< 13;i++){
                if(confidence[i] > disConfidence){
                    disConfidence= confidence[i];
                    disStatus=iterD;
                    iterD++;
                }
            }

            String[] dis = {"absent","detected"};

            int HumidityStatus=0;
            float HumidityStatusConfidence=0;
            int iterH=0;
            for (int i =13; i< 15;i++){
                if(confidence[i] > HumidityStatusConfidence){
                    HumidityStatusConfidence= confidence[i];
                    HumidityStatus=iterH;
                    iterH++;
                }
            }
            String[] Humidity ={"enough","lacking"};

            int StageGrowthStatus=0;
            float StageGrowthStatusConfidence=0;
            int iterG=0;
            for (int i =15; i< 19;i++){
                if(confidence[i] > StageGrowthStatusConfidence){
                    StageGrowthStatusConfidence= confidence[i];
                    StageGrowthStatus=iterG;
                    iterG++;

                }
            }

            String[] growthStage = {"mature","growing stage","germinating","flowering"};

            int TypeGrowthStatus=0;
            float TypeGrowthStatusConfidence=0;
            int iterGT=0;
            for (int i =19; i< 23;i++){
                if(confidence[i] > TypeGrowthStatusConfidence){
                    TypeGrowthStatusConfidence= confidence[i];
                    TypeGrowthStatus=iterGT;
                    iterGT++;
                }
            }

            String[] growthType = {"succulent","perennial","shrubby","herby","arborescent"};

            int leafStatus=0;
            float leafStatusConfidence=0;
            int iterl=0;
            for (int i =23; i< 27;i++){
                if(confidence[i] > leafStatusConfidence){
                    leafStatusConfidence= confidence[i];
                    leafStatus=iterl;
                    iterl++;
                }
            }

            String[] leaf = {"small","medium","large"};

//            results =               "Type: "+ types[maxtype]
//                                + "\nStatus: "+ status[maxStatus]
//                                + "\nDiscoloration: "+ dis[disStatus]
//                                + "\nHumidity: "+ Humidity[HumidityStatus]
//                                +"\nGrowth: "+ growthStage[StageGrowthStatus]
//                                +"\nGrowth Type: "+ growthType[TypeGrowthStatus]
//                                +"\nleaf size: "+leaf[leafStatus];


            tv_type.setText("Type: "+ types[maxtype]);
            tv_status.setText("Status: "+ status[maxStatus]);
            tv_dis.setText("Discoloration: "+ dis[disStatus]);
            tv_hum.setText("Humidity: "+ Humidity[HumidityStatus]);
            tv_growth.setText("Growth: "+ growthStage[StageGrowthStatus]);
            tv_suc.setText("Growth Type: "+growthType[TypeGrowthStatus]);
            tv_leaf.setText("leaf size: "+leaf[leafStatus]);


//
            //String result= Integer.toString(confidence.length);

//            String results = Integer.toString(maxtype) + "\n"
//                            +Integer.toString(maxStatus) + "\n"
//                            +Integer.toString(disStatus) + "\n"
//                            +Integer.toString(HumidityStatus) + "\n"
//                            +Integer.toString(StageGrowthStatus) + "\n"
//                            +Integer.toString(TypeGrowthStatus) + "\n"
//                             +Integer.toString(leafStatus);
            //classified.setText(results);



            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }
}