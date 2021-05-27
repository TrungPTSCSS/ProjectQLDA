package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doan.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    EditText edtPhone,edtName,edtPassword;
    Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtName=(EditText)findViewById(R.id.edtName);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        edtPhone=(EditText)findViewById(R.id.edtPhone);
        btnSignUp=(Button)findViewById(R.id.btnSignUp);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference table_user=database.getReference("User");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog mDialog =new ProgressDialog(SignUpActivity.this);
                mDialog.setMessage("Vui lòng chờ");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //kiểm tra số điện thoại đã tồn tại hay chưa
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "số điện thoại đã đăng ký", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            mDialog.dismiss();
                            User user = new User(edtName.getText().toString(),edtPassword.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "", Toast.LENGTH_SHORT).show();
                            finish();
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