package com.example.groupdata

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val dao: GroupDao, val server: ServerConnection) {

    suspend fun getAll(): MutableList<SchoolData>{
        return withContext(Dispatchers.IO){
            return@withContext dao.getAll()}
    }
    suspend fun  getId(): Int{
        return withContext(Dispatchers.IO){
            return@withContext dao.getId()
        }
    }
    suspend fun getFromServer(): String? {
        return withContext(Dispatchers.IO){
            return@withContext server.getContent()
        }
    }
    suspend fun PostServer(schoolData: SchoolData){
        server.sendDataToServer(schoolData)
    }

    suspend fun upsertGroup(group: Group){
        dao.updateGroup(group)
    }
    suspend fun upsertStudent(list: MutableList<Student>){
        dao.updateStudent(list)
    }
    suspend fun deleteGroup(schoolData: SchoolData){
        dao.deleteStudents(schoolData.students)
        dao.deleteGroup(schoolData.group)
    }
    suspend fun deleteStudent(student: Student){
        dao.deleteStudent(student)
    }
}

