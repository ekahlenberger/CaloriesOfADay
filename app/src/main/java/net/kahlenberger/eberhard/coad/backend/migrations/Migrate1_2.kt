package net.kahlenberger.eberhard.coad.backend.migrations

import androidx.room.DeleteColumn
import androidx.room.migration.AutoMigrationSpec

@DeleteColumn.Entries(
    DeleteColumn(
        tableName = "dishes",
        columnName = "parentId"
    )
)
class Migrate1_2 : AutoMigrationSpec