package com.example.faiz_foodorderingapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.faiz_foodorderingapp.Interface.ItemClickListener;
import com.example.faiz_foodorderingapp.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView orderid;
        public TextView orderaddress;
        public TextView ordername;
        public TextView orderphone;
        public TextView orderdate;
        public TextView ordertotal;
        public Button orderedit;
        public Button orderdelete;
        private ItemClickListener itemClickListener;

        public MenuViewHolder(View itemView){
            super(itemView);

            orderid = itemView.findViewById(R.id.orderid);
            orderaddress = itemView.findViewById(R.id.orderaddress);
            ordername = itemView.findViewById(R.id.ordername);
            orderphone = itemView.findViewById(R.id.orderphone);
            orderdate = itemView.findViewById(R.id.orderdate);
            ordertotal = itemView.findViewById(R.id.ordertotal);
            orderedit = itemView.findViewById(R.id.btnorderedit);
            orderdelete = itemView.findViewById(R.id.btnorderdelete);

            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

    @Override
        public void onClick(View view)
        {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
}
