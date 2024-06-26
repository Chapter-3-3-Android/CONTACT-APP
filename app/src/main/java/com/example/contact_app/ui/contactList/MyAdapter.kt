package com.example.contact_app.ui.contactList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact_app.R
import com.example.contact_app.data.model.Image
import com.example.contact_app.data.model.User
import com.example.contact_app.databinding.ItemRecyclerviewBinding

class MyAdapter(val mItems: List<User>, var listType: Int) :
    RecyclerView.Adapter<MyAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int, type: Int)
    }

    var clickToDetail: ItemClick? = null  //recyclerview의 item항목에는 아이템을 눌렀을 때 mypage로 넘어가게 하는 버튼,
    var clickToLike: ItemClick? = null // 좋아요를 누르는 버튼이 있다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {  // 클릭 이벤트 추가 부분
            clickToDetail?.onClick(it, position, listType)

        }
        holder.like.setOnClickListener {  // 좋아요를 클릭하면
            clickToLike?.onClick(it, position, listType)
        }

        val image = mItems[position].profileImage

        when (image) {
            is Image.ImageUri -> holder.profileImageView.setImageURI(image.uri)
            is Image.ImageDrawable -> holder.profileImageView.setImageResource(image.drawable)
        }

        when (mItems[position].isFavorite) { //수정하기!!!!!!!!!!!!!!!!!!!!!!
            false -> holder.like.setImageResource(R.drawable.ic_like)
            true -> holder.like.setImageResource(R.drawable.ic_pressed_like)
        }

        // user의 좋아요 클릭 여부에 따라 이미지가 다르게 표시된다.
        holder.userName.text = mItems[position].name
        holder.phoneNumber.text = mItems[position].phoneNumber
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class Holder(binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val profileImageView = binding.ivProfile
        val userName = binding.tvUsername
        val phoneNumber = binding.tvPhoneNumber
        val like = binding.ivUserlike
    }
}