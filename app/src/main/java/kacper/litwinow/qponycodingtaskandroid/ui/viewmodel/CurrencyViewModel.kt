package kacper.litwinow.qponycodingtaskandroid.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kacper.litwinow.qponycodingtaskandroid.constants.DAY_IN_MILLISECONDS
import kacper.litwinow.qponycodingtaskandroid.constants.YYYY_MM_DD
import kacper.litwinow.qponycodingtaskandroid.extension.formatToDateFromString
import kacper.litwinow.qponycodingtaskandroid.extension.merge
import kacper.litwinow.qponycodingtaskandroid.model.CurrencyModel
import kacper.litwinow.qponycodingtaskandroid.repository.CurrencyRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _currencies = MutableLiveData<List<CurrencyModel?>>()
    val currencies: LiveData<List<CurrencyModel?>> = _currencies

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress

    var isLoadMore = true

    init {
        loadCurrencies()
    }

    fun loadCurrencies() {
        viewModelScope.launch {
            isLoadMore = false
            _progress.postValue(true)
            val newCurrencies = getCurrencies()
            val currentCurrencies = _currencies.value ?: listOf()
            val updatedCurrencies = merge(currentCurrencies, newCurrencies)
            _currencies.postValue(updatedCurrencies)
            _progress.postValue(false)
            isLoadMore = !newCurrencies.isNullOrEmpty()
        }
    }

    private suspend fun getCurrencies(): List<CurrencyModel?> {
        val currentCurrencies = _currencies.value ?: listOf()
        return if (currentCurrencies.isNullOrEmpty()) {
            currencyRepository.loadNewestCurrency(getOldestDate())
        } else {
            currencyRepository.loadHistoricalCurrencies(getOldestDate())
        }
    }

    private fun getOldestDate(): Long? {
        val currentCurrencies = _currencies.value ?: listOf()
        val oldestDate =
            currentCurrencies.filterIsInstance(CurrencyModel.Rate::class.java).map { it.date }
                .distinct().lastOrNull()
        val timeStamp = oldestDate?.formatToDateFromString(YYYY_MM_DD)?.time
        return when {
            timeStamp == null && currentCurrencies.isNullOrEmpty() ->
                System.currentTimeMillis() - DAY_IN_MILLISECONDS
            timeStamp != null && !currentCurrencies.isNullOrEmpty() ->
                timeStamp - DAY_IN_MILLISECONDS
            else -> null
        }
    }
}
