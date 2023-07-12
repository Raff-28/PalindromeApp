package com.rafif.suitmediaapp.ui.mainscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.rafif.suitmediaapp.databinding.ActivityMainBinding
import com.rafif.suitmediaapp.ui.secondscreen.SecondActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            binding.btnNext.setOnClickListener {
                val name = binding.edtName.text.toString()
                if (name.isNotEmpty()) {
                    val intent = Intent(this@MainActivity, SecondActivity::class.java)
                    intent.putExtra("name", name)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "Please enter a name", Toast.LENGTH_SHORT).show()
                }
            }
            btnCheck.setOnClickListener {
                val text = binding.edtPalindrome.text.toString()
                if (text.isNotEmpty()) {
                    val normalizedText = text.filter { it.isLetterOrDigit() }.lowercase()
                    val isPalindrome = normalizedText == normalizedText.reversed()
                    val message = if (isPalindrome) "isPalindrome" else "not palindrome"
                    AlertDialog.Builder(this@MainActivity)
                        .setMessage(message)
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                } else {
                    Toast.makeText(this@MainActivity, "Please enter a word to be checked", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}