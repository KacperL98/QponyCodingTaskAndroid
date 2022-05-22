package kacper.litwinow.qponycodingtaskandroid.mapper

import kacper.litwinow.qponycodingtaskandroid.extension.merge
import kacper.litwinow.qponycodingtaskandroid.model.Currency
import kacper.litwinow.qponycodingtaskandroid.model.CurrencyModel
import javax.inject.Inject

class CurrencyItemMapper @Inject constructor() {

    fun mergeListCurrencyItem(
        latestCurrencyModels: List<CurrencyModel?>,
        historicalCurrencyModels: List<CurrencyModel?>
    ) = merge(latestCurrencyModels, historicalCurrencyModels)

    fun transformCurrencyToCurrencyItems(currency: Currency?): List<CurrencyModel?> {
        return mutableListOf<CurrencyModel>().apply {
            if (currency != null) {
                add(CurrencyModel.Date(currency.date))
                currency.rates?.createRatesHashMap()?.forEach { map ->
                    add(CurrencyModel.Rate(map.key, map.value, currency.date))
                }
            }
        }
    }
}
