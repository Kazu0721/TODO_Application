package com.example.todoapplication

import android.content.Context
import androidx.room.Room
import com.example.todoapplication.model.RoomSingleton
import com.example.todoapplication.model.ToDoDao
import com.example.todoapplication.repository.Repository
import com.example.todoapplication.repository.RepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    @Singleton
    abstract fun bindToDoRepository(impl: RepositoryImplementation): Repository
}




@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideRoomSingleton(@ApplicationContext context: Context): RoomSingleton{
        return Room.databaseBuilder(context, RoomSingleton::class.java, "todo.db").build()
    }
    @Provides
    @Singleton
    fun provideToDoDao(db: RoomSingleton): ToDoDao {
        return db.todoDao()
    }
}