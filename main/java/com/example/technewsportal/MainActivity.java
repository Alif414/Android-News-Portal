package com.example.technewsportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    String category, stringPhoto, title, website, sDate;
    int intPhoto;
    Drawable photo;
    Date date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        ArrayList<NewsItem> newsList = new ArrayList<>();

        //Opens txt
        InputStream file = getResources().openRawResource(R.raw.news_items);
        Scanner scanner = new Scanner(file);

        //Reads all lines
        while(scanner.hasNextLine()){
            category = scanner.nextLine();
            category = category.replace("category:", "");

            stringPhoto = scanner.nextLine();
            //finds photo using txt line
            stringPhoto = stringPhoto.replace("photo:", "@drawable/");
            stringPhoto = stringPhoto.replace(".png", "");
            intPhoto = getResources().getIdentifier(stringPhoto, "drawable", getPackageName());
            photo = getResources().getDrawable(intPhoto);

            //Sets details to String
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

            //Adds into list
            newsList.add(new NewsItem(category, photo, title, website, date));
        }
        scanner.close();

        //Sorts according to date
        Collections.sort(newsList, (item1, item2) -> {
            Date date1 = item1.getDate();
            Date date2 = item2.getDate();

            if (date1 != null && date2 != null) {
                boolean b1;
                boolean b2;
                boolean isAscending = true;
                //Checks and places date
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
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("website", website);
                startActivity(intent);
            }
        });
    }
}