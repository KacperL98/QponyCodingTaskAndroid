package kacper.litwinow.qponycodingtaskandroid.repository

import kacper.litwinow.qponycodingtaskandroid.api.FixerApi
import kacper.litwinow.qponycodingtaskandroid.constants.DAY_IN_MILLISECONDS
import kacper.litwinow.qponycodingtaskandroid.constants.LOAD_TIME_COUNT
import kacper.litwinow.qponycodingtaskandroid.constants.YYYY_MM_DD
import kacper.litwinow.qponycodingtaskandroid.extension.formatToStringFromLong
import kacper.litwinow.qponycodingtaskandroid.model.Currency
import kacper.litwinow.qponycodingtaskandroid.model.CurrencyModel
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val fixerApi: FixerApi
) {

    suspend fun loadNewestCurrency(date: Long?): List<CurrencyModel?> {
        return if (date != null) {
            val latestCurrencyItems =
                transformCurrencyToCurrencyItems(getLatestCurrency())
            val historicalCurrencyItems = loadHistoricalCurrencies(date)
            mergeListCurrencyItem(latestCurrencyItems, historicalCurrencyItems)
        } else {
            emptyList()
        }
    }

    suspend fun loadHistoricalCurrencies(oldestDateCurrency: Long?): List<CurrencyModel?> {
        oldestDateCurrency?.let {
            val historicalItems = mutableListOf<CurrencyModel?>()
            var date = oldestDateCurrency
            repeat(LOAD_TIME_COUNT) {
                historicalItems.addAll(
                    transformCurrencyToCurrencyItems(
                        getHistoricalCurrency(date.formatToStringFromLong(YYYY_MM_DD))
                    )
                )
                date -= DAY_IN_MILLISECONDS
            }

            return historicalItems
        } ?: return emptyList()
    }

    private fun mergeListCurrencyItem(
        latestCurrencyModels: List<CurrencyModel?>,
        historicalCurrencyModels: List<CurrencyModel?>
    ) = (latestCurrencyModels union historicalCurrencyModels).toList()

    private fun transformCurrencyToCurrencyItems(currency: Currency?): List<CurrencyModel?> {
        return mutableListOf<CurrencyModel>().apply {
            if (currency != null) {
                add(CurrencyModel.Date(currency.date))
                currency.rates?.createRatesHashMap()?.forEach { map ->
                    add(CurrencyModel.Rate(map.key, map.value, currency.date))
                }
            }
        }
    }


    private suspend fun getLatestCurrency(): Currency? {
        return try {
            fixerApi.getNewestCurrency().body()!!
        } catch (e: Exception) {
            throw IllegalStateException("Unexpected error")
        }
    }

    private suspend fun getHistoricalCurrency(date: String): Currency? {
        return try {
            fixerApi.getHistoricalCurrencies(date).body()!!
        } catch (e: Exception) {
            throw IllegalStateException("Unexpected error")
        }
    }
}