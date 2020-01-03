package kr.ac.kumoh.s20161184.mycardview1125

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.collection.LruCache
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_song.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object{
        const val QUEUE_TAG = "volley"
        const val SERVER_URL = "http://MYIP/"
    }
    data class Song(var title:String,
                    var singer:String,
                    var image: String)

    var mArray = ArrayList<Song>()
    private lateinit var mQueue:RequestQueue

    private var mResult: JSONObject? = null

    private var mAdapter = SongAdapter()
 private lateinit var mImageLoader: ImageLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mQueue = Volley.newRequestQueue(this)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)//this안됨
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }

        mImageLoader = ImageLoader(
            mQueue,
            object : ImageLoader.ImageCache{
                private val cache = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap? {
                    return cache.get(url)
                }
                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }

            }
        )
        requestSong()
    }

    override fun onStop() {
        super.onStop()
        mQueue.cancelAll(QUEUE_TAG)
    }


    private fun requestSong()
    {
        val url = "${SERVER_URL}song.php"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { res ->
                Log.i("RESULT",res.toString())
                mResult = res
                drawList()
            },
            Response.ErrorListener { err ->
                Log.e("ERROR",err.toString())
                Toast.makeText(this,err.toString(), Toast.LENGTH_LONG).show()

            }
        )

        request.tag = QUEUE_TAG
        mQueue.add(request)
    }

    private fun drawList(){
        val items = mResult?.getJSONArray("list") ?: return

        mArray.clear() //기존에 있던걸 다 지워야 똑같은것이 쌓이지 않는다.

        for(i in 0 until items.length()){
            val item = items[i] as JSONObject

            val id = item.getInt("id")
            val title = item.getString("title")
            val singer = item.getString("singer")
            val image = item.getString("image")

            Log.i("RESULT2",item.getString("title"))
            Log.i("RESULT3",item.getString("singer"))

            mArray.add(Song(title,singer,image))

        }

        mAdapter.notifyDataSetChanged()//얘를 해야 리스트가 다시 그려진다.
    }


    inner class SongAdapter(): RecyclerView.Adapter<SongAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
        {
            var txTitle: TextView = itemView.findViewById(R.id.textTitle)
            var txSinger: TextView = itemView.findViewById(R.id.textSinger)
            var imImage: NetworkImageView = itemView.findViewById(R.id.imImage)

        }
        override fun getItemCount(): Int {

            return mArray.size;
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAdapter.ViewHolder {
            val root = LayoutInflater.from(applicationContext).inflate(R.layout.item_song,parent,false)
            return ViewHolder(root)
        }
        override fun onBindViewHolder(holder: SongAdapter.ViewHolder, position: Int) {

            holder.txTitle.text =  mArray[position].title
            holder.txSinger.text = mArray[position].singer
            holder.imImage.setImageUrl("${SERVER_URL}${mArray[position].image}",mImageLoader)
        }
    }
}
