package com.koflox.common_ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

abstract class BaseComposeFragment : Fragment() {

    private var _view: ComposeView? = null
    val view: ComposeView
        get() = requireNotNull(_view)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        }.also {
            _view = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _view = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        addViewObservers()
    }

    open fun initViews() = Unit

    open fun addViewObservers() = Unit

}
