package id.hikmah.binar.challenge5.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.challenge5.MainActivity
import id.hikmah.binar.challenge5.R
import id.hikmah.binar.challenge5.database.UserRepo
import id.hikmah.binar.challenge5.databinding.FragmentLoginBinding
import id.hikmah.binar.challenge5.viewModelsFactory
import id.hikmah.binar.challenge5.viewmodel.AuthViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }
    private val sharedPrefs by lazy { context?.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE) }
    private val viewModel: AuthViewModel by viewModelsFactory { AuthViewModel(userRepo, sharedPrefs) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLoginState()
        doLogin()
        moveToRegister()
        observeData()
    }

    private fun checkLoginState() {
        val loginState = sharedPrefs?.getBoolean("LOGIN_STATE", false)
        if (loginState == true) { // true = pindah ke Home
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun doLogin() {
        binding.btnLogin.setOnClickListener {
            // Get value dari EditText
            val etEmail = binding.editUsername.text.toString()
            val etPassword = binding.editPassword.text.toString()
            // Validasi inputan kosong
            if (loginValidation(etEmail, etPassword)) {
                // Jalankan fungsi di ViewModel
                viewModel.loginUser(etEmail, etPassword)
            }
        }
    }

    private fun loginValidation(email: String, password: String): Boolean {
        var result = true
        if (email.isEmpty()) { // jika kosong
            binding.editUsername.error = "Email tidak boleh kosong!"
            result = false
        } else {
            binding.editUsername
        }

        if (password.isEmpty()) { // jika kosong
            binding.editPassword.error = "Password tidak boleh kosong!"
            result = false
        }  else {
            binding.editPassword
        }

        return result
    }

    private fun moveToRegister() {
        binding.btnToregist.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun observeData() {
        viewModel.statusLogin.observe(viewLifecycleOwner) {
            if (it == "Berhasil Login") { // jika berhasil
                // Munculkan toast 'Berhasil Login'
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                // Pindah screen ke HomeFragment (berada di MainActivity)
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else { // jika gagal == munculkan toast 'user & email salah'
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

}