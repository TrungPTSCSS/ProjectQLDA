package com.example.appnote;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appnote.ui.editprofile.EditProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    Database db;
    public static int IDCurrent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_form);
        db = new Database(this);
//        db.GetData("INSERT INTO listFood VALUES (null, 'Hamburger', 'Bánh mì, trứng, thịt bò, mozarella, cà chua, rau cải, ...', 'Nướng thịt bỏ, chiên trứng. Bánh mì cắt ra cho rau lên, sau đó thêm vài miếng cà chua, bỏ thịt vò và trứng lên. Sau cùng bỏ miếng phomai mozarella lên trên cùng. bỏ miếng bánh mì kia lên lại. Sau đó ghim cây vào để giữ cố định bánh mì. Sau đó đem ra thưởng thức','23/05/2021' ,null)");
//        db.QueryData("INSERT INTO Food VALUES (null, 'Hamburger', 'Bánh mì, trứng, thịt bò, mozarella, cà chua, rau cải, ...', 'Nướng thịt bỏ, chiên trứng. Bánh mì cắt ra cho rau lên, sau đó thêm vài miếng cà chua, bỏ thịt vò và trứng lên. Sau cùng bỏ miếng phomai mozarella lên trên cùng. bỏ miếng bánh mì kia lên lại. Sau đó ghim cây vào để giữ cố định bánh mì. Sau đó đem ra thưởng thức','23/05/2021' ,null)");
//        db.QueryData("INSERT INTO Food VALUES (null, 'Cháo', 'Bánh mì, trứng, thịt bò, mozarella, cà chua, rau cải, ...', 'Nướng thịt bỏ, chiên trứng. Bánh mì cắt ra cho rau lên, sau đó thêm vài miếng cà chua, bỏ thịt vò và trứng lên. Sau cùng bỏ miếng phomai mozarella lên trên cùng. bỏ miếng bánh mì kia lên lại. Sau đó ghim cây vào để giữ cố định bánh mì. Sau đó đem ra thưởng thức','23/05/2021' ,null)");
//        db.QueryData("INSERT INTO Food VALUES (null, 'Phở Hà Nội', 'Bánh mì, trứng, thịt bò, mozarella, cà chua, rau cải, ...', 'Nướng thịt bỏ, chiên trứng. Bánh mì cắt ra cho rau lên, sau đó thêm vài miếng cà chua, bỏ thịt vò và trứng lên. Sau cùng bỏ miếng phomai mozarella lên trên cùng. bỏ miếng bánh mì kia lên lại. Sau đó ghim cây vào để giữ cố định bánh mì. Sau đó đem ra thưởng thức','23/05/2021' ,null)");

        Button btn_login = findViewById(R.id.btnLogin);
        EditText mail = (EditText) findViewById(R.id.txtEmai_login);
        EditText pass = (EditText) findViewById(R.id.txtpassword_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString().trim();
                String password = pass.getText().toString().trim();
                Boolean checklogin = db.login(email, password);
                if(checklogin == true)
                {
                    int id=getIdFromEmail(mail.getText().toString());
                    MainActivity.IDCurrent=id;
                    startActivity(new Intent(MainActivity.this, ContentMainNav.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
                }
            }

        });
        Button btn_addAccount = findViewById(R.id.btnAddAccount);
        btn_addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterAccount.class));
            }
        });
        Button btn_exit = findViewById(R.id.btnExit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Thoát ứng dụng", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String getEmail(String email)
    {
        return email;
    }
    private int getIdFromEmail(String email)
    {
        String query = "SELECT * FROM Users WHERE email= '"+email+"';";
        SQLiteDatabase DB = db.getWritableDatabase();
        Cursor cursor = DB.rawQuery(query, null);
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            break;
        }
        cursor.close();
        db.close();
        return id;
    }
}