package org.androidtown.myfood.remote;

import org.androidtown.myfood.item.RestaurantItem;
import org.androidtown.myfood.item.ReviewItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Zimincom on 2016. 11. 23..
 */

public interface RemoteService {

    String BASE_URL = "http://sample-env.3x7naibynm.us-west-2.elasticbeanstalk.com/";
    //String BASE_URL = "http://172.30.1.50:3000";
    String MEMBER_ICON_URL = BASE_URL + "/member/";
    String IMAGE_URL = BASE_URL + "/img/";


    @POST("/restaurant/info/review")
    Call<String> insertReview(@Body ReviewItem reviewItem);

    @GET("/restaurant/info/{id}")
    Call<RestaurantItem> getRestarantInfo(@Path("id") int id);

    @FormUrlEncoded
    @POST("/restaurant/category")
    Call<ArrayList<RestaurantItem>> getRestaurantByTag(@Field("category") String category);

    @FormUrlEncoded
    @POST("/restaurant/keyword")
    Call<ArrayList<RestaurantItem>> getRestaurantByKey(@Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("/restaurant/review")
    Call<ArrayList<ReviewItem>> getReviewById(@Field("id") int id);

    @GET("/restaurant/distance")
    Call<ArrayList<RestaurantItem>> getListDistanceOrder(@Query("user_latitude") double userLatitude,
                                                        @Query("user_longitude") double userLongitude);


}
