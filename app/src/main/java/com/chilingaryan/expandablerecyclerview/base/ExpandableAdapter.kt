package com.chilingaryan.expandablerecyclerview.base


import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import java.util.ArrayList


abstract class ExpandableAdapter(baseRecyclerViewItems: ArrayList<*>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var baseRecyclerViewItems: ArrayList<BaseRecyclerViewItem> = ArrayList()

    init {
        try {
            this.baseRecyclerViewItems = baseRecyclerViewItems as ArrayList<BaseRecyclerViewItem>
        } catch (e: Exception) {
            throw IllegalArgumentException("Please Add Items Of Class extending BaseRecyclerViewItem")
        }
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    override fun getItemCount(): Int {
        return baseRecyclerViewItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return baseRecyclerViewItems[position].depth
    }
}
