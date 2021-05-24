package com.example.appnote.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appnote.Database;
import com.example.appnote.FoodView;
import com.example.appnote.ListFoodAdapter;
import com.example.appnote.MainActivity;
import com.example.appnote.R;

import java.sql.Blob;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<FoodView> listFood;
//    ListFoodAdapter cateFoodViewAdapter;
    int stt;
    int idFood;
//    private HomeViewModel homeViewModel;
    ListFoodAdapter listFoodAdapter;
    private HomeViewModel homeViewModel;
    Database database;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        database = new Database(getContext());

        final ListView listViewFood = root.findViewById(R.id.lvFood);
        listFood=new ArrayList<>();
//        listFood.add( new FoodView(1,"Hamburger","a"));
//        listFood.add( new FoodView(2,"Cháo","a"));
//        listFood.add( new FoodView(3,"Hamburger","a"));
//        listFood.add( new FoodView(4,"Cháo","a"));
//        listFood.add( new FoodView(5,"Hamburger","a"));
//        listFood.add( new FoodView(6,"Cháo","a"));
//        listFood.add( new FoodView(7,"Hamburger","a"));
//        listFood.add( new FoodView(8,"Cháo","a"));
        getDataFood();
        listFoodAdapter= new ListFoodAdapter(listFood);
        listViewFood.setAdapter(listFoodAdapter);
        listFoodAdapter.notifyDataSetChanged();


        listViewFood.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                idFood = position + 1;
                stt=position;
//                Toast.makeText(getContext(),stt,Toast.LENGTH_LONG).show();
                return false;
            }
        });
//        listFood.add(new FoodView(1,'Hamburger','a'));
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        registerForContextMenu(listViewFood);
        setHasOptionsMenu(true);
        return root;
    }
    private void getDataFood(){
//        Cursor dataFood=database.GetData("INSERT INTO listFood VALUES (null, 'Hamburger', 'Bánh mì, trứng, thịt bò, mozarella, cà chua, rau cải, ...', 'Nướng thịt bỏ, chiên trứng. Bánh mì cắt ra cho rau lên, sau đó thêm vài miếng cà chua, bỏ thịt vò và trứng lên. Sau cùng bỏ miếng phomai mozarella lên trên cùng. bỏ miếng bánh mì kia lên lại. Sau đó ghim cây vào để giữ cố định bánh mì. Sau đó đem ra thưởng thức','23/05/2021' ,null)");
        Cursor dataFood=database.GetData("SELECT * FROM Food");
        listFood.clear();
        while (dataFood.moveToNext()){
            int id=dataFood.getInt(0);
            String name = dataFood.getString(1);
            String nguyenLieu= dataFood.getString(2);
            String congthuc=dataFood.getString(3);
            String created = dataFood.getString(4);
            byte[] img = dataFood.getBlob(5);
            listFood.add(new FoodView(id,name,nguyenLieu,congthuc,created,img));
//            listFoodAdapter.notifyDataSetChanged();
//            database =dataStatus.getString(1);
//            Toast.makeText(this,ten,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1,v.getId(),1,"Thêm vào danh sách");
//        menu.add(2,v.getId(),2,"Xóa");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    //Chọn option trong menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getTitle()=="Thêm vào danh sách"){
//            Toast.makeText(getContext(),"Thêm vào danh sách",Toast.LENGTH_SHORT).show();
            boolean testFood = database.checkFood(idFood,MainActivity.IDCurrent);
            if(testFood){
                database.QueryData("INSERT INTO yourFood VALUES(null, "+ idFood+", "+ MainActivity.IDCurrent+") ");
                Toast.makeText(getContext(),"Đã thêm món ăn",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getContext(),"Bạn đã thêm món ăn này!",Toast.LENGTH_LONG).show();
            }
//            Toast.makeText(getContext(),stt,Toast.LENGTH_SHORT).show();
//            fix=true;
//            Fixdialog(listCategory.get(stt),stt);
        }
//        }else if(item.getTitle()=="Xóa"){
//            Toast.makeText(getContext(),"Xóa",Toast.LENGTH_SHORT).show();
//            Deldialog(listCategory.get(stt),stt);
//        }
        return super.onContextItemSelected(item);
    }
}