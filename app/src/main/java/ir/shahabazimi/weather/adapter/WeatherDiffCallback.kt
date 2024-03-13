package ir.shahabazimi.weather.adapter

import androidx.recyclerview.widget.DiffUtil
import weather.domain.models.DailyWeatherModel


/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 05
 **/
//this class extends RecyclerView DiffUtil to compare two list of recycler adapter data model to decide if the dataset is changed or not
class WeatherDiffCallback(
    private val oldWeatherList: List<DailyWeatherModel>,
    private val newWeatherList: List<DailyWeatherModel>
) : DiffUtil.Callback() {

    override fun getOldListSize() =
        oldWeatherList.size

    override fun getNewListSize() =
        newWeatherList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldWeatherList[oldItemPosition].date == newWeatherList[newItemPosition].date

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldWeatherList[oldItemPosition].hashCode() == newWeatherList[newItemPosition].hashCode()
}