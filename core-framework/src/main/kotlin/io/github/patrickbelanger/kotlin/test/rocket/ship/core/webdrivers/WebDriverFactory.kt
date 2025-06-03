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

import io.github.patrickbelanger.kotlin.test.rocket.ship.core.configurations.SeleniumConfiguration
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.types.SupportedBrowser
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.types.SupportedDevice
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.browsers.options.ChromeBrowserOptions
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.browsers.options.EdgeBrowserOptions
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.browsers.options.FirefoxBrowserOptions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class WebDriverFactory(
    private val chromeBrowserOptions: ChromeBrowserOptions,
    private val edgeBrowserOptions: EdgeBrowserOptions,
    private val firefoxBrowserOptions: FirefoxBrowserOptions,
    private val seleniumConfiguration: SeleniumConfiguration
) {
    private val logger : Logger = LoggerFactory.getLogger(WebDriverContext::class.java)

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun get(): WebDriver {
        val target = seleniumConfiguration.webdriver
        logger.info("✨ Instantiate WebDriver for $target")

        return when (target) {
            is SeleniumConfiguration.SupportedTarget.Browser -> createSeleniumWebDriver(target.browser)
            is SeleniumConfiguration.SupportedTarget.Device -> createAppiumWebDriver(target.device)
        }
    }

    private fun createAppiumWebDriver(device: SupportedDevice): WebDriver {
        TODO()
    }

    private fun createSeleniumWebDriver(browser: SupportedBrowser): WebDriver {
        logger.info("🖥️ Starting desktop browser: $browser")
        return if (seleniumConfiguration.grid.enabled) {
            val options = when(browser) {
                SupportedBrowser.CHROME -> chromeBrowserOptions.create()
                SupportedBrowser.EDGE -> edgeBrowserOptions.create()
                SupportedBrowser.FIREFOX -> firefoxBrowserOptions.create()
            }
            logger.info("ℹ️ Using remote WebDriver at ${seleniumConfiguration.grid.host}")
            val webdriver = RemoteWebDriver.builder()
                .addAlternative(options)
                .address(seleniumConfiguration.grid.host)
                .build()
            if (webdriver is FirefoxDriver) {
                webdriver.manage().window().maximize()
            }
            webdriver
        } else {
            when (browser) {
                SupportedBrowser.CHROME -> ChromeDriver(chromeBrowserOptions.create())
                SupportedBrowser.EDGE -> EdgeDriver(edgeBrowserOptions.create())
                SupportedBrowser.FIREFOX -> {
                    val webdriver = FirefoxDriver(firefoxBrowserOptions.create())
                    if (seleniumConfiguration.browserOptions.startMaximized) {
                        webdriver.manage().window().maximize()
                    }
                    webdriver
                }
            }
        }
    }
}