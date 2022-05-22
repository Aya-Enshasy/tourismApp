package com.example.pablo.interfaces;


import com.example.pablo.model.bookingInfo.CartExample;
import com.example.pablo.model.buyorder.BuyOrderExample;
import com.example.pablo.model.churches.ChurchesExample;
import com.example.pablo.model.edit.EditExample;
import com.example.pablo.model.hotels.HotelsExample;
import com.example.pablo.model.logout.LogOutExample;
import com.example.pablo.model.RegisterRequest;
import com.example.pablo.model.RegisterResponse;
import com.example.pablo.model.LoginRequest;
import com.example.pablo.model.churches.Data;
import com.example.pablo.model.login.ExampleLogin;
import com.example.pablo.model.RestaurantsExam;
import com.example.pablo.model.mosques.MosqueExample;
import com.example.pablo.model.order_details.OrderDetailsExample;
import com.example.pablo.model.orders.OrdersExample;
import com.example.pablo.model.register.Example;
import com.example.pablo.model.rooms.RoomsExample;
import com.example.pablo.model.users.UsersExample;
import com.example.pablo.reservations.Datum;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Service {

    //login
    @POST("login")
    Call<ExampleLogin> login(@Body LoginRequest loginRequest);

    //register
    @POST("register")
    Call<Example> register(@Header("Content-Type") String type
            , @Body RegisterRequest registerRequest);

    //logout
    @POST("logout")
    Call<LogOutExample> logOutUser(@Header("Authorization") String token);

    //get user photo
    @Multipart
    @POST("register")
    Call<RegisterResponse> UserImage(
            @Header("Accept") String accept
            , @Part MultipartBody.Part image);

//***************************************************************************

    //hotels
    @GET("hotels")
    Call<List<com.example.pablo.model.hotels.Data>> getPopularHotels(@Header("Authorization") String token);

    @GET("hotels")
    Call<List<com.example.pablo.model.hotels.Data>> getHotels(@Header("Authorization") String token);

    //hotels details
    @GET("hotels/{id}")
    Call<HotelsExample> getHotelsDetails(@Path("id") int id, @Header("Authorization") String token);

    //room
    @GET("hotel_rooms")
    Call<List<com.example.pablo.model.rooms.Data>> getRoom(@Header("Authorization") String token);

    //room
    @GET("hotel_rooms/{id}")
    Call<RoomsExample> getRoomDetails(@Path("id") Long id, @Header("Authorization") String token);

    //booking info
    @FormUrlEncoded
    @POST("addNewOrderItem")
    Call<CartExample>  bookInfo(
            @Field("check_in") String check_in,
            @Field("check_out") String check_out,
            @Field("room_count") String room_count,
            @Field("room_id") String room_id,
            @Header("Authorization") String token
     );


    //room_reservations
    @GET("getAuthOrderItems")
    Call<com.example.pablo.model.cart.CartExample> getCart(@Header("Authorization") String token);

    //delete item from cart
    @DELETE("orders/{id}")
    Call<Datum> deleteItem(@Path("id") Long itemId, @Header("Authorization") String token);

    //edit item from cart
    @FormUrlEncoded
    @POST("orders/{id}")
    Call<EditExample> editItem(
            @Path("id") Long itemId,
            @Field("check_in") String check_in,
            @Field("check_out") String check_out,
            @Field("room_count") String room_count,
            @Field("room_id") Long  room_id,
            @Field("_method") String _method,
            @Header("Authorization") String token);

    //clear all cart
    @DELETE("deleteAuthReservations")
    Call<Datum> clearCart(@Header("Authorization") String token);

    //get hotels orders
    @GET("getAuthHotelOrders")
    Call<OrdersExample> getHotelOrders(@Header("Authorization") String token);

    //delete item from cart
    @DELETE("orders/{id}")
    Call<OrderDetailsExample> getHotelOrdersDetails(@Path("id") Long itemId, @Header("Authorization") String token);


    //buy order
    @POST("buyAllAuthOrderItems")
    Call<BuyOrderExample> BuyOrder(@Header("Authorization") String token);

    //**************************************************************************
    //mosque
    @GET("mosques")
    Call<List<com.example.pablo.model.mosques.Data>> getMosques(@Header("Authorization") String token);

    //mosque details
    @GET("mosques/{id}")
    Call<MosqueExample> getMosqueDetails(@Path("id") int id, @Header("Authorization") String token);

    //churches
    @GET("churches")
    Call<List<Data>> getChurches(@Header("Authorization") String token);

    //churches details
    @GET("churches/{id}")
    Call<ChurchesExample> getChurchesDetails(@Path("id") int id, @Header("Authorization") String token);

    //******************************************************************************
    //get user details from
    @GET("users/{id}")
    Call<UsersExample> getUserDetails(@Path("id") Long id, @Header("Authorization") String token);

    //******************************************************************************
    //get orders test
    @GET("Orders/{id}")
    Call<List<RegisterResponse>> getOrders(@Path("userId") int id);

    //get orders restaurant
    @GET("hotelOrders")
    Call<List<RestaurantsExam>> getRestaurantOrders();

    //get orders hotel
//    @GET("hotel")
//    Call<List<HotelsExam>> getHotelsOrders();

    //getFavourites
    @GET("favourite")
    Call<List<RestaurantsExam>> getRestaurantFavourite();

    //Restaurants
    @GET("Restaurants")
    Call<List<RestaurantsExam>> getRestaurant();

    //Restaurants
    @GET("Restaurants")
    Call<List<RestaurantsExam>> getFreshRecipes();

    //RestaurantDetails
    @GET("RestaurantDetails/{id}")
    Call<RestaurantsExam> getRestaurantDetails(@Path("id") int id);



    class ApiClient {

        private static final String BASE_URL = "http://54.226.80.5/api/";

//        static Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        public static Service getRetrofitInstance() {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .writeTimeout(10000, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("Accept", "application/json")
                                    .addHeader("Content-Type", "multipart/form-data")
                                    .addHeader("X-Requested-With", "XMLHttpRequest")
                                    // .addHeader("Authorization", "Bearer" + Token)
                                    .addHeader("Accept-Language", "en")

                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })

                    .readTimeout(10000, TimeUnit.SECONDS).addInterceptor(interceptor).addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("Accept", "application/json")
                                    .addHeader("Content-Type", "multipart/form-data")
                                    .addHeader("X-Requested-With", "XMLHttpRequest")
                                    // .addHeader("Authorization", "Bearer" + Token)
                                    .addHeader("Accept-Language", "en")

                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();


            return retrofit.create(Service.class);
        }
    }

}

