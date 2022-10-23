package io.github.jan.supabase.plugins

/**
 * The plugin manager is used to manage installed plugins
 */
class PluginManager(val installedPlugins: Map<String, SupabasePlugin>) {

    /**
     * Retrieve an installed plugin by its [key] or null if no plugin with the given key is installed
     */
    inline fun <reified Plugin: SupabasePlugin, Config, Provider : SupabasePluginProvider<Config, Plugin>> getPluginOrNull(provider: Provider): Plugin? {
        return installedPlugins[provider.key] as? Plugin
    }

    /**
     * Retrieve an installed plugin by its [key] or throw an [IllegalArgumentException] if no plugin with the given key is installed
     */
    inline fun <reified Plugin: SupabasePlugin, Config, Provider : SupabasePluginProvider<Config, Plugin>> getPlugin(provider: Provider): Plugin {
        return getPluginOrNull(provider) ?: throw IllegalStateException("Plugin ${provider.key} not installed or not of type ${Plugin::class}")
    }

    /**
     * Closes all installed plugins
     */
    suspend inline fun closeAllPlugins() {
        installedPlugins.values.forEach { it.close() }
    }

}