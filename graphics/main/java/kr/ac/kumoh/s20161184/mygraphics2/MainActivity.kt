package kr.ac.kumoh.s20161184.mygraphics2


import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(outState: Bundle) { // 상태 저장
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("points", myView.point) //데이터 저장
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) { //저장한거 가져오기
        super.onRestoreInstanceState(savedInstanceState)
        myView.point = savedInstanceState.getParcelableArrayList<Point>("points") as ArrayList<Point> // 데이터 가져오기
    }
}

