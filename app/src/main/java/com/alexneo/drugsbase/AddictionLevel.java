package com.alexneo.drugsbase;


// Степени зависимости

import com.google.gson.annotations.SerializedName;

public enum AddictionLevel {
    @SerializedName
            ("0") None,
    @SerializedName
            ("1")Low,
    @SerializedName
            ("2")Medium,
    @SerializedName
            ("3")High
}
