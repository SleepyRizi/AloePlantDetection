package com.example.plant_detection;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class cascade_plant {
    private Interpreter interpreter;
    // define input size
    private int INPUT_SIZE;
    // define height and width of original frame
    private int height=0;
    private int width=0;
    // now define Gpudelegate
    // it is use to implement gpu in interpreter
    private GpuDelegate gpuDelegate=null;

    // now define cascadeClassifier for plants detection
    private CascadeClassifier cascadeClassifier;

    cascade_plant(AssetManager assetManager, Context context, String modelPath, int inputSize) throws IOException {
        INPUT_SIZE=inputSize;
        // set GPU for the interpreter

        Interpreter.Options options=new Interpreter.Options();
        gpuDelegate=new GpuDelegate();
        // add gpuDelegate to option
        options.addDelegate(gpuDelegate);
        // now set number of threads to options
        options.setNumThreads(4); // set this according to your phone
        // this will load model weight to interpreter

        interpreter=new Interpreter(loadModelFile(assetManager,modelPath),options);




        // if model is load print
        Log.d("cascade_plant","Model is loaded");

        // now we will load haarcascade classifier
        try {
            // define input stream to read classifier
            InputStream is=context.getResources().openRawResource(R.raw.cascade);
            // create a folder
            File cascadeDir=context.getDir("cascade",Context.MODE_PRIVATE);
            // now create a new file in that folder
            File mCascadeFile=new File(cascadeDir,"cascade1");
            // now define output stream to transfer data to file we created
            FileOutputStream os=new FileOutputStream(mCascadeFile);
            // now create buffer to store byte
            byte[] buffer=new byte[4096];
            int byteRead;
            // read byte in while loop
            // when it read -1 that means no data to read
            while ((byteRead=is.read(buffer)) !=-1){
                // writing on mCascade file
                os.write(buffer,0,byteRead);

            }

            //Toast.makeText(context,"Model is loaded",Toast.LENGTH_SHORT).show();
            // close input and output stream
            is.close();
            os.close();
            cascadeClassifier=new CascadeClassifier(mCascadeFile.getAbsolutePath());
            // if cascade file is loaded print
            Log.d("Cascade_plant","Classifier is loaded");


        }
        catch (IOException e){
            e.printStackTrace();
        }


    }
    public Mat recognizeImage(Mat mat_image, Context context){
        Mat a=mat_image.t();
        Core.flip(a,mat_image,1);
        a.release();
        //Core.flip(mat_image.t(),mat_image,1);


        Mat grayscaleImage= new Mat();
        Imgproc.cvtColor(mat_image,grayscaleImage,Imgproc.COLOR_RGB2GRAY);

        height = grayscaleImage.height();
        width= grayscaleImage.width();


        int absolutePlantSize=(int)(height*0.1);
        // now create MatofRect to store face
        MatOfRect plants=new MatOfRect();
        // check if cascadeClassifier is loaded or not
        if(cascadeClassifier !=null){
            // detect plant in frame
            //                                  input         output
            cascadeClassifier.detectMultiScale(grayscaleImage,plants,1.1,2,2,
                    new Size(absolutePlantSize,absolutePlantSize),new Size());
            // minimum size
        }
        String[] types = {"Aloe brevifolia", "Aloe vera", "Aloe aristata",
                "Aloe x principis", "Aloe viridiflora", "Aloe Barbedensis",
                "Aloe Broomii", "Aloe Striatula", "Aloe massawana"};
        String[] status= {"Healthy","Non-Healthy"};
        String[] dis = {"absent","detected"};
        String[] Humidity ={"enough","lacking"};
        String[] growthStage = {"mature","growing stage","germinating","flowering"};
        String[] growthType = {"succulent","perennial","shrubby","herby","arborescent"};
        String[] leaf = {"small","medium","large"};
        int fontplant = Imgproc.FONT_HERSHEY_SIMPLEX;
        Rect[] plantsArray = plants.toArray();
        for (int i=0; i< plantsArray.length; i++){
            String results="";
            //Imgproc.rectangle(mat_image,plantsArray[i].tl(),plantsArray[i].br(),new Scalar(0,255,0,255),2);
            Rect roi = new Rect((int)plantsArray[i].tl().x,(int)plantsArray[i].tl().y,
                    ((int)plantsArray[i].br().x)-(int)(plantsArray[i].tl().x),
                    ((int)plantsArray[i].br().y)-(int)(plantsArray[i].tl().y));

            Mat cropped = new Mat(grayscaleImage,roi);
            Mat cropped_rgba= new Mat(mat_image,roi);

            Bitmap bitmap = null;
            bitmap = Bitmap.createBitmap(cropped_rgba.cols(),cropped_rgba.rows(),Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(cropped_rgba,bitmap);

            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,96,96,false);

            ByteBuffer byteBuffer = converBitmapToByteBuffer(scaledBitmap);

            Object[] input= new Object[1];
            input[0] = byteBuffer;

            Map<Integer,Object> output_map= new TreeMap<>();
            float[][] confidence = new float[1][27];

            output_map.put(0,confidence);


            interpreter.runForMultipleInputsOutputs(input,output_map);

            Object plant_out = output_map.get(0);
            //Object plant_out = Array.get(output_map.get(0),0);



            //System.out.println(Array.get(plant_out,0).getClass().getName());

           float[] plant_matrix= new float[27];

            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(8);
            for(int op=0;op<27;op++){
                plant_matrix[op] = Float.parseFloat(df.format(Array.get(Array.get(plant_out,0),op)));
                //System.out.println(df.format(Array.get(Array.get(plant_out,0),op)));
            }
//            System.out.println(Arrays.toString(plant_matrix));
            int maxtype=0;
            float typeConfidence=0;
            for (int typ =0; typ< 9;typ++){
                if(plant_matrix[typ] > typeConfidence){
                    typeConfidence= plant_matrix[typ];
                    maxtype=typ;
                }
            }
//            String[] types = {"Aloe brevifolia", "Aloe vera", "Aloe aristata",
//                    "Aloe x principis", "Aloe viridiflora", "Aloe Barbedensis",
//                    "Aloe Broomii", "Aloe Striatula", "Aloe massawana"};
           // System.out.println(types[maxtype]);

            int maxStatus=0;
            float statusConfidence=0;
            int iterS =0;
            for (int st =9; st< 11;st++){
                if(plant_matrix[st] > statusConfidence){
                    statusConfidence= plant_matrix[st];
                    maxStatus=iterS;
                    iterS++;
                }
            }

//            String[] status= {"Healthy","Non-Healthy"};

            int disStatus=0;
            float disConfidence=0;
            int iterD =0;
            for (int d =11; d< 13;d++){
                if(plant_matrix[d] > disConfidence){
                    disConfidence= plant_matrix[d];
                    disStatus=iterD;
                    iterD++;
                }
            }

//            String[] dis = {"absent","detected"};

            int HumidityStatus=0;
            float HumidityStatusConfidence=0;
            int iterH=0;
            for (int hum =13; hum< 15;hum++){
                if(plant_matrix[hum] > HumidityStatusConfidence){
                    HumidityStatusConfidence= plant_matrix[hum];
                    HumidityStatus=iterH;
                    iterH++;
                }
            }
//            String[] Humidity ={"enough","lacking"};

            int StageGrowthStatus=0;
            float StageGrowthStatusConfidence=0;
            int iterG=0;
            for (int stg =15; stg< 19;stg++){
                if(plant_matrix[stg] > StageGrowthStatusConfidence){
                    StageGrowthStatusConfidence= plant_matrix[stg];
                    StageGrowthStatus=iterG;
                    iterG++;

                }
            }

//            String[] growthStage = {"mature","growing stage","germinating","flowering"};

            int TypeGrowthStatus=0;
            float TypeGrowthStatusConfidence=0;
            int iterGT=0;
            for (int gs =19; gs< 23;gs++){
                if(plant_matrix[gs] > TypeGrowthStatusConfidence){
                    TypeGrowthStatusConfidence= plant_matrix[gs];
                    TypeGrowthStatus=iterGT;
                    iterGT++;
                }
            }

//            String[] growthType = {"succulent","perennial","shrubby","herby","arborescent"};

            int leafStatus=0;
            float leafStatusConfidence=0;
            int iterl=0;
            for (int ls =23; ls< 27;ls++){
                if(plant_matrix[ls] > leafStatusConfidence){
                    leafStatusConfidence= plant_matrix[ls];
                    leafStatus=iterl;
                    iterl++;
                }
            }

//            String[] leaf = {"small","medium","large"};

//
//            String results = Integer.toString(maxtype) + "\n"
//                            +Integer.toString(maxStatus) + "\n"
//                            +Integer.toString(disStatus) + "\n"
//                            +Integer.toString(HumidityStatus) + "\n"
//                            +Integer.toString(StageGrowthStatus) + "\n"
//                            +Integer.toString(TypeGrowthStatus) + "\n"
//                             +Integer.toString(leafStatus);
//            System.out.println(results);
            results = "Type: "+ types[maxtype] + "\n Status: "+ status[maxStatus]
                    + "\n Discoloration: "+ dis[disStatus]
                    + "\n Humidity: "+ Humidity[HumidityStatus]
                    +"\n Growth: "+ growthStage[StageGrowthStatus]
                    +"\n Growth Type: "+ growthType[TypeGrowthStatus]
                    +"\n leaf size: "+leaf[leafStatus];
//            ;
            //System.out.println(results);

           int[] arrayx= {42,23,14,20,24,13,42};

            String[] parts= results.split("\n");
            int y=0;
            int y0, dy;
            y0=20; dy=4;
            int txtiter=0;
            int xiter=0;
            for (String part: parts) {

                y= y0+ txtiter* dy;
                Imgproc.putText(cropped_rgba, part
                        , new Point(arrayx[xiter], y*3), 1, 1.7, new Scalar(255, 165, 0, 255), 4);
                Imgproc.putText(cropped_rgba, part
                        , new Point(arrayx[xiter], y*3), 1, 1.7, new Scalar(0, 0, 255, 255), 2);
                xiter+=1;
                txtiter+=3;
            }






//
//            if (gender_val > 0.5) {
//
//                Imgproc.putText(cropped_rgba,"Female, "+age_value,new Point(10,20),1,1.7,new Scalar(255,0,0,255),4);
//
//            }else{
//                Imgproc.putText(cropped_rgba,"Male, "+age_value,new Point(10,20),1,1.7,new Scalar(0,0,255,255),4);
//
//            }
//            Log.d("cascade","Output"+age_value+","+gender_val);




            cropped_rgba.copyTo(new Mat(mat_image,roi));

        }
        Mat b=mat_image.t();
        Core.flip(b,mat_image,0);
        b.release();

        //Core.flip(mat_image.t(),mat_image,0);
        return mat_image;
    }

    private ByteBuffer converBitmapToByteBuffer(Bitmap scaledBitmap) {
        ByteBuffer byteBuffer;
        int size_image = 96;
        byteBuffer = ByteBuffer.allocateDirect(4 * size_image * size_image * 3);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intvalues = new int[size_image * size_image];
        scaledBitmap.getPixels(intvalues, 0, scaledBitmap.getWidth(), 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < size_image; i++){
            for (int j = 0; j < size_image; j++) {
                final int val = intvalues[pixel++];
                byteBuffer.putFloat((((val >> 16) & 0xFF)) / 255.0f);
                byteBuffer.putFloat((((val >> 8) & 0xFF)) / 255.0f);
                byteBuffer.putFloat(((val & 0xFF)) / 255.0f);
            }
        }
        return  byteBuffer;
    }



    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelpath) throws IOException{
        AssetFileDescriptor assetFileDescriptor = assetManager.openFd(modelpath);
        FileInputStream inputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset= assetFileDescriptor.getStartOffset();
        long declaredLength= assetFileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }

}
