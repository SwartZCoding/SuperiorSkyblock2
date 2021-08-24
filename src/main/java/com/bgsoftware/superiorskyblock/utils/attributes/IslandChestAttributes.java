package com.bgsoftware.superiorskyblock.utils.attributes;

public final class IslandChestAttributes extends AttributesRegistry<IslandChestAttributes.Field> {

    public IslandChestAttributes(){
        super(Field.class);
    }

    @Override
    public IslandChestAttributes setValue(Field field, Object value) {
        return (IslandChestAttributes) super.setValue(field, value);
    }

    public enum Field {

        INDEX,
        CONTENTS

    }

}
