package com.example.nvb.business;

import android.view.View;
import android.widget.Toast;

import com.example.nvb.MainActivity;
import com.example.nvb.R;
import com.example.nvb.dataholder.LoginDataHolder;
import com.example.nvb.viewholder.LoginViewHolder;
import com.mrlao.mvb.BaseBusiness;
import com.mrlao.mvb.annotation.OnClickListener;

/**
 * 登陆业务
 */
public class LoginBusiness extends BaseBusiness<LoginViewHolder, LoginDataHolder> {
    @Override
    protected void init() {

    }

    @OnClickListener(R.id.btn_login)
    public void loginButtonClick(View v) {
        if (!dataHolder.isPassVerifyCode) {
            Toast.makeText(activity, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        // 进行账号密码验证
        if ("admin".equals(viewHolder.etLoginName.getText().toString()) &&
                "123456".equals(viewHolder.etPassword.getText().toString())) {
            Toast.makeText(activity, "登陆成功", Toast.LENGTH_SHORT).show();
            startActivity(MainActivity.class);
        } else {
            Toast.makeText(activity, "账号或密码不正确，登陆失败", Toast.LENGTH_SHORT).show();
        }
    }
}
