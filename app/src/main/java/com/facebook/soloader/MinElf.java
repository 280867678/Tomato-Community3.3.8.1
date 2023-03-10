package com.facebook.soloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/* loaded from: classes2.dex */
public final class MinElf {
    public static String[] extract_DT_NEEDED(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            return extract_DT_NEEDED(fileInputStream.getChannel());
        } finally {
            fileInputStream.close();
        }
    }

    public static String[] extract_DT_NEEDED(FileChannel fileChannel) throws IOException {
        long j;
        long j2;
        int i;
        long j3;
        boolean z;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8;
        long j9;
        long j10;
        long j11;
        long j12;
        long j13;
        long j14;
        long j15;
        long j16;
        long j17;
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        if (getu32(fileChannel, allocate, 0L) != 1179403647) {
            throw new ElfError("file is not ELF");
        }
        boolean z2 = true;
        if (getu8(fileChannel, allocate, 4L) != 1) {
            z2 = false;
        }
        if (getu8(fileChannel, allocate, 5L) == 2) {
            allocate.order(ByteOrder.BIG_ENDIAN);
        }
        if (z2) {
            j = getu32(fileChannel, allocate, 28L);
        } else {
            j = get64(fileChannel, allocate, 32L);
        }
        if (z2) {
            j2 = getu16(fileChannel, allocate, 44L);
        } else {
            j2 = getu16(fileChannel, allocate, 56L);
        }
        if (z2) {
            i = getu16(fileChannel, allocate, 42L);
        } else {
            i = getu16(fileChannel, allocate, 54L);
        }
        if (j2 == 65535) {
            if (z2) {
                j16 = getu32(fileChannel, allocate, 32L);
            } else {
                j16 = get64(fileChannel, allocate, 40L);
            }
            if (z2) {
                j17 = getu32(fileChannel, allocate, j16 + 28);
            } else {
                j17 = getu32(fileChannel, allocate, j16 + 44);
            }
            j2 = j17;
        }
        long j18 = j;
        long j19 = 0;
        while (true) {
            if (j19 >= j2) {
                j3 = 0;
                break;
            }
            if (z2) {
                j15 = getu32(fileChannel, allocate, j18 + 0);
            } else {
                j15 = getu32(fileChannel, allocate, j18 + 0);
            }
            if (j15 != 2) {
                j18 += i;
                j19++;
            } else if (z2) {
                j3 = getu32(fileChannel, allocate, j18 + 4);
            } else {
                j3 = get64(fileChannel, allocate, j18 + 8);
            }
        }
        long j20 = 0;
        if (j3 == 0) {
            throw new ElfError("ELF file does not contain dynamic linking information");
        }
        long j21 = j3;
        long j22 = 0;
        int i2 = 0;
        while (true) {
            if (z2) {
                z = z2;
                j4 = getu32(fileChannel, allocate, j21 + j20);
            } else {
                z = z2;
                j4 = get64(fileChannel, allocate, j21 + j20);
            }
            if (j4 == 1) {
                j5 = j3;
                if (i2 == Integer.MAX_VALUE) {
                    throw new ElfError("malformed DT_NEEDED section");
                }
                i2++;
            } else {
                j5 = j3;
                if (j4 == 5) {
                    if (z) {
                        j6 = getu32(fileChannel, allocate, j21 + 4);
                    } else {
                        j6 = get64(fileChannel, allocate, j21 + 8);
                    }
                    j22 = j6;
                }
            }
            long j23 = 16;
            j21 += z ? 8L : 16L;
            j20 = 0;
            if (j4 != 0) {
                z2 = z;
                j3 = j5;
            } else if (j22 == 0) {
                throw new ElfError("Dynamic section string-table not found");
            } else {
                int i3 = 0;
                while (true) {
                    if (i3 >= j2) {
                        j7 = 0;
                        break;
                    }
                    if (z) {
                        j10 = getu32(fileChannel, allocate, j + j20);
                    } else {
                        j10 = getu32(fileChannel, allocate, j + j20);
                    }
                    if (j10 == 1) {
                        if (z) {
                            j12 = getu32(fileChannel, allocate, j + 8);
                        } else {
                            j12 = get64(fileChannel, allocate, j + j23);
                        }
                        if (z) {
                            j11 = j2;
                            j13 = getu32(fileChannel, allocate, j + 20);
                        } else {
                            j11 = j2;
                            j13 = get64(fileChannel, allocate, j + 40);
                        }
                        if (j12 <= j22 && j22 < j13 + j12) {
                            if (z) {
                                j14 = getu32(fileChannel, allocate, j + 4);
                            } else {
                                j14 = get64(fileChannel, allocate, j + 8);
                            }
                            j7 = j14 + (j22 - j12);
                        }
                    } else {
                        j11 = j2;
                    }
                    j += i;
                    i3++;
                    j2 = j11;
                    j23 = 16;
                    j20 = 0;
                }
                long j24 = 0;
                if (j7 == 0) {
                    throw new ElfError("did not find file offset of DT_STRTAB table");
                }
                String[] strArr = new String[i2];
                int i4 = 0;
                while (true) {
                    if (z) {
                        j8 = getu32(fileChannel, allocate, j5 + j24);
                    } else {
                        j8 = get64(fileChannel, allocate, j5 + j24);
                    }
                    if (j8 == 1) {
                        if (z) {
                            j9 = getu32(fileChannel, allocate, j5 + 4);
                        } else {
                            j9 = get64(fileChannel, allocate, j5 + 8);
                        }
                        strArr[i4] = getSz(fileChannel, allocate, j9 + j7);
                        if (i4 == Integer.MAX_VALUE) {
                            throw new ElfError("malformed DT_NEEDED section");
                        }
                        i4++;
                    }
                    j5 += z ? 8L : 16L;
                    if (j8 == 0) {
                        if (i4 != strArr.length) {
                            throw new ElfError("malformed DT_NEEDED section");
                        }
                        return strArr;
                    }
                    j24 = 0;
                }
            }
        }
    }

    private static String getSz(FileChannel fileChannel, ByteBuffer byteBuffer, long j) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            long j2 = 1 + j;
            short u8Var = getu8(fileChannel, byteBuffer, j);
            if (u8Var != 0) {
                sb.append((char) u8Var);
                j = j2;
            } else {
                return sb.toString();
            }
        }
    }

    private static void read(FileChannel fileChannel, ByteBuffer byteBuffer, int i, long j) throws IOException {
        int read;
        byteBuffer.position(0);
        byteBuffer.limit(i);
        while (byteBuffer.remaining() > 0 && (read = fileChannel.read(byteBuffer, j)) != -1) {
            j += read;
        }
        if (byteBuffer.remaining() > 0) {
            throw new ElfError("ELF file truncated");
        }
        byteBuffer.position(0);
    }

    private static long get64(FileChannel fileChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(fileChannel, byteBuffer, 8, j);
        return byteBuffer.getLong();
    }

    private static long getu32(FileChannel fileChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(fileChannel, byteBuffer, 4, j);
        return byteBuffer.getInt() & 4294967295L;
    }

    private static int getu16(FileChannel fileChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(fileChannel, byteBuffer, 2, j);
        return byteBuffer.getShort() & 65535;
    }

    private static short getu8(FileChannel fileChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(fileChannel, byteBuffer, 1, j);
        return (short) (byteBuffer.get() & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class ElfError extends RuntimeException {
        ElfError(String str) {
            super(str);
        }
    }
}
