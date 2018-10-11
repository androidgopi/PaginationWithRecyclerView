package com.sreeyainfotech.paginationwithrecyclerview.networkcall;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KSTL on 03-04-2017.
 */

public class ApiClient {
    private static Retrofit retrofit = null;
    private static String production_url = "http://pharmasoft.org/retailapi/";
    //  private static String test_url = "http://183.82.0.10:8013/";

    public static Retrofit getClient(Context mContext) {

        if (retrofit == null) {
            HttpLoggingInterceptor.Logger fileLogger = new HttpLoggingInterceptor.Logger() {

                @Override
                public void log(String message) {
                    writeToFile(message);
                }
            };

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(fileLogger)
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new ConnectivityInterceptor(mContext))
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(production_url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }

        return retrofit;
    }

    private static void writeToFile(String message) {
        File logFile = new File("sdcard/ProtransApp_log.file");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(message);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

   // http://pharmasoft.org/retailapi/Transactions/StockNSales

   // header  Authorization  bearer Omw3J--p1AwkLQ491cwzH6TAwahosODq0m0bdOY4IxEIQnI7kx2VOyA3DNmlZldMcYO0w9eyVfq9K_2p4rpcQvlzU1GjRmiyZIpbABuJFKLIcOUpYB6lPqhU_T2UTpxBkPtfU_3_b7FA76OnYWXjgyUisd5ND5fws-OnhSP4w1N9ZgNAQ9_RmuY9zq4CnLiI4pDRT7CAPdRPVlp2HAfnNz1yp6HM0ZCj8B8yTwOeLQVXYBddTq_LgHNnEVh5L_os8edjJPsyR7zQFP-kfl9xodEgOQ39RkDvM03L8M79fFGMEOiqRPYdE5k1n4Y8RNZwtZajQcgSN3YI08_XM25472cocbTmV1CGEfWOdjnTUbbSFkM2F9T3FDUNzmiczVJ7PztYe6GvcELFcxiUx6Wg8Q

 //   {"ClientId":"-1","ProductId":"Retail","ItemGrpId":"-1","Offset":"0","SortColName":"Name","SortColOrder":"asc","CMPName":"KANUMURI PHARMACY","DType":"CM"}

}
