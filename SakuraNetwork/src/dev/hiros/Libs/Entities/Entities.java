package dev.hiros.Libs.Entities;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.entity.EntityType;

import dev.hiros.Hub.NMS.VillagerMerchant;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.EntityVillager;

public enum Entities {
	HUB_VILLAGERMERCHANT("VillagerMerchant", 120, EntityType.VILLAGER, EntityVillager.class, VillagerMerchant.class);
	
    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends EntityInsentient> nmsClass;
    private Class<? extends EntityInsentient> customClass;
 
    private Entities(String name, int id, EntityType entityType, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
    }
 
    public String getName() {
        return this.name;
    }
 
    public int getID() {
        return this.id;
    }
 
    public EntityType getEntityType() {
        return this.entityType;
    }
 
    public Class<? extends EntityInsentient> getNMSClass() {
        return this.nmsClass;
    }
 
    public Class<? extends EntityInsentient> getCustomClass() {
        return this.customClass;
    }
 
    /**
    * Register our entities.
    */
    public static void registerEntities() {
        for (Entities entity : values())
            a(entity.getCustomClass(), entity.getName(), entity.getID());
    }
 
    /**
    * A convenience method.
    * @param clazz The class.
    * @param f The string representation of the private static field.
    * @return The object found
    * @throws Exception if unable to get the object.
    */
    @SuppressWarnings("rawtypes")
    private static Object getPrivateStatic(Class clazz, String f) throws Exception {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return field.get(null);
    }
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void a(Class paramClass, String paramString, int paramInt) {
        try {
            ((Map) getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
            ((Map) getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
            ((Map) getPrivateStatic(EntityTypes.class, "e")).put(Integer.valueOf(paramInt), paramClass);
            ((Map) getPrivateStatic(EntityTypes.class, "f")).put(paramClass, Integer.valueOf(paramInt));
            ((Map) getPrivateStatic(EntityTypes.class, "g")).put(paramString, Integer.valueOf(paramInt));
        } catch (Exception exc) {
            // Unable to register the new class.
        }
    }
 
}
