package com.example.authorization.presentation.start

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.authorization.R
import com.example.authorization.common.ext.toast
import com.example.authorization.databinding.FragmentStartBinding
import com.example.authorization.domain.model.QrCode
import com.example.authorization.presentation.base.BaseFragment
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {

    private val viewModel: StartViewModel by viewModels()


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            } else {
                checkPermissionCamera(requireActivity())
            }
        }

    private val scanLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            run {
                if (result.contents == null) {
                    toast(getString(R.string.cancelled))
                } else {
                    viewModel.qrCode(QrCode(result.contents))
                }
            }
        }

    private fun showCamera() {
        val options = ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setPrompt(getString(R.string.scan_qr))
            setCameraId(0)
            setBeepEnabled(false)
            setBarcodeImageEnabled(true)
            setOrientationLocked(false)
        }
        scanLauncher.launch(options)
    }

    private fun checkPermissionCamera(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showCamera()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            toast(getString(R.string.camera_permission_required))
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    override fun initListeners() = with(binding) {
        super.initListeners()

        icQrCode.setOnClickListener {
            checkPermissionCamera(requireActivity())
        }

        btLogin.setOnClickListener { navigate(StartFragmentDirections.actionStartToLogin()) }

        btRegistration.setOnClickListener { navigate(StartFragmentDirections.actionStartToRegistration()) }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.qrResult.observe(viewLifecycleOwner) {
            toast(it)
        }
    }
}