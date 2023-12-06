package com.example.groupdata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainViewModel(private val repository: Repository) : ViewModel() {

    val currentGroup = MutableLiveData<SchoolData>()
    val tempLiveData = MutableLiveData<MutableList<SchoolData>>()


    val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun getListSchoolDataFromLocalDB(){
        viewModelScope.launch {
            tempLiveData.value = repository.getAll()
        }
    }
    fun getListSchoolDataFromServer(){
        viewModelScope.launch {
            val s = repository.getFromServer()
            val gson = Gson()
            val listType = object : TypeToken<MutableList<SchoolData>>() {}.type
            val list: MutableList<SchoolData> = gson.fromJson(s,listType)
            tempLiveData.value = list
        }
    }
    fun PostServer(schoolData: SchoolData){
        viewModelScope.launch {
            repository.PostServer(schoolData)
        }
    }

    fun createNewGroup(onGroupCreated: () -> Unit){
        viewModelScope.launch {
            val group = Group(groupName = "new")
            repository.upsertGroup(group)
            val groupId = repository.getId()
            group.id = groupId
            currentGroup.value = SchoolData(group,  mutableListOf())
            onGroupCreated.invoke()
        }
    }
    fun createNewStudent(){
        currentGroup.value!!.students.add(Student(groupId = currentGroup.value!!.group.id, name = "Ivan", age = "18", grade = "A+"))
    }
    fun saveAll(){
        viewModelScope.launch {
            repository.upsertGroup(currentGroup.value!!.group)
            repository.upsertStudent(currentGroup.value!!.students)
            tempLiveData.value!!.add(currentGroup.value!!)
            repository.PostServer(currentGroup.value!!)
        }
    }
    fun setCurrentItem(schoolData: SchoolData){
        currentGroup.value = schoolData
    }
    fun setNameGroup(name: String){
        currentGroup.value!!.group.groupName = name
    }
    fun setNameStudent(name: String, index: Int){
        currentGroup.value!!.students.get(index).name = name
    }
    fun setAgeStudent(age: String, index: Int){
        currentGroup.value!!.students.get(index).age = age
    }
    fun setGradeStudent(grade: String, index: Int){
        currentGroup.value!!.students.get(index).grade = grade
    }
    fun deleteGroup(schoolData: SchoolData, index: Int){
        viewModelScope.launch{
            repository.deleteGroup(schoolData)
            tempLiveData.value!!.removeAt(index)
        }
    }
    fun deleteStudent(index: Int){
        viewModelScope.launch {
            repository.deleteStudent(currentGroup.value!!.students.get(index))
            currentGroup.value!!.students.removeAt(index)
        }

    }
    fun Push(){
        val gson = Gson()
        val s = gson.toJson(tempLiveData.value)
        println(s)
    }

}


