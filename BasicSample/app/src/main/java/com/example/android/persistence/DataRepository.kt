package com.example.android.persistence

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData

import com.example.android.persistence.db.AppDatabase
import com.example.android.persistence.db.entity.ProductEntity

/**
 * Repository handling the work with products and comments.
 */
class DataRepository private constructor(private val mDatabase: AppDatabase) {
    private val mObservableProducts: MediatorLiveData<List<ProductEntity>>

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    val products: LiveData<List<ProductEntity>>
        get() = mObservableProducts

    init {
        mObservableProducts = MediatorLiveData()

        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts()
        ) { productEntities ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableProducts.postValue(productEntities)
            }
        }
    }

    fun loadProduct(productId: Int): LiveData<ProductEntity> {
        return mDatabase.productDao().loadProduct(productId)
    }

    companion object {

        private var Instance: DataRepository? = null

        fun getInstance(database: AppDatabase): DataRepository? {
            if (Instance == null) {
                synchronized(DataRepository::class.java) {
                    if (Instance == null) {
                        Instance = DataRepository(database)
                    }
                }
            }
            return Instance
        }
    }
}
