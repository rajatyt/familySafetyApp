package com.example.familysafetyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(val listMember: List<Member_Model>) :
	RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val tv_name = itemView.findViewById<TextView>(R.id.tv_member_name)

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val item = inflater.inflate(R.layout.item_member, parent, false)
		return ViewHolder(item)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val itemPos=listMember[position]
		holder.tv_name.text=itemPos.name

	}

	override fun getItemCount(): Int {
		return listMember.size
	}
}