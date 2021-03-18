package com.axeedo.mewallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import com.axeedo.mewallet.TransactionFragments.TransactionListFragment;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity
        implements OnSwitchFragmentListener {

    TransactionsViewModel transactionsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            // instantiate ViewModel
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
                .addToBackStack("activity back stack")
                .commit();
    }


    @Override
    public void goToFragment(@NonNull @NotNull Class<? extends Fragment> fragmentClass,
                             @Nullable Bundle args) {
        switchToFragment(fragmentClass, args);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}