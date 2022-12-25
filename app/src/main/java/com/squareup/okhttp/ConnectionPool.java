package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class ConnectionPool {
    private static final ConnectionPool systemDefault;
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;
    private final LinkedList<Connection> connections = new LinkedList<>();
    private final ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
    private final Runnable connectionsCleanupRunnable = new Runnable() { // from class: com.squareup.okhttp.ConnectionPool.1
        @Override // java.lang.Runnable
        public void run() {
            ArrayList<Connection> arrayList = new ArrayList(2);
            synchronized (ConnectionPool.this) {
                ListIterator listIterator = ConnectionPool.this.connections.listIterator(ConnectionPool.this.connections.size());
                int i = 0;
                while (listIterator.hasPrevious()) {
                    Connection connection = (Connection) listIterator.previous();
                    if (connection.isAlive() && !connection.isExpired(ConnectionPool.this.keepAliveDurationNs)) {
                        if (connection.isIdle()) {
                            i++;
                        }
                    }
                    listIterator.remove();
                    arrayList.add(connection);
                    if (arrayList.size() == 2) {
                        break;
                    }
                }
                ListIterator listIterator2 = ConnectionPool.this.connections.listIterator(ConnectionPool.this.connections.size());
                while (listIterator2.hasPrevious() && i > ConnectionPool.this.maxIdleConnections) {
                    Connection connection2 = (Connection) listIterator2.previous();
                    if (connection2.isIdle()) {
                        arrayList.add(connection2);
                        listIterator2.remove();
                        i--;
                    }
                }
            }
            for (Connection connection3 : arrayList) {
                Util.closeQuietly(connection3.getSocket());
            }
        }
    };

    static {
        String property = System.getProperty("http.keepAlive");
        String property2 = System.getProperty("http.keepAliveDuration");
        String property3 = System.getProperty("http.maxConnections");
        long parseLong = property2 != null ? Long.parseLong(property2) : 300000L;
        if (property != null && !Boolean.parseBoolean(property)) {
            systemDefault = new ConnectionPool(0, parseLong);
        } else if (property3 != null) {
            systemDefault = new ConnectionPool(Integer.parseInt(property3), parseLong);
        } else {
            systemDefault = new ConnectionPool(5, parseLong);
        }
    }

    public ConnectionPool(int i, long j) {
        this.maxIdleConnections = i;
        this.keepAliveDurationNs = j * 1000 * 1000;
    }

    public static ConnectionPool getDefault() {
        return systemDefault;
    }

    public synchronized Connection get(Address address) {
        Connection connection;
        connection = null;
        ListIterator<Connection> listIterator = this.connections.listIterator(this.connections.size());
        while (listIterator.hasPrevious()) {
            Connection previous = listIterator.previous();
            if (previous.getRoute().getAddress().equals(address) && previous.isAlive() && System.nanoTime() - previous.getIdleStartTimeNs() < this.keepAliveDurationNs) {
                listIterator.remove();
                if (!previous.isSpdy()) {
                    try {
                        Platform.get().tagSocket(previous.getSocket());
                    } catch (SocketException e) {
                        Util.closeQuietly(previous.getSocket());
                        Platform platform = Platform.get();
                        platform.logW("Unable to tagSocket(): " + e);
                    }
                }
                connection = previous;
                break;
            }
        }
        if (connection != null && connection.isSpdy()) {
            this.connections.addFirst(connection);
        }
        this.executorService.execute(this.connectionsCleanupRunnable);
        return connection;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void recycle(Connection connection) {
        if (!connection.isSpdy() && connection.clearOwner()) {
            if (!connection.isAlive()) {
                Util.closeQuietly(connection.getSocket());
                return;
            }
            try {
                Platform.get().untagSocket(connection.getSocket());
                synchronized (this) {
                    this.connections.addFirst(connection);
                    connection.incrementRecycleCount();
                    connection.resetIdleStartTime();
                }
                this.executorService.execute(this.connectionsCleanupRunnable);
            } catch (SocketException e) {
                Platform platform = Platform.get();
                platform.logW("Unable to untagSocket(): " + e);
                Util.closeQuietly(connection.getSocket());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void share(Connection connection) {
        if (!connection.isSpdy()) {
            throw new IllegalArgumentException();
        }
        this.executorService.execute(this.connectionsCleanupRunnable);
        if (!connection.isAlive()) {
            return;
        }
        synchronized (this) {
            this.connections.addFirst(connection);
        }
    }
}
