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
import org.openqa.selenium.chrome.ChromeOptions

@ExtendWith(MockKExtension::class)
class ChromeBrowserOptionsTest {
    @MockK
    lateinit var seleniumConfiguration: SeleniumConfiguration

    @MockK
    lateinit var browserOptions: SeleniumConfiguration.SeleniumBrowserOptionsConfiguration

    @InjectMockKs
    lateinit var chromeBrowserOptions: ChromeBrowserOptions

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        clearAllMocks()
    }

    @Test
    fun `should create ChromeOptions with expected preferences`() {
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
        val options: ChromeOptions = chromeBrowserOptions.create()
        val prefs = chromeBrowserOptions.configureExperimentalPreferences(options)

        /* Assert */
        assertNotNull(options)
        assertEquals(true, prefs["credentials_enable_service"])
        assertEquals(false, prefs["profile.password_manager_enabled"])
        assertEquals(true, options.getCapability("acceptInsecureCerts"))
        assertEquals(PageLoadStrategy.NORMAL, options.getCapability("pageLoadStrategy"))
        println(options.capabilityNames)
    }
}