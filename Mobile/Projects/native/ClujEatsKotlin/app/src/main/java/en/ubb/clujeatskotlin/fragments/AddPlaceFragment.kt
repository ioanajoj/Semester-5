package en.ubb.clujeatskotlin.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import en.ubb.clujeatskotlin.R
import kotlinx.android.synthetic.main.add_place_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class AddPlaceFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_place_fragment)

        setFABListener()
        setScheduleListeners()
    }


    private fun setFABListener() {
        fabConfirm.setOnClickListener{
            val replyIntent = Intent()
            if (TextUtils.isEmpty(nameTextField.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = nameTextField.text.toString()
                val address = addressTextField.text.toString()
                val photo = "https://syndlab.com/files/view/5db9b150252346nbDR1gKP3OYNuwBhXsHJerdToc5I0SMLfk7qlv951730.jpeg"
//                val photo = ""
                replyIntent.putExtra(NAME, name)
                replyIntent.putExtra(ADDRESS, address)
                replyIntent.putExtra(PHOTO, photo)
                setResult(Activity.RESULT_OK, replyIntent)
                Toast.makeText(applicationContext,
                    "The place will only be saved remotely after a connection to the internet is established",
                    Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }

    companion object {
        const val NAME = "name"
        const val ADDRESS = "address"
        const val PHOTO = "photo"
    }

    @SuppressLint("SimpleDateFormat")
    private fun setScheduleListeners() {
        // MONDAY
        mondayTime1.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                mondayTime1.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        mondayTime2.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                mondayTime2.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        mondaySwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mondayTime1.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                mondayTime2.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                mondayTime1.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                mondayTime2.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        // TUESDAY
        tuesdayTime1.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                tuesdayTime1.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        tuesdayTime2.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                tuesdayTime2.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        tuesdaySwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tuesdayTime1.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                tuesdayTime2.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                tuesdayTime1.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                tuesdayTime2.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        // WEDNESDAY
        wednesdayTime1.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                wednesdayTime1.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        wednesdayTime2.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                wednesdayTime2.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        wednesdaySwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                wednesdayTime1.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                wednesdayTime2.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                wednesdayTime1.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                wednesdayTime2.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        // THURSDAY
        thursdayTime1.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                thursdayTime1.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        thursdayTime2.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                thursdayTime2.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        thursdaySwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                thursdayTime1.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                thursdayTime2.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                thursdayTime1.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                thursdayTime2.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        // FRIDAY
        fridayTime1.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                fridayTime1.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        fridayTime2.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                fridayTime2.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        fridaySwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                fridayTime1.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                fridayTime2.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                fridayTime1.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                fridayTime2.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        // SATURDAY
        saturdayTime1.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                saturdayTime1.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        saturdayTime2.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                saturdayTime2.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        saturdaySwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saturdayTime1.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                saturdayTime2.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                saturdayTime1.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                saturdayTime2.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        // SUNDAY
        sundayTime1.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                sundayTime1.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        sundayTime2.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                sundayTime2.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        sundaySwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sundayTime1.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                sundayTime2.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                sundayTime1.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                sundayTime2.paintFlags = mondayTime1.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }


}