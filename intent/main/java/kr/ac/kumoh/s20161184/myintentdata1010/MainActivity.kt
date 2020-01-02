package kr.ac.kumoh.s20161184.myintentdata1010

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {//공통으로 가지는거는 이렇게

    companion object{//클래스만 쓸수있는 스태틱이 있었는데 코틀린에서는 이렇게 함 여기에 놔두면 공통으로 객체를 사용할 수 있다.그냥 스태틱이구나 생각하센 여기서는
        const val REQUEST_SECOND = 100
        const val keyName = "name"//??

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnIU.setOnClickListener(this) //현재 만들어논 거라는거.
        btnIU2.setOnClickListener(this)
//            val intent = Intent(this,SecondActivity::class.java)
//            intent.putExtra(keyName,"아이유") //name이라는 스트링으로 아이유가 넘어감 , 여기다가 여러개 해서 넘길 수 있다.
//            startActivity(intent)
//
    }

    override fun onClick(v: View?) {//조커와 아이유를 공통적으로 여기서 처리하기 위함
        val intent = Intent(this,SecondActivity::class.java)

        when(v?.id){ // v는 null일수 있기 떄문에 ? 필수 when은 스위치문임
            btnIU.id -> intent.putExtra(keyName,"아이유")
            btnIU2.id -> intent.putExtra(keyName,"아이유2")
            null -> return
        }

        //startActivity(intent) 이거는 그냥 실행하는거
        startActivityForResult(intent, REQUEST_SECOND) //얘는 답을 얻을라고 실행한느거 100번이라고 보내기보다 컨스트 벨류로!

    }
    //startActivityForResult에서 호출ㄷ된 SecondActivity에서 끝나면 여기로 결과가 오게 끔 만들어져 있음
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {//여기서 받는 듯 requestCode는 우리가 정했던 위의 request_second코드를 가져와야댐, 구분하기 위해
        super.onActivityResult(requestCode, resultCode, data) // data로 넘어온 것
        //resultCode는 RESULT_OK라는 코드를 넘겨준 것

        if(resultCode == Activity.RESULT_OK){
            when(requestCode)//어떤 놈ㅇ 의해서 불려졋니?
            {
                REQUEST_SECOND -> {
                    textView.text = data?.getStringExtra(SecondActivity.keyResult)//data는 널일 수 있기 때문에 그냥 data.하면 안됨 아니면 따로 널인지 아닌지 체크가 필수
                }
            }

        }
    }
}
