package com.example.technewsportal;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class FragmentA extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    String category, stringPhoto, title, website, sDate;
    int intPhoto;
    Drawable photo;
    Date date = null;
//    private RecyclerView.LayoutManager layoutManager;
    private onFragmentInteractionListener fragmentListener;

    public interface onFragmentInteractionListener {
        public void passData(String website);
    }


    public FragmentA() {
        // Required empty public constructor
    }

    public static FragmentA newInstance() {
        FragmentA fragment = new FragmentA();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_a, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        ArrayList<NewsItem> newsList = new ArrayList<>();
        //NewsTxt stuff here

        InputStream file = getResources().openRawResource(R.raw.news_items);
        Scanner scanner = new Scanner(file);

        while(scanner.hasNextLine()){
            category = scanner.nextLine();
            category = category.replace("category:", "");

            stringPhoto = scanner.nextLine();
            stringPhoto = stringPhoto.replace("photo:", "@drawable/");
            stringPhoto = stringPhoto.replace(".png", "");
            intPhoto = getResources().getIdentifier(stringPhoto, "drawable", getContext().getPackageName());
            photo = getResources().getDrawable(intPhoto);

            title = scanner.nextLine();
            title = title.replace("title:", "");

            website = scanner.nextLine();
            website = website.replace("website:", "");

            sDate = scanner.nextLine();
            sDate = sDate.replace("date:", "");
            try {
                date = new SimpleDateFormat("dd-MM-yyyy").parse(sDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            newsList.add(new NewsItem(category, photo, title, website, date));
        }
        scanner.close();

        Collections.sort(newsList, (item1, item2) -> {
            Date date1 = item1.getDate();
            Date date2 = item2.getDate();

            if (date1 != null && date2 != null) {
                boolean b1;
                boolean b2;
                boolean isAscending = true;
                if (!isAscending) {
                    b1 = date2.after(date1);
                    b2 = date2.before(date1);
                }else {
                    b1 = date1.after(date2);
                    b2 = date1.before(date2);
                }

                if (b1 != b2) {
                    if (b1) {
                        return -1;
                    }
                    if (!b1) {
                        return 1;
                    }
                }
            }
            return 0;
        });


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        newsAdapter = new NewsAdapter(newsList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                fragmentListener.passData(website);
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentListener = (onFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement onFragmentInteractionListener");
        }
    }
}
