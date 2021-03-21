package com.axeedo.mewallet.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axeedo.mewallet.Database.Category;
import com.axeedo.mewallet.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoryListAdapter  extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private final LayoutInflater mInflater;
    List<Category> mCategories;

    public CategoryListAdapter(Context context){ mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.category_item_fragment, parent, false);
        return new CategoryListAdapter.CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if(mCategories != null){
            Category current = mCategories.get(position);
            holder.setCategoryItem(current);
        } else {
            Category tmp = new Category("Loading category");
            holder.setCategoryItem(tmp);
        }
    }

    public void setCategories(List<Category> categories){
        mCategories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCategories!=null)
            return mCategories.size();
        else return 0;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView mCategoryName;

        public CategoryViewHolder(View view) {
            super(view);
            mCategoryName = (TextView) view.findViewById(R.id.category_item_name);
        }

        /**
         * Parses a {@link Category} object (item) and puts it in the view.
         * @param item
         */
        public void setCategoryItem(@NonNull Category item){
            mCategoryName.setText(item.getName());
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + "'" + mCategoryName.getText() + "'";
        }
    }
}
