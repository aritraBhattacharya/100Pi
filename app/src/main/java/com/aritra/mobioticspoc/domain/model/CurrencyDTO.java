package com.aritra.mobioticspoc.domain.model;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Id;

@Entity(active = true)
public class CurrencyDTO {


    @Expose
    @Id
    private String Currency;
    @Expose
    private String CurrencyLong;
    @Expose
    private int MinConfirmation;
    @Expose
    private Double TxFee;
    @Expose
    private Boolean IsActive;
    @Expose
    private Boolean IsRestricted;
    @Expose
    private String CoinType;
    @Expose
    private String BaseAddress;
    @Expose
    private String Notice;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 47890084)
    private transient CurrencyDTODao myDao;
    @Generated(hash = 1250125099)
    public CurrencyDTO(String Currency, String CurrencyLong, int MinConfirmation,
            Double TxFee, Boolean IsActive, Boolean IsRestricted, String CoinType,
            String BaseAddress, String Notice) {
        this.Currency = Currency;
        this.CurrencyLong = CurrencyLong;
        this.MinConfirmation = MinConfirmation;
        this.TxFee = TxFee;
        this.IsActive = IsActive;
        this.IsRestricted = IsRestricted;
        this.CoinType = CoinType;
        this.BaseAddress = BaseAddress;
        this.Notice = Notice;
    }
    @Generated(hash = 1197004773)
    public CurrencyDTO() {
    }
    public String getCurrency() {
        return this.Currency;
    }
    public void setCurrency(String Currency) {
        this.Currency = Currency;
    }
    public String getCurrencyLong() {
        return this.CurrencyLong;
    }
    public void setCurrencyLong(String CurrencyLong) {
        this.CurrencyLong = CurrencyLong;
    }
    public int getMinConfirmation() {
        return this.MinConfirmation;
    }
    public void setMinConfirmation(int MinConfirmation) {
        this.MinConfirmation = MinConfirmation;
    }
    public Double getTxFee() {
        return this.TxFee;
    }
    public void setTxFee(Double TxFee) {
        this.TxFee = TxFee;
    }
    public Boolean getIsActive() {
        return this.IsActive;
    }
    public void setIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
    }
    public Boolean getIsRestricted() {
        return this.IsRestricted;
    }
    public void setIsRestricted(Boolean IsRestricted) {
        this.IsRestricted = IsRestricted;
    }
    public String getCoinType() {
        return this.CoinType;
    }
    public void setCoinType(String CoinType) {
        this.CoinType = CoinType;
    }
    public String getBaseAddress() {
        return this.BaseAddress;
    }
    public void setBaseAddress(String BaseAddress) {
        this.BaseAddress = BaseAddress;
    }
    public String getNotice() {
        return this.Notice;
    }
    public void setNotice(String Notice) {
        this.Notice = Notice;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 775931499)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCurrencyDTODao() : null;
    }
   
}