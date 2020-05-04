package com.mrlao.nvb;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.mrlao.nvb.annotation.OnClickListener;
import com.mrlao.nvb.annotation.OnItemClickListener;
import com.mrlao.nvb.annotation.OnLongClickListener;
import com.mrlao.nvb.util.AnnotationUtil;
import com.mrlao.nvb.util.ResourceIDHelper;
import com.mrlao.nvb.util.TypeUtil;
import com.mrlao.nvb.util.ViewHolderHelper;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * @param <ViewHolder>
 * @author mr-lao
 */
public abstract class BaseBusiness<ViewHolder extends IViewHolder, DataHolder> implements IBusiness<ViewHolder, DataHolder> {
    private IBusiness THIS;
    @SuppressWarnings("all")
    protected Activity activity;
    @SuppressWarnings("all")
    protected ViewHolder viewHolder;
    @SuppressWarnings("all")
    protected DataHolder dataHolder;

    @SuppressWarnings("all")
    private Map<String, Object> sesstion;

    @SuppressWarnings("all")
    public void setSesstion(Map<String, Object> sesstion) {
        this.sesstion = sesstion;
    }

    @SuppressWarnings("all")
    public BaseBusiness() {
        this.THIS = this;
    }

    /**
     * 初始化
     */
    @SuppressWarnings("all")
    protected abstract void init();


    @Override
    public void initBusiness(Activity activity, View layout) {
        ResourceIDHelper.parseResourceID(THIS);
        this.activity = activity;
        if (layout == null) {
            layout = activity.getWindow().getDecorView();
        }
        initViewHolder(layout);
        initDataHolder();
        initListeners(layout);
        init();
    }

