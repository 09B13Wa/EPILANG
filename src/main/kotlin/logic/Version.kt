package logic

import kotlin.math.pow

class Version: Cloneable, Comparable<Version> {
    private var majorVal: Int = 0
    private var minorVal: Int = 0
    private var buildVal: Int = -1
    private var revisionVal: Int = -1

    var major: Int
        get() {
            return majorVal
        }
        private set(value) {
            checkForNegativeNumbers(value,"major")
            majorVal = value
        }

    var minor: Int
        get() {
            return minorVal
        }
        private set(value) {
            checkForNegativeNumbers(value, "minor")
            minorVal = value
        }

    var build: Int
        get() {
            return buildVal
        }
        private set(value)  {
            if (value < -1) {
                throw IllegalArgumentException("Error: the build value can't be inferior to -1")
            }
            buildVal = value
        }

    var revision: Int
        get() {
            return revisionVal
        }
        private set(value) {
            if (value < -1) {
                throw IllegalArgumentException("Error: the revision value can't be inferior to -1")
            }
            revisionVal = value
        }

    var majorRevision: Short
        get() {
            val revisionCopy: Int = revisionVal
            val shiftedValue: Int = revisionCopy.shr(16)
            return shiftedValue.toShort()
        }
        set(value) {
            val revisionCopy: Int = revisionVal
            val minorRevisionShift: Int = revisionCopy.shr(16)
            val lowerBits: Int = revisionVal - minorRevisionShift
            val valueInt: Int = value.toInt()
            val upperBits: Int = valueInt.shl(16)
            revisionVal = lowerBits + upperBits
        }

    var minorRevision: Short
        get() {
            val revisionCopy: Int = revisionVal
            var shiftedValue: Int = revisionCopy.shr(16)
            shiftedValue *= (2.0.pow(16)).toInt()
            val toReturn: Int = revisionVal - shiftedValue
            return toReturn.toShort()
        }
        set(value) {
            revisionVal = revisionVal.shr(16)
            revisionVal = revisionVal.shl(16)
            revisionVal += value
        }

    init {
        majorVal = 0
        minorVal = 0
        buildVal = -1
        revisionVal = -1
    }

    constructor() {
        majorVal = 0
        minorVal = 0
        buildVal = -1
        revisionVal = -1
    }

    constructor(version: Version) {
        majorVal = version.major
        minorVal = version.minor
        buildVal = version.build
        revisionVal = version.revision
    }

    constructor(versionString: String) {
        val valuesToAssign: Quadruple<Int, Int, Int, Int> = parseVersionString(versionString)
        major = valuesToAssign.first
        minor = valuesToAssign.second
        build = valuesToAssign.third
        revision = valuesToAssign.forth
    }

    constructor(major: Int) {
        this.major = major
        minorVal = 0
        buildVal = -1
        revisionVal = -1
    }

    constructor(major: Int, minor: Int) {
        this.major = major
        this.minor = minor
        buildVal = -1
        revisionVal = -1
    }

    constructor(major: Int, minor: Int, build: Int) {
        this.major = major
        this.minor = minor
        this.build = build
        revisionVal = -1
    }

    constructor(major: Int, minor: Int, build: Int, revision: Int) {
        this.major = major
        this.minor = minor
        this.build = build
        this.revision = revision
    }

    constructor(double: Double) {
        val integerPart: Int = double.toInt()
        val floatingPart: Int = getNewMinorString(double)
        major = integerPart
        minor = floatingPart
    }

    private fun parseVersionString(versionString: String): Quadruple<Int, Int, Int, Int> {
        val compartments: List<String> = versionString.split(Regex("\\."))
        val numberOfCompartments: Int = compartments.size
        if (numberOfCompartments < 1 || numberOfCompartments > 4) {
            throw IllegalArgumentException("Error: expected 1, 2, 3 or 4 arguments but got $numberOfCompartments")
        }
        val majorStr: String = compartments[0]
        val minorStr: String = if(numberOfCompartments > 1) compartments[1] else "0"
        val buildStr: String = if(numberOfCompartments > 2) compartments[2] else "-1"
        val revisionStr: String = if(numberOfCompartments > 3) compartments[3] else "-1"
        val major: Int = majorStr.toInt()
        val minor: Int = minorStr.toInt()
        val build: Int = buildStr.toInt()
        val revision: Int = revisionStr.toInt()
        return Quadruple(major, minor, build, revision)
    }

