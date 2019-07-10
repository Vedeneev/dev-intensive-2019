package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        val parts: List<String>? = fullName?.split(" ")
println(parts)
        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)
        println(firstName)
        println(lastName)
        when(fullName){
            ""->{
                firstName=null
                lastName=null
            }
            " "->{
                firstName=null
                lastName=null
            }
        }

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        return "transliteration not implemented"
//        TODO("not implemented")
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return "toInitials not implemented"
//        TODO("not implemented")
    }
}