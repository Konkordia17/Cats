package com.example.cat_description.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatDescription(
    val id: String,
    val url: String
): Parcelable
