package ir.shahabazimi.eliqweather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import eliqweather.data.utils.convertToDayDate
import eliqweather.domain.models.DailyWeatherModel
import ir.shahabazimi.eliqweather.R
import ir.shahabazimi.eliqweather.databinding.WeatherItemBinding

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 05
 **/
class WeatherRecyclerViewAdapter :
    RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder>() {

    private var items: List<DailyWeatherModel> = listOf()

    private lateinit var binding: WeatherItemBinding

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DailyWeatherModel) = with(binding) {
            dayItemText.text = item.date.convertToDayDate()
            highTemperatureItemText.text = binding.root.context.getString(
                R.string.temperature_format,
                item.maxTemperature.toString()
            )
            lowTemperatureItemText.text = binding.root.context.getString(
                R.string.temperature_format,
                item.minTemperature.toString()
            )
            iconItemImageView.setImageResource(item.weatherIcon)
            conditionItemText.text = binding.root.context.getString(
                item.weatherCode
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    override fun getItemCount() = items.size

    fun setData(newItems: List<DailyWeatherModel>) {
        val diffResult = DiffUtil.calculateDiff(WeatherDiffCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }
}