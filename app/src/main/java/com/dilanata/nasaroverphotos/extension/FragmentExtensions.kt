package com.dilanata.nasaroverphotos.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.dilanata.nasaroverphotos.util.RoverTypes
import com.dilanata.nasaroverphotos.util.SharedKeys
import com.github.ajalt.timberkt.e


fun Fragment.navigateSafe(
    @IdRes resId: Int,
    bundle: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    try {
        findNavController().navigate(
            resId,
            bundle,
            navOptions,
            navigatorExtras
        )
    } catch (exp: Exception) {
        e(exp)
    }
}

var Fragment.selectedRover: RoverTypes
    get() = getSharedPref(SharedKeys.DEFAULT_SHARED_KEYS, requireContext(), SharedKeys.selectRover)
    set(value) {
        saveSharedPref(SharedKeys.DEFAULT_SHARED_KEYS, requireContext(), SharedKeys.selectRover, value)
    }