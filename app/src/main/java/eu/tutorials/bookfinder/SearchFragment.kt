package eu.tutorials.bookfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import eu.tutorials.bookfinder.BookViewModel
import eu.tutorials.bookfinder.R
import eu.tutorials.bookfinder.databinding.FragmentSearchBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BookViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.resetStatus()
        // search view query listener
        binding.svQuery.setOnQueryTextListener(this)
        // observe books list
        viewModel.books.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()){
                Log.d("ITEMS", it.size.toString())
                findNavController()
                    .navigate(R.id.action_SearchFragment_to_ResultFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.setQuery(query!!)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}