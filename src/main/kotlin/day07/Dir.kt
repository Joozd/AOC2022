package day07

class Dir(val parent: Dir?) {
    private val subdirectories =  HashMap<String, Dir>()
    private val files = HashMap<String, Int>()

    fun addDir(name: String): Dir =
        Dir(this).also{
            subdirectories[name] = it
        }

    fun addFile(name:String, size: Int){
        files[name] = size
    }

    fun size(): Int = files.values.sum() + subdirectories.values.sumOf{ it.size() }

    operator fun get(subDir: String): Dir = subdirectories[subDir]!!
}