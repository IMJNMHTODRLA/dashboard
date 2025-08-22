package _RedGold__.dashboard.command

import _RedGold__.dashboard.colorFunc.Companion.gc
import com.sun.management.OperatingSystemMXBean
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.awt.Desktop
import java.lang.management.ManagementFactory
import java.net.URI

class dashboardCommand(private val plugin: Plugin) : CommandExecutor, TabCompleter {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): Boolean {
        val prefix = plugin.config.getString("prefix", "&8[&bDashboard&8] ");

        if (!sender.hasPermission("dashboard.command")) {
            sender.sendMessage(gc(
                prefix +
                "&e당신은 &bdashboard.command &e펄미션이 없어 하위 명령어를 실행할 수 &c없습니다&e." +
                "&8플러그인 다운로드: https://github.com/IMJNMHTODRLA/dashboard"
            ))
            return true
        }

        if (args.size == 0) {
            sender.sendMessage(gc(
                prefix +
                "&e당신은 &bdashboard.command &e펄미션이 있어 하위 명령어를 실행할 수 &a있습니다&e." +
                "&8플러그인 다운로드: https://github.com/IMJNMHTODRLA/dashboard"
            ))
            return true
        }

        if (args[0] == "reload") {
            plugin.reloadConfig()
            sender.sendMessage(gc(
                prefix +
                "&aconfig.yml을 다시 불러왔습니다."
            ))
        } else if (args[0] == "tps") {
            val tps = Bukkit.getTPS().clone()
            sender.sendMessage(gc(
                prefix +
                "&e1분전 TPS: &b${"%.2f".format(tps[0])}"
            ))
        } else if (args[0] == "cpu") {
            val osBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
            val cpuLoad = osBean.processCpuLoad * 100
            val cpuUsage = "%.2f".format(cpuLoad)

            sender.sendMessage(gc(
                prefix +
                "&ecpu 사용량: &b${cpuUsage}%"
            ))
        } else if (args[0] == "memory") {
            val memoryMB = Runtime.getRuntime().freeMemory() / (1024 * 1024)

            sender.sendMessage(gc(
                prefix +
                "&ememory 사용량: &b${memoryMB} MB"
            ))
        } else if (args[0] == "player") {
            val playerList = Bukkit.getServer().onlinePlayers.size

            sender.sendMessage(gc(
                prefix +
                "&e접속 중인 플레이어: &b${playerList}명"
            ))
        } else if (args[0] == "web" && sender !is Player) {
            try {
                val url = "http://localhost:${plugin.config.getInt("port", 9999)}"

                if (!Desktop.isDesktopSupported()) {
                    sender.sendMessage(gc(
                        "${prefix}&c이 OS에서는 브라우저 열기가 지원되지 않습니다."
                    ))
                }

                Desktop.getDesktop().browse(URI(url))
            } catch (e: Exception) {
                sender.sendMessage(gc(
                    "${prefix}&c사이트를 열던 중 오류가 발생했습니다: ${e.message}"
                ))
            }
        } else {
            sender.sendMessage(gc(
                prefix +
                "&e당신은 &bdashboard.command &e펄미션이 있어 하위 명령어를 실행할 수 &a있습니다&e." +
                "&8플러그인 다운로드: https://github.com/IMJNMHTODRLA/dashboard"
            ))
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (args.size == 1 && sender.hasPermission("dashboard.command")) {
            return (
                if (sender is Player) arrayListOf("reload", "tps", "cpu", "memory", "player")
                else arrayListOf("reload", "tps", "cpu", "memory", "player", "web")
            )
        }

        return mutableListOf()
    }
}