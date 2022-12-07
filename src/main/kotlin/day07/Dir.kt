package day07

class Dir(val parent: Dir?) {
    private val subdirectories =  HashMap<String, Dir>()
    private var fileSize = 0

    fun addDir(name: String): Dir =
        Dir(this).also{
            subdirectories[name] = it
        }

    fun addFile(size: Int){
        fileSize += size
    }

    //don't get this before entire tree is built
    val size: Int by lazy { fileSize + subdirectories.values.sumOf{ it.size }  }

    operator fun get(subDir: String): Dir = subdirectories[subDir]!!
}