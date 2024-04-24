package com.example.contact_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.contact_app.databinding.FragmentMyPageBinding

private const val ARG_PARAM1 = "param1"
class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {

        //mypage에 newinstance 메소드를 contactlistfragment의 데이터를 넘겨받기 위해 추가
        @JvmStatic
        fun newInstance(param1: Bundle) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                    putBundle(ARG_PARAM1, param1)

                }
            }
    }
}