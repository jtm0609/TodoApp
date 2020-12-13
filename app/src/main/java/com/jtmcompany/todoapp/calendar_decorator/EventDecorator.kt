package com.jtmcompany.todoapp.calendar_decorator

import android.graphics.Color
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.*

class EventDecorator(var year:String, var month:String, var day:String) : DayViewDecorator {
    val calendar= Calendar.getInstance()

    override fun shouldDecorate(calDay: CalendarDay?): Boolean {
        calDay?.copyTo(calendar)

        val mYear=calendar.get(Calendar.YEAR)
        val mMonth=calendar.get(Calendar.MONTH)
        val mDay=calendar.get(Calendar.DATE)

        return (mYear==year.toInt()) &&(mMonth==month.toInt()) && (mDay==day.toInt())
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(8f, Color.RED))

    }

}