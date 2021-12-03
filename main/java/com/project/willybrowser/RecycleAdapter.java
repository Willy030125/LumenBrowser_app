package com.project.willybrowser;
/*
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    Context context;
    String[] siteList;
    String[] siteUrl;
    Bitmap[] siteLogo;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView display_siteInfo;
        TextView display_siteUrl;
        ImageView tab_siteImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            display_siteInfo = itemView.findViewById(R.id.display_siteInfo);
            display_siteUrl = itemView.findViewById(R.id.display_siteUrl);
            tab_siteImage = itemView.findViewById(R.id.tab_siteImage);
        }

        @Override
        public void onClick(View itemView) {

        }
    }

    public RecycleAdapter(Context context, String[] siteList, String[] siteUrl, Bitmap[] siteLogo) {
        this.context = context;
        this.siteList = siteList;
        this.siteUrl = siteUrl;
        this.siteLogo = siteLogo;
    }

    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tab_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {
        holder.display_siteInfo.setText(siteList[position]);
        holder.display_siteUrl.setText(siteUrl[position]);
        if (siteLogo != null) {
            holder.tab_siteImage.setImageBitmap(siteLogo[position]);
        }
        else {
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}

 */