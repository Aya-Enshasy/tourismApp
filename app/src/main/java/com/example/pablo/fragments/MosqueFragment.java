package com.example.pablo.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pablo.activity.NoInternetConnection;
import com.example.pablo.activity.Login;
import com.example.pablo.details_activities.MosqueDetails;
import com.example.pablo.interfaces.MyInterface;
import com.example.pablo.interfaces.Service;
import com.example.pablo.adapters.MosqueAdapter;
import com.example.pablo.databinding.FragmentMosqueBinding;
import com.example.pablo.model.mosques.Data;
import com.example.pablo.adapters.popularMosquesAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.example.pablo.activity.Login.PREF_NAME;
import static com.example.pablo.activity.Login.parseError;

public class MosqueFragment extends Fragment {

    MosqueAdapter mosqueAdapter;
    FragmentMosqueBinding binding;
    List<Data> list;
    Service service;
    boolean isConnected = false;
    ConnectivityManager connectivityManager;
    public static final String Item_KEY = "mosque_key";
    com.example.pablo.adapters.popularMosquesAdapter popularMosquesAdapter;
    private View root;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MosqueFragment() {
    }

    public static MosqueFragment newInstance() {
        MosqueFragment fragment = new MosqueFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMosqueBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        swipeRefresh();
        checkInternetConnection();
        adapter();
        getRetrofitInstance();
        getMosque();
        getPopularMosque();
        startShimmer();
        return view;
    }
    private void getMosque() {

        Login.SP = getActivity().getSharedPreferences(PREF_NAME ,MODE_PRIVATE);
        String token = Login.SP.getString(Login.TokenKey, "");//"No name defined" is the default value.


        service.getMosques(token).enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

                if (response.isSuccessful()) {
                    binding.recyclerview2.startLayoutAnimation();
                    stopShimmer();
//                    Toast.makeText(getActivity(), response.message()+"", Toast.LENGTH_LONG).show();
                    list = response.body();
                    mosqueAdapter.setdata(list);

                }else {
                    String errorMessage = parseError(response);
                    Log.e("errorMessage", errorMessage + "");
                    Toast.makeText(getActivity(), response.message()+"", Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getPopularMosque() {

        Login.SP = getActivity().getSharedPreferences(PREF_NAME ,MODE_PRIVATE);
        String token = Login.SP.getString(Login.TokenKey, "");//"No name defined" is the default value.

            service.getTopMosques(token).enqueue(new Callback<List<Data>>() {
                @Override
                public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

                    if (response.isSuccessful()) {
//                        Toast.makeText(getActivity(), response.message()+"", Toast.LENGTH_LONG).show();
                        stopShimmer();
                        binding.recyclerview.startLayoutAnimation();

                        list = response.body();
                        popularMosquesAdapter.setdata(list);

                    }else {
                        String errorMessage = parseError(response);
                        Log.e("errorMessage", errorMessage + "");
                        Toast.makeText(getActivity(), response.message()+"", Toast.LENGTH_LONG).show();

                    }
                }
                @Override
                public void onFailure(Call<List<Data>> call, Throwable t) {

                    Log.e( "getMessage",t.getMessage());
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void registerNetworkCallback(){


        try {

            connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){

                @Override
                public void onAvailable(@NonNull Network network) {
                    isConnected = true;
                }

                @Override
                public void onLost(@NonNull Network network) {
                    isConnected = false;
                }
            });




        }catch (Exception e){

            isConnected = false;

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        registerNetworkCallback();
    }

    public boolean isOnLine(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null || !networkInfo.isAvailable() || !networkInfo.isConnected()){
            return false;
        }
        return true;
    }

    private void adapter(){

        list = new ArrayList<>() ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        binding.recyclerview.setLayoutManager(linearLayoutManager);
        binding.recyclerview2.setLayoutManager(linearLayoutManager2);

        mosqueAdapter = new MosqueAdapter(getActivity(), new MyInterface() {
            @Override
            public void onItemClick(Long Id) {
                Intent intent = new Intent(getActivity(), MosqueDetails.class);
                intent.putExtra(Item_KEY, Id);
                startActivity(intent);
            }
        });
        popularMosquesAdapter = new popularMosquesAdapter(getActivity(), new MyInterface() {
            @Override
            public void onItemClick(Long Id) {
                Intent intent = new Intent(getActivity(), MosqueDetails.class);
                intent.putExtra(Item_KEY, Id);
                startActivity(intent);
            }
        });
        binding.recyclerview.setAdapter(popularMosquesAdapter);
        binding.recyclerview2.setAdapter(mosqueAdapter);

    }

    private void getRetrofitInstance(){
        service = Service.ApiClient.getRetrofitInstance();
    }

    private void checkInternetConnection(){
        if (!isOnLine()){
            if (isConnected){
                Toast.makeText(getActivity(),"Connected",Toast.LENGTH_SHORT).show();
            }else{

                Intent i = new Intent(getActivity(), NoInternetConnection.class);
                startActivity(i);
                getActivity().finish();

            }
        }
    }

    private void startShimmer(){
        binding.shimmerLayout.startShimmer();
        binding.shimmerLayout1.startShimmer();
    }

    private void stopShimmer(){
        binding.shimmerLayout.stopShimmer();
        binding.shimmerLayout1.stopShimmer();
        binding.shimmerLayout.setVisibility(View.GONE);
        binding.shimmerLayout1.setVisibility(View.GONE);
    }

    private void swipeRefresh(){
        SwipeRefreshLayout swipeRefreshLayout = binding.scroll;
        swipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(()->{
                swipeRefreshLayout.setRefreshing(false);
                checkInternetConnection();
                adapter();
                getRetrofitInstance();
                getMosque();
                getPopularMosque();
                startShimmer();
            },1000);
        });
    }

}