package com.amazonaws.services.p054s3.model;

import java.util.Map;

/* renamed from: com.amazonaws.services.s3.model.StaticEncryptionMaterialsProvider */
/* loaded from: classes2.dex */
public class StaticEncryptionMaterialsProvider implements EncryptionMaterialsProvider {
    private final EncryptionMaterials materials;

    public void refresh() {
    }

    public StaticEncryptionMaterialsProvider(EncryptionMaterials encryptionMaterials) {
        this.materials = encryptionMaterials;
    }

    public EncryptionMaterials getEncryptionMaterials() {
        return this.materials;
    }

    @Override // com.amazonaws.services.p054s3.model.EncryptionMaterialsAccessor
    public EncryptionMaterials getEncryptionMaterials(Map<String, String> map) {
        EncryptionMaterials encryptionMaterials;
        Map<String, String> materialsDescription = this.materials.getMaterialsDescription();
        if (map != null && map.equals(materialsDescription)) {
            return this.materials;
        }
        EncryptionMaterialsAccessor accessor = this.materials.getAccessor();
        if (accessor != null && (encryptionMaterials = accessor.getEncryptionMaterials(map)) != null) {
            return encryptionMaterials;
        }
        boolean z = false;
        boolean z2 = map == null || map.size() == 0;
        if (materialsDescription == null || materialsDescription.size() == 0) {
            z = true;
        }
        if (z2 && z) {
            return this.materials;
        }
        return null;
    }
}
