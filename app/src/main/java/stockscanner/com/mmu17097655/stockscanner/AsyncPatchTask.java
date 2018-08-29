package stockscanner.com.mmu17097655.stockscanner;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class AsyncPatchTask extends android.os.AsyncTask<String, Void, String> {

    String beacon;
    String receiverLocation;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public AsyncPatchTask(String beacon, String receiverLocation){
        this.beacon = beacon;
        this.receiverLocation = receiverLocation;
    }


    @Override
    protected String doInBackground(String... strings) {

        patchRequest(beacon, receiverLocation);

        Log.d("APP_DEBUG", "SENDING PATCH REQUEST");
        Log.d("APP_DEBUG", "PARAMS ON TASK : " + beacon + ": " + receiverLocation);
        return "Executed!!";

    }

    public void patchRequest(String beacon, String receiverLocation) {



        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();


        //GSON

        //Retrofit : MUST CHECK

        String json = "{\"beacon\": \""+beacon+"\"," +
                "    \"itemLocation\": \""+receiverLocation+"\"" +
                "  }";

        /*
        RequestBody formBody = new FormBody.Builder()
                .add("beacon", beacon)
                .add("itemLocation", receiverLocation)
                .build();
    */
        RequestBody formBody = RequestBody.create(JSON, json);


        Request request = new Request.Builder()
                .url("http://stocktrackingapi-env.xjkd2hgrqt.eu-west-2.elasticbeanstalk.com/api/products")
                .patch(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.d("APP_DEBUG", response.code() + ": " + response.message());

            // Do something with the response.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
