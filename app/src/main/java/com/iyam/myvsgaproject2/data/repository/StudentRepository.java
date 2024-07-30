/* *    Hi, Code Enthusiast! *    https://github.com/yudiatmoko */package com.iyam.myvsgaproject2.data.repository;import androidx.lifecycle.LiveData;import com.iyam.myvsgaproject2.data.database.datasource.StudentDatabaseDataSource;import com.iyam.myvsgaproject2.data.database.entity.StudentEntity;import java.util.List;public class StudentRepository{    private StudentDatabaseDataSource dataSource;    public StudentRepository(StudentDatabaseDataSource dataSource) {        this.dataSource = dataSource;    }    public LiveData<List<StudentEntity>> getAllStudents() {        return dataSource.getAllStudents();    }    public void insertStudent(StudentEntity student) {        dataSource.insertStudent(student);    }    public void deleteStudent(StudentEntity student) {        dataSource.deleteStudent(student);    }    public void updateStudent(StudentEntity student) {        dataSource.updateStudent(student);    }    public void deleteAll() {        dataSource.deleteAll();    }}