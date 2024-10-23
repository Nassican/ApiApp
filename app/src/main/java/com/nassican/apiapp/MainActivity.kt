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
    private val adapter = GOTRecyclerView { character ->
        showCharacterDetail(character)
    }

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
        lifecycleScope.launch {
            try {
                val characters = AppModule.provideCharacters().getCharacters()
                adapter.setData(characters)
            } catch (e: Exception) {
                println("Error: $e")
            }
        }
    }

    private fun showCharacterDetail(character: Result) {
        val fragment = CharacterDetailFragment.newInstance(character)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}