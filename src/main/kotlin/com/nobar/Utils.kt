package com.nobar

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.URI
import java.nio.charset.Charset
import java.util.*


object Utils {

    private var properties: Properties? = null
    //    private val caminho = "/Users/lucas/IdeaProjects/noDesvio/src/main/resources/"
//    private val caminho = "./src/main/resources/"
    private val caminho = "./"
    fun loadProperties() {
        try {
            properties = Properties()
            val fis = FileInputStream(caminho + "config.properties")

            val isr = InputStreamReader(fis, Charset.forName("UTF-8"))
                properties?.load(isr)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getProperties(properties: String): String {
        loadProperties()
        return Utils.properties?.getProperty(properties).toString()
    }

    fun getURL(caminho: String) : String {
        return URI(getProperties("pageURL") + getProperties(caminho)).toString()
    }

    fun getProcessos(): List<String> {
        val lineList = mutableListOf<String>()
        File(caminho + "processos.txt").useLines { lines -> lines.forEach { lineList.add(it.trim()) }}
        return lineList
    }
}