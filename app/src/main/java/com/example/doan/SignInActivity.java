package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doan.Common.Common;
import com.example.doan.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        edtPhone=(EditText)findViewById(R.id.edtPhone);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference table_user=database.getReference("User");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog mDialog =new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Vui lòng chờ");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            mDialog.dismiss();
                            //Get User
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Common.currentUser=user;
                                Intent homeIntent=new Intent(SignInActivity.this,HomeActivity.class);
                                startActivity(homeIntent);
                                finish();
                                //startActivity(new Intent(SignInActivity.this,HomeActivity.class));

                            } else {

                                Toast.makeText(SignInActivity.this, "Đăng nhập thất bại kiểm tra lại mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignInActivity.this, "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}