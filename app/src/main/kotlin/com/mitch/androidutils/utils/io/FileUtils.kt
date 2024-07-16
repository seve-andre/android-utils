package com.mitch.androidutils.utils.io

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class FileUtils {

    suspend fun openFile(fileName: String): File {
        return withContext(Dispatchers.IO) {
            File(fileName)
        }
    }

    suspend fun writeToFile(fileName: String, data: String /*add append option*/) {
        withContext(Dispatchers.IO) {
            File(fileName).writeText(data)
        }
    }

    suspend fun appendToFile(fileName: String, data: String /*add append option*/) {
        withContext(Dispatchers.IO) {
            File(fileName).appendText(data)
        }
    }

    suspend fun readFile() { }

    suspend fun deleteFile(filename: String) {
        withContext(Dispatchers.IO) {
            Files.deleteIfExists(Paths.get(filename))
        }
    }
    suspend fun exists(): Boolean { return true }
    suspend fun info() { }
    suspend fun renameFile() { }
    suspend fun copyFile() { }
}
