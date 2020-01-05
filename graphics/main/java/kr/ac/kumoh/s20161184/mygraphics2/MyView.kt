package kr.ac.kumoh.s20161184.mygraphics2


import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyView : View { // context가 하나인 View 상속 받는다
    private val paint = Paint()
    private val paintFill  = Paint()
    private  var bmp : Bitmap
    //var point = Point(0,0)
    var point = ArrayList<Point>()
    constructor(context : Context?) :
            this(context, null)
    constructor(context: Context?,
                attrs: AttributeSet?) :
            this(context, attrs, 0)
    constructor(context: Context?,
                attrs: AttributeSet?,
                defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)
    {
        paint.setARGB(255, 255, 0, 0)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F

        paintFill.setARGB(255, 255, 255, 0)
        paintFill.style = Paint.Style.FILL

        bmp = BitmapFactory.decodeResource(resources,R.drawable.cat)
        bmp = Bitmap.createScaledBitmap(bmp,
            (bmp.width*0.8).toInt(),
            (bmp.height*0.8).toInt(),false)
    }



    override fun onDraw(canvas: Canvas?) { // MFC 처럼 onDraw를 사용하여 화면을 그린다.
        super.onDraw(canvas)
        // canvas
        canvas?.drawBitmap(bmp, 10F, 10F, paint)
//            canvas?.drawCircle(100F,100F, 80F, paint)  // ? !! 둘다 써도 된다 , onDraw에서는 급박한 상황이기 때문에 Paint() 같은거는할당 후에 사용해라
//            canvas?.drawCircle(100F,100F, 80F, paintFill)  // ? !! 둘다 써도 된다 , onDraw에서는 급박한 상황이기 때문에 Paint() 같은거는할당 후에 사용해라
        for(i in 0 until point.size) {
            canvas?.drawCircle(
                point[i].x.toFloat(),
                point[i].y.toFloat(),
                20F,
                paintFill
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_DOWN || event?.action == MotionEvent.ACTION_MOVE){
            //point.x = event.x.toInt()
            //point.y = event.y.toInt()
            point.add(Point(event.x.toInt(),event.y.toInt()))
            invalidate()
        }

        return true
    }


}
