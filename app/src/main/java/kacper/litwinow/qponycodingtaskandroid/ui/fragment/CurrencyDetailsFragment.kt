package kacper.litwinow.qponycodingtaskandroid.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint
import kacper.litwinow.qponycodingtaskandroid.R
import kacper.litwinow.qponycodingtaskandroid.constants.DATE_CURRENCIES
import kacper.litwinow.qponycodingtaskandroid.constants.RATE_CURRENCY
import kacper.litwinow.qponycodingtaskandroid.constants.RATE_VALUE
import kacper.litwinow.qponycodingtaskandroid.databinding.FragmentCurrencyDetailsBinding
import kacper.litwinow.qponycodingtaskandroid.extension.formatDate
import kacper.litwinow.qponycodingtaskandroid.extension.formatPriceValues

@AndroidEntryPoint
class CurrencyDetailsFragment : BaseFragment<FragmentCurrencyDetailsBinding>() {

    private val rateName: String? by lazy {
        arguments?.getString(RATE_CURRENCY)
    }

    private val rateValue: Double? by lazy {
        arguments?.getDouble(RATE_VALUE)
    }

    private val date: String? by lazy {
        arguments?.getString(DATE_CURRENCIES)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCurrencyDetailsBinding =
        FragmentCurrencyDetailsBinding::inflate

    override fun setUp() {
        initView()
    }

    private fun initView() {
        with(binding) {
            txtDate.text = date.formatDate()
            txtDate.text = handleRateText(rateName, rateValue)
        }
    }

    private fun handleRateText(rateName: String?, rateValue: Double?): String {
        return getString(
            R.string.currency_value,
            rateName,
            rateValue?.formatPriceValues()
        )
    }

    companion object {
        fun newInstance(rateName: String?, rateValue: Double?, date: String?) =
            CurrencyDetailsFragment().apply {
                arguments = bundleOf(
                    RATE_CURRENCY to rateName,
                    RATE_VALUE to rateValue,
                    DATE_CURRENCIES to date
                )
            }
    }
}
