package com.rh.mygoalsapp.util

object Constants {

    const val dbName:String = "mygoals_database"
    const val addressRestfulBaseURL = "https://viacep.com.br/ws/"

    fun editTextMaxChars(label:String) : Int {
        return when(label) {

            "Nome:" -> 70
            "Email:" -> 254
            "Senha:" -> 127
            "Data de Nascimento:" -> 10
            "CEP:" -> 9
            "Logradouro:" -> 100
            "Numero:" -> 20
            "Complemento:" -> 50
            "Bairro:" -> 60
            "Cidade:" -> 60

            else -> 100
        }
    }

    fun monthNameByMonthInt(month:Int) : String {
        return when (month) {
            1 -> "Janeiro"
            2 -> "Fevereiro"
            3 -> "Março"
            4 -> "Abril"
            5 -> "Maio"
            6 -> "Junho"
            7 -> "Julho"
            8 -> "Agosto"
            9 -> "Setembro"
            10 -> "Outubro"
            11 -> "Novembro"
            else -> "Dezembro"
        }
    }
}