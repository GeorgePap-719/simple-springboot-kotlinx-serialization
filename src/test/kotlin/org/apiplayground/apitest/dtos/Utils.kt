package org.apiplayground.apitest.dtos

import org.apiplayground.apitest.Data
import org.apiplayground.apitest.decodeFromData

internal inline fun <reified T> String.toDto(): T = Data(this).decodeFromData()
