package kr.ac.kumoh.s20161184.listview

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mArray = arrayOf("아이유","BTS","설현","임창정",
        "제이레빗","아이유","BTS","설현","임창정","제이레빗",
        "아이유","BTS","설현","임창정","제이레빗","아이유",
        "BTS","설현","임창정","제이레빗","아이유","BTS","설현","임창정","제이레빗")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mArray)
        
        listView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this@MainActivity,""+mArray[i],Toast.LENGTH_LONG).show();

            var url = Uri.parse("https://www.youtube.com/results?search_query="+mArray.get(i))
            val intent= Intent(Intent.ACTION_VIEW,url) //생성자
            startActivity(intent)

        }
    }
}
