package id.hikmah.binar.challenge5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.hikmah.binar.challenge5.adapter.MovieAdapter
import id.hikmah.binar.challenge5.databinding.FragmentMovieBinding
import id.hikmah.binar.challenge5.model.MovieItem
import id.hikmah.binar.challenge5.model.ResultMovie
import id.hikmah.binar.challenge5.service.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.zip.Inflater

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter : MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        initRecyclerView()
        getDataFromNetwork()
    }
    private fun initRecyclerView(){
        movieAdapter = MovieAdapter()
        binding.rvMovie.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getDataFromNetwork(){
        val apiService = APIClient.instance
        apiService.getNowPlaying().enqueue(object: Callback<List<ResultMovie>>{
            override fun onResponse(
                call: Call<List<ResultMovie>>,
                response: Response<List<ResultMovie>>
            ) {//isSuccessful = code() == 200
                if (response.isSuccessful){
                    if (!response.body().isNullOrEmpty()){
                        response.body()?.let {
                            movieAdapter.updateData(it)
                        }
                    }
                }
                binding.pbMovie.isVisible = false

            }

            override fun onFailure(call: Call<List<ResultMovie>>, t: Throwable) {
                binding.pbMovie.isVisible = false
            }

        })
    }
}

