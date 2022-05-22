package kacper.litwinow.qponycodingtaskandroid.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kacper.litwinow.qponycodingtaskandroid.databinding.FragmentCurrenciesBinding
import kacper.litwinow.qponycodingtaskandroid.extension.gone
import kacper.litwinow.qponycodingtaskandroid.extension.show
import kacper.litwinow.qponycodingtaskandroid.model.CurrencyModel
import kacper.litwinow.qponycodingtaskandroid.ui.activity.CurrencyDetailsActivity
import kacper.litwinow.qponycodingtaskandroid.ui.adapter.CurrenciesAdapter
import kacper.litwinow.qponycodingtaskandroid.ui.viewmodel.CurrencyViewModel

@AndroidEntryPoint
class CurrenciesFragment : BaseFragment<FragmentCurrenciesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCurrenciesBinding =
        FragmentCurrenciesBinding::inflate

    private val currencyViewModel: CurrencyViewModel by viewModels()

    private val currenciesAdapter: CurrenciesAdapter? by lazy {
        CurrenciesAdapter { rateName, rateValue, date ->
            CurrencyDetailsActivity.openActivity(requireContext(), rateName, rateValue, date)
        }
    }

    override fun setUp() {
        setUpAdapter()
        loadMore()
        observeViewModel()
    }

    private fun setUpAdapter() {
        with(binding) {
            rvCurrencies.adapter = currenciesAdapter
            rvCurrencies.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun loadMore() {
        binding.rvCurrencies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                val visibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (!recyclerView.canScrollVertically(1) &&
                    visibleItemPosition + visibleItemCount >= totalItemCount && firstVisibleItemPosition > 0
                    && currencyViewModel.isLoadMore
                ) {
                    currencyViewModel.loadCurrencies()
                }
            }
        })
    }

    private fun observeViewModel() {
        currencyViewModel.progress.observe(viewLifecycleOwner) { loading ->
            showLoadingInfo(loading)
        }

        currencyViewModel.currencies.observe(viewLifecycleOwner) { state ->
            showSuccess(state)
        }
    }

    private fun showLoadingInfo(loading: Boolean) {
        with(binding) {
            when {
                loading && currenciesAdapter?.currentList.isNullOrEmpty() -> {
                    loader.gone()
                }
                loading && !currenciesAdapter?.currentList.isNullOrEmpty() -> {
                    loader.show()
                }
                else -> {
                    loader.gone()
                }
            }
        }
    }

    private fun showSuccess(models: List<CurrencyModel?>?) {
        if (models.isNullOrEmpty()) {
            binding.loader.gone()
            binding.txtError.show()
        } else {
            binding.loader.gone()
            currenciesAdapter?.submitList(models)
        }
    }
}
