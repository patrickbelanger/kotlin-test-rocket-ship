package io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.browsers.options

import io.github.patrickbelanger.kotlin.test.rocket.ship.core.configurations.SeleniumConfiguration
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.firefox.FirefoxOptions

@ExtendWith(MockKExtension::class)
class FirefoxBrowserOptionsTest {
    @MockK
    lateinit var seleniumConfiguration: SeleniumConfiguration

    @MockK
    lateinit var browserOptions: SeleniumConfiguration.SeleniumBrowserOptionsConfiguration

    @InjectMockKs
    lateinit var firefoxBrowserOptions: FirefoxBrowserOptions

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        clearAllMocks()
    }

    @Test
    fun `should create EdgeOptions with expected preferences`() {
        /* Arrange */
        every { seleniumConfiguration.browserOptions } returns browserOptions
        every { browserOptions.credentialsEnableService } returns true
        every { browserOptions.passwordManagerLeakDetection } returns false
        every { browserOptions.acceptInsecureCerts } returns true
        every { browserOptions.disablePopupBlocking } returns true
        every { browserOptions.disableExtensions } returns true
        every { browserOptions.pageLoadStrategy } returns PageLoadStrategy.NORMAL
        every { browserOptions.startMaximized } returns true

        /* Act */
        val options: FirefoxOptions = firefoxBrowserOptions.create()

        /* Assert */
        assertNotNull(options)
        assertEquals(true, options.getCapability("acceptInsecureCerts"))
        assertEquals(PageLoadStrategy.NORMAL, options.getCapability("pageLoadStrategy"))
        assertEquals(5, options.capabilityNames.size)
    }
}