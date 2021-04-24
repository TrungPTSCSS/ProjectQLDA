package com.example.appnote.ui.changepassword;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appnote.ContentMainNav;
import com.example.appnote.Database;
import com.example.appnote.MainActivity;
import com.example.appnote.R;
import com.example.appnote.User;
import com.example.appnote.ui.home.HomeFragment;

public class ChangePasswordFragment extends Fragment {
    private ChangePasswordViewModel mViewModel;
    Database db;
    Database DB;
    User user = new User();

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_fragment, container, false);
        final EditText editPassOld = view.findViewById(R.id.edit_passOld);
        final EditText editPassNew = view.findViewById(R.id.edit_passNew);
        final EditText editConfirmPass = view.findViewById(R.id.edit_confirmPass);
        DB = new Database(getActivity());
        ContentMainNav activity = (ContentMainNav) getActivity();
        db  = activity.getMyData();
        getDataUser();
        editPassOld.setText(user.password);
        Button btn_save = (Button) view.findViewById(R.id.btnChangePass);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = editPassOld.getText().toString();
                String newPass = editPassNew.getText().toString();
                String confirmPass = editConfirmPass.getText().toString();
                if(newPass.equals(confirmPass))
                {
                    Boolean checkupdatedata = DB.UpdatePass(MainActivity.IDCurrent, newPass);
                    if(checkupdatedata==true)
                    {
                        Toast.makeText(getActivity(), "Chỉnh sửa xong nhé!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "OMG, không sửa được nhé!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Password và confirm password chưa trùng khớp!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn_home = (Button) view.findViewById(R.id.btnHomePass);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContentMainNav.class));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        // TODO: Use the ViewModel
    }
    private void getDataUser(){
        Cursor cursor = db.getUser(MainActivity.IDCurrent);
        while (cursor.moveToNext()) {
            user.password = cursor.getString(2);
            break;
        }
        cursor.close();
    }
}