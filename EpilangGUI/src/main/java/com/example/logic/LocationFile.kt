package com.example.logic

import java.io.File

class LocationFile{
    private var locationVal: String = ""
    private var fileNameVal: String = ""
    private var locationsVal: MutableMap<String, String> = mutableMapOf()
    private var numberOfLocationsVal: Int = 0
    private var filePathVal: String = ""
    private var file: File = File("")
    private var locationLines: MutableMap<String, Int> = mutableMapOf()

    var location: String
        get() = locationVal
        private set(value) {
            locationVal = value
        }

    var fileName: String
        get() = fileNameVal
        private set(value) {
            fileNameVal = value
        }

    var locations: MutableMap<String, String>
        get() = locationsVal
        private set(value) {
            locationsVal = value
        }

    var filePath: String
        get() = filePathVal
        private set(value) {
            filePathVal = value
        }

    var numberOfLocations: Int
        get() = numberOfLocationsVal
        private set(value) {
            numberOfLocationsVal = value
        }

    init {
        location = ""
        fileName = ""
        locations = mutableMapOf()
        filePath = ""
        locationLines = mutableMapOf()
        file = File("")
    }

    constructor() {
        location = ""
        fileName = ""
        locations = mutableMapOf()
        filePath = ""
        locationLines = mutableMapOf()
        file = File("")
    }

    constructor(location: String, fileName: String) {
        initializeFile(location, fileName)
        initializeLocations()
    }

    constructor(filePath: String) {
        initializeFile(filePath)
        initializeLocations()
    }

    private fun initializeFile(filePath: String) {
        val file = File(filePath)
        if (!file.exists()) {
            file.createNewFile()
        }

        this.filePath = filePath
        this.fileName = file.name
        this.location = file.parentFile.absolutePath
        this.file = file
    }

    private fun initializeFile(fileName: String, location: String) {
        val filePath = location + File.separatorChar + fileName
        initializeFile(filePath)
    }

    private fun initializeLocations() {
        val lines = file.readLines()
        var lineNumber: Int = 0
        for (line in lines) {
            val lineWithoutWhiteSpace = line.replace(" ", "")
            val split: List<String> = lineWithoutWhiteSpace.split(":")
            val numberOfSections: Int = split.size
            if (numberOfSections == 2) {
                val key: String = split[0]
                val value: String = split[1]
                locations[key] = value
                locationLines[key] = lineNumber
            }
            lineNumber++
        }

        numberOfLocations = locations.size
    }

    fun addLocation(key: String, value: String) {
        locations[key] = value
        locationLines[key] = numberOfLocations
        file.appendText("$key:$value\n")
        numberOfLocations++
    }

    fun removeLocation(key: String) {
        locations.remove(key)
        locationLines.remove(key)
        numberOfLocations--
        file.delete()
        initializeFile(location, fileName)
    }

    fun getLocation(key: String): String {
        return locations[key]!!
    }

    fun getLocationLine(key: String): Int {
        return locationLines[key]!!
    }

    fun isLocation(key: String): Boolean {
        return locations.containsKey(key)
    }
}