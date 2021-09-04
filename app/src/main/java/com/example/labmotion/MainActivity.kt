package com.example.labmotion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var homeFrag: HomeFragment?= null

        // home
        supportFragmentManager.commit {
            if (homeFrag == null) homeFrag =  HomeFragment.newInstance("","")
            if (homeFrag!!.isAdded) {
                show(homeFrag!!);
            } else {
                add(R.id.container_main, homeFrag!!, "HOME");
            }
        }

        // blank
        supportFragmentManager.commit {
            if (homeFrag != null && !homeFrag!!.isHidden) {
//                hide(homeFrag!!);
                addToBackStack("HOME");
            }

            val blankFrag= BlankFragment()
            add(R.id.container_main, blankFrag, "BLANK"); // 使用 add 方法。
        }
    }
}