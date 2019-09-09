package com.example.admin.moogle.view

import android.support.test.rule.ActivityTestRule
import android.widget.EditText
import com.example.admin.moogle.R
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField val mainActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    private var mainActivity: MainActivity? = null
    @Before
    fun setUp() {
        mainActivity = mainActivityTestRule.activity
    }
    @Test
    fun checkEditText(){
        val searchEditText: EditText?= mainActivity?.findViewById(R.id.search_edittext)
        assertNotNull(searchEditText)
    }

    @After
    fun tearDown() {
        mainActivity = null
    }
}