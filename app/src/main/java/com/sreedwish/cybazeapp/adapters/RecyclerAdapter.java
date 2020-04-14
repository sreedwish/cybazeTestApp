package com.sreedwish.cybazeapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sreedwish.cybazeapp.R;
import com.sreedwish.cybazeapp.databinding.RecyclerItemBinding;
import com.sreedwish.cybazeapp.listeners.RecycleItemClickListener;
import com.sreedwish.cybazeapp.models.Bean_data;
import com.sreedwish.cybazeapp.utils.Logger;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<Bean_data> dataList;
    LayoutInflater inflater;

    RecycleItemClickListener listener;

    String colorBtn1 = "#03A9F4";
    String colorBtn2 = "#4CAF50";
    String colorBtn3Plus = "#1C7298";
    String colorBtn3Box = "#F44336";
    String colorBtn3Both = "#2034A6";
    String colorBtn3Super = "#FF9800";


    public RecyclerAdapter(List<Bean_data> dataList, RecycleItemClickListener listener) {
        this.dataList = dataList;

        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null){
            inflater= LayoutInflater.from(parent.getContext());
        }
        RecyclerItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.recycler_item,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Bean_data bean_data = dataList.get(position);

        holder.binding.setDat(bean_data);

        switch (bean_data.getButton()){

            case 1 :
                setColors(holder, colorBtn1);
                break;
            case 2:
                setColors(holder, colorBtn2);
                break;

            case 3:
                switch (bean_data.getT1()){

                    case "PLUS":
                        setColors(holder, colorBtn3Plus);
                        break;
                    case "BOX":
                        setColors(holder, colorBtn3Box);
                        break;
                    case "BOTH":
                        setColors(holder, colorBtn3Both);
                        break;
                    case "SUPER":
                        setColors(holder, colorBtn3Super);
                        break;

                }

                break;


        }


        holder.binding.chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bean_data.setChecked(!bean_data.isChecked());

                listener.onItemCheckClick(bean_data, position);
            }
        });





    }

    private void setColors(ViewHolder holder, String color){

        holder.binding.tvT1.setTextColor(Color.parseColor(color));
        holder.binding.tvT2.setTextColor(Color.parseColor(color));
        holder.binding.tvT3.setTextColor(Color.parseColor(color));
        holder.binding.tvT4.setTextColor(Color.parseColor(color));

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerItemBinding binding;

        public ViewHolder(@NonNull  RecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
