package logic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class VersionTest {
    class VersionProperties {
        @Test
        fun getMajor() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom)
            assertEquals(majorRandom, version.major)
        }

        @Test
        fun getMinor() {
            val random = Random()
            val randomInteger: Int = (random.nextInt(100) + 1)
            val version = Version(1, randomInteger)
            assertEquals(randomInteger, version.minor)
        }

        @Test
        fun getBuild() {
            val random = Random()
            val randomInteger: Int = (random.nextInt(100) + 1)
            val version = Version(1, 1, randomInteger)
            assertEquals(randomInteger, version.build)
        }

        @Test
        fun getRevision() {
            val random = Random()
            val randomInteger: Int = (random.nextInt(100) + 1)
            val version = Version(1, 1, 1, randomInteger)
            assertEquals(randomInteger, version.revision)
        }

        @Test
        fun getMajorRevision() {
            val test = Version(2, 4, 1128, 65536)
            assertEquals(1, test.majorRevision)
        }

        @Test
        fun setMajorRevision() {
            val version = Version(1, 1, 0, 0)
            version.majorRevision = 726
            assertEquals(47579136, version.revision)
            assertEquals(726, version.majorRevision)
        }

        @Test
        fun setMajorRevisionZero() {
            val version = Version(1, 1, 0, 726)
            assertEquals(0, version.majorRevision)
            assertEquals(726, version.revision)
        }

        @Test
        fun getMinorRevision() {
            val test = Version(2, 4, 1128, 47611903)
            assertEquals(32767, test.minorRevision)
        }

        @Test
        fun setMinorRevision() {
            val version = Version(1, 1, 0, 47579136)
            version.minorRevision = 726
            assertEquals(726, version.minorRevision)
            assertEquals(47579862, version.revision)

        }

        @Test
        internal fun setMinorRevisionZero() {
            val version = Version(1, 1, 0, 47579136)
            assertEquals(0, version.minorRevision)
            assertEquals(47579136, version.revision)
        }
    }

    class VersionConstructor {
        @Test
        internal fun baseConstructor() {
            val version = Version()
            assertEquals(0, version.major)
            assertEquals(0, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun constructorMajor() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom)
            assertEquals(majorRandom, version.major)
            assertEquals(0, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun constructorMajorNegative() {
            val random = Random()
            val majorRandom: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(majorRandom)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorMinor() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom)
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun constructorMinorNegative() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(0, randomNumber)
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorMinorWithMajorNegative() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber, 0)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorMinorWithMajorAndMinorNegative() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, randomNumber)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorBuild() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom)
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(buildRandom, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun constructorBuildMinusTwo() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(0, 0, -2)
            }

            val expectedMessage = "Error: the build value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorBuildMinusOne() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, -1)
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun constructorBuildWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(0, randomNumber, 1)
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorBuildMinusTwoWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(0, randomNumber, -2)
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorBuildWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber, 1, 1)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorBuildMinusTwoWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber, 1, -2)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorBuildWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, randomNumber, 1)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorBuildMinusTwoWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, randomNumber, -2)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorBuildMinusTwoWithNegativeMajorAndMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, randomNumber, -2)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevision() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(buildRandom, version.build)
            assertEquals(revisionRandom, version.revision)
        }

        @Test
        internal fun constructorRevisionMinusOne() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom,buildRandom, -1)
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(buildRandom, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun constructorRevisionMinusTwoWithNegativeMinorAndMajorAndBuildMinosTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, randomNumber, -2, -2)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionWithNegativeMinorAndMajorAndBuildNegativeTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, randomNumber, -2, 1)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionNegativeTwoWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, randomNumber, 1, -2)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, randomNumber, 1, 1)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionMinusTwoWithNegativeMajorAndBuildMinosTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, 1, -2, -2)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionWithNegativeMajorAndBuildNegativeTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, 1, -2, 1)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionNegativeTwoWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, 1, 1, -2)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(randomNumber - 2, 1, 1, 1)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionMinusTwoWithNegativeMinorAndBuildMinosTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(1, randomNumber, -2, -2)
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionWithNegativeMinorAndBuildNegativeTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(1, randomNumber, -2, 1)
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionNegativeTwoWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(1, randomNumber, 1, -2)
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(1, randomNumber, 1, 1)
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionNegativeTwoWithNegativeTwoBuild() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(1, 1, -2, -2)
            }

            val expectedMessage = "Error: the build value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionWithNegativeTwoBuild() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(1, 1, -2, 1)
            }

            val expectedMessage = "Error: the build value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorRevisionNegativeTwo() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(1, 1, 1, -2)
            }

            val expectedMessage = "Error: the revision value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        //OTHER CONSTRUCTORS

        @Test
        internal fun constructorFromOtherVersion() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            val version2 = Version(version)
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(buildRandom, version.build)
            assertEquals(revisionRandom, version.revision)
            assertEquals(majorRandom, version2.major)
            assertEquals(minorRandom, version2.minor)
            assertEquals(buildRandom, version2.build)
            assertEquals(revisionRandom, version2.revision)
            version2.majorRevision = 1
            assertEquals(1, version2.majorRevision)
            assertEquals(0, version.majorRevision)
        }
        
        //SAME BUT WITH STRING PARSER

        @Test
        internal fun constructorStringRevisionMinusTwoWithNegativeMinorAndMajorAndBuildMinosTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.$randomNumber.-2.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionWithNegativeMinorAndMajorAndBuildNegativeTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.$randomNumber.-2.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionNegativeTwoWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.$randomNumber.1.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.$randomNumber.1.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionMinusTwoWithNegativeMajorAndBuildMinosTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.1.-2.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionWithNegativeMajorAndBuildNegativeTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.1.-2.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionNegativeTwoWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.1.1.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.1.1.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionMinusTwoWithNegativeMinorAndBuildMinosTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("1.$randomNumber.-2.-2")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionWithNegativeMinorAndBuildNegativeTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("1.$randomNumber.-2.1")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionNegativeTwoWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("1.$randomNumber.1.-2")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("1.$randomNumber.1.1")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionNegativeTwoWithNegativeTwoBuild() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("1.1.-2.-2")
            }

            val expectedMessage = "Error: the build value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionWithNegativeTwoBuild() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("1.1.-2.1")
            }

            val expectedMessage = "Error: the build value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionNegativeTwo() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("1.1.1.-2")
            }

            val expectedMessage = "Error: the revision value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRevisionMinusOne() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val version = Version("$majorRandom.$minorRandom.$buildRandom.-1")
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(buildRandom, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun constructorStringRevision() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version("$majorRandom.$minorRandom.$buildRandom.$revisionRandom")
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(buildRandom, version.build)
            assertEquals(revisionRandom, version.revision)
        }

        //Test of 3-parameters

        @Test
        internal fun constructorStringBuild() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val version = Version("$majorRandom.$minorRandom.$buildRandom")
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(buildRandom, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun constructorStringBuildMinusTwo() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("0.0.-2")
            }

            val expectedMessage = "Error: the build value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringBuildMinusOne() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val version = Version("$majorRandom.$minorRandom.-1")
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun constructorStringBuildWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("0.$randomNumber.1")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringBuildMinusTwoWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("0.$randomNumber.-2")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringBuildWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("$randomNumber.1.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringBuildMinusTwoWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("$randomNumber.1.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringBuildWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.$randomNumber.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringBuildMinusTwoWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.$randomNumber.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringBuildMinusTwoWithNegativeMajorAndMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.$randomNumber.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        //Test of 2 - parameters

        @Test
        internal fun constructorStringMinor() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val version = Version("$majorRandom.$minorRandom")
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun constructorStringMinorNegative() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("0.$randomNumber")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringMinorWithMajorNegative() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("$randomNumber.0")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringMinorWithMajorAndMinorNegative() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("${randomNumber - 2}.$randomNumber")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        //Major only

        @Test
        internal fun constructorStringMajor() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val version = Version("$majorRandom")
            assertEquals(majorRandom, version.major)
            assertEquals(0, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun constructorStringMajorNegative() {
            val random = Random()
            val majorRandom: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version("$majorRandom")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRandomNumberOfArgs() {
            val random = Random(0)
            var majorRandom: Int = (random.nextInt(100) + 1)
            while (majorRandom < 4)
                majorRandom = (random.nextInt(100) + 1)
            val stringToTest: StringBuilder = StringBuilder("")
            var newRandomNum: Int
            for (i in 1..majorRandom) {
                newRandomNum = (random.nextInt(100) + 1)
                stringToTest.append("$newRandomNum.")
            }
            newRandomNum = (random.nextInt(100) + 1)
            stringToTest.append("$newRandomNum")

            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(stringToTest.toString())
            }

            val expectedMessage = "Error: expected 1, 2, 3 or 4 arguments but got ${majorRandom + 1}"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        //DOUBLE CONSTRUCTOR

        @Test
        internal fun constructorDouble() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val string = "$majorRandom.0$minorRandom"
            val double: Double = string.toDouble()
            //generate a random number for the decimal part of majorRandom
            val version = Version(double)
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }

        //Test all the possible combinations of major, minor with the double constructor
        @Test
        internal fun constructorDoubleMajorNegative() {
            val random = Random()
            val majorRandom: Int = -(random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val string = "$majorRandom.0$minorRandom"
            val double: Double = string.toDouble()
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(double)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorDoubleMinorNegative() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val string = "$majorRandom.1$minorRandom"
            val double: Double = string.toDouble()
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(double)
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorDoubleMajorMinorNegative() {
            val random = Random()
            val majorRandom: Int = -(random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val string = "$majorRandom.1$minorRandom"
            val double: Double = string.toDouble()
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version(double)
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        //test out of range for decimal part
        @Test
        internal fun constructorDoubleMajorMinorDecimalOutOfRange() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val string = "$majorRandom.01111111111111111111111"
            val double: Double = string.toDouble()
            val version = Version(double)
            assertEquals(majorRandom, version.major)
            assertEquals(2147483647, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }
    }

    class VersionInstanceMethods {
        //TEST COPY
        @Test
        internal fun copy() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            val versionCopy = version.copy()
            assertEquals(majorRandom, versionCopy.major)
            assertEquals(minorRandom, versionCopy.minor)
            assertEquals(buildRandom, versionCopy.build)
            assertEquals(revisionRandom, versionCopy.revision)
            versionCopy.majorRevision = 1
            assertEquals(1, versionCopy.majorRevision)
            assertEquals(majorRandom, version.major)
        }

        //TEST COMPARE TO
        //Test with only major
        @Test
        internal fun compareTo() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom)
            val version2 = Version(majorRandom + 1)
            assertEquals(-1, version.compareTo(version2))
            assertEquals(1, version2.compareTo(version))
            assertEquals(0, version.compareTo(version))
        }

        //Test with only minor
        @Test
        internal fun compareTo2() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom)
            val version2 = Version(majorRandom, minorRandom + 1)
            assertEquals(-1, version.compareTo(version2))
            assertEquals(1, version2.compareTo(version))
            assertEquals(0, version.compareTo(version))
        }

        //Test with only build
        @Test
        internal fun compareTo3() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom)
            val version2 = Version(majorRandom, minorRandom, buildRandom + 1)
            assertEquals(-1, version.compareTo(version2))
            assertEquals(1, version2.compareTo(version))
            assertEquals(0, version.compareTo(version))
        }

        //Test with only revision
        @Test
        internal fun compareTo4() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            val version2 = Version(majorRandom, minorRandom, buildRandom, revisionRandom + 1)
            assertEquals(-1, version.compareTo(version2))
            assertEquals(1, version2.compareTo(version))
            assertEquals(0, version.compareTo(version))
        }

        //Test with all
        @Test
        internal fun compareTo5() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            val version2 = Version(majorRandom + 1, minorRandom, buildRandom, revisionRandom)
            assertEquals(-1, version.compareTo(version2))
            assertEquals(1, version2.compareTo(version))
            assertEquals(0, version.compareTo(version))
        }

        //TEST EQUALS
        //Test with only major
        @Test
        internal fun equals() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom)
            val version2 = Version(majorRandom)
            assertEquals(true, version.equals(version2))
            assertEquals(false, version.equals(null))
        }

        //Test with only minor
        @Test
        internal fun equals2() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom)
            val version2 = Version(majorRandom, minorRandom)
            assertEquals(true, version.equals(version2))
            assertEquals(false, version.equals(null))
        }

        //Test with only build
        @Test
        internal fun equals3() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom)
            val version2 = Version(majorRandom, minorRandom, buildRandom)
            assertEquals(true, version.equals(version2))
            assertEquals(false, version.equals(null))
        }

        //Test with only revision
        @Test
        internal fun equals4() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            val version2 = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            assertEquals(true, version.equals(version2))
            assertEquals(false, version.equals(null))
        }

        //Test with other object
        @Test
        internal fun equals5() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            val version2 = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            assertEquals(false, version.equals("Hey, hello"))
            assertEquals(false, version.equals(null))
        }

        //Test inequals
        @Test
        internal fun inequals() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            val version2 = Version(majorRandom, minorRandom, buildRandom, revisionRandom + 1)
            assertEquals(false, version.equals(version2))
            assertEquals(false, version.equals(null))
        }

        //TEST TO STRING
        //Test with only major
        @Test
        internal fun toString1() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom)
            assertEquals("$majorRandom.0", version.toString())
        }

        //Test with only minor
        @Test
        internal fun toString2() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom)
            assertEquals("$majorRandom.$minorRandom", version.toString())
        }

        //Test with only build
        @Test
        internal fun toString3() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom)
            assertEquals("$majorRandom.$minorRandom.$buildRandom", version.toString())
        }

        //Test with only revision
        @Test
        internal fun toString4() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            assertEquals("$majorRandom.$minorRandom.$buildRandom.$revisionRandom", version.toString())
        }

        //Test for integer version with four numbers
        @Test
        internal fun toString5() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            assertEquals("$majorRandom.$minorRandom.$buildRandom.$revisionRandom", version.toString(4))
        }

        //Test for integer version with three numbers
        @Test
        internal fun toString6() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            assertEquals("$majorRandom.$minorRandom.$buildRandom", version.toString(3))
        }

        //Test for integer version with two numbers
        @Test
        internal fun toString7() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            assertEquals("$majorRandom.$minorRandom", version.toString(2))
        }

        //Test for integer version with one number
        @Test
        internal fun toString8() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            assertEquals("$majorRandom", version.toString(1))
        }

        //Test for integer version with zero numbers
        @Test
        internal fun toString9() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)
            assertEquals("", version.toString(0))
        }

        //Test for integer version with random number of numbers above or equal to 5
        @Test
        internal fun toString10() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)

            val randomNumber: Int = (random.nextInt(100) + 5)
            val exception = assertThrows(IllegalArgumentException::class.java) {
                version.toString(randomNumber)
            }

            val expectedMessage = "Error: expected fieldCount to be 0, 1, 2, 3 or 4 but got $randomNumber"
            val actualMessage = exception.message!!
            assertTrue(actualMessage.contains(expectedMessage))
        }

        //Test compareTo with integer
        @Test
        internal fun compareTo1() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version(majorRandom, minorRandom, buildRandom, revisionRandom)

            assertEquals(0, version.compareTo(majorRandom))
            assertEquals(1, version.compareTo(majorRandom - 1))
            assertEquals(-1, version.compareTo(majorRandom + 1))
        }

        //Test compareTo with double
        //TODO
    }

    class VersionStaticMethods {

        //Test for integer version with four numbers
        @Test
        internal fun parseRevisionMinusTwoWithNegativeMinorAndMajorAndBuildMinosTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.$randomNumber.-2.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionWithNegativeMinorAndMajorAndBuildNegativeTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.$randomNumber.-2.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionNegativeTwoWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.$randomNumber.1.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.$randomNumber.1.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionMinusTwoWithNegativeMajorAndBuildMinosTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.1.-2.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionWithNegativeMajorAndBuildNegativeTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.1.-2.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionNegativeTwoWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.1.1.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.1.1.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionMinusTwoWithNegativeMinorAndBuildMinosTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("1.$randomNumber.-2.-2")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionWithNegativeMinorAndBuildNegativeTwo() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("1.$randomNumber.-2.1")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionNegativeTwoWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("1.$randomNumber.1.-2")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("1.$randomNumber.1.1")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionNegativeTwoWithNegativeTwoBuild() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("1.1.-2.-2")
            }

            val expectedMessage = "Error: the build value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionWithNegativeTwoBuild() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("1.1.-2.1")
            }

            val expectedMessage = "Error: the build value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionNegativeTwo() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("1.1.1.-2")
            }

            val expectedMessage = "Error: the revision value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseRevisionMinusOne() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val version = Version.parse("$majorRandom.$minorRandom.$buildRandom.-1")
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(buildRandom, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun parseRevision() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val revisionRandom: Int = (random.nextInt(100) + 1)
            val version = Version.parse("$majorRandom.$minorRandom.$buildRandom.$revisionRandom")
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(buildRandom, version.build)
            assertEquals(revisionRandom, version.revision)
        }

        //Test of 3-parameters

        @Test
        internal fun parseBuild() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val buildRandom: Int = (random.nextInt(100) + 1)
            val version = Version.parse("$majorRandom.$minorRandom.$buildRandom")
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(buildRandom, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun parseBuildMinusTwo() {
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("0.0.-2")
            }

            val expectedMessage = "Error: the build value can't be inferior to -1"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseBuildMinusOne() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val version = Version.parse("$majorRandom.$minorRandom.-1")
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun parseBuildWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("0.$randomNumber.1")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseBuildMinusTwoWithNegativeMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("0.$randomNumber.-2")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseBuildWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("$randomNumber.1.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseBuildMinusTwoWithNegativeMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("$randomNumber.1.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseBuildWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.$randomNumber.1")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseBuildMinusTwoWithNegativeMinorAndMajor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.$randomNumber.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseBuildMinusTwoWithNegativeMajorAndMinor() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.$randomNumber.-2")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        //Test of 2 - parameters

        @Test
        internal fun parseMinor() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val minorRandom: Int = (random.nextInt(100) + 1)
            val version = Version.parse("$majorRandom.$minorRandom")
            assertEquals(majorRandom, version.major)
            assertEquals(minorRandom, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun parseMinorNegative() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("0.$randomNumber")
            }

            val expectedMessage = "Error: minor can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseMinorWithMajorNegative() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("$randomNumber.0")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun parseMinorWithMajorAndMinorNegative() {
            val random = Random()
            val randomNumber: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("${randomNumber - 2}.$randomNumber")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage: String = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        //Major only

        @Test
        internal fun parseMajor() {
            val random = Random()
            val majorRandom: Int = (random.nextInt(100) + 1)
            val version = Version.parse("$majorRandom")
            assertEquals(majorRandom, version.major)
            assertEquals(0, version.minor)
            assertEquals(-1, version.build)
            assertEquals(-1, version.revision)
        }

        @Test
        internal fun parseMajorNegative() {
            val random = Random()
            val majorRandom: Int = -(random.nextInt(100) + 1)
            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse("$majorRandom")
            }

            val expectedMessage = "Error: major can't be a negative number"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }

        @Test
        internal fun constructorStringRandomNumberOfArgs() {
            val random = Random(0)
            var majorRandom: Int = (random.nextInt(100) + 1)
            while (majorRandom < 4)
                majorRandom = (random.nextInt(100) + 1)
            val stringToTest: StringBuilder = StringBuilder("")
            var newRandomNum: Int
            for (i in 1..majorRandom) {
                newRandomNum = (random.nextInt(100) + 1)
                stringToTest.append("$newRandomNum.")
            }
            newRandomNum = (random.nextInt(100) + 1)
            stringToTest.append("$newRandomNum")

            val exception: Exception = assertThrows(IllegalArgumentException::class.java) {
                Version.parse(stringToTest.toString())
            }

            val expectedMessage = "Error: expected 1, 2, 3 or 4 arguments but got ${majorRandom + 1}"
            val actualMessage = exception.message!!

            assertTrue(actualMessage.contains(expectedMessage))
        }
    }
}