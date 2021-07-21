package com.darshan.personalitytest.core.database.room.injection

import android.content.Context
import androidx.room.Room
import com.darshan.personalitytest.core.database.room.PersonalityDatabase
import com.darshan.personalitytest.core.injection.qualifiers.ForApplication
import com.darshan.personalitytest.core.injection.scopes.PerApplication
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