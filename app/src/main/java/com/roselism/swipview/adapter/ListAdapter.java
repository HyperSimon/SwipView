package com.roselism.swipview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roselism.base.app.RoseAdapter2;
import com.roselism.swipview.R;
import com.roselism.swipview.library.SlideView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by simon on 16-6-9.
 */
public class ListAdapter extends RoseAdapter2<ListAdapter.MyViewHolder, String> {


    public ListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected void bindData(final int position, final MyViewHolder viewholder) {
        String data = mData.get(position);
        viewholder.mTextview.setText(data);

        viewholder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewholder.mSwipview.closeBottom();
                mData.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    protected MyViewHolder createViewHolder() {
        mConvertView = View.inflate(context, R.layout.list_item_demo, null);
        MyViewHolder myViewHolder = new MyViewHolder(mConvertView);
        mConvertView.setTag(myViewHolder);
        return myViewHolder;
    }

    static class MyViewHolder extends RoseAdapter2.ViewHolder {
        @Bind(R.id.profile) ImageView mProfile;
        @Bind(R.id.surfaceView) RelativeLayout mSurfaceView;
        @Bind(R.id.delete_button) Button mDeleteButton;
        @Bind(R.id.edit_button) Button mEditButton;
        @Bind(R.id.bottomView) LinearLayout mBottomView;
        @Bind(R.id.swipview) SlideView mSwipview;
        @Bind(R.id.text) TextView mTextview;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
