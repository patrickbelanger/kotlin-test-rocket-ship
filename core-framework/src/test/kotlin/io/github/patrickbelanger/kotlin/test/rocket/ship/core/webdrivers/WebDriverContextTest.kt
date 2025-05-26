package io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers

import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.WebDriver
import kotlin.test.assertFailsWith

@ExtendWith(MockKExtension::class)
class WebDriverContextTest {
    @RelaxedMockK
    lateinit var webDriver: WebDriver

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        clearAllMocks()
    }

    @AfterEach
    fun tearDown() {
        if (WebDriverContext.isInitialized()) {
            WebDriverContext.remove()
        }
    }

    @Test
    fun `should set and get WebDriver in same thread`() {
        /* Act */
        WebDriverContext.set(webDriver)

        /* Assert */
        assertSame(webDriver, WebDriverContext.get())
    }

    @Test
    fun `should throw when getting WebDriver if not set`() {
        /* Act */
        val ex = assertFailsWith<IllegalStateException> { WebDriverContext.get() }

        /* Assert */
        assertTrue(ex.message!!.contains("WebDriver isn't set"))
    }

    @Test
    fun `should remove WebDriver and call quit`() {
        /* Act */
        WebDriverContext.set(webDriver)

        WebDriverContext.remove()

        /* Assert */
        verify(exactly = 1) { webDriver.quit() }
        assertFalse(WebDriverContext.isInitialized())
    }

    @Test
    fun `should detect if WebDriver is initialized`() {
        /* Act & Assert */
        assertFalse(WebDriverContext.isInitialized())
        WebDriverContext.set(webDriver)
        assertTrue(WebDriverContext.isInitialized())
    }
}
