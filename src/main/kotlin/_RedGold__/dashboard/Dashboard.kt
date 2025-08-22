package _RedGold__.dashboard

import _RedGold__.dashboard.colorFunc.Companion.gc
import _RedGold__.dashboard.command.dashboardCommand
import _RedGold__.dashboard.web.website.Companion.start
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Dashboard : JavaPlugin() {

    override fun onLoad() {
        saveDefaultConfig()
    }

    override fun onEnable() {
        getCommand("dashboard")!!.setExecutor(dashboardCommand(this))

        val t = Thread {
            start(this)
        }
        t.start()

        Bukkit.getConsoleSender().sendMessage(gc(
            this.config.getString("prefix", "&8[&bDashboard&8] ") + "&aDashboard 플러그인이 활성화가 되었습니다!"
        ))
    }

    override fun onDisable() {
        Bukkit.getConsoleSender().sendMessage(gc(
            this.config.getString("prefix", "&8[&bDashboard&8] ") + "&cDashboard 플러그인이 비활성화가 되었습니다!"
        ))
    }
}
