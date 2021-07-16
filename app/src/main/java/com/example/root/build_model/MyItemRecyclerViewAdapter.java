package com.example.root.build_model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.root.build_model.MainFragment.OnListFragmentInteractionListener;
import com.example.root.build_model.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private OnListFragmentInteractionListener mListener;
    private MainFragment.OnListIconListener iconListener;
    private boolean mUseImge;

    public MyItemRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener,boolean isUseImge) {
        mValues = items;
        mListener = listener;
        mUseImge = isUseImge;
    }
    public MyItemRecyclerViewAdapter(List<DummyItem> items, MainFragment.OnListIconListener listener, boolean isUseImge) {
        mValues = items;
        iconListener = listener;
        mUseImge = isUseImge;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(mUseImge)
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item2, parent, false);
            else
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        if(mUseImge) {
                    Glide.with(holder.itemView.getContext()).load(mValues.get(position).details).placeholder(R.drawable.ic_action_name).into(holder.imageView);
        }
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem,v);
                }
            }
        });
        if(!mUseImge)
        holder.deletePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iconListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    iconListener.onListIconClick(holder.mItem,v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView imageView;
        public final ImageView deletePic;

        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            imageView = (ImageView) view.findViewById(R.id.song_pic);
            deletePic = (ImageView) view.findViewById(R.id.del_pic);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
