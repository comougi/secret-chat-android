package com.ougi.corecommon.base

import androidx.navigation.NavController

interface FragmentStarter : ScreenStarter {

    fun start(navController: NavController)

}