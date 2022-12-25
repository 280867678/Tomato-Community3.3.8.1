package com.koushikdutta.async;

import java.util.LinkedList;
import java.util.WeakHashMap;
import java.util.concurrent.Semaphore;
import p007b.p014c.p015a.C0699e;

/* loaded from: classes3.dex */
public class ThreadQueue extends LinkedList<Runnable> {

    /* renamed from: a */
    public static final WeakHashMap<Thread, ThreadQueue> f1519a = new WeakHashMap<>();

    /* renamed from: b */
    public C0699e f1520b;

    /* renamed from: c */
    public Semaphore f1521c = new Semaphore(0);

    /* renamed from: a */
    public static ThreadQueue m3874a(Thread thread) {
        ThreadQueue threadQueue;
        synchronized (f1519a) {
            threadQueue = f1519a.get(thread);
            if (threadQueue == null) {
                threadQueue = new ThreadQueue();
                f1519a.put(thread, threadQueue);
            }
        }
        return threadQueue;
    }

    /* renamed from: a */
    public static void m3876a(C0699e c0699e) {
        synchronized (f1519a) {
            for (ThreadQueue threadQueue : f1519a.values()) {
                if (threadQueue.f1520b == c0699e) {
                    threadQueue.f1521c.release();
                }
            }
        }
    }

    @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
    /* renamed from: a */
    public boolean add(Runnable runnable) {
        boolean add;
        synchronized (this) {
            add = super.add(runnable);
        }
        return add;
    }

    @Override // java.util.LinkedList, java.util.Deque, java.util.Queue
    public Runnable remove() {
        synchronized (this) {
            if (isEmpty()) {
                return null;
            }
            return (Runnable) super.remove();
        }
    }

    @Override // java.util.LinkedList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque
    public boolean remove(Object obj) {
        boolean remove;
        synchronized (this) {
            remove = super.remove(obj);
        }
        return remove;
    }
}
