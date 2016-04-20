package com.example.testscreenshot;

import java.io.IOException;

import android.content.Intent;
import android.media.projection.MediaProjection;
import android.util.DisplayMetrics;
import android.util.Log;

public class screenShot4 {/*
	private void startScreenRecord(final Intent intent) {
		Log.v("park", "startScreenRecord:sMuxer=" + sMuxer);
		synchronized (sSync) {
			if (sMuxer == null) {
				final int resultCode = intent.getIntExtra(EXTRA_RESULT_CODE, 0);
				// get MediaProjection
				final MediaProjection projection = mMediaProjectionManager
						.getMediaProjection(resultCode, intent);
				if (projection != null) {
					final DisplayMetrics metrics = getResources()
							.getDisplayMetrics();
					final int density = metrics.densityDpi;

					if (DEBUG)
						Log.v(TAG, "startRecording:");
					try {
						sMuxer = new MediaMuxerWrapper(".mp4"); // if you record
																// audio only,
																// ".m4a" is
																// also OK.
						if (true) {
							// for screen capturing
							new MediaScreenEncoder(sMuxer,
									mMediaEncoderListener, projection,
									metrics.widthPixels, metrics.heightPixels,
									density);
						}
						if (true) {
							// for audio capturing
							new MediaAudioEncoder(sMuxer, mMediaEncoderListener);
						}
						sMuxer.prepare();
						sMuxer.startRecording();
					} catch (final IOException e) {
						Log.e(TAG, "startScreenRecord:", e);
					}
				}
			}
		}
	}

	private void stopScreenRecord() {
		if (DEBUG)
			Log.v(TAG, "stopScreenRecord:sMuxer=" + sMuxer);
		synchronized (sSync) {
			if (sMuxer != null) {
				sMuxer.stopRecording();
				sMuxer = null;
				// you should not wait here
			}
		}
	}

	private void pauseScreenRecord() {
		synchronized (sSync) {
			if (sMuxer != null) {
				sMuxer.pauseRecording();
			}
		}
	}

	private void resumeScreenRecord() {
		synchronized (sSync) {
			if (sMuxer != null) {
				sMuxer.resumeRecording();
			}
		}
	}
*/}
