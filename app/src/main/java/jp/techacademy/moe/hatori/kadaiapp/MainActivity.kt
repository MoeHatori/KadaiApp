package jp.techacademy.moe.hatori.kadaiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),FragmentCallback {

    val fragments = listOf(ApiFragment(), FavoriteFragment())
    private val viewPagerAdapter by lazy { ViewPagerAdapter(this,fragments) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // ViewPager2の初期化
        viewPager2.apply {
            adapter = viewPagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL // スワイプの向き横（ORIENTATION_VERTICAL を指定すれば縦スワイプで実装可能です）
            offscreenPageLimit = viewPagerAdapter.itemCount // ViewPager2で保持する画面数
        }

        //フラグメントをインスタンス化



        // TabLayoutの初期化,TabLayoutとViewPager2を紐づける
        // TabLayoutのTextを指定する
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.setText(viewPagerAdapter.titleIds[position])
        }.attach()

        //検索ページで検索された駅名を表示
        val departure_station = intent.getStringExtra("DEPARTURE_STATION")
        val arrival_station = intent.getStringExtra("ARRIVAL_STATION")

        titleTextView.text = departure_station +" 　→　　" + arrival_station

        //ApiFragmentへの値の受け渡し
        val bundle = Bundle()
        bundle.putString("DEPARTURE",departure_station.toString())
        bundle.putString("ARRIVAL",arrival_station.toString())

        val fragment = fragments[0]
        fragment.arguments = bundle

    }

    override fun onApiResponse(course: List<Course>) {

        //★ApiFragmentから値を持ってきて処理できるようにしようと思ったけどやめた

    }

}