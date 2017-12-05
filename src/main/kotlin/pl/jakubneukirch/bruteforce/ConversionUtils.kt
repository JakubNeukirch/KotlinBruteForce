package pl.jakubneukirch.bruteforce

/**
 * Converts two-character String to Byte with specified hex value
 *
 * @return      Byte of specified hex
 */
fun String.hexToByte(): Byte {
    if (this@hexToByte.length > 2)
        return Integer.parseInt(this@hexToByte.substring((this@hexToByte.length-2)..(this@hexToByte.length-1)), 16).toByte()
    return Integer.parseInt(this@hexToByte, 16).toByte()
}

/**
 * Converts two-character String to Char with specified hex value
 *
 * @return      ASCII Char
 */
fun String.hexToChar(): Char {
    if (this@hexToChar.length > 2)
        return Integer.parseInt(this@hexToChar.substring((this@hexToChar.length-2)..(this@hexToChar.length-1)), 16).toChar()
    return Integer.parseInt(this@hexToChar, 16).toChar()
}

/**
 * Converts String of hexes to String of ASCII signs
 *
 * @return      ASCII String
 */
fun String.hexToAsciiString(): String {
    var str = this@hexToAsciiString
    var builder = StringBuilder()
    if (str.length % 2 > 0) str = "0" + str
    for (i in 0..((str.length / 2) - 1)) {
        builder.append(str.substring(i * 2, (i * 2) + 2).hexToChar())
    }
    return builder.toString()
}

/**
 * Converts String of hexes to ByteArray
 *
 * @return      ByteArray with bytes with value of hexes in String
 */
fun String.hexToAsciiByteArray(): ByteArray{
    return this@hexToAsciiByteArray.hexToAsciiString().toByteArray()
}

/**
 * Converts hex string to hex ByteArray
 *
 * @return  hex ByteArray
 */
fun String.hexToHexByteArray(): ByteArray {
    var str = this@hexToHexByteArray
    var byteArray = ByteArray((str.length / 2))
    for (i in 0..((str.length / 2) - 1)){
        byteArray[i] = Integer.parseInt(str.substring(i*2, i*2+2), 16).toByte()
    }
     return byteArray
}

/**
 * Converts ByteArray to String
 *
 * @return      String of bytes with indexed order
 */
fun ByteArray.toCharString(): String{
    return buildString {
        this@toCharString.map {
            append(
                    it.toUChar()
            )
        }
    }
}

/**
 * Converts byte to unsigned char
 *
 * @return      unsigned char with bits value of byte
 */
fun Byte.toUChar(): Char{
    return (if (this@toUChar < 0) (256 + this@toUChar) else this@toUChar.toInt()).toChar()
}

fun String.toSByteArray(): ByteArray{
    var array = ByteArray(this@toSByteArray.length)
    for(i in 0..(this@toSByteArray.length-1)){
        array[i] = this@toSByteArray[i].toInt().toByte()
    }
    return array
}