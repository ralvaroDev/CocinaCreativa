package pe.ralvaro.cocinacreativa.ui.home

import android.widget.TextView
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pe.ralvaro.cocinacreativa.databinding.FragmentHomeBinding
import pe.ralvaro.cocinacreativa.ui.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated() {
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }

}