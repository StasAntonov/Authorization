package com.example.authorization.presentation.user

import com.example.authorization.databinding.FragmentUserBinding
import com.example.authorization.presentation.base.BaseFragment

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    override fun initListeners() {
        super.initListeners()
        binding.imgBack.setOnClickListener {
            goBack()
        }
    }
}