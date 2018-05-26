package com.chilingaryan.expandablerecyclerview

import com.chilingaryan.expandablerecyclerview.base.BaseRecyclerViewItem


class DummyData : BaseRecyclerViewItem {
    var text: String
    var value: String

    constructor(depth: Int, text: String = "", value: String = "", children: ArrayList<BaseRecyclerViewItem> = ArrayList()) : super(depth, children) {
        this.text = text
        this.value = value

    }
}