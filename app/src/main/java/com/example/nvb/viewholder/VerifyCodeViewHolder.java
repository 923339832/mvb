package com.example.nvb.viewholder;

import android.widget.EditText;
import android.widget.TextView;

import com.example.nvb.R;
import com.mrlao.nvb.BaseViewHolder;
import com.mrlao.nvb.annotation.ViewInject;

/**
 * 登陆验证码ViewHolder
 */
public class VerifyCodeViewHolder extends BaseViewHolder {
    @ViewInject(R.id.et_login_verify_code)
    public EditText etVerifyCode;
    @ViewInject(R.id.tv_login_verify_code)
    public TextView tvVerifyCode;
}
