package com.manuelcarvalho.pccollector.model

import androidx.room.Dao
import androidx.room.Insert


@Dao
interface PartDao {

    @Insert
    suspend fun insertAll(vararg part: Part): List<Long>

    @androidx.room.Query("SELECT * FROM part")
    suspend fun getAllParts(): List<Part>

    @androidx.room.Query("SELECT * FROM part WHERE uuid = :partId")
    suspend fun getParts(partId: Int): Part

    @androidx.room.Query("DELETE FROM part")
    suspend fun deleteAllParts()
}