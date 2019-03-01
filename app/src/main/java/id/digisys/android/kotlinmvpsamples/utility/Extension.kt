package id.digisys.android.kotlinmvpsamples.utility

import android.content.Context
import android.view.View
import id.digisys.android.kotlinmvpsamples.db.helper.DbHelper
import java.text.SimpleDateFormat
import java.util.*

fun View.hide (){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun Date.inFuture(): Boolean{
    val createDate = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).format(Date())
    val currentDate = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).parse(createDate)
    return (this.after(currentDate))
}

val Context.db: DbHelper
    get() = DbHelper.getInstance(applicationContext)