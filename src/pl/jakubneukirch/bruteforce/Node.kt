package pl.jakubneukirch.bruteforce

class Node(levels: Int, var chars: String = values, var child: Node? = null) {
    companion object {
        //must be set before run brute-forcing and cannot be changed during brute-forcing
        var values: String = "abcdefghijklmnopqrstuvwxyz"

        fun newInstance(levels: Int, chars: List<List<Byte>>): Node {
            var node = Node(0)
            for (i in 0 until levels) {
                var c = chars[i].toByteArray()
                if (c.isEmpty())
                    c = byteArrayOf('#'.toByte())
                node = Node(
                        levels = i,
                        chars = c.toCharString(),
                        child = if (i == 1) null else node
                )
            }
            return node
        }

        fun newInstance(levels: Int, charset: String): Node {
            var node = Node(0)
            for (i in 1..levels) {
                node = Node(
                        levels = i,
                        chars = charset,
                        child = if(node.level == 0) null else node
                )
            }
            return node
        }
    }

    var level: Int
    var position = 0
    var value: Char = 'a'

    init {
        level = levels
        zero()
    }

    fun isMax(): Boolean {
        return (position == (chars.length - 1)) && (child?.isMax() ?: true)
    }

    fun zero() {
        position = 0
        value = chars[position]
        child?.zero()
    }

    private fun inc() {
        value = chars[++position]
    }

    fun iterate(): Boolean {
        if (!isMax()) {
            if (child?.isMax() ?: true) {
                inc()
                child?.zero()
            } else {
                child?.iterate()
            }
        }
        return isMax()
    }

    override fun toString(): String {
        if (this.child == null)
            return value.toString()
        return buildString {
            append(value)
            append(this@Node.child.toString())
        }
    }

    fun toCharArray(): CharArray {
        return (child?.toCharArray() ?: charArrayOf()) + charArrayOf(value)
    }

    fun toByteArray(): ByteArray {
        return (child?.toByteArray() ?: byteArrayOf()) + byteArrayOf(value.toInt().toByte())
    }

    fun toArrayList(): List<Node?> {
        return (child?.toArrayList() ?: arrayListOf()) + arrayListOf<Node?>(this)
    }
}