package kr.ac.kumoh.s20161184.mybooks1121

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object{
        const val QUEUE_TAG = "VolleyRequest"

    }

    data class Book(var author:String, var title:String, var image:String)
    var myArray=ArrayList<Book>()

    private lateinit var mQueue: RequestQueue
    private var mRequest: JSONObject? = null

    private val mAdapter = WebtoonAdapter()

    lateinit var mImageLoader : ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContentView(R.layout.activity_main)


        recyclerview.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(applicationContext)
            itemAnimator= DefaultItemAnimator()
            adapter=mAdapter
        }

        mQueue = Volley.newRequestQueue(this)

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
        requestWebtoon()
    }

    override fun onStop() {
        super.onStop()
        mQueue.cancelAll(QUEUE_TAG)
    }


    private fun requestWebtoon()
    {
        val url = "https://www.googleapis.com/books/v1/volumes?q=%EB%94%A5%EB%9F%AC%EB%8B%9D"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { res ->
                Log.i("RESULT",res.toString())
                mRequest = res
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
        val items = mRequest?.getJSONArray("items") ?: return

        myArray.clear() //기존에 있던걸 다 지워야 똑같은것이 쌓이지 않는다.

        for(i in 0 until items.length()){
            val item = items[i] as JSONObject

            val info = item.getJSONObject("volumeInfo") ?:return
            val title = info.getString("title")

            var image = ""
            if (info.has("imageLinks")){
                if(info.getJSONObject("imageLinks").has("smallThumbnail"))
                    image = info.getJSONObject("imageLinks").getString("smallThumbnail")

            }

            var author = "test"
            if(info.has("authors")) {
                author = info.getJSONArray("authors").getString(0)
            }

            myArray.add(Book(author,title,image))

        }

        mAdapter.notifyDataSetChanged()//얘를 해야 리스트가 다시 그려진다.
    }

    inner class WebtoonAdapter(): RecyclerView.Adapter<WebtoonAdapter.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val root =
                LayoutInflater.from(applicationContext).inflate(R.layout.item_books,parent,false)
            return ViewHolder(root)
        }

        override fun getItemCount(): Int {
            return myArray.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.txTitle.text=myArray[position].title
            holder.txAuthor.text=myArray[position].author
            holder.imImage.setImageUrl(myArray[position].image,mImageLoader)
        }


        inner class ViewHolder: RecyclerView.ViewHolder{

            val txAuthor :TextView
            val txTitle :TextView
            val imImage :NetworkImageView

            constructor(root:View) : super(root){

                txAuthor = root.findViewById(R.id.author)
                txTitle = root.findViewById(R.id.title)
                imImage = root.findViewById(R.id.image)

            }


        }
    }
}

