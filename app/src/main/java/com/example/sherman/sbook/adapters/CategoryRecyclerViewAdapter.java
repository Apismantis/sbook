package com.example.sherman.sbook.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.activities.CategoryActivity;
import com.example.sherman.sbook.activities.LoginActivity;
import com.example.sherman.sbook.activities.SplashActivity;
import com.example.sherman.sbook.models.Category;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sherman on 10/29/2016.
 */

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolders> {

    private List<Category> itemList;
    private Context context;

    public CategoryRecyclerViewAdapter(Context context, List<Category> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public CategoryViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, null);
        CategoryViewHolders rcv = new CategoryViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolders holder, int position) {
        holder.name.setText(itemList.get(position).getName());
        if (!itemList.get(position).getBackgound().equals(""))
            Picasso.with(context)
                    .load(itemList.get(position).getBackgound())
                    .into(holder.background);
        holder.category = itemList.get(position);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class CategoryViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public ImageView background;
        public Category category;

        public CategoryViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.tvCategoryName);
            background = (ImageView) itemView.findViewById(R.id.background);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), CategoryActivity.class);
            category.getName();
            intent.putExtra("category", category);
            view.getContext().startActivity(intent);
        }
    }


}
