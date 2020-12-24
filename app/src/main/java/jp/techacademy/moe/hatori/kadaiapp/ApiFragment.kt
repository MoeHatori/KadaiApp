package jp.techacademy.moe.hatori.kadaiapp

//import androidx.test.espresso.core.internal.deps.guava.collect.Lists

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.*
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_api.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.lang.reflect.Type


class ApiFragment : Fragment() {

    private val apiAdapter by lazy { ApiAdapter(requireContext()) }
    private val handler = Handler(Looper.getMainLooper())

    var callback: FragmentCallback? = null// -> mainactivity

    var api_departure: String = ""
    var api_arrival: String = ""
    var api_time: String = ""
    var api_date: String = ""
    var api_type: String = ""

    var url: String = ""
    var url_test = StringBuilder()


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

        api_departure = arguments?.getString("DEPARTURE").toString()
        api_arrival = arguments?.getString("ARRIVAL").toString()
        api_time = arguments?.getString("TIME").toString()
        api_date = arguments?.getString("DATE").toString()
        api_type = arguments?.getString("TYPE").toString()

        url_test = url_test.append(getString(R.string.base_url))
            .append("?APIKEY=").append(getString(R.string.api_key))
            .append("&viaList=").append(api_departure).append(":").append(api_arrival)

        Log.d("Log_Api", "/" + api_date + "/" + api_time + "/" + api_type)

        if (api_type == getString(R.string.departure_searchType)) {

            url_test = url_test
                .append("&time=").append(api_time)
                .append("&date=").append(api_date)

        } else if (api_type == getString(R.string.arrival_searchType)) {

            url_test = url_test
                .append("&time=").append(api_time)
                .append("&date=").append(api_date)
                .append("&searchType=").append("arrival")

        } else if (api_type == getString(R.string.first_searchType)) {

            url_test = url_test
                .append("&date=").append(api_date)
                .append("&searchType=").append("firstTrain")

        } else if (api_type == getString(R.string.last_searchType)) {

            url_test = url_test
                .append("&date=").append(api_date)
                .append("&searchType=").append("lastTrain")
        }

        url = url_test.toString()
        Log.d("Log_URL", url)


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

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        val request = Request.Builder()
            .url(url)
            .build()

        //Api通信を行う本体
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

                    val gsonBuilder = GsonBuilder()
                    val type: Type = object : TypeToken<MutableList<Line>>() {}.type
                    gsonBuilder.registerTypeAdapter(
                        type,
                        LineTypeAdapter()
                    )

                    val apiResponse = gsonBuilder.create().fromJson(it, ApiResponse::class.java)

                    apiResponse.resultSet.course?.let { course ->
                        list = course
                    }

                    apiResponse.resultSet.error?.let { error ->
                        handler.post {
                            callback?.onApiResponse(error.message)
                        }
                    }


                }
                handler.post {
                    updateRecyclerView(list)

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

class LineTypeAdapter : JsonDeserializer<List<Line>> {
    @Throws(JsonParseException::class)

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): List<Line> {

        val list: MutableList<Line> = ArrayList()

        // JSONが配列の場合の処理
        if (json.isJsonArray) {
            for (e in json.asJsonArray) {
                list.add(
                    context.deserialize<Any>(
                        e,
                        Line::class.java
                    ) as Line
                )
            }
        } else if (json.isJsonObject) {
            list.add(
                context.deserialize<Any>(
                    json,
                    Line::class.java
                ) as Line
            )
        }
        return list
    }
}


//class MultipleTypeAdapter : JsonDeserializer<MultipleType<*>?>,
//    JsonSerializer<MultipleType<*>?> {
//    @Throws(JsonParseException::class)
//
//    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): MultipleType<*> {
//
//        val result: MultipleType<T> = MultipleType<T>()
//
//        if (json.isJsonArray) {
//            result.values = deserializeArray(json, typeOfT, context)
//        } else {
//            result.value = context.deserialize(json, getGenericType(typeOfT))
//        }
//        return result
//    }
//
//    private fun <T> deserializeArray(
//        json: JsonElement,
//        typeOfT: Type,
//        context: JsonDeserializationContext
//    ): List<T> {
//        val values: MutableList<T> = ArrayList()
//        val t: Type = getGenericType(typeOfT)
//        for (e in json.asJsonArray) {
//            values.add(context.deserialize<T>(e, t))
//        }
//        return values
//    }
//
//    /* get actual Type of <?> */
//    private fun getGenericType(typeOfT: Type): Type {
//        return (typeOfT as ParameterizedType).getActualTypeArguments().get(0)
//    }
//
//    override fun serialize(
//        src: MultipleType<*>?,
//        typeOfSrc: Type?,
//        context: JsonSerializationContext?
//    ):  JsonElement {
//        return context!!.serialize(if (src!!.isMultiple) src.values else src.value)
//    }
//}


//                    try {
//                        val apiResponse = Gson().fromJson(it, ApiResponse::class.java)
//                        list = apiResponse.resultSet.course
//                    } catch (e: Exception) {
//                        val apiResponse_single = Gson().fromJson(
//                            it,
//                            ApiResponse_Notransfer::class.java
//                        )
//                        list_single = apiResponse_single.resultSet.course
//
//                        for (i in list_single.indices) {
//                            //list[i].price = list_single[i].price
//                        }
//
//
//                    } finally {
//                        Log.d("Test_Json", list[1].toString())
//                    }


