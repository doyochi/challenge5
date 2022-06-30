package id.hikmah.binar.challenge5.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.hikmah.binar.challenge5.adapter.TMDBAdapter
import id.hikmah.binar.challenge5.database.UserRepo
import id.hikmah.binar.challenge5.databinding.FragmentHomeBinding
import id.hikmah.binar.challenge5.BuildConfig
import id.hikmah.binar.challenge5.R
import id.hikmah.binar.challenge5.service.TMDBApiService
import id.hikmah.binar.challenge5.service.TMDBClient
import id.hikmah.binar.challenge5.model.Result
import id.hikmah.binar.challenge5.viewModelsFactory
import id.hikmah.binar.challenge5.viewmodel.HomeViewModel
import id.hikmah.binar.challenge5.viewmodel.TMDBViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var tmdbAdapter: TMDBAdapter

    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }
    private val sharedPrefs by lazy { context?.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE) }
    private val viewModel: HomeViewModel by viewModelsFactory { HomeViewModel(userRepo, sharedPrefs) }

    private val apiService : TMDBApiService by lazy { TMDBClient.instance }
    private val tmdbViewModel: TMDBViewModel by viewModelsFactory { TMDBViewModel(apiService) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        viewModel.getUsername()
        tmdbViewModel.getAllMoviePopular()
        moveToProfile()
        observeData()
    }

    private fun initRecyclerView() {
        tmdbAdapter = TMDBAdapter { id_momovie,pilem: Result ->
            val bandel = Bundle()
            bandel.putInt("aidi_pilem", id_momovie)
            findNavController().navigate(R.id.action_homeFragment_to_detailMovieFragment, bandel)
        }
        binding.apply {
            rvData.adapter = tmdbAdapter
            rvData.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun moveToProfile() {
        binding.btnProfil.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profilFragment)
        }
    }

    private fun observeData() {
        viewModel.showUsername.observe(viewLifecycleOwner) {
            // Show username di Toolbar
            binding.txtWelcomeUser.text = "Welcome, $it"
        }
        tmdbViewModel.dataSuccess.observe(viewLifecycleOwner) {
            tmdbAdapter.updateDataRecycler(it)
        }
        tmdbViewModel.dataError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}