package com.example.contact_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact_app.R
import com.example.contact_app.data.model.User
import com.example.contact_app.data.model.UserPrivider

import com.example.contact_app.databinding.FragmentContactListBinding

class ContactListFragment : Fragment() {
    companion object {
        var mapOfContact = mutableMapOf<String,User>()

        var findFavoritePosition = mutableListOf<Int>()
        var favoriteList = mutableListOf<User>()  //!!!!!!!!!!!!!!!!!!!! fragment 에 정의하니 mypage fragment로 이동하고 다시오면 favoritelist가 초기화 되는 문제 model에서 정의해야 할 듯
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
        for (x in 0 until UserPrivider.users.size) {
            mapOfContact.put(UserPrivider.users[x].phoneNumber,UserPrivider.users[x])
        }// 전체 연락처 리스트의 정보를 키값을 핸드폰번호로 한 map 에 저장

        val adapterOfContactList = MyAdapter(UserPrivider.users,1)
        val adapterOfFavoriteList = MyAdapter(favoriteList,2) // contact list 에서 좋아요 버튼을 눌러추가한 데이터들
        binding.contactListView.adapter = adapterOfContactList

        binding.favoriteListView.adapter = adapterOfFavoriteList
        binding.contactListView.layoutManager = LinearLayoutManager(context)
        binding.favoriteListView.layoutManager = LinearLayoutManager(context)
        adapterOfContactList.clickToMypage = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                val bundle = Bundle()
                bundle.putInt("key", position)
                val MyPageDataSent =
                    MyPageFragment.newInstance(bundle) // onClick함수를 adapter에 적용하여 mypage로 Bundle 데이터 넘김!!!!!!!!!!! mypage에 new instance companion object 생성

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_frag, MyPageDataSent)
                    .addToBackStack(null)
                    .commit() //mypage로 이동
            }
        }

        adapterOfContactList.clickToLike = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                if (UserPrivider.users[position].isFavorite == false) {
                    //UserPrivider.switchFavoriteByUser(position)//!!!!!!!!!!!!!!!!!!!!!!!!!
                    favoriteList?.add(UserPrivider.users[position]) // click 시 favoritelist에 클릭한 유저를 추가함
                    //!!!!!!!!!!!!!!!!!!!! userdata의 isfavorite 부분 var로 바꿔야될듯??
                    binding.favoriteListView.adapter?.notifyDataSetChanged()
                    binding.contactListView.adapter?.notifyDataSetChanged()


                }
            }


        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}
