package puzzles

import PuzzleDay


enum class PulseType{
    HIGH,
    LOW
}

class Day_20: PuzzleDay {
    override fun puzzleOne(input: String): Any? {
        val modulesMap = mutableMapOf<String, Module>()

        val separatedInput = input
            .split("\r\n")
            .map {
                val identifier = Regex(".* -").find(it)!!.value
                    .removeSuffix(" -")
                val packetsToSend = Regex("> .*").find(it)!!.value.removePrefix("> ")
                    .split(", ")

                val module = when(identifier[0]){
                    '%' -> FlipFlop(identifier
                        .removePrefix("&")
                        .removePrefix("%"), packetsToSend)
                    '&' -> Conjunction(identifier
                        .removePrefix("&")
                        .removePrefix("%"), packetsToSend)
                    else -> BroadCaster(identifier
                        .removePrefix("&")
                        .removePrefix("%"), packetsToSend)
                }

                modulesMap[identifier
                    .removePrefix("&")
                    .removePrefix("%")] = module
            }

        val pulseProxy = PulseProxy()

        val listOfConjunction = modulesMap.values
            .filter { it.name.startsWith("&") }
            .map { it.name.removePrefix("&") }

        //Add modules to conjunction input
        modulesMap
            .forEach { t, u ->
                val findedModules = u.modulesToSend.filter { it in listOfConjunction }
               if(findedModules.isNotEmpty()){
                   (modulesMap["&"+findedModules[0]] as Conjunction).modulesToRemember.add(u.name)
               }
            }

        repeat(3){
            pulseProxy.addToQueue(PacketPulse("", "broadcaster", PulseType.LOW))
            do {
                val modulePacket = pulseProxy.consumeModulePacket()
                modulesMap[modulePacket.moduleToReceive]
                    ?.also {
                        it.receivePulse(modulePacket, pulseProxy)
                    }
            }while (pulseProxy.queue.isNotEmpty())
            println()
        }

        return pulseProxy.lowerPulseConter * pulseProxy.highPulseCounter
    }

    class PulseProxy{
        var lowerPulseConter = 0
        var highPulseCounter = 0

        val queue = mutableListOf<PacketPulse>()
        fun addToQueue(packetPulse: PacketPulse){
            println("${packetPulse.moduleThatSented} -${packetPulse.pulseType}-> ${packetPulse.moduleToReceive}")
            when(packetPulse.pulseType){
                PulseType.HIGH -> highPulseCounter++
                PulseType.LOW -> lowerPulseConter++
            }
            queue.add(packetPulse)
        }

        fun consumeModulePacket() = queue.removeFirst()
    }

    abstract class Module(val name: String, val modulesToSend: List<String>){
        abstract fun receivePulse(packetPulse: PacketPulse, pulseProxy: PulseProxy)
    }

    class BroadCaster(name: String, modulesToSend: List<String>)
        : Module(name, modulesToSend){
        override fun receivePulse(packetPulse: PacketPulse, pulseProxy: PulseProxy) {
            modulesToSend.forEach {module ->
                pulseProxy.addToQueue(
                    PacketPulse(name, module, PulseType.LOW)
                )
            }
        }
    }

    class Conjunction(name: String, modulesToSend: List<String>)
        : Module(name, modulesToSend){

        var modulesToRemember = mutableListOf<String>()

        val stateOfModules: MutableMap<String, PulseType> by lazy {
            modulesToRemember.map {
                it to PulseType.LOW
            }.toMap().toMutableMap()
        }

        override fun receivePulse(packetPulse: PacketPulse, pulseProxy: PulseProxy){
            stateOfModules[packetPulse.moduleThatSented] = packetPulse.pulseType

            val pulseToSend = if(stateOfModules.all { it.value == PulseType.HIGH }) PulseType.LOW else PulseType.HIGH

            modulesToSend.forEach { module ->
                pulseProxy.addToQueue(
                    PacketPulse(name, module, pulseToSend)
                )
            }
        }
    }

    class FlipFlop(name: String, modulesToSend: List<String>)
        : Module(name, modulesToSend){
        var on = false

        override fun receivePulse(packetPulse: PacketPulse, pulseProxy: PulseProxy){
            if(packetPulse.pulseType == PulseType.HIGH) return

            on = !on

            val pulseToSend = if(on) PulseType.HIGH else PulseType.LOW

            modulesToSend.forEach { module ->
                pulseProxy.addToQueue(
                    PacketPulse(name, module, pulseToSend)
                )
            }
        }
    }

    data class PacketPulse(val moduleThatSented: String, val moduleToReceive: String, val pulseType: PulseType)

    override fun puzzleTwo(input: String): Any? {
        TODO("Not yet implemented")
    }
}