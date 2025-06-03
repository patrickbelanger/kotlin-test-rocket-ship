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

package io.github.patrickbelanger.kotlin.test.rocket.ship.core.interactions.elements

import io.github.patrickbelanger.kotlin.test.rocket.ship.core.interactions.ElementWrapper
import org.openqa.selenium.By
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ButtonInteraction(by: By) : ElementWrapper(by) {
    private val logger: Logger = LoggerFactory.getLogger(ButtonInteraction::class.java)

    fun isEnabled(): Boolean {
        return runStep("Is button enabled? - Locator: $by ", logger) {
            element.isEnabled
        }.first
    }

    fun click(): Boolean {
        return runStep("Click on button - Locator: $by", logger) {
            element.click()
        }.first
    }
}