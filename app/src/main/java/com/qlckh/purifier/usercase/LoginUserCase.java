package com.qlckh.purifier.usercase;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;
import com.qlckh.purifier.user.UseDo;

import io.reactivex.Observable;

/**
 * @author Andy
 * @date 2018/5/15 20:22
 * Desc:
 */
public class LoginUserCase {

    public Observable<UseDo> login(String name, String pwd, int type){
        return RxHttpUtils.createApi(ApiService.class)
                .login(name, pwd, type)
                .compose(Transformer.<UseDo>switchSchedulers());
    }
}
