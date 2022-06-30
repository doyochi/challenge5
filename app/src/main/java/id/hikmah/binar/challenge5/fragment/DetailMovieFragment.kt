package id.hikmah.binar.challenge5.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import id.hikmah.binar.challenge5.BuildConfig
import id.hikmah.binar.challenge5.databinding.FragmentMovieBinding
import id.hikmah.binar.challenge5.service.TMDBApiService
import id.hikmah.binar.challenge5.service.TMDBClient
import id.hikmah.binar.challenge5.viewModelsFactory
import id.hikmah.binar.challenge5.viewmodel.TMDBViewModel

class DetailMovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val apiService : TMDBApiService by lazy { TMDBClient.instance }
    private val tmdbViewModel: TMDBViewModel by viewModelsFactory { TMDBViewModel(apiService) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getPilemIDFromBandel = arguments?.getInt("aidi_pilem")
        tmdbViewModel.getDetailMovie(getPilemIDFromBandel!!)

        observeData()
    }

    private fun observeData() {
        tmdbViewModel.detailSuccess.observe(viewLifecycleOwner) {
            binding.apply {
                Glide.with(requireContext())
                    .load(BuildConfig.BASE_URL_BACKDROP + it.backdropPath)
                    .into(backgroundThumb)
                Glide.with(requireContext())
                    .load(BuildConfig.BASE_URL_IMAGE + it.posterPath)
                    .into(thumbMoviedetail)
                txtTitleMoviedetail.text = it.title
                txtReleasedateMoviedetail.text = "Release Date: ${it.releaseDate}"
                txtOverviewMoviedetail.text = it.overview
            }
        }
        tmdbViewModel.dataError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

}
