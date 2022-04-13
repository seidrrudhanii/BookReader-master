package com.seid.bookreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.seid.bookreader.Adapter.MyChapterAdapter;
import com.seid.bookreader.Common.Common;
import com.seid.bookreader.Model.Book;

import static com.seid.bookreader.Common.Common.bookSelected;

public class ChaptersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textView;
    private LinearLayoutManager layoutManager;
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        back=findViewById(R.id.back_main);
        textView=findViewById(R.id.txt_chapter_name);
        recyclerView=findViewById(R.id.recycler_chapter);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));



       // Toolbar toolbar=findViewById(R.id.toolbar);
       // toolbar.setTitle(Common.bookSelected.Name);
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_backwhite_24dp);
        fetchChapter(bookSelected);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

       /* toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fetchChapter(Common.bookSelected);
    }*/


}



    private void fetchChapter(Book bookSelected) {
        Common.chapterList= bookSelected.Chapters;
        recyclerView.setAdapter(new MyChapterAdapter(this, bookSelected.Chapters));
        textView.setText(new StringBuilder("CHAPTERS(").append(bookSelected.Chapters.size())
                .append(")"));
    }}
