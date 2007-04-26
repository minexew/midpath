/*
 * MIDPath - Copyright (C) 2006 Guillaume Legris, Mathieu Legris
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 only, as published by the Free Software Foundation. 
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details. 
 * 
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA 
 * 
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa
 * Clara, CA 95054 or visit www.sun.com if you need additional
 * information or have any questions. 
 */
package org.thenesis.midpath.mmedia;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.microedition.media.Control;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.ToneControl;
import javax.microedition.media.control.VolumeControl;

import sdljava.SDLException;
import sdljava.mixer.MixMusic;
import sdljava.mixer.SDLMixer;

public class SDLMusicPlayer extends BasicPlayer {

	private InputStream stream;
	private String type;
	private MixMusic music;
	private EndOfMediaChecker eomChecker;

	public SDLMusicPlayer() {
	}

	public SDLMusicPlayer(InputStream stream, String type) {
		this.stream = stream;
		this.type = type;
	}

	protected void doClose() {
		try {
			SDLMixer.freeMusic(music);
			SDLMixer.close();
		} catch (SDLException e) {
			//e.printStackTrace();
		} finally {
			eomChecker.stop();
		}

	}

	protected void doDeallocate() {

	}

	protected Control doGetControl(String type) {
		if (type.equals(pkgName + "VolumeControl")) {
			return new SDLVolumeControl();
		}
		return null;
	}

	protected long doGetDuration() {
		return TIME_UNKNOWN;
	}

	protected long doGetMediaTime() {
		return TIME_UNKNOWN;
	}

	protected void doPrefetch() throws MediaException {

	}

	protected void doRealize() throws MediaException {
		try {
			//SDLMixer.openAudio(22050, SDLAudio.AUDIO_S16SYS, 1, 8192);

			// FIXME Remove copy of audio data
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1000];
			int len = 0;
			while ((len = stream.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			music = SDLMixer.loadMUS(baos.toByteArray());

			//music = SDLMixer.loadMUS(stream);
			//music = SDLMixer.loadMUS("Recome.wav");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MediaException(e.getMessage());
			//e.printStackTrace();
		}

	}

	protected long doSetMediaTime(long now) throws MediaException {

		try {
			if (!SDLMixer.setMusicPosition(now)) {
				throw new MediaException("Can't set position on this media");
			}
		} catch (SDLException e) {
			throw new MediaException("Can't set position on this media");
			//e.printStackTrace();
		}

		return now;
	}

	protected boolean doStart() {

		if (SDLMixer.pausedMusic()) {
			SDLMixer.resumeMusic();
			return true;
		}

		try {
			SDLMixer.playMusic(music, 0);
			
			eomChecker = new EndOfMediaChecker();
			eomChecker.start();
			return true;
		} catch (SDLException e) {
			//e.printStackTrace();
			return false;
		}

	}

	protected void doStop() throws MediaException {
		SDLMixer.pauseMusic();
	}

	class SDLToneControl implements ToneControl {

		public void setSequence(byte[] sequence) {

			if (sequence == null)
				throw new IllegalArgumentException("null sequence");

			if ((state == Player.PREFETCHED) || (state == Player.STARTED))
				throw new IllegalStateException("Prefetched or Started");

			// TODO Auto-generated method stub
		}

	}

	class SDLVolumeControl implements VolumeControl {

		public int oldLevel = 0;
		boolean muted = false;

		public int getLevel() {

			if (muted) {
				return oldLevel;
			}

			try {
				return SDLMixer.volumeMusic(-1);
			} catch (SDLException e) {
			}

			return 0;
		}

		public boolean isMuted() {
			return (getLevel() == 0) ? true : false;
		}

		public int setLevel(int level) {

			if (muted) {
				oldLevel = level;
				return level;
			}

			try {
				return SDLMixer.volumeMusic(level);
			} catch (SDLException e) {
			}
			// Should not occur
			return 0;
		}

		public void setMute(boolean mute) {

			if (mute) {
				oldLevel = getLevel();
				setLevel(0);
				muted = true;
			} else {
				setLevel(oldLevel);
				muted = false;
			}

		}

	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public void setType(String type) {
		this.type = type;
	}

	protected boolean isEndOfMediaReached() {
		return !SDLMixer.playingMusic();
	}

}
