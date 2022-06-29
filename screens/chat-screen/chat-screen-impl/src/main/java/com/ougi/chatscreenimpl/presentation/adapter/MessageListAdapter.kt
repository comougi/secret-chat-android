package com.ougi.chatscreenimpl.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ougi.chatscreenimpl.R
import com.ougi.chatscreenimpl.databinding.RecipientMessageItemLayoutBinding
import com.ougi.chatscreenimpl.databinding.UserMessageItemLayoutBinding
import com.ougi.chatscreenimpl.presentation.viewmodel.MessageListAdapterViewModel
import com.ougi.messagerepoapi.data.entities.Message
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import com.ougi.messagerepoapi.data.repository.MessageRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MessageListAdapter @Inject constructor(
    messageListAdapterViewModelFactory: MessageListAdapterViewModel.Factory,
    private val messageRepository: MessageRepository
) : ListAdapter<PersonalMessage, RecyclerView.ViewHolder>(PersonalMessageDiffer),
    LifecycleOwner {

    private val viewModel = messageListAdapterViewModelFactory.create()

    private val _userId = runBlocking { viewModel.getUserId() }
    private val userId by lazy { _userId }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).senderId) {
            userId -> USER
            else -> RECIPIENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            USER -> {
                val binding = UserMessageItemLayoutBinding.inflate(inflater, parent, false)
                UserMessageViewHolder(binding)
            }
            RECIPIENT -> {
                val binding = RecipientMessageItemLayoutBinding.inflate(inflater, parent, false)
                RecipientMessageViewHolder(binding)
            }
            else -> {
                val binding = RecipientMessageItemLayoutBinding.inflate(inflater, parent, false)
                RecipientMessageViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        val isHideDate: Boolean =
            if (position == itemCount - 1) false
            else !compareDates(message.date, getItem(position).date)

        if (holder is RecipientMessageViewHolder) {
            val isHideId = if (position == itemCount - 1) false
            else getItem(position + 1).senderId == message.senderId
            holder.isHideId = isHideId
        }

        (holder as MessageViewHolder).run {
            this.isHideDate = isHideDate
            bind(message)
        }
    }

    interface MessageViewHolder {
        var isHideDate: Boolean
        fun bind(message: PersonalMessage)
    }

    inner class UserMessageViewHolder(
        private val binding: UserMessageItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root), MessageViewHolder {
        override var isHideDate: Boolean = true

        override fun bind(message: PersonalMessage) {
            with(binding) {
                if (!isHideDate) {
                    dateTextView.showDate(message.date)
                }
                messageTextView.setMessageText(message)
                messageTimeTextView.text = parseDate(message.date)

                lifecycleScope.launch {
                    messageRepository.getMessageById(message.id).collect {
                        val resource = when (it.status) {
                            Message.Status.SENDING -> R.drawable.ic_sent_48
                            Message.Status.SENT -> R.drawable.ic_sent_48
                            Message.Status.DELIVERED -> R.drawable.ic_delivered_48
                            Message.Status.FAIL -> R.drawable.ic_fail_48
                            else -> R.drawable.ic_fail_48
                        }
                        messageStatusImageView.setImageResource(resource)
                        messageStatusImageView.setBackgroundResource(resource)
                    }
                }
            }
        }
    }

    inner class RecipientMessageViewHolder(
        private val binding: RecipientMessageItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root), MessageViewHolder {

        override var isHideDate: Boolean = true
        var isHideId: Boolean = true

        override fun bind(message: PersonalMessage) {
            with(binding) {
                if (!isHideDate) {
                    dateTextView.showDate(message.date)
                }
                messageTextView.setMessageText(message)
                messageTimeTextView.text = parseDate(message.date)

                if (!isHideId) {
                    recipientIdTextView.text = message.senderId
                    recipientIdTextView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun TextView.showDate(date: Instant) {
        text = SimpleDateFormat("MMMM dd").format(Date.from(date.toJavaInstant())).toString()
        visibility = View.VISIBLE
    }

    private fun TextView.setMessageText(message: PersonalMessage) {
        lifecycleScope.launch {
            val messageText = messageRepository.decryptMessageData(message.data)
            text = messageText.first
        }
    }

    private fun parseDate(instant: Instant): String {
        return SimpleDateFormat("HH:mm").format(Date.from(instant.toJavaInstant())).toString()
    }

    private fun compareDates(instant1: Instant, instant2: Instant): Boolean {
        val instant1DayAndYear = getDayAndYearOfInstant(instant1)
        val instant2DayAndYear = getDayAndYearOfInstant(instant2)

        val dayCompareResult = instant1DayAndYear.first.compareTo(instant2DayAndYear.first)
        val yearCompareResult = instant1DayAndYear.second.compareTo(instant2DayAndYear.second)

        if (yearCompareResult == -1) return false
        return if (yearCompareResult == 0) {
            when (dayCompareResult) {
                1 -> true
                else -> false
            }
        } else false
    }

    private fun getDayAndYearOfInstant(instant: Instant): Pair<Int, Int> {
        val calendar = Calendar.getInstance()
        val date = Date.from(instant.toJavaInstant())
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_YEAR)
        val year = calendar.get(Calendar.YEAR)
        return day to year
    }

    object PersonalMessageDiffer : DiffUtil.ItemCallback<PersonalMessage>() {
        override fun areItemsTheSame(
            oldItem: PersonalMessage,
            newItem: PersonalMessage
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PersonalMessage,
            newItem: PersonalMessage
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val USER = 1
        private const val RECIPIENT = 0
    }

    override fun getLifecycle(): Lifecycle {
        return LifecycleRegistry(this)
    }
}