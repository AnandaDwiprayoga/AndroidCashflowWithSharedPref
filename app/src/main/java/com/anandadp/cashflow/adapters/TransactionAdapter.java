package com.anandadp.cashflow.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anandadp.cashflow.MainActivity;
import com.anandadp.cashflow.R;
import com.anandadp.cashflow.models.Transaction;

import java.util.List;

import static com.anandadp.cashflow.models.Transaction.Type.CREDIT;
import static com.anandadp.cashflow.models.Transaction.Type.DEBIT;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    private List<Transaction> items;
    private OnItemTransactionListener listener;

    public TransactionAdapter(List<Transaction> items, OnItemTransactionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void refresh(List<Transaction> items){
        if (this.items.size() != 0){
            this.items.clear();
        }
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDesc;
        TextView tvAmount;
        TextView tvJenis;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmount    = itemView.findViewById(R.id.text_amount);
            tvDesc      = itemView.findViewById(R.id.text_description);
            tvJenis     = itemView.findViewById(R.id.text_jenis);
        }

        public void bind(final int index, final Transaction item){
            tvDesc.setText(item.getDescription());
            tvAmount.setText(MainActivity.convertToRupiah(item.getAmount()));

            if (item.getType() == CREDIT){
                tvJenis.setText("CREDIT");
                tvJenis.setBackgroundColor(Color.parseColor("#0def42"));
                tvAmount.setTextColor(Color.parseColor("#0def42"));
            }else if (item.getType() == DEBIT){
                tvJenis.setText("DEBIT");
                tvJenis.setBackgroundColor(Color.parseColor("#dc3d2a"));
                tvAmount.setTextColor(Color.parseColor("#dc3d2a"));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTransactionClicked(index, item);
                }
            });
        }
    }

    public interface OnItemTransactionListener {
        void onTransactionClicked(int index, Transaction item);
    }
}
