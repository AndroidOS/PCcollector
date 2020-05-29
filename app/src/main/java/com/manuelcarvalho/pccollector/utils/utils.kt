package com.manuelcarvalho.pccollector.utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.manuelcarvalho.pccollector.R

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

fun dialogueQuery(context: Context) {
    val alertDialog: AlertDialog? = context.let {
        val builder = AlertDialog.Builder(it)

        var items = arrayOf<CharSequence>(
            "Academy",
            "Atarisoft",
            "Beyond",
            "Boone",
            "Broderbund",
            "CBS Soft.",
            "Commodore",
            "Creative",
            "HES",
            "Imagic",
            "Xonox"
        )
        builder.apply {
            setPositiveButton(
                R.string.ok,
                DialogInterface.OnClickListener { dialog, id ->
                    // User clicked OK button
                })
            setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })

            setTitle("Choose Cartridge")

            //Test data


            builder.setItems(
                items,

                DialogInterface.OnClickListener { dialog, which ->


                })

        }

        builder.create()
    }

    alertDialog?.show()
}
