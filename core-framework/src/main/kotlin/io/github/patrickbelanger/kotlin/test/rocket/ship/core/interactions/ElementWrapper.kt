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

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

abstract class ElementWrapper(
    protected val by: By = By.id(""),
    protected val nameOrId: String? = null,
    protected val index: Int? = null,
    protected val webElement: WebElement? = null
) : SeleniumWrapper() {
    private val logger: Logger = LoggerFactory.getLogger(ElementWrapper::class.java)

    protected fun ariaRole(role: String): String? {
        return runStep("Get aria role from element - Locator: $by", logger) {
            element.ariaRole
        }!!
    }

    protected fun attribute(name: String): String? {
        return runStep("Get attributes from element - Locator: $by", logger) {
            element.getAttribute(name)
        }!!
    }

    protected fun cssValue(propertyName: String): String? {
        return runStep("Get css value from element - Locator: $by", logger) {
            element.getCssValue(propertyName)
        }!!
    }

    protected val element: WebElement
        get() {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by))
        }

    protected val elements: List<WebElement>
        get() {
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by))
        }

    protected fun findElement(): WebElement =
        WebDriverWait(webDriver, Duration.ofSeconds(10))
            .until(ExpectedConditions.presenceOfElementLocated(by))

    protected fun findElements(): List<WebElement> =
        WebDriverWait(webDriver, Duration.ofSeconds(10))
            .until(ExpectedConditions.presenceOfAllElementsLocatedBy(by))

    protected fun isDisplayed(): Boolean {
        return findElement().isDisplayed
    }

    protected fun text(): String? {
        return runStep("Get text from element - Locator: $by", logger) {
            element.text
        }!!
    }
}