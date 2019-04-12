package com.cts.colortracktabdemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_page.*

class PageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            when (it.getInt(TYPE_PAGE)) {
                TYPE_UNFINISHED -> page_text.text = "已接订单"
                TYPE_FINISHED -> page_text.text = "完成订单"
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TYPE_PAGE = "PageFragment_type"

        const val TYPE_UNFINISHED = 101 //未完成的
        const val TYPE_FINISHED = 102 //已完成的

        @JvmStatic
        fun newInstance(type: Int) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putInt(TYPE_PAGE, type)
                }
            }
    }
}