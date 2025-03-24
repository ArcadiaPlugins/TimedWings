package me.furkanzhlp.timedwings.adapter.adapters.fabledskyblock;


import com.craftaro.skyblock.permission.BasicPermission;
import com.craftaro.skyblock.permission.PermissionType;
import com.craftaro.third_party.com.cryptomorin.xseries.XMaterial;

public class FabledSkyBlockPermission extends BasicPermission {
    public FabledSkyBlockPermission() {
        super("TimedWingsFly", XMaterial.FEATHER, PermissionType.GENERIC);
    }
}
