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

package io.github.patrickbelanger.kotlin.test.rocket.ship.core.interactions

import io.github.patrickbelanger.kotlin.test.rocket.ship.core.webdrivers.WebDriverContext
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import java.time.Duration

abstract class SeleniumWrapper {
    open val webDriver: WebDriver
        get() = WebDriverContext.get()

    val wait: WebDriverWait
        get() = WebDriverWait(webDriver, Duration.ofSeconds(10))

    val javaScriptExecutor: JavascriptExecutor
        get() = webDriver as JavascriptExecutor

    inline fun <T> runStep(
        stepDescription: String,
        logger: Logger,
        block: () -> T
    ): T? {
        return runCatching { block() }
            .onSuccess {
                logger.info("✅ [PASSED] - $stepDescription")
            }.onFailure { error ->
                logger.error("❌ [FAILED] - $stepDescription - Error: ${error.message}", error)
            }.getOrNull()
    }
}