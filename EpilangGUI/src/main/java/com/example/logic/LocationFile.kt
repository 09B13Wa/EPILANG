package com.example.logic

import java.io.File

/**
 * LocationFile class is used to store the locations of files.
 * It keeps track of the location of files as well as an associated name
 * for easy access and configuration.
 */
class LocationFile {
    private var locationVal: String = ""
    private var fileNameVal: String = ""
    private var locationsVal: MutableMap<String, String> = mutableMapOf()
    private var numberOfLocationsVal: Int = 0
    private var filePathVal: String = ""
    private var file: File = File("")
    private var locationLines: MutableMap<String, Int> = mutableMapOf()

    /**
     * The location of the location file.
     * @return the location of the location file.
     * The setter is private.
     */
    var location: String
        get() = locationVal
        private set(value) {
            locationVal = value
        }

    /**
     * The name of the location file.
     * @return the name of the location file.
     * The setter is private.
     */
    var fileName: String
        get() = fileNameVal
        private set(value) {
            fileNameVal = value
        }

    /**
     * The locations in the location file.
     * @return a map of the name of files and their locations.
     * The setter is private.
     */
    var locations: MutableMap<String, String>
        get() = locationsVal
        private set(value) {
            locationsVal = value
        }

    /**
     * The complete path of the location file.
     * @return the complete path of the location file.
     * The setter is private.
     */
    var filePath: String
        get() = filePathVal
        private set(value) {
            filePathVal = value
        }

    /**
     * The number of locations in the location file.
     * @return the number of locations in the location file.
     * The setter is private.
     */
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
        numberOfLocationsVal = 0
    }

    /**
     * A constructor for the LocationFile class.
     *
     * Sets the location to "",
     *
     * the file name to "",
     *
     * the locations to an empty map,
     *
     * the number of locations to 0,
     *
     * the file path to "",
     *
     * and the file to an empty file.
     */
    constructor() {
        location = ""
        fileName = ""
        locations = mutableMapOf()
        filePath = ""
        locationLines = mutableMapOf()
        file = File("")
        numberOfLocationsVal = 0
    }

    /**
     * A constructor for the LocationFile class.
     * Initializes the location file with the given location and file name.
     * Creates the file if it doesn't exist.
     * Loads all data from the file into the fields
     * @param location the location of the location file.
     * This is not the full path of the file but just the path to its directory.
     * @param fileName the name of the location file.
     */
    constructor(location: String, fileName: String) {
        initializeFile(location, fileName)
        initializeLocations()
    }

    /**
     * A constructor for the LocationFile class.
     * Initializes the location file with the given full path of the file.
     * Creates the file if it doesn't exist.
     * Loads all data from the file into the fields
     * @param filePath the full path of the location file.
     */
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

    /**
     * Adds a location to the location file.
     * @param key the name of the location.
     * @param value the location path of the location.
     */
    fun addLocation(key: String, value: String) {
        locations[key] = value
        locationLines[key] = numberOfLocations
        file.appendText("$key:$value\n")
        numberOfLocations++
    }

    /**
     * Removes a location from the location file.
     * @param key the name of the location.
     */
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