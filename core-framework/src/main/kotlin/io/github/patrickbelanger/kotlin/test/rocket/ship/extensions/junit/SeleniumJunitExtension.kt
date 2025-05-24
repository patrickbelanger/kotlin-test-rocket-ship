package io.github.patrickbelanger.kotlin.test.rocket.ship.extensions.junit

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.slf4j.LoggerFactory

class SeleniumJunitExtension : AfterEachCallback, BeforeEachCallback {
    private val logger = LoggerFactory.getLogger(SeleniumJunitExtension::class.java)

    override fun afterEach(context: ExtensionContext) {
        logger.info("🚀 Prepare to launch test: ${context.displayName}")
    }

    override fun beforeEach(context: ExtensionContext) {
        logger.info("🧹 Cleaning up test: ${context.displayName}")
    }
}