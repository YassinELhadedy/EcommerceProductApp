package com.jumia.myapplication.ui.feed

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.jumia.myapplication.R
import com.jumia.myapplication.databinding.FragmentProductDetailBinding
import com.jumia.myapplication.domain.Product
import com.jumia.myapplication.ui.exception.ErrorMessageFactory
import com.jumia.myapplication.ui.feed.adapter.ProductDetailAdapter
import com.jumia.myapplication.ui.util.progress.WaitingDialog
import com.jumia.myapplication.ui.util.state.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Runnable


@ExperimentalPagingApi
@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    @ExperimentalPagingApi
    private val productViewModel: ProductViewModel by activityViewModels()
    private val mWaitingDialog: WaitingDialog by lazy { WaitingDialog(requireActivity()) }
    private lateinit var viewDataBinding: FragmentProductDetailBinding

    // Creating Object of ViewPagerAdapter
    private lateinit var mViewPagerAdapter: ProductDetailAdapter
    private var isOpen = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewDataBinding = FragmentProductDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = productViewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val safeArgs: ProductDetailFragmentArgs by navArgs()
        observer()
        productViewModel.productInfo(safeArgs.productId)
    }

    private fun initViewPager(images: List<String>) {
        mViewPagerAdapter = ProductDetailAdapter(images)
        viewDataBinding.viewpager.adapter = mViewPagerAdapter
    }

    private fun observer() {
        productViewModel.productData.observe(viewLifecycleOwner) {
            when (it?.status) {
                Status.SUCCESS -> {
                    mWaitingDialog.dismissDialog()
                    if (it.data is Product) {
//                        initViewPager(it.data.imageList ?: emptyList())
                        initViewPager(
                            listOf(
                                "https://dlskits.com/wp-content/uploads/2018/01/Chelsea-Dream-League-Soccer-Logo.png",
                                "https://dlskits.com/wp-content/uploads/2018/01/Chelsea-Dream-League-Soccer-Logo.png",
                                "https://dlskits.com/wp-content/uploads/2018/01/Chelsea-Dream-League-Soccer-Logo.png",
                                "https://dlskits.com/wp-content/uploads/2018/01/Chelsea-Dream-League-Soccer-Logo.png"
                            )
                        )

                    }
                }
                Status.ERROR -> {
                    mWaitingDialog.dismissDialog()
                    Toast.makeText(
                        requireContext(),
                        ErrorMessageFactory.create(requireActivity(), it.data as Throwable),
                        Toast.LENGTH_LONG
                    ).show()
                }
                Status.LOADING -> {
                    mWaitingDialog.showDialog()
                }
                else -> {}
            }
        }
    }

     override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT  > 23) {
            initializePlayer()
        }
    }

     override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Build.VERSION.SDK_INT  <= 23 || player == null) {
            initializePlayer()
        }
    }

     override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT  <= 23) {
            releasePlayer()
        }
    }

     override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT  > 23) {
            releasePlayer()
        }
    }

    private val playbackStateListener: Player.Listener = playbackStateListener()
    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L


    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                viewDataBinding.videoView1.player = exoPlayer

                val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
                exoPlayer.setMediaItem(mediaItem)
                val secondMediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3")
                exoPlayer.addMediaItem(secondMediaItem)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentWindow, playbackPosition)
                exoPlayer.prepare()
            }

//        val handler = Handler()
//
//        handler.postDelayed(Runnable {
//            if (player?.duration!! > 0) {
//                viewDataBinding.videoView1.findViewById<AppCompatSeekBar>(R.id.idSeekBarProgress).progress =
//                    player?.currentPosition?.toInt()!!
//            }
//        }, 500)



//        viewDataBinding.videoView1.findViewById<ImageButton>(R.id.idIBForward).setOnClickListener {
//            player?.seekForward()
//        }
//        viewDataBinding.videoView1.findViewById<ImageButton>(R.id.idIBBack).setOnClickListener {
//            player?.seekBack()
//        }
//
//        viewDataBinding.videoView1.findViewById<ImageButton>(R.id.idIBPlay).setOnClickListener {
//            if (player?.isPlaying == true) {
//                player?.pause()
//                viewDataBinding.videoView1.findViewById<ImageButton>(R.id.idIBPlay).setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_play_circle_outline_24))
//            } else {
//                player?.play()
//                viewDataBinding.videoView1.findViewById<ImageButton>(R.id.idIBPlay).setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_pause_24))
//            }
//        }

    }

    private fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentWindowIndex
            playWhenReady = this.playWhenReady
            release()
        }
        player = null
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        viewDataBinding.videoView1.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

