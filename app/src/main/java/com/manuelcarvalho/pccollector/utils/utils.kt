package com.manuelcarvalho.pccollector.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

fun sendEmail(context: Context) {
    var stringList = ""
    var manufacturer = ""
//    for (n in viewModel.carts.value!!) {
//        stringList += "${n.catridge}\n"
//        manufacturer = n.manufacturer
//    }

    val to = "tom@gmail.com"
    val subject = "cartridge list for ${manufacturer}."
    val message = stringList

    val intent = Intent(Intent.ACTION_SEND)
    val addressees = arrayOf(to)
    intent.putExtra(Intent.EXTRA_EMAIL, addressees)
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, message)
    intent.type = "message/rfc822"
    startActivity(context, Intent.createChooser(intent, "Select Email Sending App :"), null)


}