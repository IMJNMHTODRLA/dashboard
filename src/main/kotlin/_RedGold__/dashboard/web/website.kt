package _RedGold__.dashboard.web

import com.sun.net.httpserver.HttpServer
import org.bukkit.World
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.net.InetSocketAddress

class website {
    companion object {
        @Throws(IOException::class)
        fun start(plugin: Plugin) {
            val server = HttpServer.create(InetSocketAddress(plugin.config.getInt("port", 9999)), 0)
            server.createContext("/", Index())
            server.createContext("/world", World())
            server.executor = null
            server.start()
        }


        fun getWorldSize(world: World): Long {
            fun folderSize(folder: File): Long {
                return folder.listFiles()?.sumOf { if (it.isDirectory) folderSize(it) else it.length() } ?: 0
            }
            return folderSize(world.worldFolder)
        }
    }
}