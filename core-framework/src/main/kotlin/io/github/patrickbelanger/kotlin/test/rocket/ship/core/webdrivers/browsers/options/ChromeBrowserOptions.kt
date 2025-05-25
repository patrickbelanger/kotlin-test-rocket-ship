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
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.stereotype.Component

@Component
class ChromeBrowserOptions(config: SeleniumConfiguration) : ChromiumBasedBrowserOptions(config) {
    fun create(): ChromeOptions {
        val options = ChromeOptions()
        configureAcceptInsecuredCerts(options)
        configureDisableExtensions(options)
        configureDisablePopupBlocking(options)
        configureExperimentalPreferences(options)
        configurePageLoadStrategy(options)
        configureStartMaximized(options)

        return options
    }

    fun configureExperimentalPreferences(options: ChromeOptions): Map<String, Boolean> {
        val prefs = mapOf(
            "credentials_enable_service" to config.browserOptions.credentialsEnableService,
            "profile.password_manager_enabled" to config.browserOptions.passwordManagerLeakDetection
        )
        options.setExperimentalOption("prefs", prefs)
        return prefs
    }
}