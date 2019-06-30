package com.aritra.mobioticspoc.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aritra.mobioticspoc.R;
import com.aritra.mobioticspoc.domain.model.CurrencyDTO;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoHolder> {

    private final Context mContext;
    List<CurrencyDTO> currencyFiles;

    public VideoListAdapter(List<CurrencyDTO> mCurrencyFiles, Context context) {
        this.currencyFiles = mCurrencyFiles;
        mContext = context;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.video_unit, viewGroup, false);
        return new VideoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder videoHolder, int i) {
        videoHolder.currency.setText(mContext.getString(R.string.currency)+currencyFiles.get(i).getCurrency());
        videoHolder.currencyLong.setText(mContext.getString(R.string.currency_long)+currencyFiles.get(i).getCurrencyLong());
        videoHolder.txFree.setText(mContext.getString(R.string.tx_free)+String.valueOf(currencyFiles.get(i).getTxFee()==null?0.0:currencyFiles.get(i).getTxFee()));

   }

    @Override
    public int getItemCount() {
        return this.currencyFiles.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        TextView currency, currencyLong,txFree;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            currency = (TextView) itemView.findViewById(R.id.currency_text);
            currencyLong = (TextView) itemView.findViewById(R.id.currency_long_text);
            txFree = (TextView) itemView.findViewById(R.id.txfree_text);
        }
    }
}
