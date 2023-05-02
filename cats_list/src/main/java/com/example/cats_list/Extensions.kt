package com.example.cats_list

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.getCurrentPosition(): Int {
    return (this.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
}