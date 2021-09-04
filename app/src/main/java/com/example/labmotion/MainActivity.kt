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

        var blankFrag: BlankFragment?= null
        if (blankFrag == null) blankFrag =  BlankFragment.newInstance() // 如果 home fragment 還沒創建過，創建 fragment，

        supportFragmentManager.commit {
            if (blankFrag.isAdded) { // 如果 home fragment 已經被 add 過，
                show(blankFrag); // 顯示它。
            } else { // 反之，
                add(R.id.container_main, blankFrag, "BLANK"); // 使用 add 方法。
            }
        }
    }
}