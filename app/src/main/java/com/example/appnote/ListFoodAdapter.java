package com.example.appnote;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//import com.example.appnauan.ui.Database.Entity.Category;

public class ListFoodAdapter extends BaseAdapter {
    final ArrayList<FoodView> listFood;
    public ListFoodAdapter(ArrayList<FoodView> listFood) {
        this.listFood = listFood;
    }


    @Override
    public int getCount() {
        return listFood.size();
    }

    @Override
    public Object getItem(int position) {
        return listFood.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listFood.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewCate;
        if(convertView == null){
            viewCate = View.inflate(parent.getContext(), R.layout.layout_danhsachmonan,null);
        }else viewCate=convertView;
        //bind dữ liệu
        FoodView food= listFood.get(position);
        ((TextView) viewCate.findViewById(R.id.titleFood)).setText(String.format(food.name));
        ((TextView) viewCate.findViewById(R.id.createdDay)).setText(String.format("Created: %s",food.Createdday));

        return viewCate;
    }
}

