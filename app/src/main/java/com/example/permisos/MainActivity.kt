package com.example.permisos

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.permisos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnFoto.setOnClickListener { chequeoPermisos() }
    }

    companion object {
        val COG_PERMISO_CAMARA = 111
    }

    private fun chequeoPermisos() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //Permiso no aceptado
            solicitarPermisoCamara()
        } else {
            abrirCamara()
        }
    }

    private fun abrirCamara() {
        Toast.makeText(this, "Abrir camara", Toast.LENGTH_SHORT).show()
    }

    private fun solicitarPermisoCamara() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, "Permisos rechazados", Toast.LENGTH_SHORT).show()
        } else {
            //perdir permisos
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                COG_PERMISO_CAMARA
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == COG_PERMISO_CAMARA) {//permisos
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara()
            } else {
                Toast.makeText(this, "Permisos rechazados primera vez", Toast.LENGTH_SHORT).show()
            }
        }
    }
}