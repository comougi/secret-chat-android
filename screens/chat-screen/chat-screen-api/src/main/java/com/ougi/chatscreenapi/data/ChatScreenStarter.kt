package com.ougi.chatscreenapi.data

import com.ougi.corecommon.base.FragmentStarter

interface ChatScreenStarter : FragmentStarter {
    interface Factory {
        fun create(chatId: String): ChatScreenStarter
    }
}