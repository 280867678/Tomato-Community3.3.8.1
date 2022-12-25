package com.one.tomato.thirdpart.m3u8.download;

import com.one.tomato.thirdpart.m3u8.download.entity.M3U8Task;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class DownloadQueue {
    private List<M3U8Task> queue = new ArrayList();

    public void offer(M3U8Task m3U8Task) {
        this.queue.add(m3U8Task);
    }

    public M3U8Task poll() {
        if (this.queue.size() >= 2) {
            this.queue.remove(0);
            return this.queue.get(0);
        } else if (this.queue.size() != 1) {
            return null;
        } else {
            this.queue.remove(0);
            return null;
        }
    }

    public M3U8Task peek() {
        if (this.queue.size() >= 1) {
            return this.queue.get(0);
        }
        return null;
    }

    public boolean remove(M3U8Task m3U8Task) {
        if (contains(m3U8Task)) {
            return this.queue.remove(m3U8Task);
        }
        return false;
    }

    public boolean contains(M3U8Task m3U8Task) {
        return this.queue.contains(m3U8Task);
    }

    public M3U8Task getTask(String str) {
        M3U8Task m3U8Task = new M3U8Task(str);
        for (int i = 0; i < this.queue.size(); i++) {
            if (this.queue.get(i).equals(m3U8Task)) {
                return this.queue.get(i);
            }
        }
        return null;
    }

    public int size() {
        return this.queue.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean isHead(M3U8Task m3U8Task) {
        return m3U8Task.equals(peek());
    }

    public void clear() {
        this.queue.clear();
    }
}
