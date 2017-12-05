package pl.jakubneukirch.bruteforce

import java.io.File
import java.util.*

const val ENG_ALPHABET_CHARSET = "abcdefghijklmnopqrstuvwxyz'"

fun getOptimizedKeyBytes(lineList: List<String>, charset: String = ENG_ALPHABET_CHARSET)
        : ArrayList<List<Byte>> {
    var fileBytes = getKeyBytes(lineList, charset)
    var outputBytesLines = ArrayList<List<Byte>>()

    var maxLength = 0  //most bytes in line
    for (i in 0..(fileBytes.size - 1))
        maxLength = if (fileBytes[i].size > maxLength) fileBytes[i].size else maxLength

    var bytesInLine: List<List<Byte>>
    var bytes: List<Byte>

    var correctBytes: List<Byte>?
    for (j in 0..(maxLength - 1)) {

        correctBytes = null
        for(i in 0..(fileBytes.size-1)){
            bytesInLine = fileBytes[i]

            if (bytesInLine.size > j) {
                bytes = bytesInLine[j]

                correctBytes = getCommonElements(correctBytes ?: bytes, bytes)
            }
        }
        outputBytesLines.add(correctBytes ?: arrayListOf())
    }
    return outputBytesLines
}


/**
 * Returns chars which can make chars specified in charset by xor encryption
 *
 * @param   lineList    List of Lines for which will be Bytes searched
 * @param   charset     Chars which will be searched
 * @return              List of Lines. Line is List which contains lists with chars which
 *                      will be in key  for specified char in line
 */
fun getKeyBytes(lineList: List<String>, charset: String = ENG_ALPHABET_CHARSET): ArrayList<List<List<Byte>>> {
    var fileBytes = ArrayList<List<List<Byte>>>()
    var lineBytes: ArrayList<List<Byte>>
    for (line in lineList) {
        lineBytes = ArrayList<List<Byte>>()
        var ba = line.hexToHexByteArray()
        for (byte: Byte in ba) {
            lineBytes.add(getPossibleKeyBytes(byte, charset.toByteArray()))
        }
        fileBytes.add(lineBytes)
    }
    return fileBytes
}

/**
 * @param   desiredChars    ByteArray of chars which should contain decrypted string
 * @param   encryptedByte   Byte which will be decrypted
 * @return                  Bytes which can change specified encryptedByte into one of desiredChars
 *                          with use of xor encryption
 */
private fun getPossibleKeyBytes(encryptedByte: Byte, desiredChars: ByteArray): List<Byte> {
    var list = ArrayList<Byte>()

    var encryptedInt: Int
    var desiredInt: Int
    var xored: Int

    for (desiredByte: Byte in desiredChars) {

        desiredInt = if (desiredByte < 0) 256 + desiredByte else desiredByte.toInt()
        encryptedInt = if (encryptedByte < 0) 256 + encryptedByte else encryptedByte.toInt()

        xored = (encryptedInt xor desiredInt)

        list.add(xored.toByte())
    }
    return list
}

/**
 * Gets bytes which can make chars specified in charset.
 *
 * @return      common elements of both lists
 */
private fun getCommonElements(list1: List<Byte>, list2: List<Byte>): List<Byte> {
    val out = ArrayList<Byte>()
    list1.map { it ->
        for (byte in list2) {
            if (it == byte) {
                out.add(it)
                break
            }
        }
    }
    return out
}

/**
 * Returns BruteForce object set up for specified file
 *
 * @param   fileName    Name of file
 * @param   keyLevel    How long is key
 * @param   charset     What signs does contain encoded String
 * @return              BruteForce object set up for xor decryption of specified file
 */
fun getXorBruteForce(fileName: String, keyLevel: Int = -1, charset: String = ENG_ALPHABET_CHARSET): BruteForce {
    var lines = readFileLines(fileName)
    var keyBytes = getOptimizedKeyBytes(lines, charset)
    var level = if (keyLevel == -1) getLongestLine(lines) else keyLevel
    return BruteForce(level, keyBytes)
}

private fun getLongestLine(lines: List<String>): Int {
    var maxLength = 0
    for (i in 0 until lines.size) {
        maxLength = if (lines[i].length > maxLength) lines[i].length else maxLength
    }
    return maxLength/2
}

private fun readFileLines(fileName: String): List<String> {
    val scanner = Scanner(File(fileName))
    val list = ArrayList<String>()
    var maxLength: Int = 0
    do {
        list.add(scanner.nextLine())
        maxLength = if (list[list.size - 1].length > maxLength) list[list.size - 1].length else maxLength
    } while (scanner.hasNextLine())
    return list
}