    @SuppressWarnings("all")
    private void initDataHolder() {
        Class dataHolderClass = TypeUtil.getTypeClass(this, 1);
        if (null == dataHolderClass) {
            throw new RuntimeException(this.getClass().getName() + "中DataHolder为空");
        }
        if (dataHolderClass == NotNeedDataHolder.class) {
            return;
        }
        this.dataHolder = (DataHolder) sesstion.get(dataHolderClass.getName());
        if (null != this.dataHolder) {
            return;
        }

        try {
            this.dataHolder = (DataHolder) dataHolderClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        sesstion.put(dataHolderClass.getName(), this.dataHolder);
    }

    @Override
    public boolean needInterceptNextBusinessInit() {
        return false;
    }

    private void initListeners(View view) {
        //设置点击事件
        initClickListeners(view);
        //设置长按事件
        initLongClickListeners(view);
        //设置选项点击事件
        initItemClickListener(view);
    }

    private void initItemClickListener(View view) {
        ArrayList<OnItemClickListener> listeners = new ArrayList<>();
        ArrayList<Method> methodList = new ArrayList<>();
        AnnotationUtil.parseMethodAnnotation(OnItemClickListener.class, this.getClass(), listeners, methodList);
        if (listeners.isEmpty()) {
            return;
        }
        for (int i = 0; i < listeners.size(); i++) {
            setOnItemClickListeners(methodList.get(i), listeners.get(i), view);
        }
    }

    private void setOnItemClickListeners(final Method method, OnItemClickListener listener, View view) {
        int[] ids = listener.value();
        if (ids.length == 0) {
            ids = ResourceIDHelper.ids(listener.idsText());
        }
        if (ids.length == 0) {
            return;
        }
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    method.invoke(THIS, parent, view, position, id);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        for (int id : ids) {
            View _absListView = view.findViewById(id);
            if (!(_absListView instanceof AbsListView)) {
                throw new RuntimeException("@OnItemClickListener注解中指定的资源ID对应的View必须是AbsListView类型");
            }
            AbsListView absListView = (AbsListView) _absListView;
            absListView.setOnItemClickListener(onItemClickListener);
        }
    }

    private void initLongClickListeners(View view) {
        ArrayList<OnLongClickListener> listeners = new ArrayList<>();
        ArrayList<Method> methodList = new ArrayList<>();
        AnnotationUtil.parseMethodAnnotation(OnLongClickListener.class, this.getClass(), listeners, methodList);
        if (listeners.isEmpty()) {
            return;
        }
        for (int i = 0; i < listeners.size(); i++) {
            setOnLongClickListeners(methodList.get(i), listeners.get(i), view);
        }
    }

    private void setOnLongClickListeners(final Method method, OnLongClickListener listener, View view) {
        int[] ids = listener.value();
        if (ids.length == 0) {
            ids = ResourceIDHelper.ids(listener.idsText());
        }
        if (ids.length == 0) {
            return;
        }
        View.OnLongClickListener onlcl = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    return (Boolean) method.invoke(THIS, v);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        for (int id : ids) {
            view.findViewById(id).setOnLongClickListener(onlcl);
        }
    }

    private void initClickListeners(View view) {
        ArrayList<OnClickListener> listeners = new ArrayList<>();
        ArrayList<Method> methodList = new ArrayList<>();
        AnnotationUtil.parseMethodAnnotation(OnClickListener.class, this.getClass(), listeners, methodList);
        if (listeners.isEmpty()) {
            return;
        }
        for (int i = 0; i < listeners.size(); i++) {
            setOnClickListeners(methodList.get(i), listeners.get(i), view);
        }
    }

    private void setOnClickListeners(final Method method, OnClickListener listener, View view) {
        int[] ids = listener.value();
        if (ids.length == 0) {
            ids = ResourceIDHelper.ids(listener.idsText());
        }
        if (ids.length == 0) {
            return;
        }
        View.OnClickListener oncl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    method.invoke(THIS, v);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        for (int id : ids) {
            view.findViewById(id).setOnClickListener(oncl);
        }
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }

    @SuppressWarnings("all")
    private void initViewHolder(View layout) {
        Class viewHolderClass = TypeUtil.getTypeClass(this, 0);
        if (null == viewHolderClass) {
            throw new RuntimeException(this.getClass().getName() + "中ViewHolder为空");
        }
        if (viewHolderClass == NotNeedViewHolder.class) {
            return;
        }

        this.viewHolder = (ViewHolder) sesstion.get(viewHolderClass.getName());
        if (null != this.viewHolder) {
            return;
        }

        try {
            this.viewHolder = (ViewHolder) viewHolderClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (!viewHolder.initView(layout)) {
            ViewHolderHelper.initViewHolder(layout, this.viewHolder);
        }

        sesstion.put(viewHolderClass.getName(), this.viewHolder);
    }

    @Override
    public boolean onDestroy() {
        return false;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    @Override
    @Deprecated// 推荐使用DataHolder来代替不同Business之间的数据共享
    public void addParam(Object param, String key) {
        sesstion.put(key, param);
    }

    @SuppressWarnings("all")
    @Override
    @Deprecated// 推荐使用DataHolder来代替不同Business之间的数据共享
    public <T> T getParam(String key) {
        if (sesstion.containsKey(key)) {
            return (T) sesstion.get(key);
        }
        return null;
    }

    /**
     * 启动Activity
     *
     * @param c     需要启动的Activity类
     * @param key   键
     * @param value 值
     */
    protected void startActivity(Class c, String key, Serializable value) {
        Intent intent = new Intent(activity, c);
        intent.putExtra(key, value);
        activity.startActivity(intent);
    }

    /**
     * 启动Activity
     *
     * @param activityClass
     * @param keys
     * @param values
     */
    @SuppressWarnings("all")
    protected void startActivity(Class<? extends Activity> activityClass, String[] keys, Serializable[] values) {
        if (null == keys || values == null) {
            startActivity(activityClass);
            return;
        }
        if (keys.length != values.length) {
            throw new IllegalArgumentException("keys的数组长度必须和values的数组长度相等");
        }
        Intent intent = new Intent(activity, activityClass);
        for (int i = 0; i < keys.length; i++) {
            intent.putExtra(keys[i], values[i]);
        }
        activity.startActivity(intent);
    }

    @SuppressWarnings("all")
    protected void startActivity(Class<? extends Activity> activityClass) {
        activity.startActivity(new Intent(activity, activityClass));
    }

    /**
     * 启动Activity并获取返回值
     *
     * @param cls
     * @param requestCode 请求码【注意的是，目前发现在使用AndroidStudio编译程序的时候，requestCode的值大于1000时，程序会莫名崩溃】
     */
    @SuppressWarnings("all")
    protected void startActivityForResult(Class<? extends Activity> cls, int requestCode) {
        if (requestCode > 1000) {
            Log.w("business", "requestCode的值大于1000，如果你的程序莫名崩溃了，请将requestCode的值设置成小于1000的值，例如100");
        }
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动Activity
     *
     * @param c     需要启动的Activity类
     * @param key   键
     * @param value 值
     */
    protected void startActivityForResult(Class c, String key, Serializable value, int requestCode) {
        Intent intent = new Intent(activity, c);
        intent.putExtra(key, value);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取Activity传递的意图值
     *
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    @SuppressWarnings("all")
    protected <T> T getIntentValue(String key, Class<T> cls, T defaultValue) {
        Intent intent = activity.getIntent();
        if (cls == int.class || cls == Integer.class) {
            Integer value = intent.getIntExtra(key, (Integer) defaultValue);
            return (T) value;
        }
        if (cls == long.class || cls == Long.class) {
            Long value = intent.getLongExtra(key, (Long) defaultValue);
            return (T) value;
        }
        if (cls == float.class || cls == Float.class) {
            Float value = intent.getFloatExtra(key, (Float) defaultValue);
            return (T) value;
        }
        if (cls == double.class || cls == Double.class) {
            Double value = intent.getDoubleExtra(key, (Double) defaultValue);
            return (T) value;
        }
        if (cls == boolean.class || cls == Boolean.class) {
            Boolean value = intent.getBooleanExtra(key, (Boolean) defaultValue);
            return (T) value;
        }
        if (cls == String.class) {
            return (T) intent.getStringExtra(key);
        }
        if (Serializable.class.isAssignableFrom(cls)) {
            return (T) intent.getSerializableExtra(key);
        }
        if (Parcelable.class.isAssignableFrom(cls)) {
            return intent.getParcelableExtra(key);
        }
        if (int[].class == cls) {
            return (T) intent.getIntArrayExtra(key);
        }
        if (float[].class == cls) {
            return (T) intent.getFloatArrayExtra(key);
        }
        if (long[].class == cls) {
            return (T) intent.getLongArrayExtra(key);
        }
        if (double[].class == cls) {
            return (T) intent.getDoubleArrayExtra(key);
        }
        if (String[].class == cls) {
            return (T) intent.getStringExtra(key);
        }
        return null;
    }
}
