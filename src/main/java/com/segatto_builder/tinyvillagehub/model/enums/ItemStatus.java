package com.segatto_builder.tinyvillagehub.model.enums;

public enum ItemStatus {
    ACTIVE,         // Live and visible to others
    INACTIVE,       // User removed from marketplace (but not deleted)
    PENDING,        // Someone is negotiating/interested
    COMPLETED,      // Successfully traded/donated
}