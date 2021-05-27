package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.doan.Interface.ItemClickListener;
import com.example.doan.ViewHolder.FoodViewHolder;
import com.example.doan.model.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryfoodId="";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        database=FirebaseDatabase.getInstance();
        foodList=database.getReference("Food");
        recyclerView=(RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //get Intent
        if(getIntent()!=null)
            categoryfoodId=getIntent().getStringExtra("CategoryfoodId");
        if(!categoryfoodId.isEmpty()&&categoryfoodId!=null){

            LoadListFood(categoryfoodId);

        }

    }

    private void LoadListFood(String categoryfoodId) {
        adapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryfoodId))
                //select*form Food where MenuId

        {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);
                Food local=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail=new Intent(FoodList.this,FoodDetailActivity.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey()); // gửi FoodID to new activity
                        startActivity(foodDetail);

                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }
}