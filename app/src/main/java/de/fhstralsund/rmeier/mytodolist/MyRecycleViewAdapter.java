package de.fhstralsund.rmeier.mytodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.fhstralsund.rmeier.mytodolist.data.RecycleViewAdapterTitle;

/**
 * Datenadapter für ein generisches {@link RecycleViewAdapterTitle} Item
 *
 * @author Robert Meier
 */
public class MyRecycleViewAdapter<T extends RecycleViewAdapterTitle> extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {

    private String noItems;
    private int mTextSize = 26;

    public class MyViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mItemView;

        private MyViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView.findViewById(R.id.todoTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemListener.recyclerViewListClicked(v, this.getLayoutPosition()); //getLayoutposition statt getPosition, da Funktion ab API 22 deprecated
        }
    }

    private final LayoutInflater mInflater;
    private List<T> mItems;
    private RecyclerViewClickListener mItemListener;

    public MyRecycleViewAdapter(Context context, RecyclerViewClickListener itemListener) {
        noItems = context.getString(R.string.noItems);
        mInflater = LayoutInflater.from(context);
        mItemListener = itemListener;
    }

    @Override
    public MyRecycleViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyRecycleViewAdapter.MyViewHolder holder, int position) {
        if (mItems != null) {
            T cur = mItems.get(position);
            holder.mItemView.setText(cur.getViewAdapterTitle());
        } else {
            holder.mItemView.setText(noItems);
        }
        holder.mItemView.setTextSize(this.mTextSize);
    }

    public void setItems(List<T> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void setTextSize(int size) {
        this.mTextSize = size;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }


    /**
     * Holt Item aus der TodoListe in der ausgewählten Position
     *
     * @param position Stelle in der Liste
     * @return TodoItem oder null, sofern keine Items vorhanden sind oder Position >= size
     */
    public T getItem(int position) {
        if (mItems != null && mItems.size() > position) {
            return mItems.get(position);
        }
        return null;
    }


}
