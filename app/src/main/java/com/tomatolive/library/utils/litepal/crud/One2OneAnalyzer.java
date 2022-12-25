package com.tomatolive.library.utils.litepal.crud;

import com.tomatolive.library.utils.litepal.crud.model.AssociationsInfo;
import com.tomatolive.library.utils.litepal.util.DBUtility;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class One2OneAnalyzer extends AssociationsAnalyzer {
    /* JADX INFO: Access modifiers changed from: package-private */
    public void analyze(LitePalSupport litePalSupport, AssociationsInfo associationsInfo) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        LitePalSupport associatedModel = getAssociatedModel(litePalSupport, associationsInfo);
        if (associatedModel != null) {
            buildBidirectionalAssociations(litePalSupport, associatedModel, associationsInfo);
            dealAssociatedModel(litePalSupport, associatedModel, associationsInfo);
            return;
        }
        litePalSupport.addAssociatedTableNameToClearFK(DBUtility.getTableNameByClassName(associationsInfo.getAssociatedClassName()));
    }

    private void dealAssociatedModel(LitePalSupport litePalSupport, LitePalSupport litePalSupport2, AssociationsInfo associationsInfo) {
        if (associationsInfo.getAssociateSelfFromOtherModel() != null) {
            bidirectionalCondition(litePalSupport, litePalSupport2);
        } else {
            unidirectionalCondition(litePalSupport, litePalSupport2);
        }
    }

    private void bidirectionalCondition(LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        if (litePalSupport2.isSaved()) {
            litePalSupport.addAssociatedModelWithFK(litePalSupport2.getTableName(), litePalSupport2.getBaseObjId());
            litePalSupport.addAssociatedModelWithoutFK(litePalSupport2.getTableName(), litePalSupport2.getBaseObjId());
        }
    }

    private void unidirectionalCondition(LitePalSupport litePalSupport, LitePalSupport litePalSupport2) {
        dealsAssociationsOnTheSideWithoutFK(litePalSupport, litePalSupport2);
    }
}
