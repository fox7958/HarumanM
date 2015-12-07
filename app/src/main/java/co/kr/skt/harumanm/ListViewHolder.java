package co.kr.skt.harumanm;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

/**
 * Created by T on 2015-12-01.
 */
public class ListViewHolder extends RecyclerView.ViewHolder {

    public interface OnItemClickListener{
        public void onItemClick(String name, int position);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    EditText editTitle;
    EditText editFinish;

    public ListViewHolder(View itemView) {
        super(itemView);
        editTitle = (EditText)itemView.findViewById(R.id.edit_title);
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(mTitle, position);
                    mListener.onItemClick(mFinish, position);
                }
            }
        });
        editFinish = (EditText)itemView.findViewById(R.id.edit_finish);

    }
    String mFinish;
    String mTitle;
    public void setData(String title, String finish){
        mTitle = title;
        mFinish = finish;
        editTitle.setText(title);
        editFinish.setText(finish);
    }
}
