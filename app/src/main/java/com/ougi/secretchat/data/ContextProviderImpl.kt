package com.ougi.secretchat.data

import android.content.Context
import com.ougi.coreutils.utils.ContextProvider

internal class ContextProviderImpl(_context: Context) : ContextProvider {
    override val context: Context = _context
}