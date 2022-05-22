package kacper.litwinow.qponycodingtaskandroid.ui.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import kacper.litwinow.qponycodingtaskandroid.ui.fragment.CurrencyDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kacper.litwinow.qponycodingtaskandroid.constants.DATE_CURRENCIES
import kacper.litwinow.qponycodingtaskandroid.constants.RATE_CURRENCY
import kacper.litwinow.qponycodingtaskandroid.constants.RATE_VALUE
import kacper.litwinow.qponycodingtaskandroid.databinding.ActivityCurrencyDetailsBinding

@AndroidEntryPoint
class CurrencyDetailsActivity : BaseActivity<ActivityCurrencyDetailsBinding>() {

    private val rateName: String? by lazy {
        intent.extras?.getString(RATE_CURRENCY)
    }

    private val rateValue: Double? by lazy {
        intent.extras?.getDouble(RATE_VALUE)
    }

    private val date: String? by lazy {
        intent.extras?.getString(DATE_CURRENCIES)
    }

    override val bindingInflater: (LayoutInflater) -> ActivityCurrencyDetailsBinding =
        ActivityCurrencyDetailsBinding::inflate

    override fun setUp() {
        setUpToolbar()
        replaceFragment(CurrencyDetailsFragment.newInstance(rateName, rateValue, date))
    }

    private fun setUpToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    companion object {
        fun openActivity(context: Context, rateName: String?, rateValue: Double?, date: String?) {
            Intent(context, CurrencyDetailsActivity::class.java).apply {
                putExtra(RATE_CURRENCY, rateName)
                putExtra(RATE_VALUE, rateValue)
                putExtra(DATE_CURRENCIES, date)
            }.also { intent ->
                context.startActivity(intent)
            }
        }
    }
}
