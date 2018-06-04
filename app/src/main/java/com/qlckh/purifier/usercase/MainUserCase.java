package com.qlckh.purifier.usercase;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.BannerDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;

import io.reactivex.Observable;

/**
 * @author Andy
 * @date 2018/5/17 11:19
 * Desc:
 */
public class MainUserCase {

    public Observable<BannerDao> getBannerImg(){
        return RxHttpUtils.createApi(ApiService.class)
                .getBanner()
                .compose(Transformer.<BannerDao>switchSchedulers());
    }
}
