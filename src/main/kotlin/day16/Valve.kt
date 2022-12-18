package day16

class Valve(val flowRate: Int, val exits: List<String>) {
    override fun toString() = "Valve fr = $flowRate, exits = $exits"
}