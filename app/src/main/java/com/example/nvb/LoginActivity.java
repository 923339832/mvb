package com.example.nvb;

        import com.example.nvb.business.LoginBusiness;
        import com.example.nvb.business.VerifyCodeBusiness;
        import com.mrlao.nvb.BaseActivity;
        import com.mrlao.nvb.annotation.Business;
        import com.mrlao.nvb.annotation.ViewLayout;

@ViewLayout(R.layout.activity_login)
@Business(business = {
        VerifyCodeBusiness.class,
        LoginBusiness.class
})
public class LoginActivity extends BaseActivity {

}
