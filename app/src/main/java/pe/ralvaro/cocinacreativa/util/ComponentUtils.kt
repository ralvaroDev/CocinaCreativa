package pe.ralvaro.cocinacreativa.util

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import timber.log.Timber

fun ChipGroup.obtainCheckedChildTag(): List<String> {
    val mutableTagList = mutableListOf<String>()
    for (index in 0 until this.childCount) {
        val chip: Chip = this.getChildAt(index) as Chip
        if (chip.isChecked)
            mutableTagList.add(chip.tag as String)
    }
    return mutableTagList.toList()
}

fun ChipGroup.applyCheckedOnAll(isChecked: Boolean) {
    for (index in 0 until this.childCount) {
        val chip: Chip = this.getChildAt(index) as Chip
        chip.isChecked = isChecked
    }
}

fun ChipGroup.applyCheckToSome(tagList: List<String>) {
    tagList.forEach {
        findViewWithTag<Chip>(it)?.isChecked = true
    }
}

