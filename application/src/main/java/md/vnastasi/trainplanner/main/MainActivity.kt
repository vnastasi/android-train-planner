package md.vnastasi.trainplanner.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import md.vnastasi.trainplanner.R
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
    }
}
