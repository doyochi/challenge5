package id.hikmah.binar.challenge5.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.challenge5.R
import id.hikmah.binar.challenge5.database.UserRepo
import id.hikmah.binar.challenge5.databinding.FragmentRegisterBinding
import id.hikmah.binar.challenge5.viewModelsFactory
import id.hikmah.binar.challenge5.viewmodel.RegisterViewModel

class RegisterFragment() : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }
    private val sharedPrefs by lazy { context?.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE) }
    private val viewModel: RegisterViewModel by viewModelsFactory { RegisterViewModel(userRepo, sharedPrefs) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        actionRegister()

    }

    private fun actionRegister() {
        binding.btnDaftar.setOnClickListener {
            // Get value dari TextEditText
            val etUsername = binding.editUsername.editableText.toString()
            val etEmail = binding.editEmail.editableText.toString()
            val etPassword1 = binding.editPassword1.editableText.toString()
            val etPassword2 = binding.editPassword2.editableText.toString()

            // Validasi inputan jika tidak ada yg kosong / jml karakter terpenuhi
            if (registerValidation(etUsername, etEmail, etPassword1, etPassword2)) {
                // Jalankan fungsi addUser di ViewModel
                viewModel.addUserToDb(etUsername, etEmail, etPassword1)
            }
        }

    }

    private fun registerValidation(username: String, email: String, pass1: String, pass2: String): Boolean {
        var result = true
        if (username.isEmpty()) { // jika kosong
            binding.editUsername.error = "Username tidak boleh kosong!"
            result = false
        } else if (username.length < 5) { // jika kurang dari 5 karakter
            binding.editUsername.error = "Minimum 5 karakter!"
            result = false
        } else {
        }

        if (email.isEmpty()) { // jika kosong
            binding.editEmail.error = "Email tidak boleh kosong!"
            result = false
        } else {
        }

        if (pass1.isEmpty()) { // jika kosong
            binding.editPassword1.error = "Password tidak boleh kosong!"
            result = false
        } else if (pass1.length < 6) { // jika kurang dari 6 karakter
            binding.editPassword1.error = "Password minimum 6 Karakter!"
            result = false
        } else {
        }

        if (pass2.isEmpty()) { // jika kosong
            binding.editPassword2.error = "Konfirmasi Password tidak boleh kosong!"
            result = false
        } else if (pass2 != pass1) { // jika konfirm pass tdk sama dgn pass
            binding.editPassword2.error = "Password harus sama!"
            result = false
        } else {
        }

        return result
    }

    private fun observeData() {
        viewModel.userIsRegist.observe(viewLifecycleOwner) {
            binding.editUsername.error = "Username sudah dipakai"
        }

        viewModel.emailIsRegist.observe(viewLifecycleOwner) {
            binding.editEmail.error = "Email sudah dipakai"
        }

        viewModel.isRegist.observe(viewLifecycleOwner) {
            if (it == false) {
                Toast.makeText(requireContext(), "Gagal Daftar", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                Toast.makeText(requireContext(), "Berhasil Daftar", Toast.LENGTH_SHORT).show()
//                binding.editUsername.error = false
//                binding.editEmail.error = false
            }
        }
    }
}