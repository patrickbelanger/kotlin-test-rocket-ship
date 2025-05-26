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
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.configurations.SeleniumConfiguration.SupportedTarget
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.types.SupportedBrowser
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.browsers.options.ChromeBrowserOptions
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.browsers.options.EdgeBrowserOptions
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.browsers.options.FirefoxBrowserOptions
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.AbstractDriverOptions
import kotlin.reflect.KClass

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class WebDriverFactoryTest {
    @MockK
    private lateinit var chromeBrowserOptions: ChromeBrowserOptions

    @MockK
    private lateinit var edgeBrowserOptions: EdgeBrowserOptions

    @MockK
    private lateinit var firefoxBrowserOptions: FirefoxBrowserOptions

    @MockK
    private lateinit var seleniumConfiguration: SeleniumConfiguration

    @InjectMockKs
    private lateinit var webDriverFactory: WebDriverFactory

    private lateinit var webDriver: WebDriver

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        clearAllMocks()
    }

    @AfterEach
    fun tearDown() {
        webDriver.quit()
    }

    companion object {
        @JvmStatic
        fun supportedBrowsers(): List<Arguments> = listOf(
            Arguments.of(SupportedBrowser.CHROME, ChromeOptions(), ChromeDriver::class),
            Arguments.of(SupportedBrowser.FIREFOX, FirefoxOptions(), FirefoxDriver::class),
            Arguments.of(SupportedBrowser.EDGE, EdgeOptions(), EdgeDriver::class)
        )
    }

    @ParameterizedTest
    @MethodSource("supportedBrowsers")
    fun `should webDriverFactory instantiate driver instance based on browser`(
        browser: SupportedBrowser,
        options: AbstractDriverOptions<*>,
        expectedClass: KClass<out WebDriver>
    ) {
        /* Arrange */
        every { seleniumConfiguration.webdriver } returns SupportedTarget.Browser(browser)
        every { seleniumConfiguration.grid.enabled } returns false
        every { seleniumConfiguration.browserOptions.startMaximized } returns true

        when (browser) {
            SupportedBrowser.CHROME -> every { chromeBrowserOptions.create() } returns options as ChromeOptions
            SupportedBrowser.FIREFOX -> every { firefoxBrowserOptions.create() } returns options as FirefoxOptions
            SupportedBrowser.EDGE -> every { edgeBrowserOptions.create() } returns options as EdgeOptions
        }

        /* Act */
        webDriver = webDriverFactory.webDriver()

        /* Assert */
        assertNotNull(webDriver)
        assertTrue(expectedClass.isInstance(webDriver))
    }
}