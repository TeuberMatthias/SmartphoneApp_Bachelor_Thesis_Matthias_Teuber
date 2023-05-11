package com.example.pininterface.enums

import androidx.annotation.StringRes
import com.example.pininterface.R

enum class EnumInterfaceTypes (@StringRes val stringResId: Int) {

    STANDARD(R.string.interface_types_standard),
    COLUMN(R.string.interface_types_column),
    STANDARD_VIS(R.string.interface_types_standard_vis_aid),
    COLUMN_VIS(R.string.interface_types_column_vis_aid),
    NONE(R.string.interface_types_none),
}