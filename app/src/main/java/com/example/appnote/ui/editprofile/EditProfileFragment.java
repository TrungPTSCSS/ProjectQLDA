package com.example.appnote.ui.editprofile;

import androidx.lifecycle.ViewModelProvider;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnote.ContentMainNav;
import com.example.appnote.Database;
import com.example.appnote.MainActivity;
import com.example.appnote.R;
import com.example.appnote.RegisterAccount;
import com.example.appnote.User;
import com.example.appnote.ui.home.HomeFragment;

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel mViewModel;
    Database db;
    Database DB;
    String getMail;
    User user = new User();
    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        final EditText editMail = view.findViewById(R.id.edit_mail);
        final EditText editFirstName = view.findViewById(R.id.edit_firstName);
        final EditText editLastName = view.findViewById(R.id.edit_lastName);
        DB = new Database(getActivity());
        ContentMainNav activity = (ContentMainNav) getActivity();
        db  = activity.getMyData();
        getDataUser();

        editMail.setText(user.email);
        editFirstName.setText(user.firstname);
        editLastName.setText(user.lastname);
        Button btn_save = (Button) view.findViewById(R.id.btnChange);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editFirstName.getText().toString().trim();
                String lastName = editLastName.getText().toString().trim();
                String email = editMail.getText().toString().trim();
                Boolean checkupdatedata = DB.UpdateData(MainActivity.IDCurrent, email, firstName, lastName);
                if(checkupdatedata==true)
                {
                    Toast.makeText(getActivity(), "Chỉnh sửa xong nhé!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "OMG, không sửa được nhé!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn_home = (Button) view.findViewById(R.id.btnHome);
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
        mViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        // TODO: Use the ViewModel
    }
    private void getDataUser(){
        Cursor cursor = db.getUser(MainActivity.IDCurrent);
        while (cursor.moveToNext()) {
            user.firstname = cursor.getString(3);
            user.lastname = cursor.getString(4);
            user.email = cursor.getString(1);
            break;
        }
        cursor.close();
    }
}