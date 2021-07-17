package com.laisd.moviesapp.presentation.moviedetail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laisd.moviesapp.R
import com.laisd.moviesapp.domain.model.CastMember

class CastMembersAdapter(
    private val castMembers: List<CastMember>
) : RecyclerView.Adapter<CastMembersAdapter.CastMembersViewHolder>() {

    inner class CastMembersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"

        val tvMemberName = itemView.findViewById<TextView>(R.id.tvCastMemberName)
        val tvMemberRole = itemView.findViewById<TextView>(R.id.tvCastMemberRole)
        val ivMemberPicture = itemView.findViewById<ImageView>(R.id.ivCasMemberPicture)

        fun bind(castMember: CastMember) {
            var pictureUrl:String? = null

            castMember.photo?.let {
                pictureUrl = imageBaseUrl + it
            }

            tvMemberName.text = castMember.name
            tvMemberRole.text = castMember.character
            loadImage(pictureUrl, ivMemberPicture)
        }

        private fun loadImage(url: String?, imageView: ImageView) {
            Glide.with(itemView)
                .load(url)
                .fallback(R.drawable.drive)
                .centerCrop()
                .into(imageView)
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