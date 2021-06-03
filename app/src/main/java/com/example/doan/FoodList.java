package com.example.doan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.doan.Common.Common;
import com.example.doan.Interface.ItemClickListener;
import com.example.doan.ViewHolder.FoodViewHolder;
import com.example.doan.ViewHolder.FoodViewHolderAdmin;
import com.example.doan.model.FavoriteList;
import com.example.doan.model.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kotlin.text.UStringsKt;

public class FoodList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryfoodId="";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    DatabaseReference mDatabase;
    //Tìm kiếm
    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    ArrayList<Food> listFood;
    ArrayList<FavoriteList> listFavoriteFood;
    ArrayList<Food> list;
    public static int position;



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

        listFood=new ArrayList<>();
        list = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Food").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Food food = snapshot.getValue(Food.class);
                String key = snapshot.getKey();
                listFood.add(new Food( key,food.getName(),food.getImage(),food.getNguyenLieu(),food.getCachLam(),food.getMenuId()));
                for (int i=0;i<listFood.size();i++){
                    if(listFood.get(i).getMenuId().equals(categoryfoodId)){
                        list.add(listFood.get(i));
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         listFavoriteFood = new ArrayList<>();

        mDatabase.child("Favorite").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FavoriteList favori = snapshot.getValue(FavoriteList.class);
                String key = snapshot.getKey();
                listFavoriteFood.add(new FavoriteList(key,favori.getIdFood(),favori.getIdUser()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //Tim Kiem
        materialSearchBar=(MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Tìm Kiếm Đồ ăn");
        //   materialSearchBar.setSpeechMode(false);
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i1, int i2, int i3) {
                List<String> suggest=new ArrayList<String>();
                for(String search:suggestList)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //Khi mà tìm kiếm kết thúc khôi phục lại như ban đâu
                if(!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //Khi mà tìm kiếm  show kết quả
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void startSearch(CharSequence text) {
        searchAdapter= new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("name").equalTo(text.toString())
        ) {
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
                        foodDetail.putExtra("foodId",searchAdapter.getRef(position).getKey()); // gửi FoodID to new activity
                        startActivity(foodDetail);

                    }
                });

            }
        };
        recyclerView.setAdapter(searchAdapter);
    }
    //Hàm load đề xuất đồ ăn
    private void loadSuggest() {
        foodList.orderByChild("menuId").equalTo(categoryfoodId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Food item=postSnapshot.getValue(Food.class);
                            suggestList.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void LoadListFood(String categoryfoodId) {
        adapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("menuId").equalTo(categoryfoodId))
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
                        foodDetail.putExtra("foodId",adapter.getRef(position).getKey()); // gửi FoodID to new activity
                        startActivity(foodDetail);
                    }

                });

            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.ADD))
        {
            FavoriteList listFavorite = new FavoriteList(list.get(position).getId(),SignInActivity.IDUser);
            mDatabase.child("Favorite").child( "0" + (listFavoriteFood.size() + 100)).setValue(listFavorite);



            Toast.makeText(FoodList.this,"Đã Thêm",Toast.LENGTH_LONG).show();
        }


        return super.onContextItemSelected(item);

    }
}