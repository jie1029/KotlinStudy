package kr.ac.kumoh.s20161184.myintentdata1010

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.textView
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    companion object{
        const val keyResult = "result"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        //textView.text = intent.getStringExtra(MainActivity.keyName)//에러를 내주기 때문에 편리하다. "name" 여기다가 넣으면 에러를 안내줌
            editText.setText(intent.getStringExtra(MainActivity.keyName))//edit은 string이 아니라서 setText로 바꿔주자!

        when(intent.getStringExtra(MainActivity.keyName)){
            "아이유" -> imageView.setImageResource(R.drawable.iu)
            "아이유2" -> imageView.setImageResource(R.drawable.iu2)
        }

        btnResult.setOnClickListener {
            val result = Intent()
            result.putExtra(keyResult, editText.text.toString())//edit은 우리가 쓰는 string이 아니라서 이러게 바꿔저야댐
            setResult(Activity.RESULT_OK,result) // 이까지하면 정상 작동을 안함
            finish()//이걸 해줘야 엑티비티가 꺼진다
        }
    }
}
