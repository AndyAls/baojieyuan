package com.qlckh.purifier.usercase;

import com.qlckh.purifier.api.ApiService;
import com.qlckh.purifier.dao.CuntryDao;
import com.qlckh.purifier.http.RxHttpUtils;
import com.qlckh.purifier.http.interceptor.Transformer;

import io.reactivex.Observable;

/**
 * @author Andy
 * @date 2018/5/31 10:16
 * Desc:
 */
public class CuntryListModel {

    public Observable<CuntryDao> getCuntryList(){

        return RxHttpUtils.createApi(ApiService.class)
                .getCuntryList()
                .compose(Transformer.switchSchedulers());
    }
}
