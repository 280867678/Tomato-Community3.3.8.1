package com.one.tomato.entity.p079db;

import com.broccoli.p150bh.R;
import com.one.tomato.utils.AppUtil;
import java.io.Serializable;
import java.util.List;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.LevelBean */
/* loaded from: classes3.dex */
public class LevelBean extends LitePalSupport implements Serializable {
    private int commentCount;
    private int commentCount_times;
    private int currentLevelIndex;
    private int currentLevelValue;
    private int levelUpReward;
    private List<LevelCfg> listLevelCfg;
    private int nextLevelValue;
    private int noAuditCount;
    private int prestigeBalance;
    private int pubCount;
    private int pubCount_times;
    private int replyCount;
    private int replyCount_times;

    public LevelBean() {
    }

    public LevelBean(int i) {
        this.currentLevelIndex = i;
    }

    public int getLevelNickIndex() {
        int i = this.currentLevelIndex;
        if (i > 0) {
            if (i >= 26) {
                return 6;
            }
            if (i >= 21) {
                return 5;
            }
            if (i >= 16) {
                return 4;
            }
            if (i >= 11) {
                return 3;
            }
            if (i >= 6) {
                return 2;
            }
        }
        return 1;
    }

    public String getLevelNickName() {
        return AppUtil.getResources().getStringArray(R.array.level_nick)[getLevelNickIndex() - 1];
    }

    public int getNextLevelValue() {
        return this.nextLevelValue;
    }

    public void setNextLevelValue(int i) {
        this.nextLevelValue = i;
    }

    public int getCurrentLevelValue() {
        return this.currentLevelValue;
    }

    public void setCurrentLevelValue(int i) {
        this.currentLevelValue = i;
    }

    public int getCurrentLevelIndex() {
        return this.currentLevelIndex;
    }

    public void setCurrentLevelIndex(int i) {
        this.currentLevelIndex = i;
    }

    public int getPrestigeBalance() {
        return this.prestigeBalance;
    }

    public void setPrestigeBalance(int i) {
        this.prestigeBalance = i;
    }

    public int getLevelUpReward() {
        return this.levelUpReward;
    }

    public void setLevelUpReward(int i) {
        this.levelUpReward = i;
    }

    public int getNoAuditCount() {
        return this.noAuditCount;
    }

    public void setNoAuditCount(int i) {
        this.noAuditCount = i;
    }

    public int getPubCount() {
        return this.pubCount;
    }

    public void setPubCount(int i) {
        this.pubCount = i;
    }

    public int getCommentCount() {
        return this.commentCount;
    }

    public void setCommentCount(int i) {
        this.commentCount = i;
    }

    public int getReplyCount() {
        return this.replyCount;
    }

    public void setReplyCount(int i) {
        this.replyCount = i;
    }

    public int getPubCount_times() {
        return this.pubCount_times;
    }

    public void setPubCount_times(int i) {
        this.pubCount_times = i;
    }

    public int getCommentCount_times() {
        return this.commentCount_times;
    }

    public void setCommentCount_times(int i) {
        this.commentCount_times = i;
    }

    public int getReplyCount_times() {
        return this.replyCount_times;
    }

    public void setReplyCount_times(int i) {
        this.replyCount_times = i;
    }

    public List<LevelCfg> getListLevelCfg() {
        return this.listLevelCfg;
    }

    public void setListLevelCfg(List<LevelCfg> list) {
        this.listLevelCfg = list;
    }

    /* renamed from: com.one.tomato.entity.db.LevelBean$LevelCfg */
    /* loaded from: classes3.dex */
    public class LevelCfg implements Serializable {
        private int freeLookTime;
        private int levelUpReward;
        private int maxPrestige;

        public LevelCfg() {
        }

        public int getLevelUpReward() {
            return this.levelUpReward;
        }

        public void setLevelUpReward(int i) {
            this.levelUpReward = i;
        }

        public int getFreeLookTime() {
            return this.freeLookTime;
        }

        public void setFreeLookTime(int i) {
            this.freeLookTime = i;
        }

        public int getMaxPrestige() {
            return this.maxPrestige;
        }

        public void setMaxPrestige(int i) {
            this.maxPrestige = i;
        }
    }
}
