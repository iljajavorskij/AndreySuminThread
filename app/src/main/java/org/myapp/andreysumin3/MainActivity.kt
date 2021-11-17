package org.myapp.andreysumin3

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import org.myapp.andreysumin3.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy {
       ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.button.setOnClickListener{
            loadData()
        }

    }

    private fun loadData(){
        mBinding.progressBar.isVisible = true
        mBinding.button.isEnabled = false
        loadCity {
            mBinding.text2.text = it
            loadTemperature(it){
                mBinding.text4.text = it.toString()
                mBinding.progressBar.isVisible = false
                mBinding.button.isEnabled = true
            }
        }
    }

    private fun loadCity(callback:(String) -> Unit){
        thread {
            Thread.sleep(5000)
            runOnUiThread {//запустить на главном потоке(под капотом использует Хэндлер (проверяет если текущий поток не равен главному то вызывает hendler.post(action)
                callback.invoke("Moscow")
            }

        }
    }
    private fun loadTemperature(city :String,callback:(Int) -> Unit){
        thread {
            runOnUiThread {
                Toast.makeText(this,"data load $city",Toast.LENGTH_LONG).show()
            }
            Thread.sleep(5000)
            runOnUiThread {//данный метод создает хэндлер с лупером
                callback.invoke(88)
            }
        }
    }
}