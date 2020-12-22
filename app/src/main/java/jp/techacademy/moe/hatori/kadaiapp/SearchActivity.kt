package jp.techacademy.moe.hatori.kadaiapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : AppCompatActivity(), View.OnClickListener{

    //カレンダーの設定
    val calendar = Calendar.getInstance()
    private var mYear = calendar.get(Calendar.YEAR)
    private var mMonth = calendar.get(Calendar.MONTH)
    private var mDay = calendar.get(Calendar.DAY_OF_MONTH)
    private var mHour = calendar.get(Calendar.HOUR_OF_DAY)
    private var mMinute = calendar.get(Calendar.MINUTE)


    private var searchType:String = ""


    private val mOnDateClickListener = View.OnClickListener {
        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                mYear = year
                mMonth = month
                mDay = dayOfMonth
                val dateString = mYear.toString() + "/" + String.format("%02d", mMonth + 1) + "/" + String.format("%02d", mDay)
                date_button.text = dateString
            }, mYear, mMonth, mDay)
        datePickerDialog.show()
        Log.d("Log_date",mYear.toString()+mMonth.toString()+mDay.toString())
    }

    private val mOnTimeClickListener = View.OnClickListener {
        val timePickerDialog = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                mHour = hour
                mMinute = minute
                val timeString = String.format("%02d", mHour) + ":" + String.format("%02d", mMinute)
                time_button.text = timeString
            }, mHour, mMinute, false)
        timePickerDialog.show()
        Log.d("Log_time",mHour.toString()+mMinute.toString())
    }


    private fun initSpinners() {
        var spinner = findViewById<Spinner>(R.id.searchTypeSpinner)
        val items = resources.getStringArray(R.array.searchType_item)

        //アダプターにアイテム配列を設定
        val Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        //スピナーにアダプターを設定
        spinner.adapter = Adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            searchType = parent.getItemAtPosition(position).toString()
            Log.d("Log_spinner",searchType)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchButton.setOnClickListener(this)
        date_button.setOnClickListener(mOnDateClickListener)
        time_button.setOnClickListener(mOnTimeClickListener)
        initSpinners()


    }

    override fun onClick(view: View?) {


        if (departureEditText.text.length == 0 || arrivalEditView.text.length == 0){
            //エラーのメッセージ出す
            errorTextView.text = "出発・到着駅を入力してください"
        } else {
            val intent = Intent(this,MainActivity::class.java)
            //出発到着
            intent.putExtra("SEARCH_DEPARTURE",departureEditText.text.toString())
            intent.putExtra("SEARCH_ARRIVAL",arrivalEditView.text.toString())
            //時間
            intent.putExtra("SEARCH_TIME",String.format("%02d", mHour)+String.format("%02d", mMinute))
            //日付
            intent.putExtra("SEARCH_DATE",mYear.toString()+String.format("%02d", mMonth + 1)+mDay.toString())
            //検索タイプ
            intent.putExtra("SEARCH_TYPE",searchType)
            startActivity(intent)
        }


    }


}