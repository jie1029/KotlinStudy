package kr.ac.kumoh.s20161184.myproject4


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.NetworkImageView
import org.json.JSONObject
import java.util.zip.Inflater

/**
 * A simple [Fragment] subclass.
 */
class YoutuberFragment : Fragment() {

    companion object{
        const val SERVER_URL = "http://MYIP/"
    }

    data class youtuber(var name:String, var content:String, var image:String)

    lateinit var mQueue:RequestQueue
    private var mResult:JSONObject? = null
    var mArray = ArrayList<youtuber>()

    private var mAdapter = YoutuberAdapter()
    lateinit var mImageLoader: ImageLoader


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_youtuber, container, false)

        val root = inflater.inflate(R.layout.fragment_youtuber,container,false)

        mQueue = MySingleton.getInstance(activity!!).requestQueue
        mImageLoader = MySingleton.getInstance(activity!!).imageLoader

        val url = "${SERVER_URL}youtuber.php"

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                mResult = it
                drawList()
            },
            Response.ErrorListener {
                Toast.makeText(activity,it.toString(),Toast.LENGTH_SHORT).show()
            }
        )

        mQueue.add(jsonRequest)


        val r = root.findViewById<RecyclerView>(R.id.recyclerView)
        r.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }

        return root

    }

    fun drawList(){

        val items = mResult?.getJSONArray("list")?:return

        for(i in 0 until items.length()){
            val item:JSONObject = items[i] as JSONObject

            val name = item.getString("name")
            val content = item.getString("content")
            val image = item.getString("image")

            mArray.add(youtuber(name,content,image))


        }

        mAdapter.notifyDataSetChanged()
    }

    inner class YoutuberAdapter():RecyclerView.Adapter<YoutuberAdapter.ViewHolder>(){

        inner class ViewHolder: RecyclerView.ViewHolder {

            var txName : TextView
            var txContent: TextView
            var imImage: NetworkImageView

            constructor(root:View):super(root){
                txName = root.findViewById(R.id.textName)
                txContent = root.findViewById(R.id.textContent)
                imImage = root.findViewById(R.id.imImage)

            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): YoutuberAdapter.ViewHolder {
            val root = LayoutInflater.from(activity).inflate(R.layout.item_youtuber,parent,false)
            return ViewHolder(root)
        }

        override fun getItemCount(): Int {
            return mArray.size
        }

        override fun onBindViewHolder(holder: YoutuberAdapter.ViewHolder, position: Int) {
            holder.txName.text = mArray[position].name
            holder.txContent.text = mArray[position].content
            holder.imImage.setImageUrl("${SERVER_URL}${mArray[position].image}",mImageLoader)
        }

    }


}
