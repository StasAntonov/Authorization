package com.example.authorization.presentation.start

import com.example.authorization.databinding.FragmentStartBinding
import com.example.authorization.presentation.base.BaseFragment

class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {
    override fun initListeners() {
        super.initListeners()
        binding.btLogin.setOnClickListener { navigate(StartFragmentDirections.actionStartToLogin()) }

        binding.btRegistration.setOnClickListener { navigate(StartFragmentDirections.actionStartToRegistration()) }
    }
}