    fun copy(): Version {
        return Version(majorVal, minorVal, buildVal, revisionVal)
    }

    override fun clone(): Any {
        return Version(majorVal, minorVal, buildVal, revisionVal)
    }

    override fun compareTo(other: Version): Int {
        if (major < other.major) {
            return -1
        }
        else if (major > other.major){
            return 1
        }
        else if (major == other.major) {
            if (minor < other.minor) {
                return -1
            }
            else if (minor > other.minor){
                return 1
            }
            else if (minor == other.minor) {
                if (build < other.build) {
                    return -1
                }
                else if (build > other.build){
                    return 1
                }
                else if (build == other.build) {
                    if (revision < other.revision) {
                        return -1
                    }
                    else if (revision > other.revision){
                        return 1
                    }
                    else if (revision == other.revision) {
                        return 0
                    }
                }
            }
        }

        return -2 //There is a problem if this happens
    }

    override fun equals(other: Any?): Boolean {
        val equals: Boolean = when (other) {
            null -> {
                false
            }
            is Version -> {
                major == other.major && minor == other.minor && build == other.build && revision == other.revision
            }
            else -> {
                false
            }
        }

        return equals
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    fun toString(fieldCount: Int): String {
        val stringToReturn: String = when (fieldCount) {
            0 -> ""
            1 -> "$major"
            2 -> "$major.$minor"
            3 -> "$major.$minor.$build"
            4 -> "$major.$minor.$build.$revision"
            else -> throw IllegalArgumentException("Error: expected fieldCount to be 0, 1, 2, 3 or 4 but got $fieldCount")
        }

        return stringToReturn
    }

    override fun toString(): String {
        return if (buildVal == -1) "$major.$minor"
        else if (revisionVal == -1) "$major.$minor.$build"
        else "$major.$minor.$build.$revision"
    }

    companion object {
        fun parse(versionString: String): Version {
            return Version(versionString)
        }

        fun tryParse(versionString: String, version: Version): Boolean {
            var succeded: Boolean
            try {
                val newVersion: Version = Version(versionString)
                version.major = newVersion.major
                version.minor = newVersion.minor
                version.build = newVersion.build
                version.revision = newVersion.revision
                succeded = true
            }
            catch(illegalArgException: IllegalArgumentException) {
                succeded = false
            }

            return succeded
        }
    }

    private fun checkForNegativeNumbers(value: Int, name: String) {
        if (value < 0) {
            throw IllegalArgumentException("Error: $name can't be a negative number")
        }
    }

    operator fun inc(): Version {
        if (major < Int.MAX_VALUE) {
            return Version(major + 1, minor, build, revision)
        }
        else if (minor < Int.MIN_VALUE) {
            return Version(major, minor + 1, build, revision)
        }
        else if (build < Int.MAX_VALUE) {
            return Version(major, minor, build + 1, revision)
        }
        else if (revision < Int.MAX_VALUE) {
            return Version(major, minor, build, revision + 1)
        }

        return Version(major, minor, build, revision)
    }

    operator fun dec(): Version {
        if (major > 0) {
            return Version(major - 1, minor, build, revision)
        }
        else if (minor > 0) {
            return Version(major, minor - 1, build, revision)
        }
        else if (build > 0) {
            return Version(major, minor, build - 1, revision)
        }
        else if (revision > 0) {
            return Version(major, minor, build, revision - 1)
        }

        return Version(major, minor, build, revision)
    }

    private fun setUpNewOperatorValue(baseValue: Int, value: Int, operator : (input: Int, Int) -> Int): Int {
        if (Int.MAX_VALUE - operator(baseValue, value) >= 0) {
            if(operator(baseValue, value) < 0)
                return 0
            return baseValue
        }

        return Int.MAX_VALUE
    }

    private fun setUpNewOperatorValueDivision(baseValue: Int, value: Int): Int {
        return baseValue / value
    }

    private fun add(a: Int, b: Int): Int {
        return a + b
    }

    private fun multiply(a: Int, b: Int): Int {
        return a * b
    }

    private fun mod(a: Int, b: Int): Int {
        return a % b
    }

    operator fun plus(int: Int): Version {
        return Version(setUpNewOperatorValue(major, int, ::add))
    }

    private fun checkValueThresholdNewMinorString(decimalComponent: String, maxValue: String = "2147483647", i: Int = 0): Boolean {
        return if (i > 10) {
            true
        } else if (decimalComponent[i] > maxValue[i]) {
            false
        } else if (decimalComponent[i] == maxValue[i]) {
            checkValueThresholdNewMinorString(decimalComponent, maxValue, i + 1)
        } else {
            true
        }
    }

    private fun getNewMinorString(double: Double): Int {
        val newVersionMinor: Int
        val doubleStr: String = double.toString()
        val doubleStrCompartments: List<String> = doubleStr.split(Regex("\\."))
        val decimalComponent: String = doubleStrCompartments[1]
        val decimalComponentLength: Int = decimalComponent.length
        newVersionMinor = if(decimalComponentLength > 0 && decimalComponent[0] != '1') {
            //positive number
            val adjustedDecimalComponent: String = decimalComponent.drop(1)
            if (decimalComponentLength > 11) {
                Int.MAX_VALUE
            } else if (decimalComponentLength == 11) {
                if (checkValueThresholdNewMinorString(adjustedDecimalComponent)) adjustedDecimalComponent.toInt()
                else Int.MAX_VALUE
            } else {
                adjustedDecimalComponent.toInt()
            }
        } else if (decimalComponentLength > 0) {
            val adjustedDecimalComponent: String = decimalComponent.drop(1)
            if (decimalComponentLength > 11) {
                0
            } else if (decimalComponentLength == 11) {
                if (checkValueThresholdNewMinorString(adjustedDecimalComponent,  "2147483648")) -(adjustedDecimalComponent.toInt())
                else 0
            } else {
                -(adjustedDecimalComponent.toInt())
            }
        } else {
            0
        }

        return newVersionMinor
    }

    operator fun plus(double: Double): Version {
        val newVersionMajor: Int = setUpNewOperatorValue(major, double.toInt(), ::add)
        val newVersionMinor: Int = setUpNewOperatorValue(minor, getNewMinorString(double), ::add);
        return Version(newVersionMajor, newVersionMinor, build, revision)
    }

    operator fun plus(version: Version): Version {
        val newVersionMajor: Int = setUpNewOperatorValue(major, version.major, ::add)
        val newVersionMinor: Int = setUpNewOperatorValue(minor, version.minor, ::add)
        val newVersionBuild: Int = setUpNewOperatorValue(build, version.build, ::add)
        val newVersionRevision: Int = setUpNewOperatorValue(revision, version.revision, ::add)
        return Version(newVersionMajor, newVersionMinor, newVersionBuild, newVersionRevision)
    }

    operator fun minus(int: Int): Version {
        return this + -int
    }

    operator fun minus(double: Double): Version {
        return this + -double
    }

    operator fun minus(version: Version): Version {
        val negativeVersion = Version(version.major, version.minor, version.build, version.revision)
        negativeVersion.major = -major
        negativeVersion.minor = -minor
        negativeVersion.build = -build
        negativeVersion.revision = -revision
        return this + negativeVersion
    }

    operator fun times(int: Int): Version {
        return this + (major * (int - 1))
    }

    operator fun times(double: Double): Version {
        val newMajorValue: Int = setUpNewOperatorValue(major, double.toInt(), ::multiply)
        val newMinorValue: Int = setUpNewOperatorValue(minor, getNewMinorString(double), ::multiply)

        return Version(newMajorValue, newMinorValue, build, revision)
    }

    operator fun times(version: Version): Version {
        val newVersionMajor: Int = setUpNewOperatorValue(major, major * (version.major - 1), ::add)
        val newVersionMinor: Int = setUpNewOperatorValue(minor, minor * (version.minor - 1), ::add)
        val newVersionBuild: Int = setUpNewOperatorValue(build, build * (version.build - 1), ::add)
        val newVersionRevision: Int = setUpNewOperatorValue(revision, revision * (version.revision - 1), ::add)
        return Version(newVersionMajor, newVersionMinor, newVersionBuild, newVersionRevision)
    }

    operator fun div(int: Int): Version {
        if (int < 1) {
            throw IllegalArgumentException("Error: can't divide by a negative number or 0")
        }

        return Version(major / int, minor, build, revision)
    }

    operator fun div(double: Double): Version {
        val newVersionMajor: Int = setUpNewOperatorValueDivision(major, double.toInt())
        val newVersionMinor: Int = setUpNewOperatorValueDivision(minor, getNewMinorString(double))

        return Version(newVersionMajor, newVersionMinor, build, revision)
    }

    operator fun div(version: Version): Version {
        if (version.major < 1 || version.minor < 1 || version.build < 1 || version.revision < 1) {
            throw IllegalArgumentException("Error: can't divide by a negative number or 0")
        }

        val newMajorValue: Int = setUpNewOperatorValueDivision(major, version.major)
        val newMinorValue: Int = setUpNewOperatorValueDivision(minor, version.minor)
        val newBuildValue: Int = setUpNewOperatorValueDivision(build, version.build)
        val newRevisionValue: Int = setUpNewOperatorValueDivision(revision, version.revision)

        return Version(newMajorValue, newMinorValue, newBuildValue, newRevisionValue)
    }

    operator fun rem(int: Int): Version {
        val newMajorValue: Int = setUpNewOperatorValue(major, int, ::mod)

        return Version(newMajorValue, minor, build, revision)
    }

    operator fun rem(double: Double): Version {
        val newMajorValue: Int = setUpNewOperatorValue(major, double.toInt(), ::mod)
        val newMinorValue: Int = setUpNewOperatorValue(minor, getNewMinorString(double), ::mod)

        return Version(newMajorValue, newMinorValue, build, revision)
    }

    operator fun rem(version: Version): Version {
        val newMajorValue: Int = setUpNewOperatorValue(major, version.major, ::mod)
        val newMinorValue: Int = setUpNewOperatorValue(minor, version.minor, ::mod)
        val newBuildValue: Int = setUpNewOperatorValue(build, version.build, ::mod)
        val newRevisionValue: Int = setUpNewOperatorValue(revision, version.revision, ::mod)

        return Version(newMajorValue, newMinorValue, newBuildValue, newRevisionValue)
    }

    operator fun rangeTo(version: Version): Quadruple<List<Int>, List<Int>, List<Int>, List<Int>> {
        val majorRange: List<Int> = (major..version.major).toList()
        val minorRange: List<Int> = (minor..version.minor).toList()
        val buildRange: List<Int> = (build..version.build).toList()
        val revisionRange: List<Int> = (revision..version.revision).toList()

        return Quadruple(majorRange, minorRange, buildRange, revisionRange)
    }

    operator fun get(int: Int): Int {
        val value: Int = when (int % 4) {
            0 -> major
            1 -> minor
            2 -> build
            3 -> revision
            else -> -2 //should never happen
        }

        return value
    }

    operator fun get(firstValue: Int, secondValue: Int): Pair<Int, Int> {
        return Pair(this[firstValue], this[secondValue])
    }

    operator fun get(firstValue: Int, secondValue: Int, thirdValue: Int): Triple<Int, Int, Int> {
        return Triple(this[firstValue], this[secondValue], this[thirdValue])
    }

    operator fun get(firstValue: Int, secondValue: Int, thirdValue: Int, forthValue: Int): Quadruple<Int, Int, Int, Int> {
        return Quadruple(this[firstValue], this[secondValue], this[thirdValue], this[forthValue])
    }

    operator fun compareTo(int: Int): Int {
        return major - int
    }

    operator fun compareTo(double: Double): Int {
        val majorValue: Int = double.toInt()
        val minorValue: Int = getNewMinorString(double)

        if (major < majorValue) {
            return -1
        }
        else if (major > majorValue) {
            return 1
        }
        else if (major == majorValue) {
            if (minor < minorValue) {
                return -1
            }
            else if (minor > minorValue) {
                return 1
            }
            else if (minor == minorValue) {
                return 0
            }
        }

        return -2 //should never happen
    }
}