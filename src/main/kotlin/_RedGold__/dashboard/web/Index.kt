package _RedGold__.dashboard.web

import _RedGold__.dashboard.web.website.Companion.getWorldSize
import com.sun.management.OperatingSystemMXBean
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import org.bukkit.Bukkit
import java.io.IOException
import java.lang.management.ManagementFactory

class Index : HttpHandler {
    @Throws(IOException::class)
    override fun handle(exchange: HttpExchange) {
        var html =
            this.javaClass.getResourceAsStream("/index.html")?.
                    bufferedReader().use { it?.readText() ?: "html 파일을 불러오는데 실패하였습니다. :(" }

        //---------------------------------- cpu 사용량 -----------------------------------
        val osBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
        val cpuLoad = osBean.processCpuLoad * 100
        val cpuUsage = "%.2f".format(cpuLoad)

        html = html.replace("%cpu", cpuUsage)
        //---------------------------------- cpu 사용량 끝 ---------------------------------

        //---------------------------------- 메모리 사용량 ----------------------------------
        val memoryMB = Runtime.getRuntime().freeMemory() / (1024 * 1024)

        html = html.replace("%memory", memoryMB.toString())
        //---------------------------------- 메모리 사용량 끝 -------------------------------

        //---------------------------------- 서버 TPS -------------------------------------
        val tps = Bukkit.getServer().tps.clone()

        html = html.replace("%tps", tps[0].toString())
        //---------------------------------- 서버 TPS 끝 ----------------------------------

        //---------------------------------- 서버 접속 인원 --------------------------------
        val playerList = Bukkit.getServer().onlinePlayers.size

        html = html.replace("%players", playerList.toString())
        //---------------------------------- 서버 접속 인원 끝 ------------------------------

        //---------------------------------- 월드 그거 --------------------------------
        val world = Bukkit.getServer().worlds.firstOrNull() ?: return
        val worldSize = getWorldSize(world) / (1024 * 1024 * 1024)

        html = html.replace("%world", world.name)
        html = html.replace("%capacity", worldSize.toString())
        //---------------------------------- 월드 그거 끝 ------------------------------

        exchange.sendResponseHeaders(200, html.length.toLong())
        exchange.responseBody.use { os ->
            os.write(html.toByteArray())
        }
    }
}