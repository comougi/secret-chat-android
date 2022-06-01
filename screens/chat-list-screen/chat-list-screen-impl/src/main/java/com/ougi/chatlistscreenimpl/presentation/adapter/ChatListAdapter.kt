package com.ougi.chatlistscreenimpl.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ougi.chatlistscreenimpl.databinding.ChatListItemBinding
import com.ougi.chatrepoapi.data.entity.Chat

class ChatListAdapter : ListAdapter<Chat, ChatListAdapter.ChatItemViewHolder>(ChatDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChatListItemBinding.inflate(inflater, parent, false)
        return ChatItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        val chat = getItem(position)
        holder.bind(chat)
    }

    class ChatItemViewHolder(private val binding: ChatListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.chatTitleTextView.text = chat.chatTitle
            binding.lastMessageTextView.text = "Last message"
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