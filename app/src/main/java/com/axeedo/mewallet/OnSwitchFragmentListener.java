package com.axeedo.mewallet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public interface OnSwitchFragmentListener {
    /**
     * Informs the parent activity to switch to a given fragment
     * @param fragmentClass
     * @param args
     */
    public void goToFragment(@NonNull @NotNull Class<? extends Fragment> fragmentClass,
                             @Nullable Bundle args);
}