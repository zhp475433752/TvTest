/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dyxc.tvtest.video.exo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.media.PlaybackTransportControlGlue;

import java.io.File;
import java.util.HashMap;

/**
 * Sample PlaybackSeekDataProvider that reads bitmaps stored on disk.
 * e.g. new PlaybackSeekDiskDataProvider(duration, 1000, "/sdcard/frame_%04d.jpg")
 * Expects the seek positions are 1000ms interval, snapshots are stored at
 * /sdcard/frame_0001.jpg, ...
 */
public class PlaybackSeekDiskDataProvider extends PlaybackSeekAsyncDataProvider {

    final Paint mPaint;
    final String mPathPattern;
    MediaMetadataRetriever metadataRetriever;
    String mVideoUrl;

    PlaybackSeekDiskDataProvider(String url, long duration, long interval, String pathPattern) {
        mVideoUrl = url;
        mPathPattern = pathPattern;
        int size = (int) (duration / interval) + 1;
        long[] pos = new long[size];
        for (int i = 0; i < pos.length; i++) {
            pos[i] = i * duration / pos.length;
        }
        setSeekPositions(pos);
        mPaint = new Paint();
        mPaint.setTextSize(16);
        mPaint.setColor(Color.BLUE);

        metadataRetriever = new MediaMetadataRetriever();
        if (Build.VERSION.SDK_INT >= 14) {
            metadataRetriever.setDataSource(mVideoUrl, new HashMap<String, String>());
        } else {
            metadataRetriever.setDataSource(mVideoUrl);
        }

    }

    protected Bitmap doInBackground(Object task, int index, long positionMs) {
        Log.d(TAG, "doInBackground --- index - " + index +" - positionMs - "+positionMs);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            // Thread might be interrupted by cancel() call.
        }
        if (isCancelled(task)) {
            return null;
        }

        String path = String.format(mPathPattern, (index + 1));
        if (new File(path).exists()) {
            return BitmapFactory.decodeFile(path);
        } else {
//            Bitmap bmp = Bitmap.createBitmap(160, 160, Bitmap.Config.RGB_565);
//            Canvas canvas = new Canvas(bmp);
//            canvas.drawColor(Color.YELLOW);
//            canvas.drawText(path, 10, 80, mPaint);
//            canvas.drawText(Integer.toString(index), 10, 150, mPaint);
            Bitmap videoFrame = getVideoFrame(positionMs * 1000);

            return videoFrame;//参数为微秒
        }
    }

    /**
     * Helper function to set a demo seek provider on PlaybackTransportControlGlue based on
     * duration.
     */
    public static void setDemoSeekProvider(Context context, final PlaybackTransportControlGlue glue, final String videoUrl, final String videoId) {
        String cachePath = context.getCacheDir().toString();
        String pathPattern = cachePath + "/"+videoId+"/frame_%04d.jpg";
        if (glue.isPrepared()) {
            glue.setSeekProvider(new PlaybackSeekDiskDataProvider(
                    videoUrl,
                    glue.getDuration(),
                    glue.getDuration() / 100,
                    pathPattern));
        } else {
            glue.addPlayerCallback(new PlaybackGlue.PlayerCallback() {
                @Override
                public void onPreparedStateChanged(PlaybackGlue glue) {
                    if (glue.isPrepared()) {
                        glue.removePlayerCallback(this);
                        PlaybackTransportControlGlue transportControlGlue =
                                (PlaybackTransportControlGlue) glue;
                        transportControlGlue.setSeekProvider(new PlaybackSeekDiskDataProvider(
                                videoUrl,
                                transportControlGlue.getDuration(),
                                transportControlGlue.getDuration() / 100,
                                pathPattern));
                    }
                }
            });
        }
    }

    private Bitmap getVideoFrame(long timeUs) {
        return metadataRetriever.getFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
    }

}
