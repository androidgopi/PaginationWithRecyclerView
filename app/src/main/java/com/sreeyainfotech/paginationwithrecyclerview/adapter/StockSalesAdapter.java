package com.sreeyainfotech.paginationwithrecyclerview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sreeyainfotech.paginationwithrecyclerview.MainActivity;
import com.sreeyainfotech.paginationwithrecyclerview.R;
import com.sreeyainfotech.paginationwithrecyclerview.Utilities;
import com.sreeyainfotech.paginationwithrecyclerview.model.StockNSale;

import java.util.List;

public class StockSalesAdapter extends RecyclerView.Adapter/*RecyclerView.Adapter<StockSalesAdapter.ViewHolder>*/ {

    private final int VIEW_ITEM = 0;
    private final int VIEW_PROG = 1;
    private StockSalesAdapter.OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading, isMoreDataAvailable = true;
    private MainActivity stockNSalesFragment;

    Context mContext;
    List<StockNSale> purchaseDataList;

    public StockSalesAdapter(Context mContext, List<StockNSale> purchaseDataList) {
        this.mContext = mContext;
        this.purchaseDataList = purchaseDataList;
    }

    @NonNull
    @SuppressLint("NewApi")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh = null;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stocksale_item_layout, parent, false);
            return new StockSalesViewHolder(v);
        } else if (viewType == VIEW_PROG) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar, parent, false);
            return new ProgressViewHolder(v);
        }
        return null;

    }

    @Override
    public int getItemViewType(int position) {
        if(purchaseDataList.get(position).getName().equals("load")){
            return VIEW_PROG;
        }else{
            return VIEW_ITEM;
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && onLoadMoreListener!=null){
            isLoading = true;
            onLoadMoreListener.onLoadMore();
        }

        if (holder instanceof StockSalesViewHolder) {

            if(position % 2 == 0){
                ((StockSalesViewHolder) holder).row_layout.setBackgroundColor(Color.WHITE);
            }else{
                ((StockSalesViewHolder) holder).row_layout.setBackgroundColor(mContext.getResources().getColor(R.color.row_bg));
            }

            StockNSale data = purchaseDataList.get(position);

            if (data.getName() != null && !data.getName().equalsIgnoreCase(""))

                if(data.getName().length()>30){
                    ((StockSalesViewHolder) holder).name_txt.setText(String.valueOf(data.getName().substring(0,30)+"..."));
                }else{
                    ((StockSalesViewHolder) holder).name_txt.setText(data.getName());
                }


            if (data.getOpeningQty() != null && !data.getOpeningQty().equalsIgnoreCase("")) {
                ((StockSalesViewHolder) holder).open_qty.setText(String.valueOf(Math.round(Double.parseDouble(data.getOpeningQty()))));
            }

            if (data.getOpenValue() != null && !data.getOpenValue().equalsIgnoreCase("")) {
                ((StockSalesViewHolder) holder).open_qty_value.setText(String.valueOf(Utilities.getFormatedDecimalNumber(Math.round(Double.parseDouble(data.getOpenValue())))));
            }

            if (data.getPurchaseQty() != null && !data.getPurchaseQty().equalsIgnoreCase("")) {
                ((StockSalesViewHolder) holder).purchase_qty.setText(String.valueOf(Math.round(Double.parseDouble(data.getPurchaseQty()))));
            }

            if (data.getPurchaseValue() != null && !data.getPurchaseValue().equalsIgnoreCase("")) {
                ((StockSalesViewHolder) holder).purchase_qty_value.setText(String.valueOf(Utilities.getFormatedDecimalNumber(Math.round(Double.parseDouble(data.getPurchaseValue())))));
            }

            if (data.getSaleQty() != null && !data.getSaleQty().equalsIgnoreCase("")) {
                ((StockSalesViewHolder) holder).sales_qty.setText(String.valueOf(Math.round(Double.parseDouble(data.getSaleQty()))));
            }

            if (data.getSaleValue() != null && !data.getSaleValue().equalsIgnoreCase("")) {
                ((StockSalesViewHolder) holder).sales_qty_value.setText(String.valueOf(Utilities.getFormatedDecimalNumber(Math.round(Double.parseDouble(data.getSaleValue())))));
            }

            if (data.getClosingQty() != null && !data.getClosingQty().equalsIgnoreCase("")) {
                ((StockSalesViewHolder) holder).closing_qty.setText(String.valueOf(Math.round(Double.parseDouble(data.getClosingQty()))));
            }

            if (data.getClosingValue() != null && !data.getClosingValue().equalsIgnoreCase("")) {
                ((StockSalesViewHolder) holder).closing_qty_value.setText(String.valueOf(Utilities.getFormatedDecimalNumber(Math.round(Double.parseDouble(data.getClosingValue())))));
            }

            ((StockSalesViewHolder) holder).name_txt.setContentDescription(data.getName()+"/"+data.getLevel());
            ((StockSalesViewHolder) holder).name_txt.setTag(data.getId());
            ((StockSalesViewHolder) holder).name_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getContentDescription().toString().split("/")[1].equalsIgnoreCase("0")) {
                        stockNSalesFragment.closingStockCallback(v.getContentDescription().toString().split("/")[0], v.getTag().toString());
                    }
                }
            });
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return purchaseDataList == null ? 0 : purchaseDataList.size();
    }

    public void setLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setCallBack(MainActivity stockNSalesFragment) {
        this.stockNSalesFragment = stockNSalesFragment;
    }

    public static class StockSalesViewHolder extends RecyclerView.ViewHolder {


        LinearLayout row_layout;
        TextView name_txt;
        TextView open_qty;
        TextView open_qty_value;
        TextView purchase_qty;
        TextView purchase_qty_value;
        TextView sales_qty;
        TextView sales_qty_value;
        TextView closing_qty;
        TextView closing_qty_value;


        public StockSalesViewHolder(View view) {
            super(view);
            row_layout = (LinearLayout) view.findViewById(R.id.row_layout);
            name_txt=(TextView)view.findViewById(R.id.name_txt);
            open_qty=(TextView)view.findViewById(R.id.open_qty);
            open_qty_value=(TextView)view.findViewById(R.id.open_qty_value);
            purchase_qty=(TextView)view.findViewById(R.id.purchase_qty);
            purchase_qty_value=(TextView)view.findViewById(R.id.purchase_qty_value);
            sales_qty=(TextView)view.findViewById(R.id.sales_qty);
            sales_qty_value=(TextView)view.findViewById(R.id.sales_qty_value);
            closing_qty=(TextView)view.findViewById(R.id.closing_qty);
            closing_qty_value=(TextView)view.findViewById(R.id.closing_qty_value);



        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
        }
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }
}


