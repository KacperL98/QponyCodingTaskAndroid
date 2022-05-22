package kacper.litwinow.qponycodingtaskandroid.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kacper.litwinow.qponycodingtaskandroid.constants.DATE
import kacper.litwinow.qponycodingtaskandroid.constants.RATE
import kacper.litwinow.qponycodingtaskandroid.model.CurrencyModel
import kacper.litwinow.qponycodingtaskandroid.ui.viewholder.CurrencyViewHolder

class CurrenciesAdapter(private val onItemClick: (rateName: String?, rateValue: Double?, date: String?) -> Unit) :
    ListAdapter<CurrencyModel, CurrencyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindCurrencyValues(getItem(position), onItemClick)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CurrencyModel.Date -> DATE
            is CurrencyModel.Rate -> RATE
            else -> throw IllegalArgumentException("Error $position")
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CurrencyModel>() {

            override fun areItemsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: CurrencyModel,
                newItem: CurrencyModel
            ): Boolean =
                oldItem == newItem
        }
    }
}
