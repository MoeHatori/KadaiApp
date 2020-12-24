package jp.techacademy.moe.hatori.kadaiapp

interface FragmentCallback {

    //Api通信の結果をMainActivityに持ち込みたいときの処理
    fun onApiResponse(string: String)

}