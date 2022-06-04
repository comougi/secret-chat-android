package com.ougi.chatlistscreenimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.ougi.chatlistscreenimpl.R
import com.ougi.chatlistscreenimpl.databinding.FragmentCreateChatDialogBinding
import com.ougi.chatlistscreenimpl.di.ChatListScreenComponentHolder
import com.ougi.chatlistscreenimpl.presentation.viewmodel.CreateChatDialogFragmentViewModel
import com.ougi.corecommon.base.view.BaseDialogFragment
import com.ougi.ui.views.LoadingDialog
import javax.inject.Inject
import com.ougi.ui.R as uiR


class CreateChatDialogFragment :
    BaseDialogFragment<FragmentCreateChatDialogBinding>(FragmentCreateChatDialogBinding::inflate) {

    private val viewModel: CreateChatDialogFragmentViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var viewModelFactory: CreateChatDialogFragmentViewModel.Factory

    private val ids = mutableSetOf<String>()

    override fun onAttach(context: Context) {
        ChatListScreenComponentHolder.getInstance().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogParams()
        setIdInputLayoutParams()
        setStartChattingButtonParams()
    }

    private fun setDialogParams() {
        val window = dialog?.window
        val displayMetrics = resources.displayMetrics
        val dialogWidth = (displayMetrics.widthPixels * 0.97).toInt()
        window?.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT)

        window?.setBackgroundDrawableResource(uiR.drawable.rounded_container)
    }

    private fun setIdInputLayoutParams() {
        with(binding.idInputEditText) {
            addTextChangedListener { editableText ->
                if (editableText != null && editableText.length >= MAX_ID_LENGTH) {
                    val id = editableText.toString().take(MAX_ID_LENGTH)
                    if (ids.none { it == id }) {
                        addNewChip(id)
                    } else {
                        val message =
                            requireContext().getString(R.string.you_already_added_user_with_id) + " " + id
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                    editableText.clear()
                }
            }
        }
    }

    private fun addNewChip(id: String) {
        with(binding.idChipsContainer) {
            val chip = layoutInflater.inflate(R.layout.id_chip_layout, this, false) as Chip
            chip.setEnsureMinTouchTargetSize(false)
            chip.text = id
            val dialog = LoadingDialog()
            dialog.title = context.getString(R.string.check_has_user_id)
            dialog.action = { viewModel.isUserRegistered(id) }
            dialog.results = viewModel.hasUserResultStateFlow
            dialog.onSuccess = {
                addView(chip)
                ids += id
                dialog.dismiss()
            }
            dialog.show(parentFragmentManager, null)

            chip.setOnCloseIconClickListener {
                ids.remove(id)
                removeView(chip)
            }
        }

    }

    private fun setStartChattingButtonParams() {
        with(binding.startChattingButton) {
            setOnClickListener {
                val dialog = LoadingDialog()
                dialog.isCancelable = false
                dialog.title = context.getString(R.string.check_has_user_id)
                dialog.action = { viewModel.createChat(ids.toList()) }
                dialog.results = viewModel.createChatResultStateFlow
                dialog.onSuccess = {
                    dialog.dismiss()
                    this@CreateChatDialogFragment.dismiss()
                    //TODO : navigate to chat here
                }
                dialog.show(parentFragmentManager, null)
            }
        }
    }


    companion object {
        private const val MAX_ID_LENGTH = 14
    }
}