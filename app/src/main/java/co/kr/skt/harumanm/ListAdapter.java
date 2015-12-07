package co.kr.skt.harumanm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 2015-12-01.
 */
public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    List<String> items = new ArrayList<String>();

    ListViewHolder.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(ListViewHolder.OnItemClickListener listener){
        mItemClickListener = listener;
    }
    public void add(String item){
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, null);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.setData(items.get(position), items.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
