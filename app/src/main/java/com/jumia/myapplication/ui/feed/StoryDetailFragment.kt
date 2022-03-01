package com.jumia.myapplication.ui.feed

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.jumia.myapplication.R
import dagger.hilt.android.AndroidEntryPoint
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory


@AndroidEntryPoint
class StoryDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val videoView = requireActivity().findViewById(R.id.videoView1) as VideoView
//
//        //Creating MediaController
//
//        //Creating MediaController
//
//        //specify the location of media file
//
//        //specify the location of media file
//        val uri = Uri.parse("https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4")
//
//        //Setting MediaController and URI, then starting the videoView
//
//        //Setting MediaController and URI, then starting the videoView
//        videoView.setMediaController(null)
//        videoView.setVideoURI(uri)
//        videoView.requestFocus()
//        videoView.start()
//
        val data = listOf<MyStory>()

        val myStories = arrayListOf<MyStory>(MyStory("https://i.imgur.com/N0XsVNk.png"), MyStory("https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4"))

        for (story in data) {
            myStories.add(
                MyStory(
                    story.url
                )
            )
        }

        StoryView.Builder(activity?.supportFragmentManager)
            .setStoriesList(myStories) // Required
            .setStoryDuration(20000) // Default is 2000 Millis (2 Seconds)
            .setTitleText("Hamza Al-Omari") // Default is Hidden
            .setSubtitleText("Damascus") // Default is Hidden
            .setTitleLogoUrl("some-link") // Default is Hidden
            .setStoryClickListeners(object : StoryClickListeners {
                override fun onDescriptionClickListener(position: Int) {
                    //your action
                }

                override fun onTitleIconClickListener(position: Int) {
                    //your action
                }
            }) // Optional Listeners
            .build() // Must be called before calling show method
            .show()

//        val video = Uri.parse("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4")
//
//        videoView.setVideoURI(video)
//        videoView.requestFocus()
//        videoView.start()

//        val videoURL =
//            "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4"
//
//        val exoPlayerView = activity?.findViewById<PlayerView>(R.id.video_view)
//        try {
//
//            // bandwisthmeter is used for
//            // getting default bandwidth
//            val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
//
//            // track selector is used to navigate between
//            // video using a default seekbar.
//            val trackSelector: TrackSelector =
//                DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
//
//            // we are adding our track selector to exoplayer.
//           val exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
//
//            // we are parsing a video url
//            // and parsing its video uri.
//            val videouri = Uri.parse(videoURL)
//
//            // we are creating a variable for datasource factory
//            // and setting its user agent as 'exoplayer_view'
//            val dataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")
//
//            // we are creating a variable for extractor factory
//            // and setting it to default extractor factory.
//            val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()
//
//            // we are creating a media source with above variables
//            // and passing our event handler as null,
//            val mediaSource: MediaSource =
//                ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null)
//
//            // inside our exoplayer view
//            // we are setting our player
//            exoPlayerView?.setPlayer(exoPlayer)
//
//            // we are preparing our exoplayer
//            // with media source.
//            exoPlayer.prepare(mediaSource)
//
//            // we are setting our exoplayer
//            // when it is ready.
//            exoPlayer.setPlayWhenReady(true)
//        } catch (e: Exception) {
//            // below line is used for
//            // handling our errors.
//            Log.e("TAG", "Error : $e")
//        }

    }

}