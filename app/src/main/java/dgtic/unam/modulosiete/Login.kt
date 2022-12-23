package dgtic.unam.modulosiete

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import dgtic.unam.modulosiete.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        login()
        validatePreviousSession()
    }

    private fun validatePreviousSession() {
        val preferences = getSharedPreferences(getString(R.string.preference_file_path), Context.MODE_PRIVATE)
        var email: String? = preferences.getString("email", null)
        if(email!=null){
            successfulLoginStep(email)
        }

    }

    private fun login(){
        //register a new user
        binding.updateUser.setOnClickListener{
            if(!binding.username.text.toString().isEmpty() &&
                !binding.password.text.isEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                ).addOnCompleteListener {
                    if(it.isComplete){
                        //redirect user to the home activity
                        Toast.makeText(binding.signin.context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        successfulLoginStep(it.result?.user?.email?:"")
                    } else {
                        //error
                        alertMessage()
                    }
                }
            }
        }
        //login for a previous register user
        binding.loginbtn.setOnClickListener {
            if(!binding.username.text.toString().isEmpty() &&
                !binding.password.text.isEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                ).addOnCompleteListener {
                    if(it.isSuccessful){
                        successfulLoginStep(it.result?.user?.email?: "")
                    } else{
                        alertMessage()
                    }
                }
            }
        }
    }

    private fun successfulLoginStep(email: String) {
        var pasos = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(pasos)
    }

    private fun alertMessage() {
        val bulder = AlertDialog.Builder(this)
        bulder.setTitle("Mensaje")
        bulder.setMessage("Se produjo un error. No se pudieron validar las credenciales. Intente nuevamente")
        bulder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = bulder.create()
        dialog.show()
    }
}