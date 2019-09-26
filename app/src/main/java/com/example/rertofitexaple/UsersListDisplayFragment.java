package com.example.rertofitexaple;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersListDisplayFragment extends Fragment implements UserInputFragment.RegistrationResponseListener, UserAdapter.OnRowButtonClickListener, UserEditFragment.EditResponseListener {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<UserModel> userList = new ArrayList<>();
    int editedRowPosition = 0;
    Retrofit retrofit;
    UserApiInterface apiInterface;


    public UsersListDisplayFragment() {
        // Required empty public constructor
    }

    public static UsersListDisplayFragment getInstance() {
        UsersListDisplayFragment usersListDisplayFragment = new UsersListDisplayFragment();
        return usersListDisplayFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        retrofit = RetrofitClient.getInstance();
        apiInterface = retrofit.create(UserApiInterface.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_list_display, container, false);
        loadAllUserData();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        userAdapter = new UserAdapter(getActivity(), userList, this);
        recyclerView.setAdapter(userAdapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.items_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemAdd) {
            loadInputDialogFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadInputDialogFragment() {
        UserInputFragment userInputFragment = new UserInputFragment();
        userInputFragment.setResponseListener(this);
        userInputFragment.show(getFragmentManager(), "UserInput");
    }

    private void loadAllUserData() {
        userList.clear();
        Call<UserDataList> userDataListCall = apiInterface.getAllUserData();
        userDataListCall.enqueue(new Callback<UserDataList>() {
            @Override
            public void onResponse(Call<UserDataList> call, Response<UserDataList> response) {
                userList = response.body().getUsers();
                Collections.sort(userList, new UserIdComparator());
                userAdapter.setUserList(userList);
                Log.v("User List", userList.toString());
            }

            @Override
            public void onFailure(Call<UserDataList> call, Throwable t) {
                Toast.makeText(getContext(), "Get Failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void showAllUsers(UserModel userModel) {
        //loadAllUserData();
        userList.add(userModel);
        Collections.sort(userList, new UserIdComparator());
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditClicked(int position) {
        editedRowPosition = position;
        UserModel userModel = userList.get(editedRowPosition);
        UserEditFragment userEditFragment = UserEditFragment.getInstance(userModel);
        userEditFragment.setResponseListener(this);
        userEditFragment.show(getFragmentManager(), "EditFragment");
    }

    @Override
    public void updateUserList(UserModel userModel) {
//        loadAllUserData();
        // userAdapter.notifyDataSetChanged();
        userList.set(editedRowPosition, userModel);
        Collections.sort(userList, new UserIdComparator());
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteClicked(int position) { // position

        final UserModel userModel = userList.get(position);
        int userId = userModel.getUserId();
        final Call<UserModel> deleteCall = apiInterface.deleteUser(userId);
        deleteCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                deleteRowFromRecyclerView(userModel);
                Toast.makeText(getActivity(), "Delete Success " + userModel, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

                Toast.makeText(getActivity(), "Delete Failure", Toast.LENGTH_LONG).show();
            }

        });


    }

    public void deleteRowFromRecyclerView(UserModel userModel) {
        userList.remove(userModel);
        userAdapter.notifyDataSetChanged();
    }


}
