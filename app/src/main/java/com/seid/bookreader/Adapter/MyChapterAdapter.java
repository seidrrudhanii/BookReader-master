package com.seid.bookreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seid.bookreader.Common.Common;
import com.seid.bookreader.Interface.IRecyclerItemClickListener;
import com.seid.bookreader.Model.Chapter;
import com.seid.bookreader.R;
import com.seid.bookreader.ViewBookActivity;

import java.util.List;

public class MyChapterAdapter extends RecyclerView.Adapter<MyChapterAdapter.MyViewHolder> {
    private final Context context;
    private final List<Chapter> chapterList;
    private final LayoutInflater inflater;


    public MyChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= inflater.inflate(R.layout.chapter_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
holder.textView.setText(chapterList.get(position).Name);


holder.setRecyclerItemClickListener(new IRecyclerItemClickListener() {
    @Override
    public void onClick(int position) {
        Common.chapterSelected=chapterList.get(position);
        Common.chapterIndex=position;
       // Intent i = new Intent(context, ViewBookActivity.class);
       // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(new Intent(context, ViewBookActivity.class));

    }
});
    }




    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        long DURATION = 500;
        private boolean on_attach = true;
        IRecyclerItemClickListener recyclerItemClickListener;

        public MyViewHolder(@NonNull View itemView, IRecyclerItemClickListener recyclerItemClickListener) {
            super(itemView);
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.chapter_numb);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            recyclerItemClickListener.onClick(getAdapterPosition());}


        }
    }

