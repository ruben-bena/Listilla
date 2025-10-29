package com.ruben.listilla

import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.activity.R
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.view.ViewGroup
import android.graphics.BitmapFactory
import android.widget.ImageView
import android.widget.TextView
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    // Model: ArrayList de Record (intents=puntuació, nom)
    class Record(var intents: Int, var nom: String)
    var records: ArrayList<Record> = ArrayList<Record>()

    // ArrayAdapter serà l'intermediari amb la ListView
    lateinit var adapter: ArrayAdapter<Record>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Afegim alguns exemples
        records.add(Record(33, "Manolo"))
        records.add(Record(12, "Pepe"))
        records.add(Record(42, "Laura"))

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = object : ArrayAdapter<Record>(this,R.layout.list_item,records)
        {
            override fun getView(pos: Int, convertView: View?, container: ViewGroup): View {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                var convertView = convertView
                if (convertView == null) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false)
                }
                // pintem imatge
                val bitmap = BitmapFactory.decodeStream( assets.open("ieti_logo.png") )
                convertView.findViewById<ImageView>(R.id.imageView).setImageBitmap( bitmap )
                // "Pintem" valors (quan es refresca)
                convertView.findViewById<TextView>(R.id.nom).text = getItem(pos)?.nom
                convertView.findViewById<TextView>(R.id.intents).text = getItem(pos)?.intents.toString()
                return convertView
            }
        }

        // busquem la ListView i li endollem l'ArrayAdapter
        val lv = findViewById<ListView>(R.id.recordsView)
        lv.setAdapter(adapter)

    }
}