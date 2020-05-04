### 传统的安卓开发
传统的安卓开发，对于同一个界面上的功能，往往都是把功能的实现代码写到同一个Activity类中，这样子，各种各样的业务功能实现代码充斥在同一个Java类中，一个Activity的代码量往往会达到几百行，甚至几千行！
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200503233141531.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI1OTI5NTQ3,size_16,color_FFFFFF,t_70)传统的Android开发存在四个问题

#### 1、耦合度高
>不同的功能业务代码都集中到同一个Activity类中，就像一堆石头都放在同一个盒子中一样，就必定会造成石头与石头挨在一起，难以分辨出具体的每一颗石头，同样也难从Activity中分辨中具体功能对应的业务代码，从而使软件耦合度变得很高。

#### 2、维护困难
>果是由于因造成的，软件维护困难是因为软件耦合度高。要想修复一个软件功能BUG，就首先要在找到功能相关的代码，仔细阅读代码，理解实现代码思路，才能给出修复BUG的方法，并修复BUG。因为软件耦合度高，BUG对应的代码就很难被找到，即使代码找到了，也很难被阅读理解，这样子BUG就很难被修复。

#### 3、新功能拓展难度高
>当添加一个新功能，就需要往Activity类中添加实现代码，而Activity中本身就有很多其他功能的实现代码了，这样子，就很容易因为程序员的一不小心，改动了别的功能业务代码，造成程序出现新的bug。

#### 4、团队合作难度高
>如果团队中的多个成员，需要同时实现同一个界面上的不同功能，那么，就意味着这些成员要同时修改同一个Activity类，这样子，就造成了代码冲突。


### MVB开发框架
模型Modle、视图View、业务Business，简称MVB。
Modle主要用来处理业务的数据，View主要用于接收用户请求与显示处理结果，Business主要用于响应并处理用户的请求。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200504120332158.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI1OTI5NTQ3,size_16,color_FFFFFF,t_70)
用户点击软件界面View，View将用户请求发送给Activity，Activity调用符合的Business来处理用户请求，Business根据用户请求调用相应的Model来处理数据，Business处理完毕之后，直接调用View来响应用户请求处理结果。

#### MVB特点
#### 1、耦合度低
>同一个界面中的不同功能，它的业务实现代码是放在不同的Business中的，这样子就避免了所有业务功能实现代码都堆积在同一个Activity类中的尴尬情况。
比如，一个用户注册界面，用户输入账号的时候需要动态检测账号是否可用（账号是否已经注册），用户输入验证码的时候，需要动态检测验证码是否正确，用户输入手机号码的时候，需要动态检测手机号码是否合法以用手机号码是否已经被注册使用。MVB开发框架会创建Business1来处理用户账号的要动态检测需求，会创建Business2来处理验证码动态验证需求，会创建Business3来处理手机号码检测需求。

#### 2、维护简单
>不同的功能对应不同的Business，因此，同一个Business中的业务逻辑都比较专一，代码可读性比较高。当发现某个功能出bug的时候，用户只需要找到对应的Business即可快速找到问题的代码所在，并快速修复。由于Business之间的代码隔离，所以不用太担心修改一个功能BUG而影响到其他功能的正常使用。

#### 3、可拓展性高
>因为不同的功能业务实现对应不同的Business，因此，当一个界面需要添加新的功能需求的时候，只需要添加新的Business即可。这样子可以使新增代码对原有代码的影响性降到最低，不容易出现因为添加了新功能而造成原有功能出现问题的情况。

#### 4、便于团队合作
>不同的功能对应不同的Business，所以即使是同一个页面上的不同功能需要不同的团队成员同时实现，成员之间各个修改各自的Business，几乎不存在同时修改同一个资源文件的可能，因此，团队合作灵活。

