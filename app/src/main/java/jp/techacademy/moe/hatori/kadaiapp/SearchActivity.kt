package jp.techacademy.moe.hatori.kadaiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("DEPARTURE_STATION",departureEditText.text.toString())
        intent.putExtra("ARRIVAL_STATION",arrivalEditView.text.toString())
        startActivity(intent)
    }


}