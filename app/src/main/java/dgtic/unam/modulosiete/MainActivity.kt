package dgtic.unam.modulosiete

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicioToolsBar()
    }

    private fun inicioToolsBar(){
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.abrir, R.string.cerrar)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.unam_32)
        iniciarNavegacionView()

        //retreive data from the login activity
        var bundle:Bundle? = intent.extras
        var email:String? =  bundle?.getString("email")
        //save current session data
        val preferences =  getSharedPreferences(getString(R.string.preference_file_path),
            Context.MODE_PRIVATE).edit()
        preferences.putString("email", email)
        preferences.apply()
    }

    private fun iniciarNavegacionView() {
        val navegacionView: NavigationView = findViewById(R.id.nav_view)
        navegacionView.setNavigationItemSelectedListener(this)
        val headerView: View = LayoutInflater.from(this).inflate(R.layout.header_main, navegacionView, false)
        navegacionView.addHeaderView(headerView)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.constraint_layout -> {
                startActivity(Intent(this,ConstraintActivity::class.java))
            }
            R.id.nestedscrollview->{
                startActivity(Intent(this,NestedScrollViewActivity::class.java))
            }
            R.id.collapsing->{
                startActivity(Intent(this,CollapsingToolbarLayout::class.java))
            }
            R.id.recyclerView->{
                startActivity(Intent(this,RecyclerView::class.java))
            }
            R.id.video->{
                startActivity(Intent(this,Video::class.java))
            }
            R.id.sonido->{
                startActivity(Intent(this,Sonido::class.java))
            }
            R.id.logout->{
                val preferences = getSharedPreferences(getString(R.string.preference_file_path), Context.MODE_PRIVATE).edit()
                preferences.clear()
                preferences.apply()
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, Login::class.java))
                finish()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}