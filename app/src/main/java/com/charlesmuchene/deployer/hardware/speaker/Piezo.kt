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

package com.charlesmuchene.deployer.hardware.speaker

import com.charlesmuchene.deployer.utilities.loge
import com.google.android.things.contrib.driver.pwmspeaker.Speaker
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.atomic.AtomicBoolean


/**
 * A handle to a piezo speaker
 */
class Piezo(pin: String) : Speaker(pin) {

    private val job = Job()
    private val isPlaying = AtomicBoolean()

    /**
     * Play the sample melody
     */
    fun play() {

        if (isPlaying.get()) return
        isPlaying.set(true)

        launch(parent = job) {
            try {
                for (index in 0..7) {

                    // To calculate the note duration, take one second divided by the note type.
                    // e.g. quarter note = 1000 / 4, eighth note = 1000/8, etc.
                    val noteDuration = 1000 / noteDurations[index]

                    val note = melody[index]
                    if (note != 0) play(note.toDouble())
                    delay(noteDuration)

                    stop()

                    // To distinguish the notes, set a minimum time between them.
                    // 50% of the note's duration seems to work well
                    val pauseBetweenNotes = (noteDuration * 0.5).toLong()
                    delay(pauseBetweenNotes)

                }
            } catch (e: Exception) {
                loge(e.localizedMessage)
            }
        }
    }

    override fun stop() {
        try {
            super.stop()
        } catch (e: Exception) {
            loge(e.localizedMessage)
        }
    }

    companion object {

        // Note durations: 4 = quarter note, 8 = eighth note, nk.
        var noteDurations = intArrayOf(4, 8, 8, 4, 4, 4, 4, 4)

        var melody = intArrayOf(NOTE_C4, NOTE_G3, NOTE_G3, NOTE_A3, NOTE_G3, 0, NOTE_B3, NOTE_C4)

    }
}