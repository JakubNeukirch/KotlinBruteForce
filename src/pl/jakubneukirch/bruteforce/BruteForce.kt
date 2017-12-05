package pl.jakubneukirch.bruteforce

class BruteForce {
    private var rootNode: Node

    private var onEach: (key: ByteArray) -> Unit = {}

    constructor(keyLevel: Int, possibleBytes: List<List<Byte>>) {
        this.rootNode = Node.newInstance(keyLevel, possibleBytes)
    }

    constructor(levels: Int, charset: String){
        rootNode = Node.Companion.newInstance(levels, charset)
    }

    fun run() {
        var isLast: Boolean
        var key: ByteArray
        do{
            key = rootNode.toByteArray()
            onEach(key)
            isLast = rootNode.isMax()
            rootNode.iterate()
        }while (!isLast)
    }

    fun doOnEach(onEach: (key: ByteArray) -> Unit): BruteForce {
        this.onEach = onEach
        return this
    }

}