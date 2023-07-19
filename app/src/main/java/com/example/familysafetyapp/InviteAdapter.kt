package com.example.familysafetyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InviteAdapter(val inviteMember: List<Invite_Model>) :
	RecyclerView.Adapter<InviteAdapter.ViewHolder>() {
	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		val inviteName = itemView.findViewById<TextView>(R.id.invite_name)
		val inviteNumber = itemView.findViewById<TextView>(R.id.invite_number)

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val item = inflater.inflate(R.layout.item_invite, parent, false)
		return ViewHolder(item)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
	val itemPosition=inviteMember[position]
		holder.inviteName.text=itemPosition.name
		holder.inviteNumber.text= itemPosition.number

	}

	override fun getItemCount(): Int {
		return inviteMember.size
	}
}