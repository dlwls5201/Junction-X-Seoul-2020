package com.junction.mymusicmap.presentation.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import com.junction.mymusicmap.R
import com.junction.mymusicmap.databinding.ActivityMainBinding
import com.junction.mymusicmap.presentation.musicsearch.MusicSearchDialogFragment
import com.junction.mymusicmap.presentation.userpage.UserPageDialogFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.tistory.blackjinbase.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main), OnMapReadyCallback {
    override var logTag = "MainActivity"
    private val viewModel: MainViewModel by viewModel()
    private var preMarker = Marker()

    // 지도 관련
    private lateinit var mMap: NaverMap
    private lateinit var mlocationSource: FusedLocationSource
    private var mMarker = mutableListOf<Marker>()

    override fun onStart() {
        super.onStart()
        mlocationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        initMap()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.mMap = naverMap
        mMap.locationSource = mlocationSource
        mMap.locationTrackingMode = LocationTrackingMode.Follow

        /*mMap.addOnLocationChangeListener {
            if(flag) {
                mMap.cameraPosition = CameraPosition(LatLng(it.latitude,it.longitude),17.0)
                Dlog.d("MyTag","latitude ${it.latitude} , latitude : ${it.longitude}")
                flag = !flag
            }
        }*/

        mMap.cameraPosition = CameraPosition(LatLng(37.38001321351567, 127.11851119995116),15.0)

        initMapSetting()
        //getLocation()
        getMarkerData()
        initButton()
    }

    private fun initMap(){
        val mapFragment = MapFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragmentMap,mapFragment).commit()
        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (mlocationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!mlocationSource.isActivated) { // 권한 거부됨
                mMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initMapSetting(){
        val uiSettings = mMap.uiSettings
        uiSettings.isLocationButtonEnabled = true
        uiSettings.isZoomControlEnabled = false
    }

    private fun getLocation() : LatLng{
        val cameraPosition = mMap.cameraPosition
        Log.d("test",cameraPosition.target.latitude.toString())
        Log.d("test",cameraPosition.target.longitude.toString())
        return cameraPosition.target
    }

    @SuppressLint("ResourceAsColor")
    private fun getMarkerData(){
        val markers = listOf(
            LatLng(37.38001321351567, 127.11851119995116),
            LatLng(37.378546827477855, 127.11984157562254),
            LatLng(37.376637072444105, 127.12052822113036),
            LatLng(37.37530703574853, 127.12190151214598),
            LatLng(37.371657839593894, 127.11645126342773),
            LatLng(37.36855417793982, 127.1207857131958)
        )
        markers.forEach{
            mMarker.plusAssign(Marker().apply {
                position = it
                iconTintColor = R.color.cornflower
                width = 85
                height = 100
                zIndex = 0
                captionText = "Hoohoo"

                setOnClickListener {
                    preMarker.width = 85
                    preMarker.height = 100
                    preMarker.icon = Marker.DEFAULT_ICON
                    preMarker.zIndex = 0
                    //캡션 왜 안나오냐....젠
                    captionText = "Hoohoo"
                    captionColor =  R.color.cornflower
                    width = Marker.SIZE_AUTO
                    height = Marker.SIZE_AUTO
                    zIndex = 1
                    showMusicBar()
                    //이미지 수정 필
                    icon = OverlayImage.fromResource(R.drawable.pin_othermusic_profile)
                    preMarker = this
                    true
                }
            })
        }
        mMarker.forEach {
            it.map = mMap
        }
    }

    private fun showMusicBar(){
        llMusicBox.visibility = View.VISIBLE
        llMusicBox.setOnClickListener {
            pinClickEvent()
        }
    }

    private fun pinClickEvent() {
        //TODO[승윤] 프로필 화면 이동
    }

    private fun initButton() {
        btnSearchMusic.setOnClickListener {
            val bottomSheetFragment = MusicSearchDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        btnProfile.setOnClickListener {
            val bottomSheetFragment = UserPageDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }

    companion object {
        const val ClientID = "8cp49jftg3"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}