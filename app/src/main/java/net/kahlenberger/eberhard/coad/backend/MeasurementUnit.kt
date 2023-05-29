package net.kahlenberger.eberhard.coad.backend

import net.kahlenberger.eberhard.coad.R

enum class MeasurementUnit {
    Grams,
    Kilograms,
    Liters,
    Milliliters,
    Pints,
    Quarts,
    Gallons,
    Pounds,
    Ounces,
    Drams,
    Pieces,
    Portions,
    Teaspoons,
    Tablespoons,
    Cups,
    Pinches,
    Dashes,

    // Add more units as needed

}
fun MeasurementUnit.getResourceId(): Int {
    return when (this) {
        MeasurementUnit.Grams -> R.string.grams
        MeasurementUnit.Kilograms -> R.string.kilograms
        MeasurementUnit.Liters -> R.string.liters
        MeasurementUnit.Milliliters -> R.string.milliliters
        MeasurementUnit.Pints -> R.string.pints
        MeasurementUnit.Quarts -> R.string.quarts
        MeasurementUnit.Gallons -> R.string.gallons
        MeasurementUnit.Pounds -> R.string.pounds
        MeasurementUnit.Ounces -> R.string.ounces
        MeasurementUnit.Drams -> R.string.drams
        MeasurementUnit.Pieces -> R.string.pieces
        MeasurementUnit.Portions -> R.string.portions
        MeasurementUnit.Teaspoons -> R.string.teaspoons
        MeasurementUnit.Tablespoons -> R.string.tablespoons
        MeasurementUnit.Cups -> R.string.cups
        MeasurementUnit.Pinches -> R.string.pinches
        MeasurementUnit.Dashes -> R.string.dashes
    }
}
fun MeasurementUnit.toLocalizedShortUnitStringId(): Int {
    return when (this) {
        MeasurementUnit.Grams -> R.string.shortGrams
        MeasurementUnit.Kilograms -> R.string.shortKilograms
        MeasurementUnit.Liters -> R.string.shortLiters
        MeasurementUnit.Milliliters -> R.string.shortMilliliters
        MeasurementUnit.Pints -> R.string.shortPints
        MeasurementUnit.Quarts -> R.string.shortQuarts
        MeasurementUnit.Gallons -> R.string.shortGallons
        MeasurementUnit.Pounds -> R.string.shortPounds
        MeasurementUnit.Ounces -> R.string.shortOunces
        MeasurementUnit.Drams -> R.string.shortDrams
        MeasurementUnit.Pieces -> R.string.shortPieces
        MeasurementUnit.Portions -> R.string.shortPortions
        MeasurementUnit.Teaspoons -> R.string.shortTeaspoons
        MeasurementUnit.Tablespoons -> R.string.shortTablespoons
        MeasurementUnit.Cups -> R.string.cups
        MeasurementUnit.Pinches -> R.string.pinches
        MeasurementUnit.Dashes -> R.string.dashes
    }
}

