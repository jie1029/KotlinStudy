package kr.ac.kumoh.s20161184.adaptertest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Array

class MainActivity : AppCompatActivity() {

    data class Song(var title:String, var singer:String)

    private var mSong =ArrayList<Song>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSong.add(Song("좋은날","아이유"));
        mSong.add(Song("정말","배고파"));
        mSong.add(Song("보고싶다","김범수"));
        mSong.add(Song("애인있어요","이은미"));
        mSong.add(Song("소주한잔","임창정"));
        mSong.add(Song("Ocean Eye","Billie Eilish"));
        mSong.add(Song("돌아오지마","헤이즈"));
        mSong.add(Song("Woman","HONNE"));
        mSong.add(Song("붕붕","김하온"));
        mSong.add(Song("시차","우원재"));
        mSong.add(Song("내가 더 나빠","헤이즈"));
        mSong.add(Song("idontwannabeyouanymore","Billie Eilish"));
        mSong.add(Song("좋은날","아이유"));
        mSong.add(Song("정말","배고파"));
        mSong.add(Song("보고싶다","김범수"));
        mSong.add(Song("애인있어요","이은미"));
        mSong.add(Song("소주한잔","임창정"));
        mSong.add(Song("Ocean Eye","Billie Eilish"));
        mSong.add(Song("돌아오지마","헤이즈"));
        mSong.add(Song("Woman","HONNE"));
        mSong.add(Song("붕붕","김하온"));
        mSong.add(Song("시차","우원재"));
        mSong.add(Song("내가 더 나빠","헤이즈"));
        mSong.add(Song("idontwannabeyouanymore","Billie Eilish"));

        listView.adapter = SongAdapter(this, R.layout.item_song , mSong);

    }

    //크게보면 첫번째
    private class SongViewHolder{
        lateinit var txTitle: TextView // 초기화를 늦게해줄꺼다!
        lateinit var txSingger: TextView

    }


    inner class SongAdapter(context: Context, resource: Int, objects: MutableList<Song>):
        ArrayAdapter<Song>(context, resource, objects) {

        private val mInflater = LayoutInflater.from(context)
        private val mLayout = resource
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view: View
            val holder: SongViewHolder

            //크게보면 두번쩨
            if (convertView == null) { // << 얘가 없으면 원래 있었는데도 똑같이 계속 만듬
                view = mInflater.inflate(//얘가 끊임없이 호출됨 계속 만드는것 , 얘는 xml을 객체화 해서불러오는 것
                    mLayout, parent, false
                )
                holder = SongViewHolder()//얘가 뭐해주는거지?
                holder.txTitle = view.findViewById(R.id.text1)
                holder.txSingger = view.findViewById(R.id.text2)//가져오기는 가져옴 근데 홀더에 가지고있는데
                //홀더를어디에 저장할꺼냐가 문제 --> 뷰에다가해야되는데 다행히 뷰에 특이한놈을 가져올 수 있다


                view.tag = holder //이렇게 저장


            }
            //스크롤을 왓다갓다할때마다 getView가 처리한다??
            else {//재활용하려고 이미 잡아논 상태
                //이미 홀더에 저장해놔서 홀더에서 꺼내오면됨
                view = convertView
                holder = view.tag as SongViewHolder //넣어라~ // 세팅

            }


            //크게보면 ㅅㅔ번째
            holder.txTitle.text = getItem(position)?.title
            holder.txSingger.text = getItem(position)?.singer


            return view
        }
    }
}


