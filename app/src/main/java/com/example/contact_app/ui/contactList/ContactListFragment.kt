package com.example.contact_app.ui.contactList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.contact_app.R
import com.example.contact_app.data.model.Image
import com.example.contact_app.data.model.User
import com.example.contact_app.data.model.UserProvider
import com.example.contact_app.databinding.FragmentContactListBinding
import com.example.contact_app.ui.contact.ContactDetailFragment

class ContactListFragment : Fragment() {
    companion object {
        var favoriteList = mutableListOf<User>()
        var copyContactList = mutableListOf<User>()
    }

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private lateinit var onClickListener: () -> Unit

    fun onClickListener1(onClick: () -> Unit) {
        onClickListener = onClick
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (x in 0 until UserProvider.users.size) {
            copyContactList.add(UserProvider.users[x])
        }

        val my: User = UserProvider.users[0]
        val image = my.profileImage

        when (image) {
            is Image.ImageUri -> binding.ivProfile.setImageURI(image.uri)
            is Image.ImageDrawable -> binding.ivProfile.setImageResource(image.drawable)
        }

        binding.tvUsername.text = my.name
        binding.tvPhoneNumber.text = my.phoneNumber

        binding.MyProfileView.setOnClickListener {
            onClickListener()
        }

        if (favoriteList.size == 0) {
            binding.favoriteListView.visibility = View.GONE
        }

        val adapterOfContactList = MyAdapter(copyContactList, 1)
        val adapterOfFavoriteList = MyAdapter(favoriteList, 2) // contact list 에서 좋아요 버튼을 눌러추가한 데이터들

        binding.contactListView.adapter = adapterOfContactList
        binding.favoriteListView.adapter = adapterOfFavoriteList

        binding.contactListView.layoutManager = LinearLayoutManager(context)
        binding.favoriteListView.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(requireContext(), VERTICAL)
        binding.contactListView.addItemDecoration(dividerItemDecoration)
        binding.favoriteListView.addItemDecoration(dividerItemDecoration)

        adapterOfContactList.clickToDetail = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int, type: Int) {
                val detailFragment = ContactDetailFragment.newInstance(position + favoriteList.size)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_items, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        adapterOfContactList.clickToLike = object : MyAdapter.ItemClick {
            // off to on
            override fun onClick(view: View, position: Int, type: Int) {
                if (type == 1) {
                    // copycontactlist에서 에서 아이템을 가져온다 : copycontactlist에있는 것을 copy해 list에 추가
                    binding.favoriteListView.visibility = View.VISIBLE

                    // copylist의 하트를 바꾼다 copyContactList.
                    favoriteList?.add(copyContactList[position])
                    favoriteList[favoriteList.size - 1] = favoriteList[favoriteList.size - 1].copy(
                        isFavorite = true
                    )
                    binding.favoriteListView.adapter?.notifyDataSetChanged()

                    copyContactList?.removeAt(position)
                    binding.contactListView.adapter?.notifyItemRangeRemoved(
                        position,
                        copyContactList.size - position
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun switchFavoriteByUser(index: Int) {
        UserProvider.users[index].copy(
            isFavorite = !(UserProvider.users[index].isFavorite)
        )
    }
}
