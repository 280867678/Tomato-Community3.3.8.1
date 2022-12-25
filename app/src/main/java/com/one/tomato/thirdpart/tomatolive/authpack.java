package com.one.tomato.thirdpart.tomatolive;

import java.security.MessageDigest;

/* loaded from: classes3.dex */
public class authpack {
    public static int sha1_32(byte[] bArr) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(bArr);
            return ((digest[0] & 255) << 24) + ((digest[1] & 255) << 16) + ((digest[2] & 255) << 8) + ((digest[3] & 255) << 0);
        } catch (Exception unused) {
            return 0;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: CFG modification limit reached, blocks count: 6879
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:60)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:40)
        */
    /* renamed from: A */
    public static byte[] m3789A() {
        /*
            Method dump skipped, instructions count: 26793
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.one.tomato.thirdpart.tomatolive.authpack.m3789A():byte[]");
    }
}
