package com.pxl.pixelstore.data.db

import com.pxl.pixelstore.Database

class PasswordDatabase(driverFactory: DatabaseDriverFactory) {
    val database = Database(driverFactory.createDriver())
}