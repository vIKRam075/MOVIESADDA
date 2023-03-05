package com.example.pipapplication.ui.fragment.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pipapplication.R
import com.example.pipapplication.databinding.FragmentProfileBinding
import com.example.pipapplication.ui.activity.LoginActivity
import com.example.pipapplication.utils.PreferenceManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initView()
        return root
    }

    private fun initView() {
        binding.textName.text = PreferenceManager.getName()
        binding.textUserName.text = PreferenceManager.getUsername()
        binding.textEmail.text = PreferenceManager.getEmail()
        binding.btnLogout.setOnClickListener {
            dialogLogout()
        }
    }

    private fun dialogLogout(){
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(requireContext())

        // set message of alert dialog
        dialogBuilder.setTitle(getString(R.string.app_name))
        dialogBuilder.setMessage("Are you sure you want to logout?")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes") { dialog, id ->
                PreferenceManager.clearAll()
                val intent = Intent(requireActivity(),LoginActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }
            // negative button text and action
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
//        alert.setTitle("AlertDialogExample")
        alert.setOnShowListener {
            alert.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )

            alert.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false

            alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false

        }
        // show alert dialog
        alert.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}