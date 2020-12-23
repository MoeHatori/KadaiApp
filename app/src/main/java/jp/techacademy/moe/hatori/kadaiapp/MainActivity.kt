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

        //searchActivityで設定された値を受け取る
        val search_departure = intent.getStringExtra("SEARCH_DEPARTURE")
        val search_arrival = intent.getStringExtra("SEARCH_ARRIVAL")
        val search_time = intent.getStringExtra("SEARCH_TIME")
        val search_date = intent.getStringExtra("SEARCH_DATE")
        val search_type = intent.getStringExtra("SEARCH_TYPE")

        //検索ページで検索された駅名を表示
        titleTextView.text = search_departure +" 　→　　" + search_arrival
        dateTextView.text = search_date.substring(0,4)+"年"+search_date.substring(4,6)+"月"+search_date.substring(6,8)+"日    "+search_time.substring(0,2)+"時"+search_time.substring(2,4)+"分"


        //ApiFragmentへの値の受け渡し
        val bundle = Bundle()
        bundle.putString("DEPARTURE",search_departure.toString())
        bundle.putString("ARRIVAL",search_arrival.toString())
        bundle.putString("TIME",search_time.toString())
        bundle.putString("DATE",search_date.toString())
        bundle.putString("TYPE",search_type.toString())


        val fragment = fragments[0]
        fragment.arguments = bundle

    }

    override fun onApiResponse(course: List<Course>) {

        //★ApiFragmentから値を持ってきて処理できるようにしようと思ったけどやめた

    }

}