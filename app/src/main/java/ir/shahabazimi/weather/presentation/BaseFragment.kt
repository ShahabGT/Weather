package ir.shahabazimi.weather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 07
 **/
//BaseFragment is used mainly for inflating the Fragment layout
abstract class BaseFragment<B : ViewBinding> : Fragment() {

    val binding get() = _binding!!
    private var _binding: B? = null

    //this open function is optional to implement, should use for initializing the views
    protected open fun setupViews(savedInstanceState: Bundle?) {}

    //this open function is optional to implement, should use for observing and calling viewModel functions
    protected open fun initLogic() {}

    abstract fun bindView(inflater: LayoutInflater, container: ViewGroup?): ViewBinding

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindView(inflater, container) as B
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLogic()
        setupViews(savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}