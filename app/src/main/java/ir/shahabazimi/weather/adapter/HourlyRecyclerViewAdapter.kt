package ir.shahabazimi.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.shahabazimi.weather.R
import ir.shahabazimi.weather.databinding.HourlyItemBinding
import weather.domain.models.HourlyWeatherModel

/**
 * @Author: Shahab Azimi
 * @Date: 2024 - 03 - 13
 **/
//main recycler adapter which implemented the HourlyDiffCallback
class HourlyRecyclerViewAdapter :
    RecyclerView.Adapter<HourlyRecyclerViewAdapter.ViewHolder>() {

    private val items: MutableList<HourlyWeatherModel> = mutableListOf()

    private lateinit var binding: HourlyItemBinding

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HourlyWeatherModel) = with(binding) {
            hourItemText.text = item.time
            temperatureItemText.text = binding.root.context.getString(
                R.string.temperature_format,
                item.temperature
            )
            iconItemImageView.setAnimation(item.weatherIcon)
            iconItemImageView.playAnimation() // must call playAnimation after setAnimation

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            HourlyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    override fun getItemCount() = items.size

    //this function is used to load and reload data to the recyclerview
    fun setData(newItems: List<HourlyWeatherModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}