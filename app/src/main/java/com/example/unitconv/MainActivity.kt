package com.example.unitconv

import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.unitconv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val conversions = mapOf("CelsiusKelvin" to {c: Double -> c+273.15}, "CelsiusFahrenheit" to {c: Double -> (1.8*c)+32}, "CelsiusRankine" to {c: Double -> (c+273.15)*1.8},
        "KelvinCelsius" to {k: Double -> k-273.15}, "KelvinFahrenheit" to {k: Double -> (1.8*(k-273.15))+32}, "KelvinRankine" to {k: Double -> k*1.8},
        "FahrenheitCelsius" to {f: Double -> (f-32)/1.8}, "FahrenheitKelvin" to {f: Double -> ((f-32)/1.8)+273.15}, "FahrenheitRankine" to {f: Double -> f-32+(273.15*1.8)},
        "RankineCelsius" to {r: Double -> (r/1.8)-273.15}, "RankineKelvin" to {r: Double -> r/1.8}, "RankineFahrenheit" to {r: Double -> r-(273.15*1.8)+32},
        "CentimetreInch" to {c: Double -> c/2.54}, "CentimetreMetre" to {c: Double -> c/100}, "CentimetreFeet" to {c: Double -> c/(2.54*12)},
        "InchCentimetre" to {i: Double -> i*2.54}, "InchMetre" to {i: Double -> i*2.54/100}, "InchFeet" to {i: Double -> i/12},
        "MetreCentimetre" to {m: Double -> m*100}, "MetreInch" to {m: Double -> m*100/2.54}, "MetreFeet" to {m: Double -> m*100/(2.54*12)},
        "FeetCentimetre" to {f: Double -> f*12*2.54}, "FeetInch" to {f: Double -> f*12}, "FeetMetre" to {f: Double -> f*12*2.54/100},
        "KilogramPound" to {k: Double -> k*2.20462}, "KilogramOunce" to {k: Double -> k*35.274}, "KilogramGram" to {k: Double -> k*1000},
        "PoundKilogram" to {p: Double -> p/2.20462}, "PoundOunce" to {p: Double -> p*16}, "PoundGram" to {p: Double -> p*453.59237},
        "OunceKilogram" to {o: Double -> o/35.274}, "OuncePound" to {o: Double -> o/16}, "OunceGram" to {o: Double -> o*28.34952},
        "GramKilogram" to {g: Double -> g/1000}, "GramPound" to {g: Double -> g/453.59237}, "GramOunce" to {g: Double -> g/28.34952},
        "metre/skilometre/hr" to {m: Double -> m*3.6}, "metre/smiles/hr" to {m: Double -> m*2.23694}, "metre/sknot" to {m: Double -> m*1.94384},
        "kilometre/hrmetre/s" to {k: Double -> k/3.6}, "kilometre/hrmiles/hr" to {k: Double -> k/1.60934}, "kilometre/hrknot" to {k: Double -> k/1.852},
        "miles/hrmetre/s" to {M: Double -> M/2.237}, "miles/hrkilometre/hr" to {M: Double -> M*1.60934}, "miles/hrknot" to {M: Double -> M/1.15078})

    var prop_to_conv = "Temperature"
    var unit1 = "Celsius"
    var unit2 = "Celsius"
    var num1 = "0.0".toDoubleOrNull()
    var num2 = "0.0000".toDoubleOrNull()
    val temperature = arrayOf("Celsius", "Kelvin", "Fahrenheit", "Rankine")
    val length = arrayOf("Centimetre", "Inch", "Metre", "Feet")
    val weight = arrayOf("Kilogram", "Pound", "Ounce", "Gram")
    val speed = arrayOf("metre/s", "kilometre/hr", "miles/hr", "knot")
    lateinit var unit_list: Array<String>
    lateinit var aa: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.number1.setText(num1.toString())

        binding.switch2.textOn = "Convert"
        binding.switch2.textOff = "Check"
        binding.switch2.setOnClickListener {
            if (binding.switch2.isChecked) {
                binding.number2.visibility = View.INVISIBLE
                binding.editTextNumber.visibility = View.VISIBLE
                binding.button.text = "Check"
                binding.number1.setText("0.0")
                binding.editTextNumber.setText("0.0")
                binding.root.setBackgroundResource(R.color.backgroundColor)
            }
            else {
                binding.number2.visibility = View.VISIBLE
                binding.editTextNumber.visibility = View.INVISIBLE
                binding.button.text = "Convert"
                binding.number1.setText("0.0")
                binding.root.setBackgroundResource(R.color.backgroundColor)
            }
        }

        ArrayAdapter.createFromResource(this, R.array.prop, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
        with (binding.spinner) {
            prompt = "Select property"
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.number1.setText("0.0")
                binding.number2.text = "0.0000"
                binding.root.setBackgroundResource(R.color.backgroundColor)
                prop_to_conv = parent!!.getItemAtPosition(position).toString()

                unit_list = when (prop_to_conv) {
                    "Temperature" -> temperature
                    "Length" -> length
                    "Weight" -> weight
                    "Speed" -> speed
                    else -> emptyArray()
                }

                aa = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, unit_list)
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                with (binding.spinner2) {
                    adapter = aa
                    prompt = "Select unit"
                }
                binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        unit1 = parent?.getItemAtPosition(position).toString()
                        binding.root.setBackgroundResource(R.color.backgroundColor)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }

                aa = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, unit_list)
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                with (binding.spinner3) {
                    adapter = aa
                    prompt = "Select unit"
                }
                binding.spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        unit2 = parent?.getItemAtPosition(position).toString()
                        binding.root.setBackgroundResource(R.color.backgroundColor)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.button.setOnClickListener {
            if (binding.switch2.isChecked) {
                num1 = binding.number1.text.toString().toDoubleOrNull()
                num2 = binding.editTextNumber.text.toString().toDoubleOrNull()
                if ((num1==null) || (num2==null)) {
                    Toast.makeText(this, "Field can't be empty", Toast.LENGTH_LONG).show()
                }
                else {
                    if ((num2 == num1?.let { conversions[unit1+unit2]?.invoke(it) }) || ((num1==num2) && (unit1==unit2))) {
                        Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show()
                        binding.root.setBackgroundResource(R.color.green)
                    }
                    else {
                        Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show()
                        binding.root.setBackgroundResource(R.color.red)
                        num2 = if (unit1==unit2) {
                            num1
                        } else {
                            num1?.let { conversions[unit1+unit2]?.invoke(it) }
                        }
                        binding.editTextNumber.setText(num2.toString())
                        val v = getSystemService(VIBRATOR_SERVICE) as Vibrator
                        v.vibrate(500)
                    }
                }
            }
            else {
                convert()
            }
        }
    }

    private fun convert() {
        num1 = binding.number1.text.toString().toDoubleOrNull()
        if (num1==null) {
            Toast.makeText(this, "Field can't be empty", Toast.LENGTH_LONG).show()
            binding.number2.text = ""
        }
        else {
            if (num1!!<0) {
                Toast.makeText(this, prop_to_conv + " can't be negative", Toast.LENGTH_LONG).show()
            }
            else {
                if (unit1==unit2) {
                    num2 = num1
                    Toast.makeText(this, "Same units", Toast.LENGTH_SHORT).show()
                }
                else{
                    num2 = num1?.let { conversions[unit1+unit2]?.invoke(it) }
                    Toast.makeText(this, "Converted!", Toast.LENGTH_SHORT).show()
                }
                binding.number2.text = "%.4f".format(num2)
            }
        }
    }
}

