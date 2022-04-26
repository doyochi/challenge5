package id.hikmah.binar.challenge5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.challenge5.databinding.ItemMovieBinding
import id.hikmah.binar.challenge5.model.ResultMovie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    private val diffCallback = object : DiffUtil.ItemCallback<ResultMovie>(){
        override fun areItemsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean {
            TODO("Not yet implemented")
        }
    }
    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(movies: List<ResultMovie>) = listDiffer.submitList(movies)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    /*
    View holder wajib extend RecyclerView ViewHolder
    ViewHolder butuh view, maka kita tambahkan parameter view

    Untuk view binding
    bidning.root == view
    jd kita bisa mengganti view dengan binding.root
     */

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ResultMovie){
            binding.apply {
                tvMovieName.text = item.originalTitle
                Glide.with(itemView.context)
                    .load(item.posterPath)
                    .into(ivMovie)
            }
        }
    }
}