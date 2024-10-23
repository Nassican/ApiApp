package com.nassican.apiapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nassican.apiapp.data.AppModule
import com.nassican.apiapp.data.models.Result
import com.nassican.apiapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentPage = 1
    private val pageSize = 20
    private var isLoading = false

    private val adapter = GOTRecyclerView(
        onItemClick = { character -> showCharacterDetail(character) },
        onLoadMore = { loadMoreCharacters() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadCharacters()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadCharacters() {
        if (isLoading) return
        isLoading = true
        lifecycleScope.launch {
            try {
                val characters = AppModule.provideCharacters().getCharacters(currentPage, pageSize)
                adapter.addData(characters)
                currentPage++
            } catch (e: Exception) {
                println("Error: $e")
            } finally {
                isLoading = false
            }
        }
    }

    private fun loadMoreCharacters() {
        loadCharacters()
    }

    private fun showCharacterDetail(character: Result) {
        val fragment = CharacterDetailFragment.newInstance(character)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}