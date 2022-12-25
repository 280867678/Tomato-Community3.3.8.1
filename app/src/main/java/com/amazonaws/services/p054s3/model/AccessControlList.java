package com.amazonaws.services.p054s3.model;

import com.amazonaws.services.p054s3.internal.S3RequesterChargedResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/* renamed from: com.amazonaws.services.s3.model.AccessControlList */
/* loaded from: classes2.dex */
public class AccessControlList implements Serializable, S3RequesterChargedResult {
    private static final long serialVersionUID = 8095040648034788376L;
    private List<Grant> grantList;
    private Set<Grant> grantSet;
    private boolean isRequesterCharged;
    private Owner owner = null;

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void grantPermission(Grantee grantee, Permission permission) {
        getGrantsAsList().add(new Grant(grantee, permission));
    }

    public void grantAllPermissions(Grant... grantArr) {
        for (Grant grant : grantArr) {
            grantPermission(grant.getGrantee(), grant.getPermission());
        }
    }

    public void revokeAllPermissions(Grantee grantee) {
        ArrayList arrayList = new ArrayList();
        for (Grant grant : getGrantsAsList()) {
            if (grant.getGrantee().equals(grantee)) {
                arrayList.add(grant);
            }
        }
        this.grantList.removeAll(arrayList);
    }

    @Deprecated
    public Set<Grant> getGrants() {
        checkState();
        if (this.grantSet == null) {
            List<Grant> list = this.grantList;
            if (list == null) {
                this.grantSet = new HashSet();
            } else {
                this.grantSet = new HashSet(list);
                this.grantList = null;
            }
        }
        return this.grantSet;
    }

    private void checkState() {
        if (this.grantSet == null || this.grantList == null) {
            return;
        }
        throw new IllegalStateException("Both grant set and grant list cannot be null");
    }

    public List<Grant> getGrantsAsList() {
        checkState();
        if (this.grantList == null) {
            Set<Grant> set = this.grantSet;
            if (set == null) {
                this.grantList = new LinkedList();
            } else {
                this.grantList = new LinkedList(set);
                this.grantSet = null;
            }
        }
        return this.grantList;
    }

    public int hashCode() {
        Owner owner = this.owner;
        int i = 0;
        int hashCode = ((owner == null ? 0 : owner.hashCode()) + 31) * 31;
        Set<Grant> set = this.grantSet;
        int hashCode2 = (hashCode + (set == null ? 0 : set.hashCode())) * 31;
        List<Grant> list = this.grantList;
        if (list != null) {
            i = list.hashCode();
        }
        return hashCode2 + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AccessControlList.class != obj.getClass()) {
            return false;
        }
        AccessControlList accessControlList = (AccessControlList) obj;
        Owner owner = this.owner;
        if (owner == null) {
            if (accessControlList.owner != null) {
                return false;
            }
        } else if (!owner.equals(accessControlList.owner)) {
            return false;
        }
        Set<Grant> set = this.grantSet;
        if (set == null) {
            if (accessControlList.grantSet != null) {
                return false;
            }
        } else if (!set.equals(accessControlList.grantSet)) {
            return false;
        }
        List<Grant> list = this.grantList;
        if (list == null) {
            if (accessControlList.grantList != null) {
                return false;
            }
        } else if (!list.equals(accessControlList.grantList)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "AccessControlList [owner=" + this.owner + ", grants=" + getGrantsAsList() + "]";
    }

    public boolean isRequesterCharged() {
        return this.isRequesterCharged;
    }

    @Override // com.amazonaws.services.p054s3.internal.S3RequesterChargedResult
    public void setRequesterCharged(boolean z) {
        this.isRequesterCharged = z;
    }
}
