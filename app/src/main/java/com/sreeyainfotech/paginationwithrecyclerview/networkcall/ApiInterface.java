package com.sreeyainfotech.paginationwithrecyclerview.networkcall;

import com.google.gson.JsonObject;
import com.sreeyainfotech.paginationwithrecyclerview.model.StockNSale;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by KSTL on 03-04-2017.
 */

public interface ApiInterface
{

    @POST("Transactions/StockNSales")
    Call<List<StockNSale>> getStockNSales(@Header("Authorization") String token, @Body JsonObject task);


    // if you  need pagination another example with page number in MVVM Pattern
    //https://github.com/probelalkhan/android-paging-library-example/tree/master/app/src/main/java/net/simplifiedcoding/androidpagingexample
}
