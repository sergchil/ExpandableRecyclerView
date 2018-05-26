package com.chilingaryan.expandablerecyclerview.base

import android.view.View

interface OnRecyclerItemClickListener {
    fun onItemClick(view: View, clickedItem: BaseRecyclerViewItem, position: Int)
}