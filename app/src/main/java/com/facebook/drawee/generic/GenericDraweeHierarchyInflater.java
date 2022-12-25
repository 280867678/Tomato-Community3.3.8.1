package com.facebook.drawee.generic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import com.facebook.drawee.R$styleable;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.systrace.FrescoSystrace;

/* loaded from: classes2.dex */
public class GenericDraweeHierarchyInflater {
    public static GenericDraweeHierarchyBuilder inflateBuilder(Context context, AttributeSet attributeSet) {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("GenericDraweeHierarchyBuilder#inflateBuilder");
        }
        GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder = new GenericDraweeHierarchyBuilder(context.getResources());
        updateBuilder(genericDraweeHierarchyBuilder, context, attributeSet);
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
        return genericDraweeHierarchyBuilder;
    }

    public static GenericDraweeHierarchyBuilder updateBuilder(GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder, Context context, AttributeSet attributeSet) {
        boolean z;
        int i;
        boolean z2;
        boolean z3;
        int i2;
        boolean z4;
        Context context2 = context;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, R$styleable.GenericDraweeHierarchy);
            try {
                int indexCount = obtainStyledAttributes.getIndexCount();
                boolean z5 = true;
                i2 = 0;
                int i3 = 0;
                boolean z6 = true;
                boolean z7 = true;
                boolean z8 = true;
                boolean z9 = true;
                boolean z10 = true;
                boolean z11 = true;
                boolean z12 = true;
                int i4 = 0;
                while (i3 < indexCount) {
                    try {
                        int index = obtainStyledAttributes.getIndex(i3);
                        if (index == R$styleable.GenericDraweeHierarchy_actualImageScaleType) {
                            genericDraweeHierarchyBuilder.setActualImageScaleType(getScaleTypeFromXml(obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_placeholderImage) {
                            genericDraweeHierarchyBuilder.setPlaceholderImage(getDrawable(context2, obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_pressedStateOverlayImage) {
                            genericDraweeHierarchyBuilder.setPressedStateOverlay(getDrawable(context2, obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_progressBarImage) {
                            genericDraweeHierarchyBuilder.setProgressBarImage(getDrawable(context2, obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_fadeDuration) {
                            genericDraweeHierarchyBuilder.setFadeDuration(obtainStyledAttributes.getInt(index, 0));
                        } else if (index == R$styleable.GenericDraweeHierarchy_viewAspectRatio) {
                            genericDraweeHierarchyBuilder.setDesiredAspectRatio(obtainStyledAttributes.getFloat(index, 0.0f));
                        } else if (index == R$styleable.GenericDraweeHierarchy_placeholderImageScaleType) {
                            genericDraweeHierarchyBuilder.setPlaceholderImageScaleType(getScaleTypeFromXml(obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_retryImage) {
                            genericDraweeHierarchyBuilder.setRetryImage(getDrawable(context2, obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_retryImageScaleType) {
                            genericDraweeHierarchyBuilder.setRetryImageScaleType(getScaleTypeFromXml(obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_failureImage) {
                            genericDraweeHierarchyBuilder.setFailureImage(getDrawable(context2, obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_failureImageScaleType) {
                            genericDraweeHierarchyBuilder.setFailureImageScaleType(getScaleTypeFromXml(obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_progressBarImageScaleType) {
                            genericDraweeHierarchyBuilder.setProgressBarImageScaleType(getScaleTypeFromXml(obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_progressBarAutoRotateInterval) {
                            i2 = obtainStyledAttributes.getInteger(index, i2);
                        } else if (index == R$styleable.GenericDraweeHierarchy_backgroundImage) {
                            genericDraweeHierarchyBuilder.setBackground(getDrawable(context2, obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_overlayImage) {
                            genericDraweeHierarchyBuilder.setOverlay(getDrawable(context2, obtainStyledAttributes, index));
                        } else if (index == R$styleable.GenericDraweeHierarchy_roundAsCircle) {
                            getRoundingParams(genericDraweeHierarchyBuilder).setRoundAsCircle(obtainStyledAttributes.getBoolean(index, false));
                        } else if (index == R$styleable.GenericDraweeHierarchy_roundedCornerRadius) {
                            i4 = obtainStyledAttributes.getDimensionPixelSize(index, i4);
                        } else {
                            int i5 = i4;
                            if (index == R$styleable.GenericDraweeHierarchy_roundTopLeft) {
                                z6 = obtainStyledAttributes.getBoolean(index, z6);
                            } else if (index == R$styleable.GenericDraweeHierarchy_roundTopRight) {
                                z8 = obtainStyledAttributes.getBoolean(index, z8);
                            } else if (index == R$styleable.GenericDraweeHierarchy_roundBottomLeft) {
                                z12 = obtainStyledAttributes.getBoolean(index, z12);
                            } else if (index == R$styleable.GenericDraweeHierarchy_roundBottomRight) {
                                z10 = obtainStyledAttributes.getBoolean(index, z10);
                            } else if (index == R$styleable.GenericDraweeHierarchy_roundTopStart) {
                                z7 = obtainStyledAttributes.getBoolean(index, z7);
                            } else if (index == R$styleable.GenericDraweeHierarchy_roundTopEnd) {
                                z9 = obtainStyledAttributes.getBoolean(index, z9);
                            } else if (index == R$styleable.GenericDraweeHierarchy_roundBottomStart) {
                                z5 = obtainStyledAttributes.getBoolean(index, z5);
                            } else if (index == R$styleable.GenericDraweeHierarchy_roundBottomEnd) {
                                z11 = obtainStyledAttributes.getBoolean(index, z11);
                            } else if (index == R$styleable.GenericDraweeHierarchy_roundWithOverlayColor) {
                                i4 = i5;
                                getRoundingParams(genericDraweeHierarchyBuilder).setOverlayColor(obtainStyledAttributes.getColor(index, 0));
                            } else {
                                i4 = i5;
                                if (index == R$styleable.GenericDraweeHierarchy_roundingBorderWidth) {
                                    getRoundingParams(genericDraweeHierarchyBuilder).setBorderWidth(obtainStyledAttributes.getDimensionPixelSize(index, 0));
                                } else if (index == R$styleable.GenericDraweeHierarchy_roundingBorderColor) {
                                    getRoundingParams(genericDraweeHierarchyBuilder).setBorderColor(obtainStyledAttributes.getColor(index, 0));
                                } else if (index == R$styleable.GenericDraweeHierarchy_roundingBorderPadding) {
                                    getRoundingParams(genericDraweeHierarchyBuilder).setPadding(obtainStyledAttributes.getDimensionPixelSize(index, 0));
                                    i3++;
                                    context2 = context;
                                }
                            }
                            i4 = i5;
                        }
                        i3++;
                        context2 = context;
                    } catch (Throwable th) {
                        th = th;
                        obtainStyledAttributes.recycle();
                        if (Build.VERSION.SDK_INT >= 17) {
                            context.getResources().getConfiguration().getLayoutDirection();
                        }
                        throw th;
                    }
                }
                obtainStyledAttributes.recycle();
                if (Build.VERSION.SDK_INT < 17 || context.getResources().getConfiguration().getLayoutDirection() != 1) {
                    z2 = z6 && z7;
                    z = z8 && z9;
                    z3 = z10 && z11;
                    z4 = z12 && z5;
                } else {
                    z2 = z6 && z9;
                    z = z8 && z7;
                    boolean z13 = z10 && z5;
                    z4 = z12 && z11;
                    z3 = z13;
                }
                i = i4;
            } catch (Throwable th2) {
                th = th2;
            }
        } else {
            z = true;
            i = 0;
            z2 = true;
            z3 = true;
            i2 = 0;
            z4 = true;
        }
        if (genericDraweeHierarchyBuilder.getProgressBarImage() != null && i2 > 0) {
            genericDraweeHierarchyBuilder.setProgressBarImage(new AutoRotateDrawable(genericDraweeHierarchyBuilder.getProgressBarImage(), i2));
        }
        if (i > 0) {
            getRoundingParams(genericDraweeHierarchyBuilder).setCornersRadii(z2 ? i : 0.0f, z ? i : 0.0f, z3 ? i : 0.0f, z4 ? i : 0.0f);
        }
        return genericDraweeHierarchyBuilder;
    }

    private static RoundingParams getRoundingParams(GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder) {
        if (genericDraweeHierarchyBuilder.getRoundingParams() == null) {
            genericDraweeHierarchyBuilder.setRoundingParams(new RoundingParams());
        }
        return genericDraweeHierarchyBuilder.getRoundingParams();
    }

    private static Drawable getDrawable(Context context, TypedArray typedArray, int i) {
        int resourceId = typedArray.getResourceId(i, 0);
        if (resourceId == 0) {
            return null;
        }
        return context.getResources().getDrawable(resourceId);
    }

    private static ScalingUtils.ScaleType getScaleTypeFromXml(TypedArray typedArray, int i) {
        switch (typedArray.getInt(i, -2)) {
            case -1:
                return null;
            case 0:
                return ScalingUtils.ScaleType.FIT_XY;
            case 1:
                return ScalingUtils.ScaleType.FIT_START;
            case 2:
                return ScalingUtils.ScaleType.FIT_CENTER;
            case 3:
                return ScalingUtils.ScaleType.FIT_END;
            case 4:
                return ScalingUtils.ScaleType.CENTER;
            case 5:
                return ScalingUtils.ScaleType.CENTER_INSIDE;
            case 6:
                return ScalingUtils.ScaleType.CENTER_CROP;
            case 7:
                return ScalingUtils.ScaleType.FOCUS_CROP;
            case 8:
                return ScalingUtils.ScaleType.FIT_BOTTOM_START;
            default:
                throw new RuntimeException("XML attribute not specified!");
        }
    }
}
