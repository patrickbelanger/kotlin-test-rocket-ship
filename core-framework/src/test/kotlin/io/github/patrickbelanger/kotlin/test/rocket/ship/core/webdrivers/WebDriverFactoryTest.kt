package io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers

import io.github.patrickbelanger.kotlin.test.rocket.ship.core.configurations.SeleniumConfiguration
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.types.SupportedBrowser
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.browsers.options.ChromeBrowserOptions
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.browsers.options.EdgeBrowserOptions
import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.browsers.options.FirefoxBrowserOptions
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

@ExtendWith(MockKExtension::class)
class WebDriverFactoryTest {
    @MockK
    private lateinit var chromeBrowserOptions: ChromeBrowserOptions

    @MockK
    private lateinit var chromeDriver: WebDriver

    @MockK
    private lateinit var edgeBrowserOptions: EdgeBrowserOptions

    @MockK
    private lateinit var firefoxBrowserOptions: FirefoxBrowserOptions

    @MockK
    private lateinit var seleniumConfiguration: SeleniumConfiguration

    @InjectMockKs
    private lateinit var webDriverFactory: WebDriverFactory

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should webDriverFactory instantiate ChromeDriver instance`() {
        /* Arrange */
        every { seleniumConfiguration.webdriver } returns eitherLeft(SupportedBrowser.CHROME)
        every { seleniumConfiguration.grid.enabled } returns false
        every { seleniumConfiguration.browserOptions.startMaximized } returns true

        every { webDriverFactory.webDriver() } returns chromeDriver

        /* Act */
        val result = webDriverFactory.webDriver()

        /* Assert */
        assertNotNull(result)
        assertTrue(result is ChromeDriver)
    }
}