package id.hikmah.binar.challenge5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.challenge5.BuildConfig
import id.hikmah.binar.challenge5.databinding.ItemDataBinding
import id.hikmah.binar.challenge5.databinding.ItemMovieBinding
import id.hikmah.binar.challenge5.model.MovieResponse
import id.hikmah.binar.challenge5.model.Result

class MovieAdapter(private val onClickListers: (id: Int, movie: Result) -> Unit):
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    val diffCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private var differ = AsyncListDiffer(this, diffCallback)

    fun updateDataRecycler(movies: MovieResponse?) = differ.submitList(movies?.results)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class MovieViewHolder(private val binding: ItemDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.cardTitleValue.text = item.title
            binding.cardOverviewValue.text = item.overview
            Glide.with(itemView.context)
                .load(BuildConfig.BASE_URL_IMAGE + item.posterPath)
                .into(binding.thumbMovie)

            binding.itemData.setOnClickListener {
                onClickListers.invoke(item.id, item)
            }
        }
    }
}