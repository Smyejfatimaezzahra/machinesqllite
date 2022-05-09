package com.example.machinesqllite;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.machinesqllite.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.machinesqllite.databinding.FragmentItemBinding;

import java.util.ArrayList;
import java.util.List;

import classes.Machine;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter2 extends RecyclerView.Adapter<MyItemRecyclerViewAdapter2.ViewHolder> implements Filterable {

    private final List<Machine> mValues;
    private List<Machine> filters;
    private NewFilter mfilter;
    private static MyItemRecyclerViewAdapter2 adapter;
    public static  MyItemRecyclerViewAdapter2 getInstance(List<Machine> items){
        if(adapter==null) adapter=new MyItemRecyclerViewAdapter2( items);
        return  adapter;
    }
    private MyItemRecyclerViewAdapter2(List<Machine> items) {
        mValues = items;
       filters = new ArrayList<>();
        filters.addAll(mValues);
        mfilter = new NewFilter(this);
    }

    public static void filter(MenuItem menuItem) {
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;

            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null){
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = filters.get(position);
        holder.mIdView.setText(filters.get(position).getId()+"");
        holder.mContentView.setText(filters.get(position).getNom());
        holder.priceView.setText(filters.get(position).getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    @Override
    public Filter getFilter() {
        return mfilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public  TextView priceView;
        public Machine mItem;
        ImageView img;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.ids;
            mContentView = binding.name;
            priceView=binding.price;
            img=binding.image;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
    public class NewFilter extends Filter {
        public RecyclerView.Adapter mAdapter;
        public NewFilter(RecyclerView.Adapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
           filters.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
               filters.addAll(mValues);
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Machine p : mValues) {
                    if (p.getNom().toLowerCase().startsWith(filterPattern)) {
                        filters.add(p);
                    }
                }
            }
            results.values = filters;
            results.count = filters.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filters = (List<Machine>) filterResults.values;
            this.mAdapter.notifyDataSetChanged();
        }
    }

}