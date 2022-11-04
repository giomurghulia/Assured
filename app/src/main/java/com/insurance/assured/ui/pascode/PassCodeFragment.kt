package com.insurance.assured.ui.pascode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.insurance.assured.databinding.FragmentPassCodeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PassCodeFragment : Fragment() {
    private val viewModel: PassCodeViewModel by viewModels()

    private lateinit var binding: FragmentPassCodeBinding

    private val passListItemAdapter = PassListItemAdapter()
    private val passNumberListItemAdapter = PassNumberListItemAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentPassCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.passCodeRecycleView.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.passCodeRecycleView.adapter = passListItemAdapter

        binding.passNumberRecycleView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.passCodeRecycleView.itemAnimator = null
        binding.passNumberRecycleView.adapter = passNumberListItemAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    passListItemAdapter.submitList(it.toList())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.action.collect {
                    if (it) {
                        Toast.makeText(context, "Success PassCode", Toast.LENGTH_SHORT).show()
//                        findNavController().navigate(PassCodeFragmentDirections.actionPassCodeFragmentToAuthorizedFragment())
                    } else {
                        Toast.makeText(context, "InCorrect PassCode", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        submitNumberList()

        passNumberListItemAdapter.setCallBack(object : PassNumberListItemAdapter.CallBack {
            override fun onItemClick(itemId: Int) {
                viewModel.onItemClick(itemId)
            }
        })
    }

    private fun submitNumberList() {
        passNumberListItemAdapter.submitList(
            listOf(
                PassNumberListItem.NumberItem(1),
                PassNumberListItem.NumberItem(2),
                PassNumberListItem.NumberItem(3),
                PassNumberListItem.NumberItem(4),
                PassNumberListItem.NumberItem(5),
                PassNumberListItem.NumberItem(6),
                PassNumberListItem.NumberItem(7),
                PassNumberListItem.NumberItem(8),
                PassNumberListItem.NumberItem(9),
                PassNumberListItem.FingerItem,
                PassNumberListItem.NumberItem(0),
                PassNumberListItem.DeleteItem
            )
        )

    }
}