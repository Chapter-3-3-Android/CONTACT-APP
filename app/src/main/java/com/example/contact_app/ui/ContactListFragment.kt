package com.example.contact_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact_app.R
import com.example.contact_app.data.model.Image
import com.example.contact_app.data.model.User
import com.example.contact_app.data.model.UserPrivider

import com.example.contact_app.databinding.FragmentContactListBinding

class ContactListFragment : Fragment() {
    companion object {

        var favoriteList = mutableListOf<User>()  //!!!!!!!!!!!!!!!!!!!! fragment 에 정의하니 mypage fragment로 이동하고 다시오면 favoritelist가 초기화 되는 문제 model에서 정의해야 할 듯
        val copyContactList = mutableListOf<User>()
    }

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       for(x in 0 until UserPrivider.users.size){
           copyContactList.add(UserPrivider.users[x])
       }



        val my : User = UserPrivider.users[0]
        val image = my.profileImage

        when(image){
            is Image.ImageUri -> binding.ivProfile.setImageURI(image.uri)
            is Image.ImageDrawable -> binding.ivProfile.setImageResource(image.drawable)
        }
        binding.tvUsername.text = my.name
        binding.tvPhoneNumber.text = my.phoneNumber

        binding.MyProfileView.setOnClickListener {
            //switchTapPosition()
        }



        val adapterOfContactList = MyAdapter(copyContactList,1)
        val adapterOfFavoriteList = MyAdapter(favoriteList,2) // contact list 에서 좋아요 버튼을 눌러추가한 데이터들
        binding.contactListView.adapter = adapterOfContactList
        binding.favoriteListView.adapter = adapterOfFavoriteList

        binding.favoriteListView.layoutManager = LinearLayoutManager(context)
        binding.contactListView.layoutManager = LinearLayoutManager(context)
        binding.favoriteListView.layoutManager = LinearLayoutManager(context)


        adapterOfContactList.clickToMypage = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int, type:Int) {
//                var newPosition:Int = position

//                if(type != 1){
//                    for (x in 0 until UserPrivider.users.size) {
//                        if(UserPrivider.users[position] == UserPrivider.users[x]) {
//                            newPosition = x
//                        }
//                    }
//                }

                val bundle = Bundle()
                bundle.putInt("position", position)
                val ContactDetailDataSent =
                   MyPageFragment.newInstance(bundle) // onClick함수를 adapter에 적용하여 mypage로 Bundle 데이터 넘김!!!!!!!!!!! mypage에 new instance companion object 생성

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_frag, ContactDetailDataSent)
                    .addToBackStack(null)
                    .commit() //mypage로 이동
            }
        }


        adapterOfContactList.clickToLike = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int,type:Int) {
                if (UserPrivider.users[position].isFavorite == false) {
                    if(type == 1){
                        copyContactList.removeAt(position)
                        binding.contactListView.adapter?.notifyDataSetChanged()

                    }
                    switchFavoriteByUser(position)

                        favoriteList?.add(UserPrivider.users[position])
                        binding.favoriteListView.adapter?.notifyDataSetChanged()


                   }




                }
            }


        }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun switchFavoriteByUser(index: Int) {
        UserPrivider.users[index].copy(
            isFavorite = !( UserPrivider.users[index].isFavorite)
        )
    }
}

//    companion object {
//        private var INSTANCE: ContactListFragment? = null
//
//        fun newInstance(): ContactListFragment {
//            return synchronized(ContactListFragment::class.java) {
//                val instance = INSTANCE ?: ContactListFragment()
//
//                if (INSTANCE == null) {
//                    INSTANCE = instance
//                }
//
//                instance
//            }
//        }
//    }

