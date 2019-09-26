package com.example.rertofitexaple;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserEditFragment extends DialogFragment {

    EditText etName, etEmail, etPassword, etPhoneNum;
    Button btnRegister;
    EditResponseListener listener;

    UserModel userModel; // instance variable

    private String putUrl = "https://jaladhi-server.herokuapp.com/updateUser";

    MySingleTon singleTon;

    public UserEditFragment() {
        // Required empty public constructor
    }

    public static UserEditFragment getInstance(UserModel userModel) {
        UserEditFragment userEditFragment = new UserEditFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", userModel);
        userEditFragment.setArguments(bundle);
        return userEditFragment;
    }

    public void setResponseListener(EditResponseListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.ThemeOverlay_AppCompat_Dialog_Alert);

        Bundle bundle = getArguments();
        userModel = (UserModel) bundle.getSerializable("user");
        singleTon = MySingleTon.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_input, container, false);
        etName = view.findViewById(R.id.etName);
        etName.setEnabled(false);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etPhoneNum = view.findViewById(R.id.etPhoneNum);
        btnRegister = view.findViewById(R.id.btnRegister);

        etName.setText(userModel.getUserName());
        etEmail.setText(userModel.getEmailId());
        etPassword.setText(userModel.getPassword());
        etPhoneNum.setText(userModel.getPhNumber());
        btnRegister.setText("Update User");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putUserData();
            }
        });
        return view;
    }

    private void putUserData() { // Update
        UserModel userModel = new UserModel(); // local variable
        userModel.setUserId(this.userModel.getUserId());
        userModel.setUserName(etName.getText().toString());
        userModel.setEmailId(etEmail.getText().toString());
        userModel.setPassword(etPassword.getText().toString());
        userModel.setPhNumber(etPhoneNum.getText().toString());
        UserApiInterface userApiInterface = RetrofitClient.getInstance().create(UserApiInterface.class);

        Call<UserModel> putCall = userApiInterface.updateExistingUser(userModel);
        putCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    UserModel user = response.body();
                    Toast.makeText(getContext(), "Update success", Toast.LENGTH_LONG).show();
                    dismiss();
                    listener.updateUserList(user);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getContext(), "Insertion Failed", Toast.LENGTH_LONG).show();

            }
        });
    }

    interface EditResponseListener {
        public void updateUserList(UserModel userModel);
    }

}
