package com.nassican.apiapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nassican.apiapp.databinding.FragmentCharacterDetailBinding
import com.nassican.apiapp.data.models.Result

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeButton.setOnClickListener {
            closeFragment()
        }

        arguments?.getParcelable<Result>("character")?.let { character ->
            binding.characterDetailName.text = character.fullName
            binding.characterDetailTitle.text = character.title
            binding.characterDetailFamily.text = character.family

            Glide.with(this)
                .load(character.imageUrl)
                .into(binding.characterDetailImage)
        }
    }

    private fun closeFragment() {
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(character: Result): CharacterDetailFragment {
            return CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("character", character)
                }
            }
        }
    }
}