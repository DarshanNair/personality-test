package com.darshan.database.room.injection

import android.content.Context
import androidx.room.Room
import com.darshan.database.room.PersonalityDatabase
import com.darshan.core.injection.qualifiers.ForApplication
import com.darshan.core.injection.scopes.PerApplication
import dagger.Module
import dagger.Provides

@Module
class PersonalityDatabaseModule {

    @Provides
    @PerApplication
    fun provideTitanDatabase(@ForApplication context: Context): PersonalityDatabase {
        return Room.databaseBuilder(
            context,
            PersonalityDatabase::class.java,
            "PersonalityDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}