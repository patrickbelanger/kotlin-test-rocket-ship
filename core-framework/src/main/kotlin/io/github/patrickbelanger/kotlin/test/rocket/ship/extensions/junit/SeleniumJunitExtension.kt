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

package io.github.patrickbelanger.kotlin.test.rocket.ship.extensions.junit

import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.WebDriverContext
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.WebDriverFactory
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.slf4j.LoggerFactory
import org.springframework.test.context.junit.jupiter.SpringExtension

class SeleniumJunitExtension : AfterEachCallback, BeforeEachCallback {
    private val logger = LoggerFactory.getLogger(SeleniumJunitExtension::class.java)

    override fun beforeEach(context: ExtensionContext) {
        logger.info("🚀 Prepare to launch test: ${context.displayName}")
        val applicationContext = SpringExtension.getApplicationContext(context)
        val webDriverFactory = applicationContext.getBean(WebDriverFactory::class.java)

        if (!WebDriverContext.isInitialized()) {
            val driver = webDriverFactory.get()
            WebDriverContext.set(driver)
        }
    }

    override fun afterEach(context: ExtensionContext) {
        logger.info("🧹 Cleaning up test: ${context.displayName}")
        if (WebDriverContext.isInitialized()) {
            WebDriverContext.get().quit()
            WebDriverContext.remove()
        }
    }
}