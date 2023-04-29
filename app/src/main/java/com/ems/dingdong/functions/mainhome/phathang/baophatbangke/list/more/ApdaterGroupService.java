package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.more;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApdaterGroupService extends RecyclerView.Adapter<ApdaterGroupService.HolderView> implements Filterable {

    public List<GroupServiceMode> mListFilter;
    List<GroupServiceMode> mList;
    Context mContext;

    public ApdaterGroupService(Context context, List<GroupServiceMode> items) {
        mContext = context;
        if (items == null) {
            mList = new ArrayList<>();
            mListFilter = new ArrayList<>();
        } else {
            mList = items;
            mListFilter = items;
        }
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ApdaterGroupService.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));

    }

    public List<GroupServiceMode> getmListFilter() {
        return mList;
    }

    @Override
    public ApdaterGroupService.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApdaterGroupService.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_groupservice, parent, false));
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListFilter = mList;
                } else {
                    List<GroupServiceMode> filteredList = new ArrayList<>();
                    for (GroupServiceMode row : mList) {

                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    mListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (!charSequence.toString().isEmpty())
                    mListFilter = (ArrayList<GroupServiceMode>) filterResults.values;
                else mListFilter = mList;
                notifyDataSetChanged();
            }
        };
    }


    public class HolderView extends RecyclerView.ViewHolder {
        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public GroupServiceMode getItem(int position) {
            return mListFilter.get(position);
        }

        @BindView(R.id.tv_title)
        TextView tvTennhomquyen;
        @BindView(R.id.cb_check)
        public ImageView cbCheck;
        @BindView(R.id.ll_background)
        public LinearLayout ll_background;

        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        public void bindView(Object model) {
            GroupServiceMode item = (GroupServiceMode) model;
            tvTennhomquyen.setText(item.getCode() + " - " + item.getName());
            if (item.isCheck()) {
                cbCheck.setVisibility(View.VISIBLE);
                ll_background.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
            } else {
                cbCheck.setVisibility(View.GONE);
                ll_background.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        }
    }
}
