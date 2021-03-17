package com.axeedo.mewallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.TransactionFragments.TransactionListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class MainActivity extends AppCompatActivity
        implements OnSwitchFragmentListener {

    TransactionsViewModel transactionsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            transactionsViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
            // set initial starting fragment
            switchToFragment(TransactionListFragment.class, null);
        }
    }

    /**
     * Switches view to the given fragment class.
     * @param fragmentClass
     * @param args
     */
    private void switchToFragment(@NonNull @NotNull Class<? extends Fragment> fragmentClass,
                               @Nullable Bundle args){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container, fragmentClass, args)
                .commit();
    }


    @Override
    public void goToFragment(@NonNull @NotNull Class<? extends Fragment> fragmentClass,
                             @Nullable Bundle args) {
        switchToFragment(fragmentClass, args);
    }
}