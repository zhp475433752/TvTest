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
import android.text.TextUtils;
import android.util.Log;

import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.media.PlaybackTransportControlGlue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Sample PlaybackSeekDataProvider that reads bitmaps stored on disk.
 * e.g. new PlaybackSeekDiskDataProvider(duration, 1000, "/sdcard/frame_%04d.jpg")
 * Expects the seek positions are 1000ms interval, snapshots are stored at
 * /sdcard/frame_0001.jpg, ...
 */
public class PlaybackSeekDiskDataProvider extends PlaybackSeekAsyncDataProvider {

//    final Paint mPaint;
    final String mPath;
    final String mName;
//    MediaMetadataRetriever metadataRetriever;
    private FFmpegMediaMetadataRetriever fFmpegMediaMetadataRetriever;
    private String mVideoUrl;
    //视频帧数
    private final static int FRAME_COUNT = 50;
    //视频帧宽度
    private final int FRAME_WIDTH = 160;
    //视频帧高度
    private final int FRAME_HEIGHT = 90;
    //视频帧图片本地缓存压缩质量[0, 100]
    private final int FRAME_COMPRESS_QUALITY = 100;

    PlaybackSeekDiskDataProvider(String url, long duration, long interval, String path, String name) {
        mVideoUrl = url;
        mPath = path;
        mName = name;
        int size = (int) (duration / interval) + 1;
        long[] pos = new long[size];
        for (int i = 0; i < pos.length; i++) {
            pos[i] = i * duration / pos.length;
        }
        setSeekPositions(pos);
//        mPaint = new Paint();
//        mPaint.setTextSize(16);
//        mPaint.setColor(Color.BLUE);

//        metadataRetriever = new MediaMetadataRetriever();
//        if (Build.VERSION.SDK_INT >= 14) {
//            metadataRetriever.setDataSource(mVideoUrl, new HashMap<String, String>());
//        } else {
//            metadataRetriever.setDataSource(mVideoUrl);
//        }


        fFmpegMediaMetadataRetriever = new FFmpegMediaMetadataRetriever();
        fFmpegMediaMetadataRetriever.setDataSource(mVideoUrl);
        fFmpegMediaMetadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
        fFmpegMediaMetadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);

        // 提前加载部分预览帧
        for (int k = 0; k < FRAME_COUNT / 2; k++) {
            prefetch(k, true);
        }
    }

    protected Bitmap doInBackground(Object task, int index, long positionMs) {

//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException ex) {
//            // Thread might be interrupted by cancel() call.
//        }
        if (isCancelled(task)) {
            return null;
        }

        String pathName = mPath + "/" + String.format(mName, index);
        Log.e("--获取视频帧--", "开始在后台执行 - index - " + index +" - positionMs - "+positionMs + ", path = "+pathName);
        if (new File(pathName).exists()) {
            Log.e("--获取视频帧--", "图片在Disk存在 - index - " + index );
            return BitmapFactory.decodeFile(pathName);
        } else {
            return getVideoFrame(positionMs * 1000, index);//参数为微秒
        }
    }

    /**
     * Helper function to set a demo seek provider on PlaybackTransportControlGlue based on
     * duration.
     */
    public static void setDemoSeekProvider(Context context, final PlaybackTransportControlGlue glue, final String videoUrl, final String videoId) {
        String path = context.getCacheDir().toString() + "/"+videoId;
        String name = "frame_%04d.jpg";
        if (glue.isPrepared()) {
//            Log.e("---provider---", "setDemoSeekProvider - true");
            glue.setSeekProvider(new PlaybackSeekDiskDataProvider(
                    videoUrl,
                    glue.getDuration(),
                    glue.getDuration() / FRAME_COUNT,
                    path,
                    name));
        } else {
//            Log.e("---provider---", "setDemoSeekProvider - false");
            glue.addPlayerCallback(new PlaybackGlue.PlayerCallback() {
                @Override
                public void onPreparedStateChanged(PlaybackGlue glue) {
//                    Log.e("---provider---", "setDemoSeekProvider - onPreparedStateChanged - "+glue.isPrepared());
                    if (glue.isPrepared()) {
                        glue.removePlayerCallback(this);
                        PlaybackTransportControlGlue transportControlGlue =
                                (PlaybackTransportControlGlue) glue;
                        transportControlGlue.setSeekProvider(new PlaybackSeekDiskDataProvider(
                                videoUrl,
                                transportControlGlue.getDuration(),
                                transportControlGlue.getDuration() / FRAME_COUNT,
                                path,
                                name));
                    }
                }
            });
        }
    }

    private Bitmap getVideoFrame(long timeUs, int index) {
//        return metadataRetriever.getScaledFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_CLOSEST_SYNC, 160, 90);

        Log.e("--获取视频帧--", "图片在Disk没有缓存，开始API获取视频帧");
        Bitmap bitmap = fFmpegMediaMetadataRetriever.getScaledFrameAtTime(timeUs, FFmpegMediaMetadataRetriever.OPTION_CLOSEST, FRAME_WIDTH, FRAME_HEIGHT); // frame at 2 seconds

        if (bitmap != null) {
            try {
                //pathAndName = /data/user/0/com.dyxc.tvtest/cache/01/frame_0026.jpg
                //保存图片到本地缓存
                File path = new File(mPath);
                if (!path.exists()) {
                    path.mkdirs();
                }
                File file = new File(mPath, String.format(mName, index));
                if (!file.exists()) {
                    file.createNewFile();
                }
                OutputStream outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, FRAME_COMPRESS_QUALITY, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e("--获取视频帧--", "保存图片异常 - FileNotFoundException - "+e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("--获取视频帧--", "保存图片异常 - IOException - "+e.getMessage());
            }
        }
        Log.d("--获取视频帧--", "bitmap视频帧大小 getByteCount - "+bitmap.getByteCount());
        return bitmap;
    }

    /**
     * 这里释放不调用好像也没错
     */
    public void release() {
        byte [] artwork = fFmpegMediaMetadataRetriever.getEmbeddedPicture();
        fFmpegMediaMetadataRetriever.release();
    }


}
