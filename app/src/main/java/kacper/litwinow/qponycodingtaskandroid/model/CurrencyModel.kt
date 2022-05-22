package kacper.litwinow.qponycodingtaskandroid.model

sealed class CurrencyModel {
    data class Date(val date: String?) : CurrencyModel()
    data class Rate(val rateName: String?, val rateValue: Double?, val date: String?) :
        CurrencyModel()
}