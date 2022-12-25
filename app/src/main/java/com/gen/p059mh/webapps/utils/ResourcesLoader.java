package com.gen.p059mh.webapps.utils;

import com.gen.p059mh.webapps.listener.ControllerProvider;
import com.gen.p059mh.webapps.utils.Utils;
import com.google.gson.Gson;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.utils.ResourcesLoader */
/* loaded from: classes2.dex */
public class ResourcesLoader {
    static final int BufferSize = 4096;
    public static String DEFAULTS_HOST = "defaultshost";
    public static String WORK_HOST = "workhost";
    ControllerProvider controllerProvider;
    ResourceType[] types;
    WACrypto waCrypto;

    /* renamed from: com.gen.mh.webapps.utils.ResourcesLoader$ListHandler */
    /* loaded from: classes2.dex */
    public interface ListHandler {
        void onList(File file, boolean z);
    }

    public void setControllerProvider(ControllerProvider controllerProvider) {
        this.controllerProvider = controllerProvider;
    }

    /* renamed from: com.gen.mh.webapps.utils.ResourcesLoader$ResourceType */
    /* loaded from: classes2.dex */
    public class ResourceType {
        Map checker;
        Map config;
        File configFile;
        List css;
        File defaultsFile;
        List dirs;
        String html;
        String type;
        File workFile;

        public String getType() {
            return this.type;
        }

        public String getHtml() {
            return this.html;
        }

        public Map getChecker() {
            return this.checker;
        }

        public List getCss() {
            return this.css;
        }

        public List getDirs() {
            return this.dirs;
        }

        public File getConfigFile() {
            return this.configFile;
        }

        public File getDefaultsFile() {
            return this.defaultsFile;
        }

        public File getWorkFile() {
            return this.workFile;
        }

        public Map getConfig() {
            return this.config;
        }

        public ResourceType(Map map, File file) {
            this.type = (String) map.get("type");
            this.html = (String) map.get("html");
            this.checker = (Map) map.get("checker");
            this.css = (List) map.get("css");
            this.dirs = (List) map.get("dirs");
            this.defaultsFile = file;
        }

        public void checkLoad() {
            String str;
            String str2 = null;
            if (this.type.equals("web")) {
                str = "http://" + ResourcesLoader.WORK_HOST + "/" + this.html;
            } else if (this.type.equals("game")) {
                str = "http://" + ResourcesLoader.WORK_HOST + "/game.js";
            } else {
                str2 = html();
                str = "http://" + ResourcesLoader.WORK_HOST + "/app.js";
            }
            ResourcesLoader.this.controllerProvider.ready2Load(this.type, str, str2);
        }

