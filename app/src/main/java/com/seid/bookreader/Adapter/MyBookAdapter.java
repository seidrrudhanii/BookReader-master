package com.seid.bookreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seid.bookreader.ChaptersActivity;
import com.seid.bookreader.Common.Common;
import com.seid.bookreader.Interface.IRecyclerItemClickListener;
import com.seid.bookreader.Model.Book;
import com.seid.bookreader.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyBookAdapter extends RecyclerView.Adapter<MyBookAdapter.MyViewHolder> {
    private final Context context;

    public MyBookAdapter(Context context) {
        this.context = context;
    }

    private List<Book> bookList;
    private LayoutInflater inflater;

    public MyBookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= inflater.inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Picasso.get().load(bookList.get(i).Image).into(myViewHolder.imageView);
        myViewHolder.textView.setText(bookList.get(i).Name);

        myViewHolder.setRecyclerItemClickListener(new IRecyclerItemClickListener() {
            @Override
            public void onClick(int position) {
                Common.bookSelected = bookList.get(position);

                Intent i = new Intent( context , ChaptersActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);


                //((MainActivity) context).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView textView;
        final ImageView imageView;

        IRecyclerItemClickListener recyclerItemClickListener;

        void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.comic_name);
            imageView=itemView.findViewById(R.id.image_comic);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerItemClickListener.onClick(getAdapterPosition());
        }
    }
}
