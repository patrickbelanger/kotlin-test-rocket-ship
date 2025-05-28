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

package io.github.patrickbelanger.kotlin.test.rocket.ship.core

import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.WebDriverContext
import io.github.patrickbelanger.kotlin.test.rocket.ship.extensions.junit.SeleniumJunitExtension
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.context.junit.jupiter.SpringExtension

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(
    SpringExtension::class,
    SeleniumJunitExtension::class
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
abstract class FrameworkBaseTest {
    private val logger: Logger = LoggerFactory.getLogger(FrameworkBaseTest::class.java)

    @BeforeEach
    fun setUp(testInfo: TestInfo) {
        logger.info("🚀 Prepare to launch test: ${testInfo.displayName}")
    }

    @AfterEach
    fun tearDown(testInfo: TestInfo) {
        logger.info("🧹 Cleaning up test: ${testInfo.displayName}")
        WebDriverContext.remove()
    }
}