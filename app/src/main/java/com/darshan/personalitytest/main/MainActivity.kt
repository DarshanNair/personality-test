package com.darshan.personalitytest.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.darshan.core.DeviceManager
import com.darshan.personalitytest.R
import com.darshan.personalitytest.category.view.CategoryListFragment
import com.darshan.personalitytest.question.view.QuestionsFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var deviceManager: DeviceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!deviceManager.isTablet) {
            if (savedInstanceState == null) {
                supportFragmentManager.commit {
                    replace(R.id.container, CategoryListFragment())
                }
            }
        } else {
            if (savedInstanceState == null) {
                supportFragmentManager.commit {
                    replace(R.id.container, CategoryListFragment())
                    replace(R.id.child_container, QuestionsFragment())
                }
            }
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
        fragmentDispatchingAndroidInjector

}