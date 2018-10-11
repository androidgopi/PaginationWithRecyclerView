package com.sreeyainfotech.paginationwithrecyclerview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.google.gson.JsonObject;
import com.sreeyainfotech.paginationwithrecyclerview.adapter.StockSalesAdapter;
import com.sreeyainfotech.paginationwithrecyclerview.custom.WrapContentLinearLayoutManager;
import com.sreeyainfotech.paginationwithrecyclerview.model.StockNSale;
import com.sreeyainfotech.paginationwithrecyclerview.networkcall.ApiClient;
import com.sreeyainfotech.paginationwithrecyclerview.networkcall.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog data_Dialog;
    public List<StockNSale> stockNSaleList = new ArrayList<>();

    private ApiInterface apiService;
    private Call<List<StockNSale>> call_stocknsales;
    private RecyclerView stock_recycle_view;
    private StockSalesAdapter stockSales_adapter;

     //if you  have to use page number put it statically
   // private static final int FIRST_PAGE = 1;
    //FIRST_PAGE+1

    public List<StockNSale> stockNSaleReportList = new ArrayList<>();
    private String itemId = "-1";


    String fromActivity = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ApiClient.getClient(MainActivity.this).create(ApiInterface.class);

       findViewes();

        updateRecyclerView();

        getStocknSalesData();

    }

    private void findViewes() {

        stock_recycle_view=(RecyclerView)findViewById(R.id.stock_recycle_view);

    }


    private void getStocknSalesData() {
        if (stockNSaleList.size() == 0) {
            data_Dialog = new ProgressDialog(MainActivity.this);
            data_Dialog.getWindow().setGravity(Gravity.BOTTOM);
            data_Dialog.setMessage("Loading...");
            data_Dialog.setCanceledOnTouchOutside(false);
            data_Dialog.setCancelable(false);
            if (!data_Dialog.isShowing()) {
                data_Dialog.show();
            }
        }
        // if you want to post Page number u can send First page
        // FIRST_PAGE

        JsonObject purchase_InputParam = new JsonObject();
        purchase_InputParam.addProperty("ClientId", "-1");
        purchase_InputParam.addProperty("ProductId", "Retail");
        purchase_InputParam.addProperty("ItemGrpId", "-1");
        purchase_InputParam.addProperty("Offset", "0");
        purchase_InputParam.addProperty("SortColName", "Name");
        purchase_InputParam.addProperty("SortColOrder", "asc");
        purchase_InputParam.addProperty("CMPName", "KANUMURI PHARMACY");
        purchase_InputParam.addProperty("DType", "CM");

        String token = "bearer Omw3J--p1AwkLQ491cwzH6TAwahosODq0m0bdOY4IxEIQnI7kx2VOyA3DNmlZldMcYO0w9eyVfq9K_2p4rpcQvlzU1GjRmiyZIpbABuJFKLIcOUpYB6lPqhU_T2UTpxBkPtfU_3_b7FA76OnYWXjgyUisd5ND5fws-OnhSP4w1N9ZgNAQ9_RmuY9zq4CnLiI4pDRT7CAPdRPVlp2HAfnNz1yp6HM0ZCj8B8yTwOeLQVXYBddTq_LgHNnEVh5L_os8edjJPsyR7zQFP-kfl9xodEgOQ39RkDvM03L8M79fFGMEOiqRPYdE5k1n4Y8RNZwtZajQcgSN3YI08_XM25472cocbTmV1CGEfWOdjnTUbbSFkM2F9T3FDUNzmiczVJ7PztYe6GvcELFcxiUx6Wg8Q";

        call_stocknsales = apiService.getStockNSales(token, purchase_InputParam);
        call_stocknsales.enqueue(new Callback<List<StockNSale>>() {
            @Override
            public void onResponse(Call<List<StockNSale>> call, Response<List<StockNSale>> response) {
                if (data_Dialog != null) {
                    if (data_Dialog.isShowing()) {
                        data_Dialog.dismiss();
                    }
                }
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (fromActivity.equalsIgnoreCase("fromBaseActivity")) {
                            stockNSaleReportList.clear();
                            stockNSaleReportList.addAll(response.body());
                        } else {
                            stockNSaleList.clear();
                            stockNSaleList.addAll(response.body());
                        }
                        stockSales_adapter.notifyDataChanged();
                    } else {
                        Utilities.showToast(MainActivity.this, response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StockNSale>> call, Throwable t) {
                if (data_Dialog != null) {
                    if (data_Dialog.isShowing()) {
                        data_Dialog.dismiss();
                    }
                }
                Utilities.showToast(MainActivity.this, t.getMessage());
            }
        });
    }

    private void updateRecyclerView() {

        stockSales_adapter = new StockSalesAdapter(MainActivity.this, stockNSaleList);
        stockSales_adapter.setCallBack(this);
        stockSales_adapter.setLoadMoreListener(new StockSalesAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                stock_recycle_view.post(new Runnable() {
                    @Override
                    public void run() {

                        int index = stockNSaleList.size() + 1;
                        fromActivity = "";

                        // if you want to post Page number u can send First page
                        // FIRST_PAGE +1
                        getStocknSalesDataWithOffset(itemId, String.valueOf(index), Utilities.SortColName, Utilities.SortColOrder);
                    }
                });
            }
        });

        stock_recycle_view.setHasFixedSize(true);
        stock_recycle_view.setLayoutManager(new WrapContentLinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        stock_recycle_view.setAdapter(stockSales_adapter);
    }


    private void getStocknSalesDataWithOffset(final String ItemGrpId, final String offset, String SortColName, String SortColOrder) {

        StockNSale mModel = new StockNSale();
        mModel.setName("load");
        stockNSaleList.add(mModel);
        stockSales_adapter.notifyItemInserted(stockNSaleList.size() - 1);

        data_Dialog = new ProgressDialog(MainActivity.this);
        data_Dialog.setMessage("Loading...");
        data_Dialog.setCanceledOnTouchOutside(false);
        data_Dialog.setCancelable(false);
        if (!data_Dialog.isShowing()) {
            data_Dialog.show();
        }

        String clientId = Utilities.getPref(MainActivity.this, "ClientID", "");
        String productName = Utilities.getPref(MainActivity.this, "ProductName", "").split(" ")[0];

        JsonObject purchase_InputParam = new JsonObject();
        purchase_InputParam.addProperty("ClientId", "-1");
        purchase_InputParam.addProperty("ProductId", "Retail");
        purchase_InputParam.addProperty("ItemGrpId", "-1");
        purchase_InputParam.addProperty("Offset", offset);
        purchase_InputParam.addProperty("SortColName", "Name");
        purchase_InputParam.addProperty("SortColOrder", "asc");
        purchase_InputParam.addProperty("CMPName", "KANUMURI PHARMACY");
        purchase_InputParam.addProperty("DType", "CM");

        String token = "bearer Omw3J--p1AwkLQ491cwzH6TAwahosODq0m0bdOY4IxEIQnI7kx2VOyA3DNmlZldMcYO0w9eyVfq9K_2p4rpcQvlzU1GjRmiyZIpbABuJFKLIcOUpYB6lPqhU_T2UTpxBkPtfU_3_b7FA76OnYWXjgyUisd5ND5fws-OnhSP4w1N9ZgNAQ9_RmuY9zq4CnLiI4pDRT7CAPdRPVlp2HAfnNz1yp6HM0ZCj8B8yTwOeLQVXYBddTq_LgHNnEVh5L_os8edjJPsyR7zQFP-kfl9xodEgOQ39RkDvM03L8M79fFGMEOiqRPYdE5k1n4Y8RNZwtZajQcgSN3YI08_XM25472cocbTmV1CGEfWOdjnTUbbSFkM2F9T3FDUNzmiczVJ7PztYe6GvcELFcxiUx6Wg8Q";


        call_stocknsales = apiService.getStockNSales(token, purchase_InputParam);
        call_stocknsales.enqueue(new Callback<List<StockNSale>>() {
            @Override
            public void onResponse(Call<List<StockNSale>> call, Response<List<StockNSale>> response) {
                if (data_Dialog != null) {
                    if (data_Dialog.isShowing()) {
                        data_Dialog.dismiss();
                    }
                }
                if (response.isSuccessful()) {
                    if (response.body() != null) {


                        stockNSaleList.remove(stockNSaleList.size() - 1);

                        if (response.body().size() > 0) {
                            if (fromActivity.equalsIgnoreCase("fromBaseActivity")) {
                                stockNSaleReportList.addAll(response.body());
                            } else {
                                stockNSaleList.addAll(response.body());
                            }
                        } else {
                            stockSales_adapter.setMoreDataAvailable(false);
                        }

                        stockSales_adapter.notifyDataChanged();
                    } else {
                        Utilities.showToast(MainActivity.this, response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StockNSale>> call, Throwable t) {
                if (data_Dialog != null) {
                    if (data_Dialog.isShowing()) {
                        data_Dialog.dismiss();
                    }
                }
                Utilities.showToast(MainActivity.this, t.getMessage());
            }
        });
    }


    public void closingStockCallback(String s, String s1) {


    }
}
