package pe.ralvaro.cocinacreativa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

interface SpecificFunc<V : ViewBinding> {
    fun onBackHandler()
}

/**
 * Define typealias to create a cleaner reference to the required lambda
 */
typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

/**
 * This tool simplifies the binding process,
 * allowing a cleaner development interface on each fragment file.
 */
abstract class BaseFragment<V : ViewBinding>(
    private val inflate: Inflate<V>
) : Fragment(), SpecificFunc<V>{

    private var _binding: V? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        onBackHandler()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }

    // Method to avoid the super .onViewCreated call and make the easy override of it
    abstract fun onViewCreated()

    // Override here so that it is not mandatory
    override fun onBackHandler() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}