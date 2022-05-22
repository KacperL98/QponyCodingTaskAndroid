package kacper.litwinow.qponycodingtaskandroid.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kacper.litwinow.qponycodingtaskandroid.R
import kacper.litwinow.qponycodingtaskandroid.databinding.ItemRateDateBinding
import kacper.litwinow.qponycodingtaskandroid.extension.formatDate
import kacper.litwinow.qponycodingtaskandroid.extension.formatPriceValues
import kacper.litwinow.qponycodingtaskandroid.model.CurrencyModel

class CurrencyViewHolder(private val binding: ItemRateDateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private fun bindDate(model: CurrencyModel.Date) {
        model.date?.let { date ->
            binding.txtDate.text =
                binding.root.context.getString(R.string.day_and_date, date.formatDate())
        }
    }

    private fun bindRate(
        model: CurrencyModel.Rate,
        onItemClick: (rateName: String?, rateValue: Double?, date: String?) -> Unit
    ) {
        if (!model.rateName.isNullOrEmpty() && model.rateValue != null) {
            binding.txtRate.text = handleRateText(model.rateName, model.rateValue)
            binding.txtRate.setOnClickListener {
                onItemClick.invoke(model.rateName, model.rateValue, model.date)
            }
        }
    }

    private fun handleRateText(rateName: String?, rateValue: Double?): String {
        return binding.root.context.getString(
            R.string.currency_value,
            rateName,
            rateValue?.formatPriceValues()
        )
    }

    fun bindCurrencyValues(
        currencyModel: CurrencyModel,
        onItemClick: (rateName: String?, rateValue: Double?, date: String?) -> Unit
    ) {
        when (currencyModel) {
            is CurrencyModel.Date -> bindDate(currencyModel)
            is CurrencyModel.Rate -> bindRate(currencyModel, onItemClick)
        }
    }

    companion object {
        fun create(parent: ViewGroup): CurrencyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rate_date, parent, false)
            val binding = ItemRateDateBinding.bind(view)
            return CurrencyViewHolder(binding)
        }
    }
}