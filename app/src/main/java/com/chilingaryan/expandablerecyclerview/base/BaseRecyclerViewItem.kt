package com.chilingaryan.expandablerecyclerview.base

abstract class BaseRecyclerViewItem {

    var depth: Int
    var children: ArrayList<BaseRecyclerViewItem>
    var position: Int = 0
    var isExpanded = false

    protected constructor(depth: Int, children : ArrayList<BaseRecyclerViewItem> = ArrayList()) {
        this.depth = depth
        this.children = children
    }

    fun hasChildren(): Boolean {
        return children.isNotEmpty()
    }
}
