/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.util.convert;

import java.util.List;

/**
 * TODO jkr ADD SHORT DESCRIPTION 
 *
 * @author <a href="mailto:jkr@touk.pl">Jakub Kurlenda</a>
 */
public class DummyClass {

    public String xxx;
    public boolean yyy;
    public int zzz;
    public double aaa;
    public List<DummyDTO> list;

    public String getXxx() {
        return xxx;
    }

    public void setXxx(String xxx) {
        this.xxx = xxx;
    }

    public boolean isYyy() {
        return yyy;
    }

    public void setYyy(boolean yyy) {
        this.yyy = yyy;
    }

    public int getZzz() {
        return zzz;
    }

    public void setZzz(int zzz) {
        this.zzz = zzz;
    }

    public double getAaa() {
        return aaa;
    }

    public void setAaa(double aaa) {
        this.aaa = aaa;
    }

    public List<DummyDTO> getList() {
        return list;
    }

    public DummyClass() {
    }

    public void setList(List<DummyDTO> list) {

        this.list = list;
    }

    public DummyClass(String xxx, boolean yyy, int zzz, double aaa) {
        this.xxx = xxx;
        this.yyy = yyy;
        this.zzz = zzz;
        this.aaa = aaa;
    }
}
