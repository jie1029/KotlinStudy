package kr.ac.kumoh.s20161184.myproject4


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_webtoon.view.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */


class WebtoonFragment : Fragment() {

    companion object{
        const val SERVER_URL = "http://MYIP/"
        const val QUEUE_TAG = "volley"
    }


    lateinit var mImageLoader: ImageLoader
    private val mAdapter = WebtoonAdapter()

    data class Webtoon(var title:String,
                       var author:String,
                       var image: String)

    private lateinit var mQueue: RequestQueue
    var mArray = ArrayList<Webtoon>()
    private var mResult: JSONObject? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_webtoon,container,false)

        var mySingleton = MySingleton.getInstance(activity!!)

        mQueue = mySingleton.requestQueue

        mImageLoader = mySingleton.imageLoader


        val url = "${SERVER_URL}webtoon.php"

        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url,
            null,
            Response.Listener { response ->
                mResult = response
                drawList()
            },
            Response.ErrorListener {
                Toast.makeText(activity,it.toString(),
                    Toast.LENGTH_LONG).show()
            })
        // Add the request to the RequestQueue.

        mySingleton.addToRequest(stringRequest)

        root.recyclerView.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
        return root

    }


    private fun drawList(){
        val items = mResult?.getJSONArray("list") ?: return

        mArray.clear()

        for(i in 0 until items.length()){
            val item = items[i] as JSONObject

            val id = item.getInt("id")
            val title = item.getString("title")
            val author = item.getString("author")
            val image = item.getString("image")


            mArray.add(Webtoon(title,author,image))

        }

        mAdapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        mQueue.cancelAll(QUEUE_TAG)
    }

    inner class WebtoonAdapter():
            RecyclerView.Adapter<WebtoonAdapter.holder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
            val root = LayoutInflater.from(activity).inflate(R.layout.item_webtoon,parent,false)
            return holder(root)
        }

        override fun getItemCount(): Int {
            return mArray.size;

        }

        override fun onBindViewHolder(holder: holder, position: Int) {
            holder.txTitle.text =  mArray[position].title
            holder.txAuthor.text = mArray[position].author
            holder.imImage.setImageUrl("${SERVER_URL}${mArray[position].image}",mImageLoader)


        }

        inner class holder:
                RecyclerView.ViewHolder,View.OnClickListener{
            override fun onClick(p0: View?) {
                Log.i("RESULT","눌렸다!")
                var intent = Intent(activity, ShowActivity::class.java)

                startActivity(intent)
                Toast.makeText(activity,"눌렸습니다${adapterPosition}",Toast.LENGTH_SHORT).show()
            }

            var txTitle: TextView
            var txAuthor: TextView
            var imImage: NetworkImageView

            constructor(root:View):super(root){
                txAuthor = root.findViewById(R.id.textAuthor)
                txTitle = root.findViewById(R.id.textTitle)
                imImage = root.findViewById(R.id.imImage)
                root.setOnClickListener(this)
            }

        }
    }


}
