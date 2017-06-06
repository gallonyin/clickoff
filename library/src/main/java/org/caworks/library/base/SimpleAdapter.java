package org.caworks.library.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 单类型列表基类
 * Created by gallon on 2017/5/18.
 */

public abstract class SimpleAdapter<T> extends RecyclerView.Adapter<RvViewHolder> {

    protected LayoutInflater mInflater;
    public List<T> mData;
    public Context mContext;
    protected int mlayoutId;

    public abstract class OnItemClickListener {
        public abstract void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position) {};
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public SimpleAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mData = data;
        this.mlayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(final RvViewHolder holder, final int position) {
        convert(holder, mData.get(holder.getLayoutPosition()), position);
        setUpItemEvent(holder);
    }

    public abstract void convert(RvViewHolder holder, T bean, int position);

    public void setUpItemEvent(final RvViewHolder holder) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这个获取位置的方法，防止添加删除导致位置不变
                    int layoutPosition = holder.getAdapterPosition();
                    onItemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getAdapterPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, layoutPosition);
                    return false;
                }
            });
        }
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RvViewHolder viewHolder = onCreateDefViewHolder(parent, viewType);
        return viewHolder;
    }

    protected RvViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new RvViewHolder(mInflater.inflate(mlayoutId, parent, false));
    }

    public void addData(int pos, T datas) {
        mData.add(pos, datas);
        notifyItemInserted(pos);
    }

    public void deleteData(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }
}
