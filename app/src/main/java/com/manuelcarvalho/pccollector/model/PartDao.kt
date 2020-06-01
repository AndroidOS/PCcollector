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
    suspend fun getPart(partId: Int): Part

    @androidx.room.Query("DELETE FROM part")
    suspend fun deleteAllParts()

    @androidx.room.Query("UPDATE part SET ownIt = :ownIt WHERE uuid = :partId")
    suspend fun updateOwnIt(partId: Int, ownIt: Boolean): Int

    @androidx.room.Query("SELECT * FROM part WHERE manufacturer = :string")
    suspend fun getManuParts(string: String): List<Part>
}