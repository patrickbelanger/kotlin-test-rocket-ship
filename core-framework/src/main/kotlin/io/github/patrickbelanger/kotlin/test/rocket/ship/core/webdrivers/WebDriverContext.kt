// Copyright (C) 2025 Patrick Bélanger
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers

import org.openqa.selenium.WebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object WebDriverContext {
    private val logger : Logger = LoggerFactory.getLogger(WebDriverContext::class.java)
    private val context: ThreadLocal<WebDriver> = ThreadLocal()

    fun set(webDriver: WebDriver) {
        logger.info("🆕 Set WebDriver for thread: ${Thread.currentThread().name}")
        context.set(webDriver)
    }

    fun get(): WebDriver {
        return context.get() ?: throw IllegalStateException("""
            ❌ Oops! 🤦‍♂️🤦‍♀️ Looks like the WebDriver isn't set for this thread.
            Make sure your test class extends FrameworkBase() to initialize it properly.
        """.trimIndent())
    }

    fun remove() {
        logger.info("🗑️ Remove WebDriver for thread: ${Thread.currentThread().name}")
        context.get().quit()
        context.remove()
    }

    fun isInitialized(): Boolean {
        return context.get() != null
    }
}