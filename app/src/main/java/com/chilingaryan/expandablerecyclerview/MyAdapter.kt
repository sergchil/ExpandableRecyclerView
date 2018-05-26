package com.chilingaryan.expandablerecyclerview

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.chilingaryan.expandablerecyclerview.base.ExpandableAdapter
import com.chilingaryan.expandablerecyclerview.base.ExpandableRecyclerView
import java.util.*


class MyAdapter : ExpandableAdapter {

    private val expandableRecyclerView: ExpandableRecyclerView
    private val listItems: ArrayList<DummyData>

    constructor(items: ArrayList<DummyData>, recyclerView: ExpandableRecyclerView) : super(items) {
        this.expandableRecyclerView = recyclerView
        this.listItems = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as Holder
        val item = listItems[position]

        viewHolder.text1.text = item.text
        viewHolder.text2.text = item.value

        // add margin for visual depth
        holder.itemView.setLeftMargin(listItems[position].depth * 100)

        // change expanded items background color
        val factor = (listItems[position].depth) * 20
        holder.itemView.setBackgroundColor(Color.argb(255, 255 - factor, 255 - factor, 255 - factor))

        if (item.hasChildren()) {
            holder.arrow.visibility = View.VISIBLE
            if (item.isExpanded) {
                holder.arrow.setImageResource(R.drawable.ic_arrow_up)
            } else {
                holder.arrow.setImageResource(R.drawable.ic_arrow_down)
            }
        } else {
            holder.arrow.visibility = View.GONE
        }

    }

    private inner class Holder : RecyclerView.ViewHolder {

        var text1: TextView
        var text2: TextView
        var arrow: ImageView

        constructor(itemView: View) : super(itemView) {
            text1 = itemView.findViewById(R.id.tv1) as TextView
            text2 = itemView.findViewById(R.id.tv2) as TextView
            arrow = itemView.findViewById(R.id.arrow) as ImageView

            itemView.setOnClickListener {
                val clickedItem = listItems[adapterPosition]
                it.context.showToast("position = ${clickedItem.position} depth = ${clickedItem.depth}")
                if (clickedItem.hasChildren()) {
                    if (clickedItem.isExpanded) {
                        arrow.setImageResource(R.drawable.ic_arrow_down)
                    } else {
                        arrow.setImageResource(R.drawable.ic_arrow_up)
                    }
                }
//                expandableRecyclerView.performAction(adapterPosition)
            }

        }
    }

    private fun View.setLeftMargin(value: Int) {
        try {
            val lp = layoutParams as ViewGroup.MarginLayoutParams
            lp.leftMargin = value
            layoutParams = lp
        } catch (e: Exception) {
        }
    }


    private fun Context.showToast(title: String) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
    }

}
