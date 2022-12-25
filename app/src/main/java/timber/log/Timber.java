package timber.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class Timber {
    private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
    private static final List<Tree> FOREST = new ArrayList();
    static volatile Tree[] forestAsArray = TREE_ARRAY_EMPTY;
    private static final Tree TREE_OF_SOULS = new Tree() { // from class: timber.log.Timber.1
        @Override // timber.log.Timber.Tree
        /* renamed from: v */
        public void mo15v(String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo15v(str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: v */
        public void mo13v(Throwable th, String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo13v(th, str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: v */
        public void mo14v(Throwable th) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo14v(th);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: d */
        public void mo24d(String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo24d(str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: d */
        public void mo22d(Throwable th, String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo22d(th, str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: d */
        public void mo23d(Throwable th) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo23d(th);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: i */
        public void mo18i(String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo18i(str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: i */
        public void mo16i(Throwable th, String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo16i(th, str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: i */
        public void mo17i(Throwable th) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo17i(th);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: w */
        public void mo12w(String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo12w(str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: w */
        public void mo10w(Throwable th, String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo10w(th, str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: w */
        public void mo11w(Throwable th) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo11w(th);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: e */
        public void mo21e(String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo21e(str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: e */
        public void mo19e(Throwable th, String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo19e(th, str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        /* renamed from: e */
        public void mo20e(Throwable th) {
            for (Tree tree : Timber.forestAsArray) {
                tree.mo20e(th);
            }
        }

        @Override // timber.log.Timber.Tree
        public void wtf(String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.wtf(str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        public void wtf(Throwable th, String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.wtf(th, str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        public void wtf(Throwable th) {
            for (Tree tree : Timber.forestAsArray) {
                tree.wtf(th);
            }
        }

        @Override // timber.log.Timber.Tree
        public void log(int i, String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.log(i, str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        public void log(int i, Throwable th, String str, Object... objArr) {
            for (Tree tree : Timber.forestAsArray) {
                tree.log(i, th, str, objArr);
            }
        }

        @Override // timber.log.Timber.Tree
        public void log(int i, Throwable th) {
            for (Tree tree : Timber.forestAsArray) {
                tree.log(i, th);
            }
        }

        @Override // timber.log.Timber.Tree
        protected void log(int i, String str, String str2, Throwable th) {
            throw new AssertionError("Missing override for log method.");
        }
    };

    public static Tree tag(String str) {
        for (Tree tree : forestAsArray) {
            tree.explicitTag.set(str);
        }
        return TREE_OF_SOULS;
    }

    public static void plant(Tree tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        }
        if (tree == TREE_OF_SOULS) {
            throw new IllegalArgumentException("Cannot plant Timber into itself.");
        }
        synchronized (FOREST) {
            FOREST.add(tree);
            forestAsArray = (Tree[]) FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Tree {
        final ThreadLocal<String> explicitTag = new ThreadLocal<>();

        @Deprecated
        protected boolean isLoggable(int i) {
            return true;
        }

        protected abstract void log(int i, String str, String str2, Throwable th);

        String getTag() {
            String str = this.explicitTag.get();
            if (str != null) {
                this.explicitTag.remove();
            }
            return str;
        }

        /* renamed from: v */
        public void mo15v(String str, Object... objArr) {
            prepareLog(2, null, str, objArr);
        }

        /* renamed from: v */
        public void mo13v(Throwable th, String str, Object... objArr) {
            prepareLog(2, th, str, objArr);
        }

        /* renamed from: v */
        public void mo14v(Throwable th) {
            prepareLog(2, th, null, new Object[0]);
        }

        /* renamed from: d */
        public void mo24d(String str, Object... objArr) {
            prepareLog(3, null, str, objArr);
        }

        /* renamed from: d */
        public void mo22d(Throwable th, String str, Object... objArr) {
            prepareLog(3, th, str, objArr);
        }

        /* renamed from: d */
        public void mo23d(Throwable th) {
            prepareLog(3, th, null, new Object[0]);
        }

        /* renamed from: i */
        public void mo18i(String str, Object... objArr) {
            prepareLog(4, null, str, objArr);
        }

        /* renamed from: i */
        public void mo16i(Throwable th, String str, Object... objArr) {
            prepareLog(4, th, str, objArr);
        }

        /* renamed from: i */
        public void mo17i(Throwable th) {
            prepareLog(4, th, null, new Object[0]);
        }

        /* renamed from: w */
        public void mo12w(String str, Object... objArr) {
            prepareLog(5, null, str, objArr);
        }

        /* renamed from: w */
        public void mo10w(Throwable th, String str, Object... objArr) {
            prepareLog(5, th, str, objArr);
        }

        /* renamed from: w */
        public void mo11w(Throwable th) {
            prepareLog(5, th, null, new Object[0]);
        }

        /* renamed from: e */
        public void mo21e(String str, Object... objArr) {
            prepareLog(6, null, str, objArr);
        }

        /* renamed from: e */
        public void mo19e(Throwable th, String str, Object... objArr) {
            prepareLog(6, th, str, objArr);
        }

        /* renamed from: e */
        public void mo20e(Throwable th) {
            prepareLog(6, th, null, new Object[0]);
        }

        public void wtf(String str, Object... objArr) {
            prepareLog(7, null, str, objArr);
        }

        public void wtf(Throwable th, String str, Object... objArr) {
            prepareLog(7, th, str, objArr);
        }

        public void wtf(Throwable th) {
            prepareLog(7, th, null, new Object[0]);
        }

        public void log(int i, String str, Object... objArr) {
            prepareLog(i, null, str, objArr);
        }

        public void log(int i, Throwable th, String str, Object... objArr) {
            prepareLog(i, th, str, objArr);
        }

        public void log(int i, Throwable th) {
            prepareLog(i, th, null, new Object[0]);
        }

        protected boolean isLoggable(String str, int i) {
            return isLoggable(i);
        }

        private void prepareLog(int i, Throwable th, String str, Object... objArr) {
            String tag = getTag();
            if (!isLoggable(tag, i)) {
                return;
            }
            if (str != null && str.length() == 0) {
                str = null;
            }
            if (str != null) {
                if (objArr != null && objArr.length > 0) {
                    str = formatMessage(str, objArr);
                }
                if (th != null) {
                    str = str + "\n" + getStackTraceString(th);
                }
            } else if (th == null) {
                return;
            } else {
                str = getStackTraceString(th);
            }
            log(i, tag, str, th);
        }

        protected String formatMessage(String str, Object[] objArr) {
            return String.format(str, objArr);
        }

        private String getStackTraceString(Throwable th) {
            StringWriter stringWriter = new StringWriter(256);
            PrintWriter printWriter = new PrintWriter((Writer) stringWriter, false);
            th.printStackTrace(printWriter);
            printWriter.flush();
            return stringWriter.toString();
        }
    }
}
