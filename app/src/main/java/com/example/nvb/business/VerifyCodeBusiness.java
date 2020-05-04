package com.example.nvb.business;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.nvb.dataholder.LoginDataHolder;
import com.example.nvb.viewholder.VerifyCodeViewHolder;
import com.mrlao.mvb.BaseBusiness;

/**
 * 验证码业务
 * 1、向服务器获取验证码数据，并生成验证码
 * 2、监听验证码输入框，动态向服务器验证验证码是否正确
 */
public class VerifyCodeBusiness extends BaseBusiness<VerifyCodeViewHolder, LoginDataHolder> {
    private String code = null;

    @Override
    protected void init() {
        // 向服务器获取验证码数据，并生成验证码
        createVerifyCode();
        // 监听验证码输入框，动态向服务器验证验证码是否正确
        listen();
    }

    private void listen() {
        viewHolder.etVerifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = viewHolder.etVerifyCode.getText().toString();
                dataHolder.isPassVerifyCode = text.equalsIgnoreCase(code);
                if (text.length() == 4) {
                    if (dataHolder.isPassVerifyCode) {
                        Toast.makeText(activity, "验证码正确！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "验证码错误！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void createVerifyCode() {
        // 获取验证码
        new Thread() {
            @Override
            public void run() {
                code = getVerifyCodeTextFromServer();

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.tvVerifyCode.setText(code);

                        code = code.trim().replace(" ", "");
                    }
                });
            }
        }.start();
    }

    private String getVerifyCodeTextFromServer() {
        // 发送HTTP请求，获取结果，返回
        // 这里只是模拟
        return " A B C D";
    }


}
