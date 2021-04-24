package com.example.appnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterAccount extends AppCompatActivity {
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnview = (Button) findViewById(R.id.btnSignin_register);
        EditText editMail = (EditText) findViewById(R.id.txtEmail_register);
        EditText editPassword = (EditText) findViewById(R.id.txtpassword_register);
        EditText editConfirmPassword = (EditText) findViewById(R.id.txtrepeatepass_register);
        db = new Database(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editMail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String confirm = editConfirmPassword.getText().toString().trim();
                int id = 0;
                if(password.equals(confirm))
                {
                    Boolean checkmail = db.checkMail(email);
                    if(checkmail == true)
                    {
                        Boolean checkInsert =  db.insertData(null, email, password, null, null);
                        if(checkInsert == true)
                        {
                            Toast.makeText(RegisterAccount.this, "Đã thêm xong rồi nhé!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(RegisterAccount.this, "OMG! Không thêm được nhé!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(RegisterAccount.this, "OMG! Trùng email rồi nhé!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RegisterAccount.this, "Password và Confirm password không trùng khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Cursor res = db.getdata();
//                if(res.getCount()==0)
//                {
//                    Toast.makeText(RegisterAccount.this, "OMG, không có gì đâu nhé!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                StringBuffer buffer = new StringBuffer();
//                while (res.moveToNext()){
//                    buffer.append("id:" + res.getString(0) +"\n");
//                    buffer.append("email:" + res.getString(1) +"\n");
//                    buffer.append("password:" + res.getString(2) +"\n");
//                    buffer.append("firstName:" + res.getString(3) +"\n");
//                    buffer.append("lastName:" + res.getString(4) +"\n");
//                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAccount.this);
//                builder.setCancelable(true);
//                builder.setTitle("User Table");
//                builder.setMessage(buffer.toString());
//                builder.show();
                startActivity(new Intent(RegisterAccount.this, MainActivity.class));
            }
        });
    }
}