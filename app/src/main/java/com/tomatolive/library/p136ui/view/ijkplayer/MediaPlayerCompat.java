package com.tomatolive.library.p136ui.view.ijkplayer;

import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomato.ijk.media.player.IMediaPlayer;
import com.tomato.ijk.media.player.IjkMediaPlayer;
import com.tomato.ijk.media.player.MediaPlayerProxy;
import com.tomato.ijk.media.player.TextureMediaPlayer;

/* renamed from: com.tomatolive.library.ui.view.ijkplayer.MediaPlayerCompat */
/* loaded from: classes3.dex */
public class MediaPlayerCompat {
    public static String getName(IMediaPlayer iMediaPlayer) {
        if (iMediaPlayer == null) {
            return "null";
        }
        if (iMediaPlayer instanceof TextureMediaPlayer) {
            StringBuilder sb = new StringBuilder("TextureMediaPlayer <");
            IMediaPlayer internalMediaPlayer = ((TextureMediaPlayer) iMediaPlayer).getInternalMediaPlayer();
            if (internalMediaPlayer == null) {
                sb.append("null>");
            } else {
                sb.append(internalMediaPlayer.getClass().getSimpleName());
                sb.append(SimpleComparison.GREATER_THAN_OPERATION);
            }
            return sb.toString();
        }
        return iMediaPlayer.getClass().getSimpleName();
    }

    public static IjkMediaPlayer getIjkMediaPlayer(IMediaPlayer iMediaPlayer) {
        if (iMediaPlayer == null) {
            return null;
        }
        if (iMediaPlayer instanceof IjkMediaPlayer) {
            return (IjkMediaPlayer) iMediaPlayer;
        }
        if (!(iMediaPlayer instanceof MediaPlayerProxy)) {
            return null;
        }
        MediaPlayerProxy mediaPlayerProxy = (MediaPlayerProxy) iMediaPlayer;
        if (!(mediaPlayerProxy.getInternalMediaPlayer() instanceof IjkMediaPlayer)) {
            return null;
        }
        return (IjkMediaPlayer) mediaPlayerProxy.getInternalMediaPlayer();
    }

    public static void selectTrack(IMediaPlayer iMediaPlayer, int i) {
        IjkMediaPlayer ijkMediaPlayer = getIjkMediaPlayer(iMediaPlayer);
        if (ijkMediaPlayer == null) {
            return;
        }
        ijkMediaPlayer.selectTrack(i);
    }

    public static void deselectTrack(IMediaPlayer iMediaPlayer, int i) {
        IjkMediaPlayer ijkMediaPlayer = getIjkMediaPlayer(iMediaPlayer);
        if (ijkMediaPlayer == null) {
            return;
        }
        ijkMediaPlayer.deselectTrack(i);
    }

    public static int getSelectedTrack(IMediaPlayer iMediaPlayer, int i) {
        IjkMediaPlayer ijkMediaPlayer = getIjkMediaPlayer(iMediaPlayer);
        if (ijkMediaPlayer == null) {
            return -1;
        }
        return ijkMediaPlayer.getSelectedTrack(i);
    }
}
