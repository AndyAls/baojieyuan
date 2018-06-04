package com.qlckh.purifier.api;

import com.qlckh.purifier.dao.BannerDao;
import com.qlckh.purifier.dao.BaojieDao;
import com.qlckh.purifier.dao.Comm2Dao;
import com.qlckh.purifier.dao.CommonDao;
import com.qlckh.purifier.dao.CuntryDao;
import com.qlckh.purifier.dao.EventListDao;
import com.qlckh.purifier.dao.GuanDao;
import com.qlckh.purifier.dao.InMsgDao;
import com.qlckh.purifier.dao.OutMessageDao;
import com.qlckh.purifier.user.UseDo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Andy
 * @date 2018/5/15 19:20
 * Desc:
 */
public interface ApiService {

    String BASE_URL = "http://api.hanziyi.cn/";
    long DEFAULT_TIME = 20;
    String IMG_URL="http://user.hanziyi.cn/upimg/";

    @FormUrlEncoded
    @POST("index.php/index/index/login")
    Observable<UseDo> login(@Field("username") String name,
                          @Field("pwd") String pwd, @Field("usertype") int type);

    @GET("index.php/index/index/get_default_news")
    Observable<BannerDao> getBanner();

    @FormUrlEncoded
    @POST("index.php/index/index/add_signin")
    Observable<CommonDao> signin(@Field("userid")int userId,@Field("address")String address,@Field("state")int state);

    @FormUrlEncoded
    @POST("index.php/index/index/add_zonghe")
    Observable<Comm2Dao> compositeSubmit(@Field("username")String username, @Field("usercode")int usercode,
                                         @Field("adduser")String adduser, @Field("adduserid")int adduserid,
                                         @Field("belongareaid")int belongareaid, @Field("ji_class")int ji_class,
                                         @Field("ji_she")int ji_she, @Field("ji_qing")int ji_qing,
                                         @Field("ji_dui")int ji_dui, @Field("address")String address,
                                         @Field("tel")String tel, @Field("imgpath")String imgpath);



    @FormUrlEncoded
    @POST("index.php/index/index/add_huanjing")
    Observable<Comm2Dao> sanitationSubmit(@Field("username")String username, @Field("usercode")int usercode,
                                         @Field("adduser")String adduser, @Field("adduserid")int adduserid,
                                         @Field("belongareaid")int belongareaid, @Field("ji_class")int ji_class,
                                         @Field("ji_dui")int ji_dui, @Field("address")String address,
                                         @Field("tel")String tel, @Field("imgpath")String imgpath);


    @FormUrlEncoded
    @POST("index.php/index/index/add_suggest")
    Observable<CommonDao> feedSubmit(@Field("userid")int userId, @Field("phone")String phone, @Field("content")String content);



    @FormUrlEncoded
    @POST("index.php/index/index/add_baofei")
    Observable<Comm2Dao> scrapSubmit(@Field("userid")int userid, @Field("fullname")String fullname,
                                          @Field("address")String address, @Field("areaid")int areaid,
                                          @Field("suggest")String suggest, @Field("caozuouserid")int caozuouserid,
                                          @Field("caozuousertel")String caijiusertel,@Field("caozuousertype")int caozuousertype);


    @FormUrlEncoded
    @POST("index/index/list_msg")
    Observable<InMsgDao> getMsgList(@Field("gotoid")String tel,@Field("page")String page, @Field("page_size")String pageSize);


    @FormUrlEncoded
    @POST("index/index/list_msg2")
    Observable<OutMessageDao> getMsgOutList(@Field("usertel")String usertel,@Field("page")String page, @Field("page_size")String pageSize);

    @FormUrlEncoded
    @POST("index.php/index/index/edit_appmsg")
    Observable<CommonDao> updateMessageState(@Field("id")int messageid);

    @FormUrlEncoded
    @POST("index.php/index/index/edit_appmsg2")
    Observable<CommonDao> updateMessageState2(@Field("id")int messageid);

    @GET("index.php/index/index/list_count")
    Observable<CuntryDao> getCuntryList();

    @GET("index/index/list_cgs")
    Observable<GuanDao> getGuanList(@Query("address") String address);

    @GET("index/index/list_bjs")
    Observable<BaojieDao> getBaojieList(@Query("address")String address);


    @FormUrlEncoded
    @POST("index/index/add_shijian")
    Observable<CommonDao> order(@Field("title")String title,@Field("content")String content,@Field("imgpath")String imgpath,
                                @Field("jiedao")String jiedao,@Field("cunguan")String cunguan,@Field("baojie")String baojie);


    @FormUrlEncoded
    @POST("index.php/index/index/list_sjs")
    Observable<EventListDao> getEventList(@Field("type")int type, @Field("user_id")int userid,
                                          @Field("page")int page, @Field("page_size")int pageSize);

    @FormUrlEncoded
    @POST("index.php/index/index/edit_sjs")
    Observable<CommonDao> sure(@Field("id")int id, @Field("status")int status);

    @FormUrlEncoded
    @POST("index.php/index/index/edit_sjs2")
    Observable<CommonDao> handEvent(@Field("id")int id, @Field("status")int status,
                                          @Field("content2")String content2, @Field("imgpath")String imgpath);


    @FormUrlEncoded
    @POST("index/index/add_appmsg")
    Observable<CommonDao> sendSumbit(@Field("fromid")String fromid, @Field("gotoid")String gotoid,
                                    @Field("title")String title, @Field("content")String content);

}
