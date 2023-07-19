package com.example.familysafetyapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

	val listContactData: ArrayList<Invite_Model> = ArrayList()
//	lateinit var container: ShimmerFrameLayout

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_home, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

//		 container =
//			requireActivity().findViewById<ShimmerFrameLayout>(R.id.shimmerData)

		val tv_logout = requireView().findViewById<ImageView>(R.id.tv_logout)

		tv_logout.setOnClickListener {
			SharedPref.putBoolean(PrefConst.IS_USER_LOGIN, false)

			FirebaseAuth.getInstance().signOut()
			val intent =Intent(context,LoginActivity::class.java)
			startActivity(intent)
		}


		val listMember = listOf<Member_Model>(
			Member_Model("Ram"),
			Member_Model("shayam"),
			Member_Model("sita"),
			Member_Model("deepak")
		)

		val adapter = MemberAdapter(listMember)

		val recycler = requireView().findViewById<RecyclerView>(R.id.rcy)
		recycler.layoutManager = LinearLayoutManager(requireContext())
		recycler.adapter = adapter
//		container.visibility = View.VISIBLE
//		container.startShimmer()
		val inviteAdapter = InviteAdapter(listContactData)

		CoroutineScope(Dispatchers.IO).launch {
			listContactData.addAll(fetchContacts())
			withContext(Dispatchers.Main) {
				inviteAdapter.notifyDataSetChanged()
//				container.stopShimmer()
			}
		}
//		container.visibility = View.GONE

		val inviteRcy = requireView().findViewById<RecyclerView>(R.id.invite_rcy)
		inviteRcy.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		inviteRcy.adapter = inviteAdapter
	}

	@SuppressLint("Range")
	private fun fetchContacts(): ArrayList<Invite_Model> {

		val listContacts: ArrayList<Invite_Model> = ArrayList()

		val cr = requireActivity().contentResolver
		val cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

		if (cursor != null && cursor.count >= 0) {

			while (cursor != null && cursor.moveToNext()) {

				val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
				val name =
					cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
				val hasPhoneNo =
					cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

				if (hasPhoneNo >= 0) {
					val plur = cr.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?",
						arrayOf(id),
						""
					)

					if (plur != null && plur.count >= 0) {
						while (plur != null && plur.moveToNext()) {
							val phoneNo =
								plur.getString(plur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
							listContacts.add(Invite_Model(name, phoneNo))
						}
					}
					plur?.close()

				}

			}
			cursor.close()

		}
		return listContacts

	}

	companion object {

		@JvmStatic
		fun newInstance() = HomeFragment()
	}

	override fun onResume() {
		super.onResume()
//		container.startShimmer()
	}

	override fun onPause() {
		super.onPause()
//		container.stopShimmer()
	}
}