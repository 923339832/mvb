package com.example.nvb.viewholder;

import android.widget.EditText;

import com.example.nvb.R;
import com.mrlao.mvb.BaseViewHolder;
import com.mrlao.mvb.annotation.ViewInject;

/**
 * 用户登陆控件ViewHolder
 */
public class LoginViewHolder extends BaseViewHolder {
    // 登陆账号控件
    @ViewInject(R.id.et_login_name)
    public EditText etLoginName;
    // 登陆密码控件
    @ViewInject(R.id.et_login_password)
    public EditText etPassword;
}
