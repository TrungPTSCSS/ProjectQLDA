package com.example.doan.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.Common.Common;
import com.example.doan.FoodList;
import com.example.doan.Interface.ItemClickListener;
import com.example.doan.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener {
    public TextView food_name;
    public ImageView food_image;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public FoodViewHolder( View itemView) {
        super(itemView);
        food_name=(TextView)itemView.findViewById(R.id.food_name);
        food_image=(ImageView)itemView.findViewById(R.id.food_image);
        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int stt=getAdapterPosition();
                FoodList.position=stt;
                return false;
            }
        });
    }
    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        contextMenu.add(0,0,getAdapterPosition(), Common.ADD);
    }
}


