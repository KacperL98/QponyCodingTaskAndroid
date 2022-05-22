package kacper.litwinow.qponycodingtaskandroid.ui.activity

import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import kacper.litwinow.qponycodingtaskandroid.databinding.ActivityCurrenciesBinding
import kacper.litwinow.qponycodingtaskandroid.ui.fragment.CurrenciesFragment

@AndroidEntryPoint
class CurrenciesActivity : BaseActivity<ActivityCurrenciesBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCurrenciesBinding =
        ActivityCurrenciesBinding::inflate

    override fun setUp() {
        replaceFragment(CurrenciesFragment())
    }
}
