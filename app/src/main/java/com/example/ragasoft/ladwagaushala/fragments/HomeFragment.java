package com.example.ragasoft.ladwagaushala.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ragasoft.ladwagaushala.R;
import com.example.ragasoft.ladwagaushala.adapter.HorizontalRecyclerAdapter;
import com.example.ragasoft.ladwagaushala.adapter.ViewPager_Image_Adapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {
    View view;
    RecyclerView recyclerview;
    HorizontalRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    int i;
    Handler handler;
    Timer swipeTimer;
    Runnable Update;
    ViewPager viewPager;
    int currentPage = 0;
    int NUM_PAGES = 0;
    int[] IMAGES = {R.drawable.naturopathyimg, R.drawable.gaushalaimage, R.drawable.donation};
    ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    CirclePageIndicator circleIndicator;

    int img[] = {R.drawable.productimage, R.drawable.productimage, R.drawable.productimage, R.drawable.productimage, R.drawable.productimage};


    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        recyclerview = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new HorizontalRecyclerAdapter(img, context);
        recyclerview.setAdapter(adapter);
        viewPager = view.findViewById(R.id.viewpagerfragemnt);
        circleIndicator = view.findViewById(R.id.circlePageIndicator);
        initViewPager();


        return view;
    }


    public void initViewPager() {
        for (i = 0; i < IMAGES.length; i++) ImagesArray.add(IMAGES[i]);
        viewPager.setAdapter(new ViewPager_Image_Adapter(getActivity(), ImagesArray));
        circleIndicator.setViewPager(viewPager);
        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        circleIndicator.setRadius(5 * density);
        NUM_PAGES = IMAGES.length;
        // Auto start of viewpager
        handler = new Handler();
        Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 1000);

        // Pager listener over indicator
        circleIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
