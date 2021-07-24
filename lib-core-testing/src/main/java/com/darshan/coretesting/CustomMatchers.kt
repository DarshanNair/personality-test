package com.darshan.coretesting

import android.view.View
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class CustomMatchers {

    companion object {
        fun withItemCount(count: Int): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("RecyclerView with item count: $count")
                }

                override fun matchesSafely(item: RecyclerView?): Boolean {
                    return item?.adapter?.itemCount == count
                }
            }
        }

        fun clickRadioButtonWithTitle(radioContainerID: Int, title: String): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified title."
                }

                override fun perform(uiController: UiController?, view: View) {
                    val radioGroup =
                        view.findViewById<FrameLayout>(radioContainerID).getChildAt(0) as RadioGroup
                    for (i in 0 until radioGroup.childCount) {
                        val radio = radioGroup.getChildAt(i)
                        if (radio is RadioButton && radio.text == title) {
                            radio.performClick()
                        }
                    }
                }
            }
        }
    }

}
