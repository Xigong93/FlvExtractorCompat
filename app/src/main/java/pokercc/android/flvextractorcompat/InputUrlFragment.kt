package pokercc.android.flvextractorcompat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import pokercc.android.flvextractorcompat.databinding.InputPlayUrlFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class InputUrlFragment : Fragment() {

    private lateinit var playerUrlFragmentBinding: InputPlayUrlFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        playerUrlFragmentBinding = InputPlayUrlFragmentBinding.inflate(inflater, container, false)
        return playerUrlFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerUrlFragmentBinding.playButton.setOnClickListener {
            val playUrl = playerUrlFragmentBinding.urlEditText.text?.toString()
            if (playUrl.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please input flv play url.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            fragmentManager!!.beginTransaction()
                    .replace(id, PlayFragment.newInstance(playUrl))
                    .addToBackStack(null)
                    .commit()


        }
    }

}
