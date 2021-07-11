package com.laisd.moviesapp.presentation.moviedetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laisd.moviesapp.R
import com.laisd.moviesapp.data.model.CastMemberResponse

class CastMembersAdapter(
    private val castMembers: Array<CastMemberResponse>
) : RecyclerView.Adapter<CastMembersAdapter.CastMembersViewHolder>() {

    inner class CastMembersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMemberName = itemView.findViewById<TextView>(R.id.tvCastMemberName)
        val tvMemberRole = itemView.findViewById<TextView>(R.id.tvCastMemberRole)
        val ivMemberPicture = itemView.findViewById<ImageView>(R.id.ivCasMemberPicture)

        fun bind(castMember: CastMemberResponse) {
            tvMemberName.text = castMember.name
            tvMemberRole.text = castMember.role
            ivMemberPicture.setImageResource(castMember.picture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastMembersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_cast_member, parent, false
        )
        return CastMembersViewHolder(view)
    }

    override fun getItemCount() = castMembers.size

    override fun onBindViewHolder(holder: CastMembersViewHolder, position: Int) {
        holder.bind(castMembers[position])
    }
}