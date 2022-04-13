package com.seid.bookreader;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.seid.bookreader.Adapter.MyViewPagerAdapter;
import com.seid.bookreader.Common.Common;
import com.seid.bookreader.Model.Chapter;
import com.google.android.material.snackbar.Snackbar;
import com.sdsmdg.tastytoast.TastyToast;

public class ViewBookActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView textView;
    private View next;
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comic);

        viewPager = findViewById(R.id.page_viewer);
        textView = findViewById(R.id.txt_chapter_name);
        back = findViewById(R.id.chapter_back);
        next = findViewById(R.id.chapter_next);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.chapterIndex == 0) {

                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.linearLayout), "You are reading first chapter", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    //  TastyToast.makeText(ViewBookActivity.this, "You are reading first chapter", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    // Toast.makeText(ViewBookActivity.this,"You are reading first chapter",Toast.LENGTH_SHORT).show();

                } else {

                    Common.chapterIndex--;
                    fetchLinks(Common.chapterList.get(Common.chapterIndex));
                    textView.setText(new StringBuilder(Common.chapterList.get(Common.chapterIndex).Name));
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.linearLayout), Common.chapterList.get(Common.chapterIndex).Name, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.chapterIndex == Common.chapterList.size() - 1) {

                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.linearLayout), "You are reading last chapter", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    //  TastyToast.makeText(ViewBookActivity.this, "You are reading last chapter", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    //  Toast.makeText(ViewBookActivity.this,"You are reading last chapter",Toast.LENGTH_SHORT).show();

                } else {

                    Common.chapterIndex++;
                    fetchLinks(Common.chapterList.get(Common.chapterIndex));
                    textView.setText(new StringBuilder(Common.chapterList.get(Common.chapterIndex).Name));
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.linearLayout), Common.chapterList.get(Common.chapterIndex).Name, Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            }
        });
        fetchLinks(Common.chapterSelected);
        textView.setText(new StringBuilder(Common.chapterSelected.Name));

    }

    private void fetchLinks(Chapter chapter) {

        if (chapter.Links != null) {
            if (chapter.Links.size() > 0) {

                MyViewPagerAdapter adapter = new MyViewPagerAdapter(getBaseContext(), chapter.Links);
                viewPager.setAdapter(adapter);

            } else {

                TastyToast.makeText(this, "No image here", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                // Toast.makeText(this,"No image here",Toast.LENGTH_SHORT).show();
            }

        } else {

            TastyToast.makeText(this, "This chapter is translating...", TastyToast.LENGTH_LONG, TastyToast.INFO);


            // Toast.makeText(this,"This chapter is translating...",Toast.LENGTH_SHORT).show();
        }

    }
}
