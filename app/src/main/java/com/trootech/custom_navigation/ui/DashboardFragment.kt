package com.trootech.custom_navigation.ui


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.trootech.custom_navigation.R


class DashboardFragment : Fragment() {

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
            }

        private val EXTRA_TEXT = "text"

        @JvmStatic
        fun createFor(screenTitle: String): DashboardFragment {
            val fragment = DashboardFragment()
        val args = Bundle()
        args.putString(EXTRA_TEXT, screenTitle)
        fragment.setArguments(args)
        return fragment
        }
    }



//    fun createFor(text: String?): DashboardFragment {
//        val fragment = DashboardFragment()
//        val args = Bundle()
//        args.putString(EXTRA_TEXT, text)
//        fragment.setArguments(args)
//        return fragment
//    }

    lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Activity) {
            mContext = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val root: ViewGroup =
            inflater.inflate(R.layout.fragment_dashboard, container, false) as ViewGroup

        val args = arguments
        val text = if (args != null) args.getString(EXTRA_TEXT) else ""
        val textView: TextView = root.findViewById(R.id.tvTitle)
        textView.text = text
        textView.setOnClickListener { v ->
            Toast.makeText(v.context, text, Toast.LENGTH_SHORT).show()
        }
//        rcVwList= root.findViewById(R.id.rv_1)
//
//        val item: ArrayList<StaticRvModel> = ArrayList()
//        item.add(StaticRvModel(R.drawable.ic_launcher_background, "Title1"))
//        item.add(StaticRvModel(R.drawable.ic_launcher_background, "Title1"))
//        item.add(StaticRvModel(R.drawable.ic_launcher_background, "Title1"))
//        item.add(StaticRvModel(R.drawable.ic_launcher_background, "Title1"))
//
//        staticRcVwAdapter=StaticAdapter(item)
//        var linerLyt= LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
//        rcVwList.layoutManager=linerLyt
//        rcVwList.adapter=staticRcVwAdapter
//
//
//
//        items.add(DynamicRvModel("Text"))
//        items.add(DynamicRvModel("Text"))
//        items.add(DynamicRvModel("Text"))
//        items.add(DynamicRvModel("Text"))
//        items.add(DynamicRvModel("Text"))
//        items.add(DynamicRvModel("Text"))
//        items.add(DynamicRvModel("Text"))
//        items.add(DynamicRvModel("Text"))
//        items.add(DynamicRvModel("Text"))
//        items.add(DynamicRvModel("Text"))
//

        return root
    }


}