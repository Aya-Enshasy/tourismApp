package com.example.pablo.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.pablo.model.ImageExam;
import com.example.pablo.R;
import com.example.pablo.interfaces.Service;
import com.example.pablo.adapters.FoodOfferAdapter;
import com.example.pablo.adapters.PopularHotelsAdapter;
import com.example.pablo.adapters.SliderAdapter;
import com.example.pablo.databinding.FragmentHomeBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    List<ImageExam> list;
    FoodOfferAdapter foodOfferAdapter;
    ProgressDialog progressDialog;
    FragmentHomeBinding binding;
    PopularHotelsAdapter popularHotelsAdapter;
//    List<HotelsExam>  list1;
    RecyclerView recyclerView,recyclerView2;
    Service serves;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView2 = view.findViewById(R.id.recyclerview1);

        list = new ArrayList<>() ;
//        list1 = new ArrayList<>() ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        foodOfferAdapter = new FoodOfferAdapter(getActivity());
        popularHotelsAdapter = new PopularHotelsAdapter(getActivity());
        recyclerView.setAdapter(foodOfferAdapter);
        recyclerView2.setAdapter(popularHotelsAdapter);
        serves = Service.ApiClient.getRetrofitInstance();

      //  getFoodImage();
//        getpopularHotel();
//       imageSliderRun();

        //Fragment
        binding.tabs.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.Hotels) {
                    openFragment(HotelsFragment.newInstance());
                    binding.tabs.getMenu().findItem(R.id.Hotels).setChecked(false);

                } else if (item.getItemId() == R.id.Mosque) {
                    openFragment(MosqueFragment.newInstance());
                }else if (item.getItemId() == R.id.Restaurants) {
                    openFragment(RestaurantsFragment.newInstance());
                } else {
                    openFragment(ChurchesFragment.newInstance());

                }
                return true;
            }
        });
        openFragment(HotelsFragment.newInstance());

        return view;

    }
    //Fragment
    void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.viewpager, fragment);
        fragmentTransaction.commit();
    }

    //section 2 ads food
//    private void getFoodImage() {
//        serves.getFoodImage().enqueue(new Callback<List<ImageExam>>() {
//            @Override
//            public void onResponse(Call<List<ImageExam>> call, Response<List<ImageExam>> response) {
//
////                Toast.makeText(getActivity(), "data", Toast.LENGTH_SHORT).show();
//                if (response.body() != null) {
//                    list = response.body();
//                    foodOfferAdapter.setdata(list);
//
//                }
//            }
//            @Override
//            public void onFailure(Call<List<ImageExam>> call, Throwable t) {
//                Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    //3ed section popular hotels
//    private void getpopularHotel() {
//        serves.getPopularHotels("Bearer " + "38|ewcq2VaLhvY6xPyNdRW1Tqp56nuvFnXA1PRNpwNf").enqueue(new Callback<List<HotelsExample>>() {
//            @Override
//            public void onResponse(Call<List<HotelsExample>> call, Response<List<HotelsExample>> response) {
//
////                Toast.makeText(getActivity(), "data", Toast.LENGTH_SHORT).show();
//                if (response.body() != null) {
//                    list1 = response.body();
//                    popularHotelsAdapter.setdata(list1);
//
//                }
//            }
//            @Override
//            public void onFailure(Call<List<HotelsExample>> call, Throwable t) {
//                Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    //slider
    private void imageSliderRun() {
        int[] images = {R.drawable.boutique_franchise_hotels, R.drawable.mosqe, R.drawable.close_woman};
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        binding.imageSlider.setSliderAdapter(sliderAdapter);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        binding.imageSlider.startAutoCycle();
    }
}