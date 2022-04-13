package com.seid.bookreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.seid.bookreader.Adapter.MyBookAdapter;
import com.seid.bookreader.Adapter.MySliderAdapter;
import com.seid.bookreader.Common.Common;
import com.seid.bookreader.Interface.IBannerLoadDone;
import com.seid.bookreader.Interface.IBookLoadDone;
import com.seid.bookreader.Model.Book;
import com.seid.bookreader.Service.PicassoLoadingService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rbddevs.splashy.Splashy;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity implements IBannerLoadDone, IBookLoadDone {
    private RecyclerView recyclerView;
    private TextView textView;
    private ImageView btn_filter_search;
    private Slider slider;
    private ProgressDialog dialog_prgd;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseReference banners_dbr;
    private DatabaseReference books_dbr;
    private IBannerLoadDone bannerListener_IBaLD;
    private IBookLoadDone bookListener_IBoLD;
boolean isRotate=false;
//ActivityMainBinding  bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setSplashy();

        recyclerView = findViewById(R.id.recycler_comic);
        btn_filter_search= findViewById(R.id.imageview_search);
        btn_filter_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FilterSearchActivity.class));
            }
        });

        banners_dbr = FirebaseDatabase.getInstance().getReference("Banners");
        books_dbr = FirebaseDatabase.getInstance().getReference("Book");
        bannerListener_IBaLD = this;
        bookListener_IBoLD = this;

        slider = findViewById(R.id.slider);
        Slider.init(new PicassoLoadingService());



        swipeRefreshLayout = findViewById(R.id.swiper);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBanner();
                loadBook();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
                loadBook();
            }
        });
        recyclerView = findViewById(R.id.recycler_comic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        textView = findViewById(R.id.txt_comic);
    }

    private void setSplashy() {
      new Splashy(this)
              .setLogo(R.mipmap.ic_launcher)
             .setTitle("BOOK READER")
              .setTitleColor("#00b894")
              .setLogoWHinDp(100,100)
              .showProgress(false)
              .setSubTitleItalic(false)
              .setSubTitleColor("#00b894")
              .setBackgroundResource(R.color.white)
              .setFullScreen(true)
              .setTime(3000)
              .show();
    }


    private void loadBook() {


        dialog_prgd = ProgressDialog.show(this, "",
                "Loading. Durim pak...", true);
        dialog_prgd.show();

        // if(!swipeRefreshLayout.isRefreshing())




        final List<Book> book_load_listbo = new ArrayList<>();
        books_dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bookSnapShot : dataSnapshot.getChildren()) {

                    Book book = bookSnapShot.getValue(Book.class);
                    book_load_listbo.add(book);
                }
                bookListener_IBoLD.onBookLoadDoneListener(book_load_listbo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadBanner() {
        banners_dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> bannerList_listring = new ArrayList<>();
                for (DataSnapshot bannerSnapShot : dataSnapshot.getChildren()) {
                    String image = bannerSnapShot.getValue(String.class);

                    bannerList_listring.add(image);


                }
                bannerListener_IBaLD.onBannerLoadDoneListener(bannerList_listring);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onBannerLoadDoneListener(List<String> banners) {

        slider.setAdapter(new MySliderAdapter(banners));

    }

    @Override
    public void onBookLoadDoneListener(List<Book> bookList) {

        Common.bookList = bookList;
        recyclerView.setAdapter(new MyBookAdapter(getBaseContext(), bookList));
        ViewCompat.setNestedScrollingEnabled(recyclerView,false);
        textView.setText(new StringBuilder("NEW BOOK (")
                .append(bookList.size())
                .append(")"));
//if (!swipeRefreshLayout.isRefreshing())
        dialog_prgd.cancel();

    }

}
