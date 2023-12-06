package com.example.groupdata

import androidx.room.*

@Dao
interface GroupDao {

    @Query("SELECT * FROM student WHERE GROUP_ID = :groupId")
    fun getAllStudentFromGroup(groupId: Int): MutableList<Student>

    @Query("SELECT * FROM `GROUP`")
    fun getAllGroup(): List<Group>

    @Transaction
    @Query("SELECT * FROM `GROUP`")
    fun getAll(): MutableList<SchoolData>

    @Query("SELECT MAX(id) FROM `GROUP`")
    fun getId(): Int

    @Upsert
    suspend fun updateGroup(group: Group)

    @Upsert
    suspend fun updateStudent(students: MutableList<Student>)

    @Delete
    suspend fun deleteGroup(group: Group)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Delete
    suspend fun deleteStudents(students: MutableList<Student>)

}