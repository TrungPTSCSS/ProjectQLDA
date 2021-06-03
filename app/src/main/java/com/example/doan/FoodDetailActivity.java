package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetailActivity extends AppCompatActivity {
    TextView food_name,food_nguyenlieu,food_cachlam;
    ImageView food_image;
    String foodId="";
    FirebaseDatabase database;
    DatabaseReference food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        database =FirebaseDatabase.getInstance();
        food=database.getReference("Food");
        food_name=(TextView)findViewById(R.id.food_name);
        food_nguyenlieu=(TextView)findViewById(R.id.food_nguyenlieu);
        food_cachlam=(TextView)findViewById(R.id.food_cachlam);
        food_image=(ImageView) findViewById(R.id.img_food);
        //get Food
        if(getIntent()!=null)
            foodId= getIntent().getStringExtra("foodId");
        if(!foodId.isEmpty())
        {
            getDetaiFood(foodId);
        }






    }

    private void getDetaiFood(String foodId) {
        food.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                Food food =datasnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(food.getImage())
                        .into(food_image);
                food_name.setText(food.getName());
                food_cachlam.setText(food.getCachLam());
                food_nguyenlieu.setText(food.getNguyenLieu());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}