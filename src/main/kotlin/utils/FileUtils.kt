package utils

fun readFile(filename: String) = {}::class.java.classLoader.getResource(filename)!!.readText()

fun readNotEmptyLines(filename: String) = readFile(filename).split("\n").filter { it.isNotBlank() }