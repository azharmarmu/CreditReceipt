package com.example.azharuddin.creditreceipt.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.azharuddin.creditreceipt.R;
import com.example.azharuddin.creditreceipt.api.UserApi;
import com.example.azharuddin.creditreceipt.model.CustomerModel;
import com.example.azharuddin.creditreceipt.model.UserModel;
import com.example.azharuddin.creditreceipt.utils.Constants;
import com.example.azharuddin.creditreceipt.utils.DialogUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.List;


/**
 * Created by azharuddin on 24/7/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context context;
    private List<UserModel> userList;

    public UserAdapter(Context context, List<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_common,
                        parent, false);

        return new UserAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final UserModel user = userList.get(position);
        holder.userName.setText(user.getName());

        holder.userEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserDialog(user);
            }
        });

        holder.userDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApi.userDBRef.child(user.getKey()).removeValue();
                userList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView userEdit, userDelete;

        MyViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.name);
            userEdit = itemView.findViewById(R.id.view_edit);
            userDelete = itemView.findViewById(R.id.delete);
        }
    }

    private void editUserDialog(final UserModel userModel) {
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        dialog.setContentView(R.layout.fragment_add);

        Window window = dialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.TOP;

        final EditText name = dialog.findViewById(R.id.et_name);
        final EditText phone = dialog.findViewById(R.id.et_phone);
        final EditText address = dialog.findViewById(R.id.et_address);

        phone.setVisibility(View.VISIBLE);
        address.setVisibility(View.GONE);

        name.append(userModel.getName());
        phone.append(userModel.getPhone());

        TextView addUser = dialog.findViewById(R.id.btn_add);
        addUser.setText("");
        addUser.append("Update");
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete user
                String key = userModel.getKey();
                UserApi.userDBRef.child(key).removeValue();

                String userName = name.getText().toString();
                String userPhone = phone.getText().toString();
                HashMap<String, Object> userMap = new HashMap<>();
                if (!userName.isEmpty()
                        && !userPhone.isEmpty()) {
                    userMap.put(Constants.USER_NAME, userName);
                    userMap.put(Constants.USER_PHONE, userPhone);
                    name.setText("");
                    phone.setText("");
                    UserApi.userDBRef.child(userName)
                            .updateChildren(userMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        DialogUtils.appToastShort(context,
                                                "User updated");
                                    } else {
                                        DialogUtils.appToastShort(context,
                                                "User not updated");
                                    }
                                }
                            });
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
