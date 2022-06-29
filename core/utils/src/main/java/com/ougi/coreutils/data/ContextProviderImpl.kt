package com.ougi.coreutils.data

import android.content.Context
import com.ougi.coreutils.utils.ContextProvider
import javax.inject.Inject

class ContextProviderImpl @Inject constructor(override val context: Context) : ContextProvider