package com.example.bannerapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.bannerapp.adapter.ImageAdapter
import com.example.bannerapp.databinding.ActivityMainBinding
import java.lang.reflect.Field


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imagesList: ArrayList<ImageItem>
    private lateinit var adapter: ImageAdapter
    private lateinit var dots: Array<ImageView>
    private lateinit var params: LinearLayout.LayoutParams
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imagesList = getData()
        setUpAdapter()
       // createDots()
        initClickListeners()

        val marginPageTransformer =
            MarginPageTransformer(resources.getDimensionPixelOffset(R.dimen.viewpager_margin))
        binding.viewPager.setPageTransformer(marginPageTransformer)

        // Optional: Reduce the offscreen page limit to ensure smooth scrolling
        binding.viewPager.offscreenPageLimit = 4

        // Add padding and margin to create the clipped effect
        val recyclerView = binding.viewPager.getChildAt(0) as RecyclerView
        recyclerView.clipToPadding = false
        recyclerView.setPadding(70, 0, 70, 0) // Adjust padding as needed

        binding.viewPager.setCurrentItem(2, true)

        startAutoScroll()

    }


    private fun getData(): ArrayList<ImageItem> {
        val imagesList = arrayListOf<ImageItem>()
        imagesList.add(ImageItem(R.drawable.n1))
        imagesList.add(ImageItem(R.drawable.nature))
        imagesList.add(ImageItem(R.drawable.nature1))
        imagesList.add(ImageItem(R.drawable.nature2))
        return imagesList
    }

    private fun setUpAdapter() {
        adapter = ImageAdapter(imagesList,{ imageItem -> handleClick(imageItem)})
        binding.viewPager.adapter = adapter
    }

    private fun handleClick(imageItem: ImageItem) {
        Toast.makeText(this, "clicked on ${imagesList.indexOf(imageItem)}", Toast.LENGTH_SHORT).show()
    }

    private fun createDots() {

//        params = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        ).apply {
//            setMargins(8, 0, 8, 0);
//        }
//
//        binding.linearLayout.removeAllViews()
//        dots = Array(imagesList.size) { ImageView(this) }
//        for (i in 0..dots.size - 1) {
//            dots[i].setImageResource(R.drawable.inactive_dot)
//            binding.linearLayout.addView(dots[i], params)
//        }
//        dots[0].setImageResource(R.drawable.active_dot)
    }


    private fun initClickListeners() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //changeColor(position)
                Toast.makeText(this@MainActivity, "clicked on position $position", Toast.LENGTH_SHORT).show()
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    private fun changeColor(position: Int) {

        for (i in 0..imagesList.size - 1) {
            if (i != position) {
                dots[i].setImageResource(R.drawable.inactive_dot)
            } else {
                dots[i].setImageResource(R.drawable.active_dot)
            }
        }
    }

    private fun startAutoScroll() {

        val runnable = object : Runnable {
            override fun run() {
                if (currentPage == adapter.itemCount) {
                   currentPage = 0
                }
                binding.viewPager.post {
                    binding.viewPager.setCurrentItem(currentPage++, true)
                }
                handler.postDelayed(this, 3000) // Adjust delay as needed
            }
        }
        handler.post(runnable)
    }

    private fun changePagerScroller() {
        try {
            var mScroller: Field? = null
            mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.setAccessible(true)
            val scroller = ViewPagerScroller(binding.viewPager.getContext())
            mScroller.set(binding.viewPager, scroller)
        } catch (e: Exception) {
            Log.e("error", "error of change scroller ", e)
        }
    }

}


