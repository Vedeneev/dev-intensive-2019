package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        val parts: List<String>? = fullName?.split(" ")
        println(parts)
        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)
        println(firstName)
        println(lastName)
        when (fullName) {
            "" -> {
                firstName = null
                lastName = null
            }
            " " -> {
                firstName = null
                lastName = null
            }
        }

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val rl = listOf(
            "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п",
            "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"
        )
        val el = listOf(
            "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "i", "k", "l", "m", "n", "o", "p",
            "r", "s", "t", "u", "f", "h", "c", "ch", "sh", "sh", "", "i", "", "e", "yu", "ya"
        )
        var payloadTransliterated = payload
        for (letter in payload) {

            if (letter.toString() == " ") {
                payloadTransliterated = payloadTransliterated.replace(letter.toString(), divider)
            } else {
                if (rl.indexOf(letter.toLowerCase().toString()) >= 0) {
                    if (letter.isUpperCase()) {
                        payloadTransliterated =
                            payloadTransliterated.replace(
                                letter.toString(),
                                el[rl.indexOf(letter.toLowerCase().toString())].toUpperCase()
                            )
                    } else {
                        payloadTransliterated =
                            payloadTransliterated.replace(
                                letter.toString(),
                                el[rl.indexOf(letter.toString())]
                            )
                    }
                }

            }
        }
        return payloadTransliterated
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var initials: String? = null
        if (firstName != null && firstName != "" && firstName != " ") {
            initials = firstName[0].toString().toUpperCase()
            if (lastName != null && lastName != "" && lastName != " ") {
                initials += lastName[0].toString().toUpperCase()
            }
        } else {
            if (lastName != null && lastName != "" && lastName != " ") {
                initials = lastName[0].toString().toUpperCase()
            }
        }
        return initials
    }
}