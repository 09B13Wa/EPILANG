package com.example.logic

class SubstringSearch {
    companion object {
        /**
         * Returns the index within this string of the first occurrence of the
         * specified substring. If it is not a substring, return -1.
         *
         * There is no Galil because it only generates one match.
         *
         * @param haystack The string to be scanned
         * @param needle The target string to search
         * @return The start index of the substring
         */
        fun indexOf(haystack: CharArray, needle: CharArray): Int {
            if (needle.isEmpty()) {
                return 0
            }
            val charTable = makeCharTable(needle)
            val offsetTable = makeOffsetTable(needle)
            var i = needle.size - 1
            var j: Int
            while (i < haystack.size) {
                j = needle.size - 1
                while (needle[j] == haystack[i]) {
                    if (j == 0) {
                        return i
                    }
                    --i
                    --j
                }
                // i += needle.length - j; // For naive method
                i += offsetTable[needle.size - 1 - j].coerceAtLeast(charTable[haystack[i].code])
            }
            return -1
        }

        /**
         * Makes the jump table based on the mismatched character information.
         */
        private fun makeCharTable(needle: CharArray): IntArray {
            val alphabetSize = Character.MAX_VALUE.code + 1 // 65536
            val table = IntArray(alphabetSize)
            for (i in table.indices) {
                table[i] = needle.size
            }
            for (i in needle.indices) {
                table[needle[i].code] = needle.size - 1 - i
            }
            return table
        }

        /**
         * Makes the jump table based on the scan offset which mismatch occurs.
         * (bad character rule).
         */
        private fun makeOffsetTable(needle: CharArray): IntArray {
            val table = IntArray(needle.size)
            var lastPrefixPosition = needle.size
            for (i in needle.size downTo 1) {
                if (isPrefix(needle, i)) {
                    lastPrefixPosition = i
                }
                table[needle.size - i] = lastPrefixPosition - i + needle.size
            }
            for (i in 0 until needle.size - 1) {
                val slen = suffixLength(needle, i)
                table[slen] = needle.size - 1 - i + slen
            }
            return table
        }

        /**
         * Is needle[p:end] a prefix of needle?
         */
        private fun isPrefix(needle: CharArray, p: Int): Boolean {
            var i = p
            var j = 0
            while (i < needle.size) {
                if (needle[i] != needle[j]) {
                    return false
                }
                ++i
                ++j
            }
            return true
        }

        /**
         * Returns the maximum length of the substring ends at p and is a suffix.
         * (good suffix rule)
         */
        private fun suffixLength(needle: CharArray, p: Int): Int {
            var len = 0
            var i = p
            var j = needle.size - 1
            while (i >= 0 && needle[i] == needle[j]) {
                len += 1
                --i
                --j
            }
            return len
        }
    }

}