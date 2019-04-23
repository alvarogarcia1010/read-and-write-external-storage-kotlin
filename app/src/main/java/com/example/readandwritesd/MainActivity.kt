package com.example.readandwritesd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    private val filepath = "Documents"
    private var externalFile: File?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_save.setOnClickListener{
            externalFile = File(getExternalFilesDir(filepath), et_file_name.text.toString())

            try{
                val fileOutPutStream = FileOutputStream(externalFile)
                fileOutPutStream.write(et_content.text.toString().toByteArray())
                fileOutPutStream.close()
            }catch (e: IOException){
                e.printStackTrace()
            }

            et_content.setText("")

            Toast.makeText(applicationContext,getString(R.string.save_succes), Toast.LENGTH_SHORT).show()

        }

        btn_read.setOnClickListener{
            externalFile = File(getExternalFilesDir(filepath), et_file_name.text.toString())

            val filename = et_file_name.text.toString()
            externalFile = File(getExternalFilesDir(filepath),filename)
            if(filename.trim()!=""){
                var fileInputStream = FileInputStream(externalFile)
                var inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader= BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()

                et_content.setText(stringBuilder.toString())

                Toast.makeText(applicationContext,getString(R.string.read_success), Toast.LENGTH_SHORT).show()
            }
        }

        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            btn_save.isEnabled = false
        }
    }

    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return (Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState) }


    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return (Environment.MEDIA_MOUNTED == extStorageState) }
}
