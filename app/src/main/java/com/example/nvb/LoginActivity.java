package com.example.nvb;

        import com.example.nvb.business.LoginBusiness;
        import com.example.nvb.business.VerifyCodeBusiness;
        import com.mrlao.mvb.BaseActivity;
        import com.mrlao.mvb.annotation.Business;
        import com.mrlao.mvb.annotation.ViewLayout;

@ViewLayout(R.layout.activity_login)
@Business(business = {
        VerifyCodeBusiness.class,
        LoginBusiness.class
})
public class LoginActivity extends BaseActivity {

}
