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

package io.github.patrickbelanger.kotlin.test.rocket.ship.watchers

import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestWatcher
import org.slf4j.LoggerFactory
import java.util.*

class TestResultWatcher : TestWatcher, BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private val logger = LoggerFactory.getLogger(TestResultWatcher::class.java)

    override fun testSuccessful(context: ExtensionContext) {
        logger.info("✅ TEST PASSED: ${context.displayName}")
    }

    override fun testFailed(context: ExtensionContext, cause: Throwable) {
        logger.error("❌ TEST FAILED: ${context.displayName}", cause)
    }

    override fun testAborted(context: ExtensionContext, cause: Throwable) {
        logger.warn("⚠️ TEST ABORTED: ${context.displayName}", cause)
    }

    override fun testDisabled(context: ExtensionContext, reason: Optional<String>) {
        logger.info("🚫 TEST DISABLED: ${context.displayName} – Reason: ${reason.orElse("N/A")}")
    }

    override fun beforeTestExecution(context: ExtensionContext) {
        logger.info("🧪 Starting test: ${context.displayName}")
    }

    override fun afterTestExecution(context: ExtensionContext) {
        logger.info("🧹 Finished test: ${context.displayName}")
    }
}