private fun playbackStateListener() = object : Player.Listener {
    override fun onPlaybackStateChanged(playbackState: Int) {
        val stateString: String = when (playbackState) {
            ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
            ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
            ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
            ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
            else -> "UNKNOWN_STATE             -"
        }
    }
}

    ////////////////////////////////////////////////////////////////////////////
//    private fun setVideoDrop() {
//        viewDataBinding.videoView.setOnPreparedListener {
//            viewDataBinding.videoCustom.idSeekBarProgress.max = viewDataBinding.videoView.duration
//            viewDataBinding.videoView.start()
//        }
//        viewDataBinding.videoCustom.idIBBack.setOnClickListener {
//            viewDataBinding.videoView.seekTo(viewDataBinding.videoView.duration - 10000)
//
//        }
//        viewDataBinding.videoCustom.idIBForward.setOnClickListener {
//            viewDataBinding.videoView.seekTo(viewDataBinding.videoView.duration + 10000)
//        }
//        viewDataBinding.videoCustom.idIBPlay.setOnClickListener {
//            if (viewDataBinding.videoView.isPlaying) {
//                viewDataBinding.videoView.pause()
//                viewDataBinding.videoCustom.idIBPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_play_circle_outline_24))
//            } else {
//                viewDataBinding.videoView.start()
//                viewDataBinding.videoCustom.idIBPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_pause_24))
//            }
//        }
//        viewDataBinding.idRLVideo.setOnClickListener {
//            if (isOpen) {
//                hideControl(true)
//                isOpen = false
//            } else {
//                isOpen = true
//                hideControl(false)
//            }
//        }
//        setHanlder()
//        intializeSeekBar()
//    }
//
//    private fun setHanlder() {
//        val handler = Handler()
//
//        handler.postDelayed(Runnable {
//            if (viewDataBinding.videoView.duration > 0) {
//                viewDataBinding.videoCustom.idSeekBarProgress.progress =
//                    viewDataBinding.videoView.currentPosition
//                viewDataBinding.videoCustom.idTVTime.text =
//                    convertTime(viewDataBinding.videoView.duration - viewDataBinding.videoView.currentPosition)
//            }
//        }, 500)
//
//
//    }
//
//    private fun convertTime(ms: Int): String {
//        return ""
//    }
//
//    private fun intializeSeekBar() {
//        viewDataBinding.videoCustom.idSeekBarProgress.setOnSeekBarChangeListener(object :
//            SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//                if (viewDataBinding.videoCustom.idSeekBarProgress.id == R.id.idSeekBarProgress) {
//                  if (p2){
//                      viewDataBinding.videoView.seekTo(p1)
//                      viewDataBinding.videoView.start()
//                  }
//
//                }
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
//
//    private fun hideControl(hide: Boolean) {
//        viewDataBinding.videoCustom.idRLControls.isVisible = !hide
//        val videoWindow = requireActivity().window
//        if (videoWindow != null) {
//            return
//        }
//
//        videoWindow?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        videoWindow?.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
//
//        if (videoWindow?.decorView != null) {
//            var uiOption = videoWindow.decorView.systemUiVisibility
//            if (Build.VERSION.SDK_INT >= 14) {
//                uiOption =
//                    if (!hide) uiOption and View.SYSTEM_UI_FLAG_LOW_PROFILE else uiOption or View.SYSTEM_UI_FLAG_LOW_PROFILE
//            }
//            if (Build.VERSION.SDK_INT >= 16) {
//                uiOption =
//                    if (!hide) uiOption and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION else uiOption or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//            }
//            if (Build.VERSION.SDK_INT >= 19) {
//                uiOption =
//                    if (!hide) uiOption and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY else uiOption or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//            }
//            videoWindow.decorView.systemUiVisibility = uiOption
//        }
//    }

}