/* Copyright (C) 2018 Charles Muchene
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.charlesmuchene.deployer.ui

import android.app.Activity
import android.os.Bundle
import com.charlesmuchene.deployer.hardware.speaker.Piezo

/**
 * Entry point of the application
 */
class MainActivity : Activity() {

    private lateinit var speaker: Piezo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSpeaker()
        speaker.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroySpeaker()
    }

    /**
     * Create a speaker resource
     */
    private fun setupSpeaker() {
        speaker = Piezo("PWM1")
        speaker.stop()
    }

    /**
     * Release speaker resources
     */
    private fun destroySpeaker() {
        speaker.stop()
        speaker.close()
    }

}
