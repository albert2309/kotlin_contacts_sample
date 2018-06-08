package com.example.android.persistence.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.support.annotation.VisibleForTesting

import com.example.android.persistence.AppExecutors
import com.example.android.persistence.db.converter.DateConverter
import com.example.android.persistence.db.entity.ProductEntity.IDao
import com.example.android.persistence.db.entity.ProductEntity

@Database(entities = [(ProductEntity::class)], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    private val mIsDatabaseCreated = MutableLiveData<Boolean>()

    val databaseCreated: LiveData<Boolean>
        get() = mIsDatabaseCreated

    abstract fun productDao(): IDao

    /**
     * Check whether the database already exists and expose it via [.getDatabaseCreated]
     */
    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    companion object {

        private var Instance: AppDatabase? = null

        @VisibleForTesting
        val DATABASE_NAME = "basic-sample-db"

        fun getInstance(context: Context, executors: AppExecutors): AppDatabase? {
            if (Instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (Instance == null) {
                        Instance = buildDatabase(context.applicationContext, executors)
                        Instance!!.updateDatabaseCreated(context.applicationContext)
                    }
                }
            }
            return Instance
        }

        /**
         * Build the database. [Builder.build] only sets up the database configuration and
         * creates a new instance of the database.
         * The SQLite database is only created when it's accessed for the first time.
         */
        private fun buildDatabase(appContext: Context,
                                  executors: AppExecutors): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            executors.diskIO().execute {
                                // Add a delay to simulate a long-running operation
                                addDelay()
                                // Generate the data for pre-population
                                val database = AppDatabase.getInstance(appContext, executors)
                                val products = DataGenerator.generateProducts()

                                insertData(database, products)
                                // notify that the database was created and it's ready to be used
                                database?.setDatabaseCreated()
                            }
                        }
                    }).build()
        }

        private fun insertData(database: AppDatabase?, products: List<ProductEntity>) {
            database?.runInTransaction { database.productDao().insertAll(products) }
        }

        private fun addDelay() {
            try {
                Thread.sleep(4000)
            } catch (ignored: InterruptedException) {
            }

        }
    }
}
