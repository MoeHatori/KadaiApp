package jp.techacademy.moe.hatori.kadaiapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ApiAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    // 取得したJsonデータを解析し、Course型オブジェクトとして生成したものを格納するリスト
    private val items = mutableListOf<Course>()

    // 表示リスト更新時に呼び出すメソッド
    fun refresh(list: List<Course>) {
        items.apply {
            clear() // items を 空にする
            addAll(list) // itemsにlistを全て追加する
        }
        notifyDataSetChanged() // recyclerViewを再描画させる
    }

    //RecyclerViewで表示させる1件分のデータ
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // ViewHolderを継承したApiItemViewHolderオブジェクトを生成し戻す
        return ApiItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_favorite, parent, false))
    }

    // ViewHolderを継承したApiItemViewHolderクラスの定義
    class ApiItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val rootView : ConstraintLayout = view.findViewById(R.id.rootView)
        val timeTextView: TextView = view.findViewById(R.id.timeTextView)
        val favoriteImageView: ImageView = view.findViewById(R.id.favoriteImageView)
        val priceTextView: TextView = view.findViewById(R.id.priceTextView)

    }

    override fun getItemCount(): Int {
        // itemsプロパティに格納されている要素数を返す
        return items.size
    }

    //1つのRecyclerViewに複数のViewHolderを出し分ける
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ApiItemViewHolder) {
            updateApiItemViewHolder(holder, position)
        }
    }

    private fun updateApiItemViewHolder(holder: ApiItemViewHolder, position: Int) {
        // 生成されたViewHolderの位置を指定し、オブジェクトを代入
        val data = items[position]
        holder.apply {
            rootView.apply {
                // 偶数番目と奇数番目で背景色を変更させる
                setBackgroundColor(ContextCompat.getColor(context,
                    if (position % 2 == 0) android.R.color.white else android.R.color.darker_gray))
            }

            var arrivaltime: String = data.route.line[0].arrivalState.datetime.text.drop(11).dropLast(9)
            var departuretime: String = data.route.line[data.route.line.size-1].arrivalState.datetime.text.drop(11).dropLast(9)
            var totaltime:Int = data.route.timeOnBoard.toInt() + data.route.timeOther.toInt() + data.route.timeWalk.toInt()

            timeTextView.text = arrivaltime + "　→　" + departuretime + "     " + totaltime.toString()+"分"

            priceTextView.text = "片道："+data.price[0].oneway+"　　乗換数："+data.route.line.count()+"回"

            favoriteImageView.setImageResource(R.drawable.ic_train)
        }
    }
}