package com.rafif.suitmediaapp.ui.secondscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.rafif.suitmediaapp.databinding.ActivitySecondBinding
import com.rafif.suitmediaapp.ui.thirdscreen.ThirdActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var chooseUserLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val name = intent.getStringExtra("name")
        chooseUserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedUserName = result.data?.getStringExtra("selectedUserName")
                binding.tvSelectedUsername.text = selectedUserName
            }
        }

        binding.apply {
            binding.tvName.text = name
            btnBack.setOnClickListener {
                onBackPressed()
            }
            btnChooseUser.setOnClickListener {
                val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
                chooseUserLauncher.launch(intent)
            }
        }
    }
}