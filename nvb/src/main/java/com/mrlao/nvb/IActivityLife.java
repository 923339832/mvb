package com.mrlao.nvb;

import android.content.Intent;
import android.view.KeyEvent;

/**
 * Activity生命周期
 *
 * @author mr-lao
 * @date 2018年05月16日
 */
public interface IActivityLife {
    /**
     * 处理从Activity跳到另外一个Activity返回来的数据（需要用到的话，子类重写此方法即可，不需要用到的话，请忽略）
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        结果
     */
    boolean onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     * 请在Activity的onDestroy()方法中调用
     */
    boolean onDestroy();

    /**
     * 请在Activity的onResume()方法中调用
     */
    void onResume();

    /**
     * 请在Activity的onPause()方法中调用
     */
    void onPause();

    /**
     * 请在Activity的onStop()方法中调用
     */
    void onStop();

    /**
     * 请在Activity的onStart()方法中调用
     */
    void onStart();

    /**
     * 请在Activity的onRestart()方法中调用
     */
    void onRestart();

    /**
     * 请在Activity的onRestart()方法中调用
     */
    boolean onKeyDown(int keyCode, KeyEvent event);

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}
