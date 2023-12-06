package com.example.groupdata

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "GROUP")
data class Group(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "NAME_GROUP") var groupName: String,

    )

@Entity(tableName = "STUDENT")
data class Student(
    @PrimaryKey(autoGenerate = true) val studentId: Int = 0,
    @ColumnInfo(name = "GROUP_ID") var groupId: Int,
    @ColumnInfo(name = "NAME") var name: String,
    @ColumnInfo(name = "AGE") var age: String,
    @ColumnInfo(name = "GRADE") var grade: String
)

data class SchoolData(
    @Embedded val group: Group,
    @Relation(
        parentColumn = "id",
        entityColumn = "GROUP_ID"
    )
    val students: MutableList<Student>
)


