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

package com.constexpr.infrastructurecore.api;

import com.constexpr.infrastructurecore.InfrastructureCorePluginHandle;
import com.constexpr.infrastructurecore.command.InfrastructureCommandManager;

/**
 * InfrastructureCoreAPI is the InfrastructureCore's Main API for utility access.
 *
 * @author constexpr
 * @version 1.0.0-ALPHA
 * @since 1.0.0-ALPHA
 */
public class InfrastructureCoreAPI {
    private static InfrastructureCorePluginHandle infrastructureCorePluginHandle;

    private static InfrastructureCommandManager infrastructureCommandManager;

    /**
     * Initializes the API for internal and external use.
     * @apiNote should only ever be called once per plugin lifecycle.
     *
     * @since 1.0.0-ALPHA
     */
    public static void initialize() {
        // Create and Initialize the API Command Manager.
        infrastructureCommandManager = new InfrastructureCommandManager();
    }

    /**
     * Standard Getter for the Core Plugin Handle.
     *
     * @return Core Plugin Handle
     *
     * @since 1.0.0-ALPHA
     */
    public static InfrastructureCorePluginHandle getInfrastructureCorePluginHandle() {
        return infrastructureCorePluginHandle;
    }

    /**
     * Standard Setter for the Core Plugin Handle.
     *
     * @param infrastructureCorePluginHandle Core Plugin Handle
     *
     * @since 1.0.0-ALPHA
     */
    public static void setInfrastructureCorePluginHandle(InfrastructureCorePluginHandle infrastructureCorePluginHandle) {
        InfrastructureCoreAPI.infrastructureCorePluginHandle = infrastructureCorePluginHandle;

        infrastructureCommandManager.initializeCommands();
    }

    /**
     * Standard Getter for the Command Manager.
     *
     * @return Core Command Manager.
     *
     * @since 1.0.0-ALPHA
     */
    public static InfrastructureCommandManager getInfrastructureCommandManager() {
        return infrastructureCommandManager;
    }
}
