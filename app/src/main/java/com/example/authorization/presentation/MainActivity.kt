package com.example.authorization.presentation

import com.example.authorization.databinding.ActivityMainBinding
import com.example.authorization.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)