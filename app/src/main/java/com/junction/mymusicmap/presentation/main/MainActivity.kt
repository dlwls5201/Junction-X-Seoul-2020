package com.junction.mymusicmap.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.junction.mymusicmap.R
import com.junction.mymusicmap.data.model.YouTubeResponse
import com.junction.mymusicmap.databinding.ActivityMainBinding
import com.junction.mymusicmap.presentation.musicsearch.MusicSearchDialogFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.tistory.blackjinbase.base.BaseActivity
import com.tistory.blackjinbase.util.Dlog
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), OnMapReadyCallback {
    override var logTag = "MainActivity"
    private val viewModel: MainViewModel by viewModel()
    private var preMarker = Marker()

    // 지도 관련
    private lateinit var mMap: NaverMap
    private lateinit var mlocationSource: FusedLocationSource
    private var mMarker = mutableListOf<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        llMusicBox.visibility = View.GONE
        initButton()
    }

    override fun onStart() {
        super.onStart()
        mlocationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        initMap()
    }

    private var myLocation: LatLng? = null

    override fun onMapReady(naverMap: NaverMap) {
        this.mMap = naverMap
        mMap.locationSource = mlocationSource
        mMap.locationTrackingMode = LocationTrackingMode.Follow

        mMap.addOnLocationChangeListener {
            myLocation = LatLng(it.latitude, it.longitude)
        }

        mMap.cameraPosition = CameraPosition(LatLng(37.38001321351567, 127.11851119995116), 15.0)

        initMapSetting()
        //getLocation()
        getMarkerData()
    }

    private fun initMap() {
        val mapFragment = MapFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentMap, mapFragment).commit()
        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (mlocationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!mlocationSource.isActivated) { // 권한 거부됨
                mMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initMapSetting() {
        val uiSettings = mMap.uiSettings
        uiSettings.isLocationButtonEnabled = true
        uiSettings.isZoomControlEnabled = false
    }

    private fun getLocation(): LatLng {
        val cameraPosition = mMap.cameraPosition
        Log.d("test", cameraPosition.target.latitude.toString())
        Log.d("test", cameraPosition.target.longitude.toString())
        return cameraPosition.target
    }

    private val profileSample = listOf(
        "https://render-cdn.zepeto.io/20201011/02/39mqBusd4sVRtA9xxB",
        "https://render-cdn.zepeto.io/20201011/02/39mqIZsd4t9UK2jDHC",
        "https://render-cdn.zepeto.io/20201011/02/39mqDcsd4thqscXJQu",
        "https://render-cdn.zepeto.io/20201011/02/39mqBvsd4tm8VJNi7h",
        "https://render-cdn.zepeto.io/20201011/02/39mqBtsd4tpNmPMErS",
        "https://render-cdn.zepeto.io/20201011/02/39mqwEsd4tsVP1O9nz",
        "https://render-cdn.zepeto.io/20201011/02/39mqKIsd4tvzO8torm",
        "https://render-cdn.zepeto.io/20201011/02/39mqDcsd4ty3P6DLO0",
        "https://render-cdn.zepeto.io/20201011/02/39mqExsd4tBt1ipmtZ",
        "https://render-cdn.zepeto.io/20201011/02/39mqJ0sd4tE2C2WOZY",
    )

    @SuppressLint("ResourceAsColor")
    private fun getMarkerData() {
        val markers = listOf(
            UserData(
                lating = LatLng(37.38001321351567, 127.11851119995116),
                userName = "peter Lee",
                userUrl = profileSample[0],
                musicThumbnail = "https://i.ytimg.com/vi/aPiQKENFWss/hq720.jpg?sqp=-oaymwEjCOgCEMoBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLBQjLaE2-qcDloG2HskC87LSvKN9Q",
                musicTile = "아이유(IU)- 너의의미(Feat.김창완)",
                musicDescription = "아이유(IU)- 너의의미(Feat.김창완)",
                musicLink = "https://www.youtube.com/watch?v=aPiQKENFWss"
            ),
            UserData(
                lating = LatLng(37.378546827477855, 127.11984157562254),
                userName = "Anne-Marie",
                userUrl = profileSample[1],
                musicThumbnail = "https://i.ytimg.com/vi/Il-an3K9pjg/hqdefault.jpg?sqp=-oaymwEjCOADEI4CSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLCVuAtkIwDhSdL_YJWnMHjvHCYuxQ",
                musicTile = "Anne-Marie - 2002 [Official Video]",
                musicDescription = "KarateWithAnneMarie",
                musicLink = "https://www.youtube.com/watch?v=Il-an3K9pjg "
            ),
            UserData(
                lating = LatLng(37.376637072444105, 127.12052822113036),
                userName = "Joos Lee",
                userUrl = profileSample[2],
                musicThumbnail = "https://i.ytimg.com/vi/dyRsYk0LyA8/hqdefault.jpg?sqp=-oaymwEjCPYBEIoBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLBv4pd7A-HNjM0_jjMpsiMAaD5lBA",
                musicTile = "BLACKPINK – ‘Lovesick Girls’ M/V",
                musicDescription = "'Lovesick Girls' 영원한 밤 창문 없는 방에 우릴 가둔 love What can we say 매번 아파도 외치는 love 다치고 망가져도",
                musicLink = "https://www.youtube.com/watch?v=dyRsYk0LyA8 "
            ),
            UserData(
                lating = LatLng(37.37530703574853, 127.12190151214598),
                userName = "HyeongJin Kim",
                userUrl = profileSample[3],
                musicThumbnail = "https://i.ytimg.com/vi/qvu4nPMyl3U/hqdefault.jpg?sqp=-oaymwEjCPYBEIoBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLDOLgt32s6tVIlPiNuIJJnkdsXl4Q",
                musicTile = "BTS (방탄소년단) 'Savage Love' (Laxed – Siren Beat)",
                musicDescription = "SavageLoveRemix #BTS #방탄소년단",
                musicLink = "https://www.youtube.com/watch?v=qvu4nPMyl3U"
            ),
            UserData(
                lating = LatLng(37.371657839593894, 127.11645126342773),
                userName = "peter",
                userUrl = profileSample[4],
                musicThumbnail = "https://i.ytimg.com/vi/04Xy83aiMgw/hqdefault.jpg?sqp=-oaymwEjCOADEI4CSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLC5pazrTcAlmXUkTYY43wUEpuyBqg",
                musicTile = "SNSD 소녀시대 - 힘내! ( Way To Go )",
                musicDescription = "Premiering on YouTube.. Way To Go. Remake of HAPTIC MOTION",
                musicLink = "https://www.youtube.com/watch?v=04Xy83aiMgw"
            ),
            UserData(
                lating = LatLng(37.36855417793982, 127.1207857131958),
                userName = "seungyoon koh",
                userUrl = profileSample[5],
                musicThumbnail = "https://i.ytimg.com/vi/fHeQemJJQII/hq720.jpg?sqp=-oaymwEjCOgCEMoBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLB5QB1Wn1_YTmHWqoc6j7K5IRs--Q",
                musicTile = "Shawn Mendes - Wonder",
                musicDescription = "New single WONDER out now Listen now",
                musicLink = "https://www.youtube.com/watch?v=fHeQemJJQII"
            )
        )

        markers.forEach { userData ->
            mMarker.add(Marker().apply {
                position = userData.lating
                iconTintColor = R.color.cornflower
                width = 85
                height = 100
                zIndex = 0
                captionText = userData.musicTile

                setOnClickListener {

                    Glide.with(this@MainActivity)
                        .asBitmap().load(userData.userUrl).override(100, 100)
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                preMarker.width = 85
                                preMarker.height = 100
                                preMarker.icon = Marker.DEFAULT_ICON
                                preMarker.zIndex = 0

                                width = Marker.SIZE_AUTO
                                height = Marker.SIZE_AUTO
                                zIndex = 1
                                icon = OverlayImage.fromBitmap(resource)
                                preMarker = this@apply
                            }
                        })

                    showMusicBar(userData)

                    true
                }
            })
        }

        mMarker.forEach {
            it.map = mMap
        }
    }

    fun addPin(item: YouTubeResponse.Item) {
        if(myLocation == null) {
            return
        }

        val userData =
            UserData(
                lating = myLocation!!,
                userName = "musixbox95",
                musicLink = item.link ?: "",
                musicTile = item.title ?: "",
                musicDescription = item.description ?: "",
                musicThumbnail = item.thumbnail ?: ""
            )

        Dlog.d("MyTag", "userData : $userData")

        mMarker.add(
            Marker().apply {
                position = userData.lating
                iconTintColor = R.color.colorAccent
                width = 85
                height = 100
                zIndex = 0
                captionText = userData.musicTile

                setOnClickListener {
                    preMarker.width = 85
                    preMarker.height = 100
                    preMarker.icon = Marker.DEFAULT_ICON
                    preMarker.zIndex = 0

                    width = Marker.SIZE_AUTO
                    height = Marker.SIZE_AUTO
                    zIndex = 1
                    icon = OverlayImage.fromResource(R.drawable.pin_othermusic_profile)
                    preMarker = this@apply

                    showMusicBar(userData)

                    true
                }
            }
        )

        mMarker.last().map = mMap
    }

    @Parcelize
    data class UserData(
        val lating: LatLng,
        val userName: String = "peter",
        val userUrl: String = "https://render-cdn.zepeto.io/20201011/02/39mqBusd4sVRtA9xxB",
        val musicThumbnail: String = "https://i.ytimg.com/vi/aPiQKENFWss/hq720.jpg?sqp=-oaymwEjCOgCEMoBSFryq4qpAxUIARUAAAAAGAElAADIQj0AgKJDeAE=&rs=AOn4CLBQjLaE2-qcDloG2HskC87LSvKN9Q",
        val musicTile: String = "아이유(IU)- 너의의미(Feat.김창완)",
        val musicDescription: String = "아이유(IU)- 너의의미(Feat.김창완)",
        val musicLink: String = "https://www.youtube.com/watch?v=aPiQKENFWss"
    ) : Parcelable

    private fun showMusicBar(userData: UserData) {
        llMusicBox.visibility = View.VISIBLE

        Glide.with(this)
            .load(userData.musicThumbnail)
            .into(ivMusic)

        tvMusicTitle.text = userData.musicTile
        tvMusicDescription.text = userData.musicDescription

        llMusicBox.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(userData.musicLink))
                    .setPackage("com.google.android.youtube")
            )
        }
    }

    private fun initButton() {
        btnSearchMusic.setOnClickListener {
            val bottomSheetFragment = MusicSearchDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        btnProfile.setOnClickListener {
            //TODO[승윤] 프로필 화면 이동
        }
    }

    companion object {
        const val ClientID = "8cp49jftg3"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}