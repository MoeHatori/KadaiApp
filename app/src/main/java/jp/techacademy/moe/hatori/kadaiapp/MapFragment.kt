package jp.techacademy.moe.hatori.kadaiapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment :Fragment(),OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_map, container, false)
        // fragment_map.xmlが反映されたViewを作成して、returnします

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//
//        val mapFragment = findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

//        mMapView = view.apply {
//            onCreate(savedInstanceState)
//            getMapAsync(this)
//        } as MapView

//
//        mMapView = view.findViewById(R.id.map) as MapView
//        mMapView.onCreate(savedInstanceState)
//        mMapView.onResume()
//        mMapView.getMapAsync(this)


        mMapView = map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@MapFragment)
        }

    }

    override fun onStart() {
        super.onStart()
        mMapView.onStart()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


}