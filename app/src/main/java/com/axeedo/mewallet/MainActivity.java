package com.axeedo.mewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.axeedo.mewallet.TransactionFragments.NewTransactionFragment;
import com.axeedo.mewallet.TransactionFragments.TransactionListFragment;

public class MainActivity extends AppCompatActivity implements NewTransactionFragment.OnNewTransaction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, NewTransactionFragment.class, null)
                    .commit();
        }
    }

    public void switchFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, TransactionListFragment.class, null)
                .commit();
    }
}