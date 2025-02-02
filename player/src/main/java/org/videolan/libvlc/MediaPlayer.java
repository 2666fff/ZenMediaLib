//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.videolan.libvlc;

import org.videolan.libvlc.VLCEvent.Listener;

import java.util.Map;

public class MediaPlayer extends VLCObject<MediaPlayer.Event> implements AWindow.SurfaceCallback {
    private Media mMedia = null;
    private boolean mPlaying = false;
    private boolean mPlayRequested = false;
    private final AWindow mWindow = new AWindow(this);

    public MediaPlayer(LibVLC libVLC) {
        this.nativeNewFromLibVlc(libVLC, this.mWindow);
    }

    public MediaPlayer(Media media) {
        if(media != null && !media.isReleased()) {
            this.mMedia = media;
            this.mMedia.retain();
            this.nativeNewFromMedia(this.mMedia, this.mWindow);
        } else {
            throw new IllegalArgumentException("Media is null or released");
        }
    }

    public IVLCVout getVLCVout() {
        return this.mWindow;
    }

    public synchronized void setMedia(Media media) {
        if(this.mMedia != null) {
            this.mMedia.release();
        }

        if(media != null) {
            if(media.isReleased()) {
                throw new IllegalArgumentException("Media is released");
            }

            media.retain();
            media.setDefaultMediaPlayerOptions();
        }

        this.mMedia = media;
        this.nativeSetMedia(this.mMedia);
    }

    public synchronized Media getMedia() {
        if(this.mMedia != null) {
            this.mMedia.retain();
        }

        return this.mMedia;
    }

    public synchronized void play() {
        if(!this.mPlaying) {
            this.mPlayRequested = true;
            if(this.mWindow.areSurfacesWaiting()) {
                return;
            }
        }

        this.mPlaying = true;
        this.nativePlay();
    }

    public synchronized void stop() {
        this.mPlayRequested = false;
        this.mPlaying = false;
        this.nativeStop();
    }

    public synchronized void onSurfacesCreated(AWindow vout) {
        if(!this.mPlaying && this.mPlayRequested) {
            this.play();
        }
    }

    public synchronized void onSurfacesDestroyed(AWindow vout) {
        if(this.mPlaying) {
            this.setVideoTrackEnabled(false);
        }

    }

    public synchronized void setVideoTitleDisplay(int position, int timeout) {
        this.nativeSetVideoTitleDisplay(position, timeout);
    }

    public synchronized boolean setAudioOutput(String aout) {
        return this.nativeSetAudioOutput(aout);
    }

    public synchronized void setEqualizer(float[] bands) {
        this.nativeSetEqualizer(bands);
    }

    public native void setRate(float var1);

    public native float getRate();

    public native boolean isPlaying();

    public native boolean isSeekable();

    public native void pause();

    public native int getPlayerState();

    public native int getVolume();

    public native int setVolume(int var1);

    public native long getTime();

    public native long setTime(long var1);

    public native float getPosition();

    public native void setPosition(float var1);

    public native long getLength();

    public native String getMeta(int var1);

    public native int getTitle();

    public native void setTitle(int var1);

    public native int getChapterCountForTitle(int var1);

    public native int getChapterCount();

    public native int getChapter();

    public native String getChapterDescription(int var1);

    public native int previousChapter();

    public native int nextChapter();

    public native void setChapter(int var1);

    public native int getTitleCount();

    public native void navigate(int var1);

    public native int getAudioTracksCount();

    public native Map<Integer, String> getAudioTrackDescription();

    public native Map<String, Object> getStats();

    public native int getAudioTrack();

    public native int setAudioTrack(int var1);

    public native int getVideoTracksCount();

    public native int setVideoTrackEnabled(boolean var1);

    public native int addSubtitleTrack(String var1);

    public native Map<Integer, String> getSpuTrackDescription();

    public native int getSpuTrack();

    public native int setSpuTrack(int var1);

    public native int getSpuTracksCount();

    public native int setAudioDelay(long var1);

    public native long getAudioDelay();

    public native int setSpuDelay(long var1);

    public native long getSpuDelay();

    public native float[] getBands();

    public native String[] getPresets();

    public native float[] getPreset(int var1);

    public synchronized void setEventListener(MediaPlayer.EventListener listener) {
        super.setEventListener(listener);
    }

    protected MediaPlayer.Event onEventNative(int eventType, long arg1, long arg2) {
        return null;
    }

    protected void onReleaseNative() {
        if(this.mMedia != null) {
            this.mMedia.release();
        }

        this.nativeRelease();
    }

    private native void nativeNewFromLibVlc(LibVLC var1, IAWindowNativeHandler var2);

    private native void nativeNewFromMedia(Media var1, IAWindowNativeHandler var2);

    private native void nativeRelease();

    private native void nativeSetMedia(Media var1);

    private native void nativePlay();

    private native void nativeStop();

    private native void nativeSetVideoTitleDisplay(int var1, int var2);

    private native void nativeSetEqualizer(float[] var1);

    private native boolean nativeSetAudioOutput(String var1);

    public static class Navigate {
        public static final int Activate = 0;
        public static final int Up = 1;
        public static final int Down = 2;
        public static final int Left = 3;
        public static final int Right = 4;

        public Navigate() {
        }
    }

    public static class Position {
        public static final int Disable = -1;
        public static final int Center = 0;
        public static final int Left = 1;
        public static final int Right = 2;
        public static final int Top = 3;
        public static final int TopLeft = 4;
        public static final int TopRight = 5;
        public static final int Bottom = 6;
        public static final int BottomLeft = 7;
        public static final int BottomRight = 8;

        public Position() {
        }
    }

    public interface EventListener extends Listener<Event> {
    }

    public static class Event extends VLCEvent {
        protected Event(int type) {
            super(type);
        }
    }
}