        public boolean check(File file) {
            try {
                Method method = getClass().getMethod((String) this.checker.get("method"), File.class, List.class);
                if (method != null) {
                    return ((Boolean) method.invoke(this, file, this.checker.get("params"))).booleanValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        public boolean fileExist(File file, List list) {
            if (list != null) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    if (!new File(file.getAbsolutePath() + "/" + list.get(i)).exists()) {
                        return false;
                    }
                }
                return true;
            }
            return true;
        }

        public void setWorkFile(File file) {
            this.workFile = file;
            if (this.type.equals("app")) {
                this.configFile = new File(file.getAbsolutePath() + "/app.json");
            } else if (this.type.equals("game")) {
                this.configFile = new File(file.getAbsolutePath() + "/game.json");
            } else if (!this.type.equals("web")) {
            } else {
                this.configFile = new File(file.getAbsolutePath() + "/web.json");
            }
        }

        public String html() {
            if (this.workFile == null) {
                return null;
            }
            String absolutePath = this.defaultsFile.getAbsolutePath();
            byte[] loadData = Utils.loadData(absolutePath + "/" + this.html, Utils.ENCODE_TYPE.DEFAULT, ResourcesLoader.this.waCrypto);
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            final ArrayList arrayList = new ArrayList();
            ListHandler listHandler = new ListHandler() { // from class: com.gen.mh.webapps.utils.ResourcesLoader.ResourceType.1
                @Override // com.gen.p059mh.webapps.utils.ResourcesLoader.ListHandler
                public void onList(File file, boolean z) {
                    if (z) {
                        arrayList.add(file);
                    }
                }
            };
            ResourcesLoader.listAllFile(new File(absolutePath + "/android"), listHandler);
            ResourcesLoader.listAllFile(new File(absolutePath), listHandler);
            ResourcesLoader.listAllFile(new File(absolutePath + "/api"), listHandler);
            int size = this.dirs.size();
            for (int i = 0; i < size; i++) {
                ResourcesLoader.listAllFile(new File(absolutePath + "/" + this.dirs.get(i)), listHandler);
            }
            Collections.sort(arrayList, new Comparator<File>() { // from class: com.gen.mh.webapps.utils.ResourcesLoader.ResourceType.2
                @Override // java.util.Comparator
                public int compare(File file, File file2) {
                    return file.getName().compareTo(file2.getName());
                }
            });
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                File file = (File) it2.next();
                if (Utils.hasSuffix(file.getAbsolutePath(), ".pre.js")) {
                    sb.append("<script type=\"application/javascript\" src=\"http://" + ResourcesLoader.DEFAULTS_HOST + file.getAbsolutePath().replace(absolutePath, "") + "\"></script>\n");
                } else {
                    sb2.append("<script type=\"application/javascript\" src=\"http://" + ResourcesLoader.DEFAULTS_HOST + file.getAbsolutePath().replace(absolutePath, "") + "\"></script>\n");
                }
            }
            int size2 = this.css.size();
            for (int i2 = 0; i2 < size2; i2++) {
                sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://" + ResourcesLoader.DEFAULTS_HOST + "/" + this.css.get(i2) + "\">\n");
            }
            return new String(loadData).replace("{{scripts_pre}}", sb.toString()).replace("{{scripts}}", sb2.toString()).replace("{{src}}", "/web.html");
        }

        public Map configs() {
            if (this.workFile == null) {
                return null;
            }
            if (this.config == null) {
                try {
                    byte[] loadData = Utils.loadData(this.configFile.getAbsolutePath(), Utils.ENCODE_TYPE.WORK, ResourcesLoader.this.waCrypto);
                    if (loadData != null) {
                        this.config = (Map) new Gson().fromJson(new String(loadData), (Class<Object>) Map.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return this.config;
        }
    }

    public static void listAllFile(File file, ListHandler listHandler) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            ArrayList arrayList = new ArrayList(listFiles.length);
            for (File file2 : listFiles) {
                arrayList.add(file2);
            }
            Collections.sort(arrayList, new Comparator<File>() { // from class: com.gen.mh.webapps.utils.ResourcesLoader.1
                @Override // java.util.Comparator
                public int compare(File file3, File file4) {
                    return file3.getName().compareTo(file4.getName());
                }
            });
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                File file3 = (File) it2.next();
                if (!file3.isDirectory()) {
                    if (Utils.hasSuffix(file3.getAbsolutePath(), ".js")) {
                        listHandler.onList(file3, true);
                    } else if (Utils.hasSuffix(file3.getAbsolutePath(), ".css")) {
                        listHandler.onList(file3, false);
                    }
                }
            }
        }
    }

    public ResourcesLoader(File file, WACrypto wACrypto, ControllerProvider controllerProvider) {
        this(file, wACrypto);
        this.controllerProvider = controllerProvider;
    }

    public ResourcesLoader(File file, WACrypto wACrypto) {
        this.waCrypto = wACrypto;
        List list = (List) new Gson().fromJson(new String(Utils.loadData(new File(file.getAbsolutePath() + "/configs.json").getAbsolutePath(), Utils.ENCODE_TYPE.DEFAULT, wACrypto)), (Class<Object>) List.class);
        list = list == null ? new ArrayList() : list;
        this.types = new ResourceType[list.size()];
        int length = this.types.length;
        for (int i = 0; i < length; i++) {
            this.types[i] = new ResourceType((Map) list.get(i), file);
        }
    }

    public ResourceType typeFor(File file) {
        ResourceType[] resourceTypeArr = this.types;
        if (resourceTypeArr != null) {
            for (ResourceType resourceType : resourceTypeArr) {
                if (resourceType.check(file)) {
                    resourceType.setWorkFile(file);
                    return resourceType;
                }
            }
            return null;
        }
        return null;
    }
}
