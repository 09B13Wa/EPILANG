package logic

import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.net.URLConnection

class Download(private var url: String, private var location: String) {

    fun download() {
        val connection: URLConnection = URL(url).openConnection()
        val inputStream: InputStream = connection.getInputStream()
        val outputStream: OutputStream = FileOutputStream(location)
        val buffer: ByteArray = ByteArray(1024)
        var length: Int = inputStream.read(buffer)
        while (length > 0) {
            outputStream.write(buffer, 0, length)
            length = inputStream.read(buffer)
        }
        outputStream.close()
        inputStream.close()
    }
}