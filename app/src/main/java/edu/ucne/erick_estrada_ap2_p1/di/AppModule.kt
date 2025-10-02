package edu.ucne.erick_estrada_ap2_p1.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.erick_estrada_ap2_p1.data.database.HuacalDb
import edu.ucne.erick_estrada_ap2_p1.data.huacal.HuacalRepositoryImpl
import edu.ucne.erick_estrada_ap2_p1.domain.repository.HuacalRepository
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.eliminarHuacalUseCase
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.guardarHuacalUseCase
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.huacalesUseCase
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.obtenerHuacalUseCase
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.obtenerHuacalesUseCase
import edu.ucne.erick_estrada_ap2_p1.domain.useCases.validarHuacalUseCase
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesdatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            HuacalDb::class.java,
            "database"
        ).fallbackToDestructiveMigration()
            .build()


    @Provides
    fun providesHuacalDao(database: HuacalDb) = database.HuacalDao()

    @Provides
    fun provideHuacalUseCases(repository: HuacalRepository): huacalesUseCase {
        val validarHuacal = validarHuacalUseCase(repository)
        return huacalesUseCase(
            guardarHuacal = guardarHuacalUseCase(repository,validarHuacal),
            eliminarHuacal = eliminarHuacalUseCase(repository),
            obtenerHuacal = obtenerHuacalUseCase (repository),
            obtenerHuacales = obtenerHuacalesUseCase(repository),
            validarHuacal = validarHuacalUseCase(repository)
        )}
    @Module
    @InstallIn(SingletonComponent::class)

    abstract class RepositoryModule {

        @Binds
        @Singleton
        abstract fun bindHuacalRepository(
            impl: HuacalRepositoryImpl
        ): HuacalRepository
    }
}