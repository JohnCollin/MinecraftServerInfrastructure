/*
 * MIT License
 *
 * Copyright (c) 2020 Collin Johnson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.constexpr.infrastructurecore;

import com.constexpr.infrastructurecore.api.InfrastructureCoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * InfrastructureCorePluginHandle is the default handle for the JavaPlugin.
 * <p>
 * Classes that extend org.bukkit.plugin.java.JavaPlugin are the entry point
 * and point of interaction of the plugin. This class handles the API-specified
 * methods in order to interact with a Spigot/Paper server.
 *
 * @author constexpr
 * @version 1.0.0-ALPHA
 * @since 1.0.0-ALPHA
 */
public final class InfrastructureCorePluginHandle extends JavaPlugin {
    public static Logger LOGGER;

    static {
        LOGGER = Bukkit.getLogger();

        LOGGER.info("Initializing InfrastructureCoreAPI...");
        InfrastructureCoreAPI.initialize();
    }

    /**
     * Constructor for InfrastructureCorePluginHandle.enabled
     * <p>
     * The purpose of the constructor is in case plugins who have InfrastructureCore
     * as a dependency need to access the enabled member for information if the
     * InfrastructureCoreAPI is enabled.
     *
     * @since 1.0.0-ALPHA
     */
    public InfrastructureCorePluginHandle() { }

    /**
     * onEnable is the handler for the plugin enable event.
     * <p>
     * This is the definitive entry point of the plugin. When the server loads
     * the plugin JAR file, the plugin.yml file directs the server to this class's
     * function.
     *
     * @since 1.0.0-ALPHA
     */
    @Override
    public void onEnable() {
        LOGGER.info("Updating InfrastructureCoreAPI with most recent instance...");
        InfrastructureCoreAPI.setInfrastructureCorePluginHandle(this);
    }

    /**
     * onDisable is the handler for the plugin disable event.
     * <p>
     * This is the definitive exit point of the plugin. When the server's plugin
     * manager calls to disable the plugin, this class function is called.
     *
     * @since 1.0.0-ALPHA
     */
    @Override
    public void onDisable() { }
}
