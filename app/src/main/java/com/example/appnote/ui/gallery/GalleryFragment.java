package com.example.appnote.ui.gallery;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.appnote.listYourFood;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
//    ListFoodAdapter listFoodAdapter;
    private GalleryViewModel galleryViewModel;
    Database database;
    ArrayList<FoodView> listFood;
    ArrayList<listYourFood> yourFood;
    public static int stt;
    ListFoodAdapter listFoodAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        database = new Database(getContext());
//        final ListView listViewFood = (ListView) root.findViewById(R.id.lvyourFood);
        final ListView listViewFood = root.findViewById(R.id.lvyourFood);
        listFood=new ArrayList<>();
        yourFood= new ArrayList<>();
        getDataYourFood();
        listFoodAdapter = new ListFoodAdapter(listFood);
        listViewFood.setAdapter(listFoodAdapter);
        listFoodAdapter.notifyDataSetChanged();
        listViewFood.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                 stt = position;
//                Toast.makeText(getActivity(),position,Toast.LENGTH_LONG).show();
                return false;
            }
        });
        registerForContextMenu(listViewFood);
        setHasOptionsMenu(true);
        return root;
    }
    private void getDataYourFood(){
        Cursor dataYourFood=database.GetData("SELECT * FROM yourFood where idUser="+MainActivity.IDCurrent+"");
        yourFood.clear();
        while (dataYourFood.moveToNext()){
            int id=dataYourFood.getInt(0);
            int idFood = dataYourFood.getInt(1);
            int idUser = dataYourFood.getInt(2);
            yourFood.add(new listYourFood(id,idFood,idUser));

            Cursor dataFood = database.GetData("SELECT * FROM Food where id= "+idFood+"");
            while (dataFood.moveToNext()){
                int idYourFood=dataFood.getInt(0);
                String name = dataFood.getString(1);
                String nguyenLieu= dataFood.getString(2);
                String congthuc=dataFood.getString(3);
                String created = dataFood.getString(4);
                byte[] img = dataFood.getBlob(5);
                listFood.add(new FoodView(idYourFood,name,nguyenLieu,congthuc,created,img));
            }
        }
        listFood.size();
    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1,v.getId(),1,"Xóa");
//        menu.add(2,v.getId(),2,"Xóa");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    //Chọn option trong menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getTitle()=="Xóa"){
//            Toast.makeText(getContext(),"xóa",Toast.LENGTH_SHORT).show();
            Deldialog();
//            database.QueryData("INSERT INTO yourFood VALUES(null, "+ idFood+", "+ MainActivity.IDCurrent+") ");
//            Toast.makeText(getContext(),stt,Toast.LENGTH_SHORT).show();
//            fix=true;
//            Fixdialog(listCategory.get(stt),stt);
        }
        return super.onContextItemSelected(item);
    }
    public void Deldialog(){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.yourfood_delete);
        Button close = (Button) dialog.findViewById(R.id.btnNo);
        Button addStatus =(Button) dialog.findViewById(R.id.btnYes);
        dialog.show();
//        status.setText(oldStatus);
        //BtnCancel
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //BtnOk Xóa
        addStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Cursor a = database.GetData("SELECT id FROM Food WHERE name= "+listFood.get(stt).name +"");
                database.QueryData("DELETE FROM yourFood WHERE idFood = "+ yourFood.get(stt).idFood +" AND idUser="+MainActivity.IDCurrent+"");
//                getDataYourFood();
                listFoodAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"Xóa thành công!",Toast.LENGTH_SHORT).show();
                listFood.remove(stt);
                listFoodAdapter.notifyDataSetChanged();
                dialog.cancel();
//                xoa=index+1;
//                database.QueryData("DELETE FROM Status WHERE Name = '"+ listStatus.get(index).name +"' AND Created ='" + listStatus.get(index).Created +"' AND UserID="+MainActivity.IDCurrent+"");

//                database.QueryData("DELETE FROM Status WHERE StatusName = '"+ listStatus.get(index).name +"' AND Created='"+ listStatus.get(index).Created +"' ");
//                getDataStatus();
//                dialog.cancel();
//                Toast.makeText(getContext(),"Xóa thành công!",Toast.LENGTH_SHORT).show();

//                statusListViewAdapter.notifyDataSetChanged();
            }
        });
    }
}