package com.chilingaryan.expandablerecyclerview.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View


class ExpandableRecyclerView : RecyclerView, OnRecyclerItemClickListener {

    private lateinit var expandableAdapter: ExpandableAdapter
    private var recyclerItemClickListener: RecyclerItemClickListener

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        this.recyclerItemClickListener = RecyclerItemClickListener(context)
        this.recyclerItemClickListener.setOnItemClick(this)
        addOnItemTouchListener(recyclerItemClickListener)
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        if (adapter !is ExpandableAdapter) {
            throw IllegalStateException("Please Set Adapter Of the ExpandableAdapter Class.")
        }
        expandableAdapter = adapter
        super.setAdapter(adapter)
    }

    override fun onItemClick(view: View, clickedItem: BaseRecyclerViewItem, position: Int) {
        performAction(position)
    }

    private fun removeAllChildren(list: List<BaseRecyclerViewItem>) {
        for (i in list) {
            if (i.isExpanded) {
                i.isExpanded = false
                removeAllChildren(i.children)
                removePrevItems(expandableAdapter.baseRecyclerViewItems, i.position, i.children.size)
            }
        }
    }

    // expand / collapse action
    private fun performAction(position: Int) {
        if (position == RecyclerView.NO_POSITION) return

        val adapterList = expandableAdapter.baseRecyclerViewItems

        val clickedItem = adapterList[position]

        if (clickedItem.isExpanded) {
            clickedItem.isExpanded = false
            removeAllChildren(clickedItem.children)
            removePrevItems(adapterList, position, clickedItem.children.size)
        } else {
            addItems(clickedItem, adapterList, position)
        }
    }

    private fun removePrevItems(tempList: ArrayList<BaseRecyclerViewItem>, position: Int, numberOfItemsAdded: Int) {
        for (i in 0 until numberOfItemsAdded) {
            tempList.removeAt(position + 1)
        }
        expandableAdapter.baseRecyclerViewItems = tempList
        expandableAdapter.notifyItemRangeRemoved(position + 1, numberOfItemsAdded)

        refreshPosition()
    }

    private fun refreshPosition() {
        var position = 0
        for (i in expandableAdapter.baseRecyclerViewItems) {
            i.position = position++
        }
    }

    private fun addItems(clickedItem: BaseRecyclerViewItem, tempList: ArrayList<BaseRecyclerViewItem>, position: Int) {
        if (clickedItem.hasChildren()) {
            tempList.addAll(position + 1, clickedItem.children)
            clickedItem.isExpanded = true
            expandableAdapter.baseRecyclerViewItems = tempList
            expandableAdapter.notifyItemRangeInserted(position + 1, clickedItem.children.size)
            refreshPosition()
        }
    }

    // using gesture detector to expand or collapse items
    private inner class RecyclerItemClickListener : RecyclerView.OnItemTouchListener {

        constructor(context: Context) {
            mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

            })
        }

        private val mGestureDetector: GestureDetector

        private var onItemClick: OnRecyclerItemClickListener? = null

        internal fun setOnItemClick(onItemClick: OnRecyclerItemClickListener) {
            this.onItemClick = onItemClick
        }

        override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
            val childView = view.findChildViewUnder(e.x, e.y)
            if (childView != null && mGestureDetector.onTouchEvent(e)) {
                childView.performClick()
                val position = view.getChildAdapterPosition(childView)
                onItemClick?.onItemClick(childView, expandableAdapter.baseRecyclerViewItems[position], position)

                return true
            }
            return false
        }

        override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(arg0: Boolean) {}

    }

}
