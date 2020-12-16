package jp.techacademy.moe.hatori.kadaiapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_api.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class ApiFragment : Fragment() {

    private val apiAdapter by lazy { ApiAdapter(requireContext()) }
    private val handler = Handler(Looper.getMainLooper())

    var callback: FragmentCallback? = null// -> mainactivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_api, container, false)
        // fragment_api.xmlが反映されたViewを作成して、returnします
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ここから初期化処理を行う
        // RecyclerViewの初期化
        recyclerView.apply {
            adapter = apiAdapter
            layoutManager = LinearLayoutManager(requireContext()) // 一列ずつ表示
        }
        swipeRefreshLayout.setOnRefreshListener {
            updateData()
        }
        updateData()
    }

    private fun updateData() {
        val url = StringBuilder()
            .append(getString(R.string.base_url))
            .append("?APIKEY=").append(getString(R.string.api_key)) // Apiを使うためのAPIKEY
            .append("&viaList=").append("立川:片倉")
            .toString()
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) { // Error時の処理
                e.printStackTrace()
                handler.post {
                    updateRecyclerView(listOf())
                }
            }

            override fun onResponse(call: Call, response: Response) { // 成功時の処理
                var list = listOf<Course>()
                response.body?.string()?.also {
                    val apiResponse = Gson().fromJson(it, ApiResponse::class.java)
                    list = apiResponse.resultSet.course
                    Log.d("Test_Json", list[1].toString())
                }
                handler.post {
                    updateRecyclerView(list)

                    callback?.onApiResponse(list)
                }
            }
        })
    }



    private fun updateRecyclerView(list: List<Course>) {
        apiAdapter.refresh(list)
        swipeRefreshLayout.isRefreshing = false // SwipeRefreshLayoutのくるくるを消す
    }


    companion object {
        private const val COUNT = 20 // 1回のAPIで取得する件数
    }
}
