package com.guzman.rene.cazarpatos

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword: EditText
    lateinit var checkBoxRecordarme: CheckBox
    lateinit var buttonLogin: Button
    lateinit var buttonNewUser: Button
    lateinit var mediaPlayer: MediaPlayer
    private lateinit var fileHandler: FileHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        
        //Inicialización de variables
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        checkBoxRecordarme = findViewById(R.id.checkBoxRecordarme)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonNewUser = findViewById(R.id.buttonNewUser)

        // ==============================================================
        //  USANDO EncryptedSharedPreferencesManager
        // ==============================================================
        fileHandler = EncryptedSharedPreferencesManager(this)

        // Lee y establece las credenciales guardadas
        readAndSetCredentials()

        //Eventos clic
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            //Validaciones de datos requeridos y formatos
            if (!validateRequiredData())
                return@setOnClickListener

            // Guardar o limpiar credenciales según el CheckBox
            if (checkBoxRecordarme.isChecked) {
                fileHandler.saveCredentials(email, password)
            } else {
                fileHandler.clearCredentials()
            }

            //Si pasa validación de datos requeridos, ir a pantalla principal
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.EXTRA_LOGIN, email)
            startActivity(intent)
            finish()
        }
        buttonNewUser.setOnClickListener {

        }
        mediaPlayer = MediaPlayer.create(this, R.raw.title_screen)
        mediaPlayer.start()
    }

    private fun readAndSetCredentials() {
        val credentials = fileHandler.readCredentials()
        if (credentials != null) {
            editTextEmail.setText(credentials.first)
            editTextPassword.setText(credentials.second)
            checkBoxRecordarme.isChecked = true
        }
    }

    private fun validateRequiredData(): Boolean {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        if (email.isEmpty()) {
            editTextEmail.error = getString(R.string.error_email_required)
            editTextEmail.requestFocus()
            return false
        }
        if (password.isEmpty()) {
            editTextPassword.error = getString(R.string.error_password_required)
            editTextPassword.requestFocus()
            return false
        }
        if (password.length < 3) {
            editTextPassword.error = getString(R.string.error_password_min_length)
            editTextPassword.requestFocus()
            return false
        }
        return true
    }

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }
}
