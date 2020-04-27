package pokercc.android.flvextractorcompat

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import pokercc.android.exoplayer.flvextractorcompat.FlvExtractorCompat

class PlayFragment : Fragment() {
    companion object {
        private const val PLAY_URL = "play_url"
        fun newInstance(playUrl: String): Fragment {
            return PlayFragment().apply {
                arguments = Bundle().apply {
                    putString(PLAY_URL, playUrl)
                }
            }
        }
    }

    private var exoplayer: SimpleExoPlayer? = null
    private var playUrl: String = ""
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playUrl = requireArguments().getString(PLAY_URL, "")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        playerView = PlayerView(inflater.context)
        return playerView
    }

    override fun onStart() {
        super.onStart()
        exoplayer = ExoPlayerFactory.newSimpleInstance(requireContext().applicationContext)
        playerView.player = exoplayer
        val dataSourceFactory = DefaultHttpDataSourceFactory(Util.getUserAgent(requireContext(), requireContext().packageName))
        val mediaSourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory, FlvExtractorCompat.FACTORY)
        exoplayer?.prepare(mediaSourceFactory.createMediaSource(Uri.parse(playUrl)))
    }

    override fun onPause() {
        super.onPause()
        exoplayer?.stop()
        exoplayer?.release()
    }
}