package com.andela.taccolation.presentation.model;

import com.andela.taccolation.app.utils.DashboardMenu;

import java.util.Objects;

public class DashboardItem {
    private int itemTitle;
    private int icon;
    private int backgroundColor;
    private DashboardMenu mDashboardMenu;

    public DashboardItem(int itemTitle, int icon, int backgroundColor, DashboardMenu dashboardMenu) {
        this.itemTitle = itemTitle;
        this.icon = icon;
        mDashboardMenu = dashboardMenu;
        this.backgroundColor = backgroundColor;
    }

    public DashboardItem() {
    }

    public int getItemTitle() {
        return itemTitle;
    }

    public int getIcon() {
        return icon;
    }

    public DashboardMenu getDashboardMenu() {
        return mDashboardMenu;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DashboardItem)) return false;
        DashboardItem that = (DashboardItem) o;
        return getItemTitle() == that.getItemTitle() &&
                getIcon() == that.getIcon() &&
                getBackgroundColor() == that.getBackgroundColor() &&
                getDashboardMenu() == that.getDashboardMenu();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemTitle(), getIcon(), getBackgroundColor(), getDashboardMenu());
    }
}
