package com.homeautomationapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.homeautomationapp.database.HomeAutomationDb

/**
 * Defines the "devices" table from [HomeAutomationDb].
 */
@Entity(tableName = "devices")
data class DeviceEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String,

    var intensity: Int?,

    var mode: String?,

    var position: Int?,

    @ColumnInfo(name = "product_type")
    var productType: String,

    var temperature: Int?,
)