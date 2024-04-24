package com.example.contact_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact_app.R
import com.example.contact_app.data.model.Image
import com.example.contact_app.data.model.User
import com.example.contact_app.data.model.UserPrivider
import com.example.contact_app.databinding.ItemRecyclerviewBinding

class MyAdapter(val mItems:List<User>,var listType:Int) : RecyclerView.Adapter<MyAdapter.Holder>(){

    interface ItemClick{
        fun onClick(view:View,position:Int,type:Int)
    }
    var clickToMypage:ItemClick? = null  //recyclerview의 item항목에는 아이템을 눌렀을 때 mypage로 넘어가게 하는 버튼,
    var clickToLike:ItemClick? = null // 좋아요를 누르는 버튼이 있다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {

        var newPosition:Int = 0
        for (x in 0 until UserPrivider.users.size) {
            if(UserPrivider.users[position].phoneNumber == UserPrivider.users[x].phoneNumber) {
                newPosition = x
            }
        }

        holder.itemView.setOnClickListener {  //클릭이벤트추가부분
            clickToMypage?.onClick(it, newPosition, listType)

        }
       // if (listType ==1) {
            holder.like.setOnClickListener {  // 좋아요를 클릭하면
                clickToLike?.onClick(it,newPosition,listType)

            }
      //  }
        val image = mItems[position].profileImage

        when(image){
            is Image.ImageUri -> holder.profileImageView.setImageURI(image.uri)
            is Image.ImageDrawable -> holder.profileImageView.setImageResource(image.drawable)
        }
        //if(listType == 1) {
            when (UserPrivider.users[newPosition].isFavorite) {
                false -> holder.like.setImageResource(R.drawable.ic_like)
                true -> holder.like.setImageResource(R.drawable.ic_pressed_like)
            }
      //  }
      // else {
           // holder.like.setImageResource(R.drawable.ic_pressed_like)
      //  }

       // user의 좋아요 클릭여부에 따라 이미지가 다르게 표시된다.
        holder.userName.text = mItems[position].name
        holder.phoneNumber.text = mItems[position].phoneNumber


    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItemCount(): Int {
        return mItems.size
    }
    inner class Holder( binding:ItemRecyclerviewBinding ) : RecyclerView.ViewHolder(binding.root) {
        val profileImageView = binding.ivProfile
        val userName = binding.tvUsername
        val phoneNumber = binding.tvPhoneNumber
        val like = binding.ivUserlike


    }

}