package com.homeautomationapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.homeautomationapp.database.HomeAutomationDb

/**
 * Defines the "user" table from [HomeAutomationDb].
 */
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "first_name")
    var firstName: String,

    @ColumnInfo(name = "last_name")
    var lastName: String,

    var birthdate: String,

    var email: String,

    var phone: String,

    @ColumnInfo(name = "street_number")
    var streetNumber: String,

    @ColumnInfo(name = "street_name")
    var streetName: String,

    @ColumnInfo(name = "postal_code")
    var postalCode: Int,

    var city: String,

    var country: String
)