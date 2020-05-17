package com.manuelcarvalho.pccollector.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Part(
    val manufacturer: String,
    val computer: String,
    val catridge: String,
    val condition: String,
    val partNum: String,
    val romUse: String,
    val rarity: String,
    val yearMade: String

) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}