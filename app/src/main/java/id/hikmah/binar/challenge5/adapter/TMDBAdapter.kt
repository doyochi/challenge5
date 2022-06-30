package id.hikmah.binar.challenge5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.challenge5.BuildConfig
import id.hikmah.binar.challenge5.databinding.ItemDataBinding
import id.hikmah.binar.challenge5.model.MoviePopularResponse
import id.hikmah.binar.challenge5.model.Result

class TMDBAdapter(private val onClickListenerUyee: (aidi: Int, Result) -> Unit):
    RecyclerView.Adapter<TMDBAdapter.TMDBViewHolder>() {

    val diffCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private var differ = AsyncListDiffer(this, diffCallback)

    fun updateDataRecycler(movies: MoviePopularResponse?) = differ.submitList(movies?.results)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TMDBViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TMDBViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TMDBViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class TMDBViewHolder(private val binding: ItemDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.cardTitleValue.text = item.title
            binding.cardOverviewValue.text = item.overview
            Glide.with(itemView.context)
                .load(BuildConfig.BASE_URL_IMAGE + item.posterPath)
                .into(binding.thumbMovie)

            binding.itemData.setOnClickListener {
                onClickListenerUyee.invoke(item.id, item)
            }
        }
    }
}