#### MVB框架的使用方法
使用MVB框架的方法非常简单，只需要四个步骤即可：
**创建ViewHolder、创建DataHolder、创建Business、修改Activity**。
由于框架中已经内置了NotNeedViewHolder与NotNeedDataHolder，如果不需要与View层打交道，也不需要进行Business间数据共享，可以不用创建ViewHolder与DataHolder，直接使用框架内置的即可。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200504143005870.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI1OTI5NTQ3,size_16,color_FFFFFF,t_70)
#### 依赖引入
加入github的maven仓库地址
```java
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
#### 引入mvb依赖
```java
dependencies {
    implementation 'com.github.923339832:mvb:1.0.1'
}
```

#### 1、创建ViewHolder
编写一个Java类继承BaseViewHolder，然后申明视图控件成员变量，使用@ViewInject注解将xml布局中的控件与类中的成员变量绑定
```java
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
```

#### 2、创建DataHolder
编写一个普通的Java类，类中可以定义任意的成员变量。DataHolder的作用是数据存储，用于同一个Activity中不同Business之间的数据共享。
```java
public class LoginDataHolder {
    // 是否通过了验证码验证
    public boolean isPassVerifyCode = false;
}
```

#### 3、创建Business
编写一个Java类，继承BaseBusiness，实现类的泛型中绑定相应的ViewHolder与DataHolder。
```java
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
```

#### 4、修改Activity继承BaseActivity，设置布局并添加Business注解
使用@ViewLayout注解将Activity与布局xml绑定
使用@Business注解将Business与Activity进行绑定
```java
@ViewLayout(R.layout.activity_login)
@Business(business = {
        VerifyCodeBusiness.class,
        LoginBusiness.class
})
public class LoginActivity extends BaseActivity {

}
```

### MVB框架的常用API
Business在实际开发中使用的频率非常高，本文只列举一些经常乃至的API。

#### 1、初始化
**void initBusiness(Activity activity, View layout);**
>initBusiness是一个抽象方法，在Activity创建的时候onCreate方法执行，initBusiness方法会自动被框架调用。initBusiness经常被用来做初始化工作，它的生命周期与Activity的onCreate相同。

#### 2、拦截其它Business
 **boolean needInterceptNextBusinessInit();**
>是否拦截Activity中的处理该Business之后的其他Business初始化，默认返回值是false。

#### 3、Activity之间请求结果
**boolean onActivityResult(int requestCode, int resultCode, Intent data);**
>监听Activity的生命周期，作用和Activity的生命周期方法相同。如果返回值为true，则处理该Business之后的其他Business的onActivityResult不会被调用，默认返回值为false。

#### 4、Activity销毁
**boolean onDestroy();**
>监听Activity的生命周期，作用和Activity的生命周期方法相同。如果返回值为true，则处理该Business之后的其他Business的onDestroy不会被调用，默认返回值为false。

#### 5、Activity前台展示
**void onResume();**
>监听Activity的生命周期，作用和Activity的生命周期方法相同

#### 6、Activity暂停
**void onPause();**
>监听Activity的生命周期，作用和Activity的生命周期方法相同

#### 7、Activity停止
**void onStop();**
>监听Activity的生命周期，作用和Activity的生命周期方法相同

#### 8、Activity启动
**void onStart();**
>监听Activity的生命周期，作用和Activity的生命周期方法相同

#### 9、Activity重新启动
**void onRestart();**
>监听Activity的生命周期，作用和Activity的生命周期方法相同

#### 10、键盘响应
**boolean onKeyDown(int keyCode, KeyEvent event);**
>监听Activity的生命周期，作用和Activity的生命周期方法相同。如果返回值为true，则处理该Business之后的其他Business的onKeyDown不会被调用，默认返回值为false。

#### 11、启动Activity
**protected void startActivity(...);**
startActivity有很多重载方法，打以在打开Activity的同时传递不同的参数。

#### 12、获取第三方Activity传递过来的值
 **protected <T> T getIntentValue(String key, Class<T> cls, T defaultValue);** 
> 作用和Activity中的getIntent().getXxxExtra()一样。
 
 ### 下载
详细的mvb源码与demo请去github下载，下载地址：**https://github.com/923339832/mvb**
