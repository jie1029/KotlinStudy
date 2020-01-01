package kr.ac.kumoh.s20161184.myresycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    data class Song(var Title:String, var Signer:String, var Link:String?);

    var mSong = ArrayList<Song>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSong.add(Song("사랑했나봐","누구더라","https://www.youtube.com"));
        mSong.add(Song("돌아오지마","헤이즈","https://www.youtube.com"));
        mSong.add(Song("사랑에 연습이 있었다면","임재현","https://www.youtube.com"));
        mSong.add(Song("안녕","폴킴","https://www.youtube.com"));

        mSong.add(Song("포장마차","황인욱","https://www.youtube.com"));
        mSong.add(Song("조금 취했어","임재현","https://www.youtube.com"));
        mSong.add(Song("사랑했나봐","누구더라","https://www.youtube.com"));
        mSong.add(Song("돌아오지마","헤이즈","https://www.youtube.com"));
        mSong.add(Song("사랑에 연습이 있었다면","임재현","https://www.youtube.com"));
        mSong.add(Song("안녕","폴킴","https://www.youtube.com"));

        mSong.add(Song("포장마차","황인욱","https://www.youtube.com"));
        mSong.add(Song("조금 취했어","임재현","https://www.youtube.com"));
        mSong.add(Song("사랑했나봐","누구더라","https://www.youtube.com"));
        mSong.add(Song("돌아오지마","헤이즈","https://www.youtube.com"));
        mSong.add(Song("사랑에 연습이 있었다면","임재현","https://www.youtube.com"));
        mSong.add(Song("안녕","폴킴","https://www.youtube.com"));

        mSong.add(Song("포장마차","황인욱","https://www.youtube.com"));
        mSong.add(Song("조금 취했어","임재현","https://www.youtube.com"));

        recyclerView.apply {
            //recyclerView.~~~ 일때이렇게 해서 다 묶을 수 있음
            setHasFixedSize(true)//고정크기
            layoutManager = LinearLayoutManager(applicationContext)//수직방향으로 할것이다?
            itemAnimator =DefaultItemAnimator()
            adapter = SongAdapter()
        }

    }

    inner class SongAdapter()
        : RecyclerView.Adapter<SongAdapter.ViewHoler>() {

        inner class ViewHoler(itemView: View): RecyclerView.ViewHolder(itemView){
            var txSinger = itemView.findViewById<TextView>(R.id.text1)
            var txTitle = itemView.findViewById<TextView>(R.id.text2)
            var txLink = itemView.findViewById<TextView>(R.id.text3)
        }


        override fun getItemCount(): Int {
            return mSong.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAdapter.ViewHoler {
            val root = LayoutInflater.from(applicationContext).inflate(R.layout.item_song,parent,false)

            return ViewHoler(root)
        }
        override fun onBindViewHolder(holder: SongAdapter.ViewHoler, position: Int) {
            holder.txLink.text = mSong[position].Link
            holder.txSinger.text = mSong[position].Signer
            holder.txTitle.text = mSong[position].Title
        }

    }
}
