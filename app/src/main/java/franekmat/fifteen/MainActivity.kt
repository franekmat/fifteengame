package franekmat.fifteen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
//         sample_text.text = stringFromJNI()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object
    {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    fun startEasy(view: View)
    {
        val intent = Intent(this@MainActivity, easy::class.java)

        startActivity(intent)
    }

    fun startMedium(view: View)
    {
        val intent = Intent(this@MainActivity, medium::class.java)

        startActivity(intent)
    }

    fun startHard(view: View)
    {
        val intent = Intent(this@MainActivity, hard::class.java)

        startActivity(intent)
    }

    fun openHelp(view: View)
    {
        val intent = Intent(this@MainActivity, help::class.java)

        startActivity(intent)
    }

    fun openHighScores(view: View)
    {
        val intent = Intent(this@MainActivity, HighScores::class.java)

        startActivity(intent)
    }
}
