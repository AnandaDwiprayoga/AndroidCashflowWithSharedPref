package com.anandadp.cashflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anandadp.cashflow.models.Transaction;

import static com.anandadp.cashflow.MainActivity.INDEX_KEY;
import static com.anandadp.cashflow.MainActivity.TRANSACTION_KEY;
import static com.anandadp.cashflow.models.Transaction.Type.CREDIT;
import static com.anandadp.cashflow.models.Transaction.Type.DEBIT;
import static com.anandadp.cashflow.models.Transaction.Type.EMPTY;

public class SaveActivity extends AppCompatActivity {

    private EditText descriptionInput;
    private EditText amountInput;
    private RadioGroup typeRadioGroup;
    private Transaction item;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        descriptionInput = findViewById(R.id.input_description);
        amountInput = findViewById(R.id.input_amount);
        typeRadioGroup = findViewById(R.id.group_type);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            item = extras.getParcelable(TRANSACTION_KEY);
            index = extras.getInt(INDEX_KEY, 0);
            descriptionInput.setText(item.getDescription());
            amountInput.setText(String.valueOf(item.getAmount()));

            if (item.getType() == DEBIT){
                typeRadioGroup.check(R.id.radio_debit);
            }else if(item.getType() == CREDIT){
                typeRadioGroup.check(R.id.radio_credit);
            }
        }
    }

    public void handleSubmit(View view) {

        if(validasiInput()) {
            String desc = descriptionInput.getText().toString();
            int amount = Integer.parseInt(amountInput.getText().toString());
            Transaction.Type type = getChekedType();

            item.setDescription(desc);
            item.setAmount(amount);
            item.setType(type);

            Intent intent = new Intent();
            intent.putExtra(TRANSACTION_KEY, item);
            intent.putExtra(INDEX_KEY, index);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean validasiInput() {
        if (TextUtils.isEmpty(descriptionInput.getText())){
            Toast.makeText(this, "Please enter desc", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(amountInput.getText()) || amountInput.getText().toString().equals("0")){
            Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (typeRadioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Please choose type debit or credit", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Transaction.Type getChekedType(){
        if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_debit){
            return DEBIT;
        }else if (typeRadioGroup.getCheckedRadioButtonId() == R.id.radio_credit){
            return CREDIT;
        }
        return EMPTY;
    }
}
