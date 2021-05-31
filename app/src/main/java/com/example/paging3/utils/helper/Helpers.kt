package com.example.paging3.utils.helper

object Helpers {

    fun convertToDateTime(dateTime: String): String {
        val arrTime = dateTime.split(" ")
        val arrDate = arrTime[0].split("-")
        val arrHour = arrTime[1].split(":")

        return StringBuilder()
            .append("${arrDate[2]} ")
            .append("${getMonth(arrDate[1])} ")
            .append("${arrDate[0]}, ")
            .append("${arrHour[0]}:${arrHour[1]} ")
            .append("WIB")
            .toString()
    }

    private fun getMonth(month: String): String {
        return when (month) {
            "01" -> "Januari"
            "02" -> "Februari"
            "03" -> "Maret"
            "04" -> "April"
            "05" -> "Mei"
            "06" -> "Juni"
            "07" -> "Juli"
            "08" -> "Agustus"
            "09" -> "September"
            "10" -> "Oktober"
            "11" -> "November"
            "12" -> "Desember"
            else -> "error"
        }
    }

}