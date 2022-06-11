package com.ougi.chatlistscreenimpl.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ougi.chatlistscreenimpl.databinding.ChatListItemBinding
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.chatscreenapi.data.ChatScreenStarter
import javax.inject.Inject

class ChatListAdapter @Inject constructor(private val chatScreenStarterFactory: ChatScreenStarter.Factory) :
    ListAdapter<Chat, ChatListAdapter.ChatItemViewHolder>(ChatDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChatListItemBinding.inflate(inflater, parent, false)
        return ChatItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        val chat = getItem(position)
        holder.bind(chat)
    }

    inner class ChatItemViewHolder(private val binding: ChatListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.chatTitleTextView.text = chat.title
            binding.lastMessageTextView.text = "Last message"

            binding.root.setOnClickListener {
                val navController = binding.root.findNavController()
                chatScreenStarterFactory.create(chat.id).start(navController)
            }
        }
    }

    object ChatDiffUtil : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }
    }
}